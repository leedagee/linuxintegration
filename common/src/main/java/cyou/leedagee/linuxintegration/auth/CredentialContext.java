package cyou.leedagee.linuxintegration.auth;

import com.mojang.authlib.Environment;
import com.mojang.authlib.EnvironmentParser;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

public class CredentialContext {
    public final Environment environment;
    public final Session session;

    private static CredentialContext LAUNCHER;

    public CredentialContext(Environment environment, Session session) {
        this.environment = environment;
        this.session = session;
    }

    public static void init() {
        LAUNCHER = new CredentialContext(
                EnvironmentParser
                        .getEnvironmentFromProperties()
                        .orElse(YggdrasilEnvironment.PROD.getEnvironment()),
                MinecraftClient.getInstance().getSession()
        );
    }

    public static CredentialContext getLauncherProvided() {
        return LAUNCHER;
    }
}
