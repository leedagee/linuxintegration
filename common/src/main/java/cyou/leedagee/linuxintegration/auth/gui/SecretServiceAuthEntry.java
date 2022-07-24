package cyou.leedagee.linuxintegration.auth.gui;

import com.mojang.authlib.Environment;
import com.mojang.authlib.EnvironmentParser;
import cyou.leedagee.linuxintegration.LinuxIntegration;
import cyou.leedagee.linuxintegration.auth.CredentialContext;
import net.minecraft.client.util.Session;
import net.minecraft.text.Text;
import org.checkerframework.checker.units.qual.C;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.Properties;
import org.freedesktop.secret.Item;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SecretServiceAuthEntry extends AbstractAuthServiceEntry {
    private final Map<String, String> attributes;
    private CompletableFuture<String> accessTokenFuture;
    private boolean ready;
    private String accessToken;

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public CredentialContext toCredentialContext(boolean finished) {
        if (!ready && finished) {
            throw new IllegalArgumentException("Credential not ready!");
        }
        return new CredentialContext(
                createAuthlibInjectorEnvironment(this.attributes.get("minecraft_yggdrasil_api_base")),
                this.getSession()
        );
    }

    @Override
    public String getServiceFriendlyName() {
        return this.attributes.get("minecraft_yggdrasil_api_base");
    }

    @Override
    public String getName() {
        return this.attributes.get("Title");
    }

    public SecretServiceAuthEntry(AuthServiceList parent, SecretServiceWrapper wrapper, DBusPath path) {
        super(parent, null);
        Properties properties;
        try {
            properties = wrapper.getObject(path.getPath(), org.freedesktop.dbus.interfaces.Properties.class);
            this.attributes = properties.Get("org.freedesktop.Secret.Item", "Attributes");
        } catch (DBusException e) {
            throw new RuntimeException(e);
        }
        LinuxIntegration.LOGGER.warn(this.attributes);
        LinuxIntegration.LOGGER.warn(this.attributes.get("minecraft_uuid"));
        accessTokenFuture = wrapper.getAccessToken(path);
    }

    public Session getSession() {
        return new Session(
                this.attributes.get("UserName"),
                this.attributes.get("minecraft_uuid"),
                getAccessToken(),
                Optional.empty(), Optional.empty(), Session.AccountType.MOJANG
        );
    }

    public String getAccessToken() {
        if (this.ready) {
            return this.accessToken;
        }
        if (accessTokenFuture.isDone()) {
            this.ready = true;
            this.accessToken = accessTokenFuture.getNow("INVALID");
        }
        return "INVALID";
    }

    private static Environment createAuthlibInjectorEnvironment(String apiBase) {
        return Environment.create(
                apiBase + "/authserver",
                apiBase + "/api",
                apiBase + "/sessionserver",
                apiBase + "/minecraftservices",
                apiBase
        );
    }

    @Override
    public Text getNarration() {
        return null;
    }
}
