package Pages;


import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitUntilState;
import org.testng.Assert;

public class SignInPage {

    public Page page;

// Locator — — — -
    String siteLogo ="id=style_header_home__8t_ie";

    String usedIDs = "text=Cet utilisateur existe déjà";

    String shortPswd = "text=Le mot de passe doit avoir au moins 8 caractères";

    String samePswds = "text=Les deux mots de passe ne sont pas identiques";

    String invalidIDs = "text=Le format de l'email est invalid";
    String emailSignIn = "id=email_register";

    String passwordSignIn = "id=password_register";

    String passwordConfirmSignIn = "id=confirm_password_register";

    String clickRegis = "id=btn_register";

    String loginProof = "text=Connexion";




// initialize Page using constructor

    public SignInPage(Page page) {
        this.page = page;

    }
//Create methods

// Login into the application

    public void signinIntoApplication(String email, String pass, String passwordConfirm) {
        try{
            enteremail(email);
            enterPassword(pass);
            enterPasswordConf(passwordConfirm);
            clickRegisButton();}
        catch (TimeoutError error){
            Assert.fail("Impossible de remplir les champs");
        }
    }

    public void enteremail(String mail) {
        page.fill(emailSignIn, mail);
    }

    public void enterPassword(String pass) {
        page.fill(passwordConfirmSignIn, pass);

    }

    public void enterPasswordConf(String pass) {
        page.fill(passwordSignIn, pass);

    }

    public void clickRegisButton() {
        page.click(clickRegis);
    }

    public String getSiteLogoVision() {
//        page.waitForTimeout(10000);

        try {
//            page.waitForURL("**/home", new Page.WaitForURLOptions().setTimeout(15000));
            page.waitForURL("**/home", new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
//            page.waitForTimeout(3000);
        } catch (TimeoutError e) {
            System.out.println("Timeout!");
        }

        if (page.isVisible(invalidIDs))
            return  ("invalidIDs");
        else{
            if (page.isVisible(siteLogo))
                return  ("ok");
            else{
                if (page.isVisible(usedIDs))
                    return  ("used_IDs");
                else if (page.isVisible(shortPswd)) {return  ("short_Pswd");}
                else if (page.isVisible(samePswds)) {return  ("same_Pswds");}
                else return ("no_logo_seen");

            }
//            else
//            return ("no_logo_seen");
        }
    }
}