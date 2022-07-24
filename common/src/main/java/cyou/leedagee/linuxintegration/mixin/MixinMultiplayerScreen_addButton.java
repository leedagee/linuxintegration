package cyou.leedagee.linuxintegration.mixin;

import cyou.leedagee.linuxintegration.auth.gui.AuthSelectionScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MixinMultiplayerScreen_addButton extends Screen {
    protected MixinMultiplayerScreen_addButton(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void addAuthSelectionScreen(CallbackInfo ci) {
        this.addDrawableChild(
                new ButtonWidget(
                        10, 10, 70, 20,
                        new LiteralText("Select Auth Service"),
                        (button) -> this.client.setScreen(new AuthSelectionScreen(this))
                )
        );
    }
}
