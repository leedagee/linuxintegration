package cyou.leedagee.linuxintegration.auth.gui;

import cyou.leedagee.linuxintegration.auth.CredentialContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

public abstract class AbstractAuthServiceEntry extends AlwaysSelectedEntryListWidget.Entry<AbstractAuthServiceEntry> {
    private final String name;
    private final AuthServiceList parent;

    protected AbstractAuthServiceEntry(AuthServiceList parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public abstract boolean isReady();

    public abstract CredentialContext toCredentialContext(boolean finished);

    public abstract String getServiceFriendlyName();

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        CredentialContext context = this.toCredentialContext(false);
        textRenderer.draw(matrices, this.getName(), x + 10, y + 10, 0xFFFFFF);
        int xx = textRenderer.draw(matrices, context.session.getUsername(), x + 10, y + 30, 0xAAAAAA);
        xx = textRenderer.draw(matrices, "@", xx, y + 30, 0x444444);
        xx = textRenderer.draw(matrices, this.getServiceFriendlyName(), xx, y + 30, 0x999999);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.parent.setSelected(this);
        return false;
    }
}
