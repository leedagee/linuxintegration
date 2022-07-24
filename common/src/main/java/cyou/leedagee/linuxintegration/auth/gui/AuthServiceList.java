package cyou.leedagee.linuxintegration.auth.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import org.freedesktop.dbus.DBusPath;

public class AuthServiceList extends AlwaysSelectedEntryListWidget<AbstractAuthServiceEntry> {
    public AuthServiceList(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
        this.addEntry(new LauncherAuthServiceEntry(this));
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.right - 6;
    }

    @Override
    public int getRowWidth() {
        return 176;
    }

    public void addSecretServiceEntry(SecretServiceWrapper wrapper, DBusPath path) {
        this.addEntry(new SecretServiceAuthEntry(this, wrapper, path));
    }
}
