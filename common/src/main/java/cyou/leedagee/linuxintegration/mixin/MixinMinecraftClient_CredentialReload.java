package cyou.leedagee.linuxintegration.mixin;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import cyou.leedagee.linuxintegration.auth.CredentialUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient_CredentialReload implements CredentialUtil.MinecraftClientAccessor {
    @Shadow
    private UserApiService userApiService;

    @Shadow
    private MinecraftSessionService sessionService;

    @Shadow private Session session;

    @Shadow private PlayerSkinProvider skinProvider;

    private File li_assetsDir;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void saveAssetsDir(RunArgs args, CallbackInfo ci) {
        this.li_assetsDir = args.directories.assetDir;
    }

    @Override
    public void li_setUserApiService(UserApiService service) {
        this.userApiService = service;
    }

    @Override
    public void li_setSessionService(MinecraftSessionService service) {
        this.sessionService = service;
    }

    @Override
    public void li_setSession(Session session) {
        this.session = session;
    }

    @Override
    public void li_setSkinProvider(PlayerSkinProvider provider) {
        this.skinProvider = provider;
    }

    @Override
    public File li_getAssetsDir() {
        return li_assetsDir;
    }
}
