package Pages;


import com.microsoft.playwright.Page;

public class SignInPage {

    Page page;

// Locator — — — -

    String email = "id=email_register";
    String password = "id=password_register";

    String passwordConfirm = "id=confirm_password_register";

    String clickRegis = "id=btn_register";

    String loginProof = "text=Connexion";




// initialize Page using constructor

    public SignInPage(Page page) {
        this.page = page;

    }

    public String verifyTitle() {
        String title = page.title();
        return title;
    }

//Create methods

// Login into the application

    public void loginIntoApplication(String email, String pass) {
        enteremail(email);
        enterPassword(pass);
        enterConfirmPassword(pass);
        clickRegisterButton();
    }

    public void enteremail(String mail) {
        page.fill(email, mail);
    }

    public void enterPassword(String pass) {
        page.fill(password, pass);
    }

    public void enterConfirmPassword(String pass) {
        page.fill(passwordConfirm, pass);
    }


    public void clickRegisterButton() {
        page.click(clickRegis);
    }

    public String getLoginProof() {
        return page.textContent(loginProof);
    }

}