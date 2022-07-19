package cyou.leedagee.linuxintegration;

import cyou.leedagee.linuxintegration.auth.CredentialContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinuxIntegration {
    public static final String MOD_ID = "linuxintegration";
    public static Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        CredentialContext.init();
    }
}