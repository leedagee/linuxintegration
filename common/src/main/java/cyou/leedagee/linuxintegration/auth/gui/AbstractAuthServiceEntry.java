package cyou.leedagee.linuxintegration.auth.gui;

import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import cyou.leedagee.linuxintegration.auth.CredentialContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.Session;
import org.freedesktop.dbus.DBusPath;

import java.util.Optional;

public abstract class AbstractAuthServiceEntry extends AlwaysSelectedEntryListWidget.Entry<AbstractAuthServiceEntry> {
    private final String entryName;
    private final String playerUUID;
    private final String username;
    private boolean ready;
    private String accessToken;

    public AbstractAuthServiceEntry(String entryName, String playerUUID, String username, DBusPath objectPath) {
        this.entryName = entryName;
        this.playerUUID = playerUUID;
        this.username = username;
        this.ready = true;
    }

    public Optional<CredentialContext> toCredentialContext() {
        return this.ready ? Optional.of(
                new CredentialContext(
                        YggdrasilEnvironment.PROD.getEnvironment(),
                        new Session(username, playerUUID, accessToken, Optional.empty(), Optional.empty(), Session.AccountType.MOJANG)
                )
        ) : Optional.empty();
    }

    public String getName() {
        return this.entryName;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }
}
