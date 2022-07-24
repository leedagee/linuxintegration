package cyou.leedagee.linuxintegration.auth.gui;

import cyou.leedagee.linuxintegration.auth.CredentialUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.freedesktop.dbus.DBusPath;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AuthSelectionScreen extends Screen {
    public static final int ENTRY_HEIGHT = 50;
    private final Screen parent;
    public AuthServiceList list;
    private SecretServiceWrapper secretService;
    private CompletableFuture<Boolean> secretServiceInitFuture;
    private boolean secretServiceEntriesAdded = false;
    private ButtonWidget applyButton;

    public AuthSelectionScreen(Screen parent) {
        super(new LiteralText("AuthSelection"));
        this.parent = parent;
    }

    @Override
    public void init() {
        this.list = new AuthServiceList(
                MinecraftClient.getInstance(),
                170, this.height - 40, 20, this.height - 20,
                ENTRY_HEIGHT
        );
        this.list.setLeftPos(20);
        this.addDrawableChild(this.list);
        this.secretService = new SecretServiceWrapper();
        this.secretServiceInitFuture = this.secretService.init();
        this.applyButton = new ButtonWidget(200, 20, 50, 20, new LiteralText("Apply"), (btn) -> {
            CredentialUtil.reloadCredentialGlobally(this.list.getSelectedOrNull().toCredentialContext(true));
        });
        this.addDrawableChild(this.applyButton);
    }

    @Override
    public void close() {
        this.secretService.close();
        this.client.setScreen(this.parent);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        if (this.secretServiceInitFuture.isDone() && this.secretServiceInitFuture.getNow(false) && !this.secretServiceEntriesAdded)
        {
            this.secretServiceEntriesAdded = true;
            this.secretService.searchResult.getLocked().stream()
                    .forEach((path) -> this.list.addSecretServiceEntry(this.secretService, path));
            this.secretService.searchResult.getUnlocked().stream()
                    .forEach((path) -> this.list.addSecretServiceEntry(this.secretService, path));
        }
        if (this.secretServiceInitFuture.isDone()) {
            int xx = textRenderer.draw(matrices, "future completed, ", 10, 10, 0xDDDDDD);
            try {
                textRenderer.draw(matrices, this.secretServiceInitFuture.get() ? "success" : "failed", xx, 10, 0xFF4488);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        } else {
            textRenderer.draw(matrices, "future not completed", 10, 10, 0xFFFFFF);
        }
    }
}
