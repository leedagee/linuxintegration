package cyou.leedagee.linuxintegration.fabric;

import cyou.leedagee.linuxintegration.LinuxIntegration;
import net.fabricmc.api.ModInitializer;

public class LinuxIntegrationFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LinuxIntegration.init();
    }
}