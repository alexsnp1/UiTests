package ui.iteration2_senior;

import api.iteration2_senior.configs.Config;
import api.iteration2_senior.tests.BaseApiTest;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import common.extensions.BrowserMatchExtension;
import common.extensions.UserSessionExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

@ExtendWith(UserSessionExtension.class)
@ExtendWith(BrowserMatchExtension.class)
public class BaseUiTest extends BaseApiTest {
    @BeforeAll
    public static void setupSelenoid() {
        Configuration.remote = Config.getProperty("uiRemote");
        Configuration.baseUrl = Config.getProperty("uiBaseUrl");
        Configuration.browser = Config.getProperty("browser");
        Configuration.browserSize = Config.getProperty("browserSize");

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true, "enableLog", true)
        );
    }
}
