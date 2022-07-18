package cyou.leedagee.linuxintegration.forge;

import dev.architectury.platform.forge.EventBuses;
import cyou.leedagee.linuxintegration.LinuxIntegration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LinuxIntegration.MOD_ID)
public class LinuxIntegrationForge {
    public LinuxIntegrationForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(LinuxIntegration.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        LinuxIntegration.init();
    }
}