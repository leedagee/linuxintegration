package cyou.leedagee.linuxintegration.auth;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import cyou.leedagee.linuxintegration.LinuxIntegration;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.util.Session;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.UserCache;
import org.lwjgl.glfw.GLFWNativeX11;

import java.io.File;

public class CredentialUtil {
    public static void reloadCredentialGlobally(CredentialContext ctx) {
        LinuxIntegration.LOGGER.warn("reloadCredentialGlobally");
        LinuxIntegration.LOGGER.warn("{} {} {}", ctx.session.getUsername(), ctx.session.getUuid(), ctx.session.getAccessToken());
        LinuxIntegration.LOGGER.warn(ctx.environment);
        MinecraftClient mc = MinecraftClient.getInstance();
        MinecraftClientAccessor mca = (MinecraftClientAccessor) mc;
        YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(mc.getNetworkProxy(), ctx.environment);

        MinecraftSessionService sessionService = authService.createMinecraftSessionService();
        mca.li_setSessionService(sessionService);

        try {
            mca.li_setUserApiService(authService.createUserApiService(ctx.session.getAccessToken()));
        }
        catch (AuthenticationException authenticationException) {
            LinuxIntegration.LOGGER.error("Failed to verify authentication", authenticationException);
            mca.li_setUserApiService(UserApiService.OFFLINE);
        }

        GameProfileRepository profileRepo = authService.createProfileRepository();
        UserCache userCache = new UserCache(profileRepo, new File(mc.runDirectory, MinecraftServer.USER_CACHE_FILE.getName()));
        userCache.setExecutor(mc);
        SkullBlockEntity.setServices(userCache, sessionService, mc);
        UserCache.setUseRemote(false);

        mca.li_setSession(ctx.session);

        PlayerSkinProvider skinProvider = new PlayerSkinProvider(
                mc.getTextureManager(),
                new File(mca.li_getAssetsDir(), "skins"),
                sessionService);
        mca.li_setSkinProvider(skinProvider);
    }

    public static long getWindowID() {
        long glfwWindowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFWNativeX11.glfwGetX11Window(glfwWindowHandle);
    }

    public interface MinecraftClientAccessor {
        void li_setUserApiService(UserApiService service);

        void li_setSessionService(MinecraftSessionService service);

        void li_setSession(Session session);

        void li_setSkinProvider(PlayerSkinProvider provider);

        File li_getAssetsDir();
    }
}
