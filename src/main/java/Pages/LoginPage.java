package Pages;


import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import org.testng.Assert;

public class LoginPage {

    Page page;

// Locator — — — -

    String email = "id=email_login";
    String password = "id=password_login";
    String clickLogin = "id=btn_login";

    String loginProof = "text=Connexion";




// initialize Page using constructor

    public LoginPage(Page page) {
        this.page = page;

    }

    public String verifyTitle() {
        String title = page.title();
        return title;
    }

//Create methods

// Login into the application

    public void loginIntoApplication(String email, String pass) {
        try{
        enteremail(email);
        enterPassword(pass);
        clickLoginButton();}
        catch (TimeoutError error){
            Assert.fail("Impossible de remplir les champs");
        }
    }

    public void enteremail(String mail) {
        page.fill(email, mail);
    }

    public void enterPassword(String pass) {
        page.fill(password, pass);
    }

    public void clickLoginButton() {
        page.click(clickLogin);
    }

    public String getLoginProof() {
        page.waitForTimeout(5000);
        return page.textContent(loginProof);
    }

}