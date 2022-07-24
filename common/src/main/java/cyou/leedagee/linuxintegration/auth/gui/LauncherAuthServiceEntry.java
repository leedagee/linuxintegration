package cyou.leedagee.linuxintegration.auth.gui;

import cyou.leedagee.linuxintegration.auth.CredentialContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class LauncherAuthServiceEntry extends AbstractAuthServiceEntry {
    public LauncherAuthServiceEntry(AuthServiceList parent) {
        super(parent, "Launcher Provided");
    }

    @Override
    public Text getNarration() {
        return new LiteralText(
                "Launcher provided authentication entry." +
                        "Username " + this.toCredentialContext(true).session.getUsername());
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public CredentialContext toCredentialContext(boolean finished) {
        return CredentialContext.getLauncherProvided();
    }

    @Override
    public String getServiceFriendlyName() {
        return "mojang";
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    }
}
