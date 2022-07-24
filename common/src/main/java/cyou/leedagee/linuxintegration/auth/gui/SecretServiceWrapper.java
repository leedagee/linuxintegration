package cyou.leedagee.linuxintegration.auth.gui;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Bytes;
import cyou.leedagee.linuxintegration.LinuxIntegration;
import cyou.leedagee.linuxintegration.auth.CredentialUtil;
import net.minecraft.util.Util;
import org.freedesktop.dbus.DBusMatchRule;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.types.Variant;
import org.freedesktop.secret.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SecretServiceWrapper {
    public static String BUS_NAME = "org.freedesktop.secrets";
    public Status status;
    private DBusConnection dBusConnection;
    private Service service;
    private Session session;
    public SearchItemsTuple searchResult;
    private DBusPath sessionPath;

    public SecretServiceWrapper() {
        this.status = Status.NO_CONNECTION;
    }

    private boolean connectDBusDaemon() {
        if (this.status != Status.NO_CONNECTION)
            return false;
        LinuxIntegration.LOGGER.info("connectDBusDaemon: starting");
        try {
            this.dBusConnection = DBusConnectionBuilder.forSessionBus().build();
        } catch (DBusException e) {
            LinuxIntegration.LOGGER.error(e);
            this.status = Status.FAILED;
            return false;
        }
        LinuxIntegration.LOGGER.info("connectDBusDaemon: success");
        this.status = Status.CONNECTED_DBUS_DAEMON;
        return true;
    }

    private boolean connectSecretService() {
        if (this.status != Status.CONNECTED_DBUS_DAEMON)
            return false;
        LinuxIntegration.LOGGER.info("connectSecretService: starting");
        try {
            this.service = this.getObject(
                    "/org/freedesktop/secrets",
                    Service.class);
            this.dBusConnection.callMethodAsync(service, "OpenSession", "plain", new Variant(""));
            OpenSessionTuple tuple = service.OpenSession("plain", new Variant(""));
            this.sessionPath = tuple.getResult();
            this.session = this.getObject(this.sessionPath.getPath(), Session.class);
        } catch (DBusException e) {
            LinuxIntegration.LOGGER.error(e);
            this.status = Status.FAILED;
            return false;
        }
        this.status = Status.CONNECTED_SECRET_SERVICE;
        LinuxIntegration.LOGGER.info("connectSecretService: success");
        return true;
    }

    private boolean searchItems() {
        if (this.status != Status.CONNECTED_SECRET_SERVICE) {
            return false;
        }
        Map<String, String> search = new HashMap<>();
        search.put("minecraft_authentication", "true");
        this.searchResult = this.service.SearchItems(search);
        this.status = Status.READY;
        LinuxIntegration.LOGGER.info("searchItems: got {} locked and {} unlocked",
                this.searchResult.getLocked().size(), this.searchResult.getUnlocked().size());
        return true;
    }

    private boolean unlockItems() {
        if (this.status != Status.READY) {
            return false;
        }
        UnlockTuple tuple = service.Unlock(this.searchResult.getLocked());
        if (tuple.getUnlocked().isEmpty()) {
            DBusPath promptPath = tuple.getPrompt();
            try {
                Prompt prompt = this.getObject(promptPath.getPath(), Prompt.class);
                CompletableFuture<Boolean> future = new CompletableFuture<>();
                this.dBusConnection.addSigHandler(new DBusMatchRule(Prompt.Completed.class), (signal) -> future.complete(true));
                prompt.doPrompt(String.valueOf(CredentialUtil.getWindowID()));
                future.join();
            } catch (DBusException e) {
                LinuxIntegration.LOGGER.error(e);
            }
        }

        return true;
    }

    public CompletableFuture<Boolean> init() {
        CompletableFuture<Boolean> ret = new CompletableFuture<>();
        Util.getIoWorkerExecutor().submit(
                () -> ret.complete(this.connectDBusDaemon() && this.connectSecretService() && this.searchItems() && this.unlockItems())
        );
        return ret;
    }

    public CompletableFuture<String> getAccessToken(DBusPath path) {
        CompletableFuture<String> ret = new CompletableFuture<>();
        Util.getIoWorkerExecutor().submit(() -> {
            try {
                Item item = this.getObject(path.getPath(), Item.class);
                Secret secret = item.GetSecret(this.sessionPath);
                ret.complete(new String(Bytes.toArray(secret.getMember2()), StandardCharsets.UTF_8));
            } catch (DBusException e) {
                LinuxIntegration.LOGGER.error(e);
                ret.complete(null);
            }
        });
        return ret;
    }

    public void close() {
        Util.getIoWorkerExecutor().submit(() -> {
            this.session.Close();
            try {
                this.dBusConnection.close();
            } catch (IOException e) {
                LinuxIntegration.LOGGER.error(e);
            }
        });
        this.status = Status.CLOSED;
    }

    public <T extends DBusInterface> T getObject(String path, Class<T> clazz) throws DBusException {
        return this.dBusConnection.getRemoteObject(BUS_NAME, path, clazz);
    }

    enum Status {
        NO_CONNECTION,
        CONNECTED_DBUS_DAEMON,
        CONNECTED_SECRET_SERVICE,
        READY,
        FAILED,
        CLOSED,
    }
}
