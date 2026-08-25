package ui.iteration2_senior;

import api.iteration2_senior.requests.steps.CustomerProfileStep;
import api.iteration2_senior.utils.RandomData;
import com.codeborne.selenide.Selenide;
import common.annotations.UserSession;
import common.data.UserSessionData;
import common.data.UserSessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.middle.pages.BankAlert;
import ui.middle.pages.DashboardPage;
import ui.middle.pages.EditProfilePage;

@UserSession()
public class ChangingTheName extends BaseUiTest {
    private UserSessionData user;
    private final String validName = RandomData.getRandomValidName();
    private final String invalidName = RandomData.getRandomInvalidName();
    private DashboardPage dashboardPage = new DashboardPage();
    private EditProfilePage editProfilePage = new EditProfilePage();

    @BeforeEach
    public void prepareTestData(UserSessions users) {
        user = users.get(0);
    }

    @Test
    public void userCanRenameThemselves() {
        dashboardPage.open().pressProfileHeader().getPage(EditProfilePage.class).shouldHaveEditProfileHeader()
                .changeName(validName).checkAlertMessageAndAccept(BankAlert.NAME_UPDATED_SUCCESSFULLY.getMessage())
                .shouldHaveEditProfileHeader();
        Selenide.refresh();
        editProfilePage.nameShouldBeVisible(validName);
        softly.assertThat(CustomerProfileStep.getCustomerProfileResponse(user.getAuthToken()).getName()).isEqualTo(validName);
    }

    @Test
    public void userCannotRenameThemselvesUsingIncorrectName() {
        dashboardPage.open().pressProfileHeader();
        String name = editProfilePage.getNameOfUser();
        editProfilePage.shouldHaveEditProfileHeader().changeName(invalidName)
                .checkAlertMessageAndAccept(BankAlert.NAME_MUST_CONTAIN_TWO_WORDS_WITH_LETTERS_ONLY.getMessage())
                .shouldHaveEditProfileHeader();
        Selenide.refresh();
        editProfilePage.nameShouldBeVisible(name);
        softly.assertThat(CustomerProfileStep.getCustomerProfileResponse(user.getAuthToken()).getName()).isNull();
    }
}
