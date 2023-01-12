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





// initialize Page using constructor

    public LoginPage(Page page) {
        this.page = page;

    }



//Create methods

// Login into the application

    public void loginIntoApplication(String email, String pass) {
        try{
        enteremail(email);
        enterPassword(pass);
        clickLoginButton();}
        catch (com.microsoft.playwright.PlaywrightException exception){
            Assert.fail("Impossible de remplir les champs");
        }
    }

    public void enteremail(String mail) {
        page.fill(email, mail, new Page.FillOptions().setTimeout(5000));
    }

    public void enterPassword(String pass) {
        page.fill(password, pass,new Page.FillOptions().setTimeout(5000));
    }

    public void clickLoginButton() {
        page.click(clickLogin, new Page.ClickOptions().setTimeout(5000));
    }



}