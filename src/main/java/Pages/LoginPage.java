package Pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    // 1. String Locators - OR
    String pageTitle = "text=Connexion";
    String emailUser = "id=email_login";
    String password = "id=password_login";
    String clickLogin = "id=btn_login";
    String confirmationLogin = "id=style_content_logo__pkvMP";
    String failedLogin = "text=Email ou mot de passe incorrect";
    String fail_email = "text=Le format de l'email est invalid";

    // 2. page constructor:
    public LoginPage(Page page) {
        this.page = page;
    }

    // 3. page actions/methods:
    public String getLoginPageTitle() {
        String title =  page.textContent(pageTitle);
        System.out.println("page title: " + title);
        return title;
    }

    public boolean isForgotPwdLinkExist() {
        return page.isVisible(failedLogin);
    }

    public boolean doLogin(String appUserName, String appPassword) {
        System.out.println("App creds: " + appUserName + ":" + appPassword);
        page.fill(emailUser, appUserName);
        page.fill(password, appPassword);
        page.click(clickLogin);
        page.locator(confirmationLogin).waitFor();
        if(page.locator(confirmationLogin).isVisible()) {
            System.out.println("user is logged in successfully....");
            return true;
        }else {
            System.out.println("user is not logged in successfully....");
            return false;
        }
    }
}
