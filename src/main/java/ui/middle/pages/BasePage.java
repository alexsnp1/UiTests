package ui.middle.pages;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class BasePage<T extends BasePage> {
    private SelenideElement accountDropDown = $(".form-control.account-selector");
    protected SelenideElement nameOfUser = $(".user-name");


    public abstract String url();

    public T open() {
        return Selenide.open(url(), (Class<T>) this.getClass());
    }

    public <T extends BasePage> T getPage(Class<T> pageClass) {
        return Selenide.page(pageClass);
    }

    public T checkAlertMessageAndAccept(String expectedMessage) {
        Alert alert = switchTo().alert();
        assertThat(alert.getText().contains(expectedMessage)).isTrue();
        return (T) this;
    }

    public T selectAccount(int userId) {
        accountDropDown.selectOptionByValue(String.valueOf(userId));
        return (T) this;
    }

    public double getSelectedAccountBalance() {
        return Double.parseDouble(
                accountDropDown
                        .getSelectedOption()
                        .getText()
                        .replaceAll(".*\\$([0-9.]+).*", "$1"));
    }

    public static void authAsUser(String authTokenUser) {
        Selenide.open("/");
        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", authTokenUser);
    }
}
