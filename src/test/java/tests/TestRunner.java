package tests;
import Utils.TestUtil;
import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import constants.AppConstants;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import listeners.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;



public class TestRunner extends BaseTest {

    @DataProvider(name = "getRegistrationTestData")
    public Object[][] getRegistrationTestData() {
        String s = AppConstants.CONTACTS_SHEET_NAME;
        if (!dataSheet.isEmpty())
            s = dataSheet;
        Object usersData[][] = TestUtil.getTestData(s);
        return usersData;
    }

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                { "T-shirt" }

        };
    }

    @DataProvider
    public Object[][] getProductDataForAdd() {
        return new Object[][] {
                { "T-shirt en coton biologique",3 },
                { "Chaussures Hommes de Ville",3 }
        };
    }

    @Test(dataProvider = "getRegistrationTestData", priority = 1)
    public void createNewUserTest(String email, String password, String passwordconf) {
        page.navigate(prop.getProperty("url_signIn").trim());

        try{
            signInPage.page.waitForURL(prop.getProperty("url_signIn").trim(), new Page.WaitForURLOptions().setTimeout(10000));}
        catch (TimeoutError ignored){}
        signInPage.signinIntoApplication(email, password, passwordconf);
        String s = signInPage.getSiteLogoVision();
        switch (s) {
            case "ok":
                System.out.println("ok");
                break;
            case "no_logo_seen":
                Assert.fail("Impossible d'acceder à la page Home");
                break;
            case "used_IDs":
                Assert.fail("L'utilisateur existe déjà");
            case "short_Pswd":
                Assert.fail("Mot de passe trop court");
            case "same_Pswds":
                Assert.fail("Les mot de passe ne correspondent pas");
            case "invalidIDs":
                Assert.fail("L'adresse mail n'a pas un format valide");
                break;
        }
    }

//    @Severity(SeverityLevel.BLOCKER) can be catch up with throws InterruptedException

    @Test(priority = 2,retryAnalyzer = Retry.class) @Severity(SeverityLevel.BLOCKER)
    public void loginPageNavigationTest(){
        try{
            homePage.page.navigate(prop.getProperty("url").trim(), new Page.NavigateOptions());
            homePage.page.waitForURL(prop.getProperty("url").trim(), new Page.WaitForURLOptions().setTimeout(10000));}
        catch (TimeoutError ignored){
            Assert.fail("Impossible d'acceder à la page de login");
        }
        loginPage.loginIntoApplication(mail, psswd);
        String s = homePage.getSiteLogoVision();

        switch (s) {
            case "ok":
                homePage.emptyTheCart();
                System.out.println("ok");
                break;
            case "wrong_IDs":
                Assert.fail("Informations de connexion Incorrects");
                break;
            case "no_logo_seen":
                Assert.fail("Impossible d'acceder à la page Home");
                break;
            case "used_IDs":
                Assert.fail("L'utilisateur existe déjà");

                break;
        }

    }



    @Test(priority = 3,dataProvider = "getProductData")
    public void searchTest(String productName)  {
        homePage.Idoasearch(productName);
        Locator p = homePage.page.locator(homePage.searchResult)
                .filter(new Locator.FilterOptions().setHasText(productName));

        int count = p.count();
        if (count  == 0 )
            count++;
        for (int i = 0; i < count; ++i) {
            String s = homePage.getResultSearch(i, productName);

            switch (s) {
                case "ok":
                    System.out.println("ok");
                    break;
                case "Aucun_produit_trouvé":
                    Assert.fail("Produit inexistant dans la base de donnée");
                    break;
                case "not_ok":
                    Assert.fail("Pas de correspondance entre le resulat et l'élement recherché");
                    break;
            }

        }
    }

    @Test(priority = 4,dataProvider = "getProductDataForAdd")
    public void addToCartTest(String productName, int X) {
        try{
     homePage.page.fill("id=style_input_navbar_search__Scaxy","", new Page.FillOptions().setTimeout(2000));}
        catch (TimeoutError error){
            Assert.fail("Impossible d'acceder à la page accueil");
        }

     Boolean b = homePage.ClickOnAnArticle(productName);
        Assert.assertTrue(b,"Article inexistant ou non localisable");
        homePage.ClickOnAddToCart(X);
        Assert.assertTrue(homePage.VerifyArticleInCart(productName),"Article absent du panier");
    homePage.page.click("text=LES PRODUITS");

    }

    @Test(priority = 5,dataProvider = "getProductDataForAdd")
    public void suppressFromCartTest(String productName, int X)  {

        homePage.ClickOnCartIcon();
        for (int i=0;i<X;i++)
            {homePage.DeleteFromCart(productName);
            }
        Assert.assertFalse(homePage.VerifyArticleDeletion(productName),"Article toujours présent dans le panier");

    }

    @Test(priority = 6)
    public void LOGOUT()  {


        try{
        homePage.page.click("text= LES PRODUITS", new Page.ClickOptions().setTimeout(5000));
        homePage.disconnect();
        homePage.page.waitForSelector("text=Connexion",new Page.WaitForSelectorOptions().setTimeout(4000));}
        catch (TimeoutError error){
            Assert.fail("Impossible de cliquer sur le boutton déconnexion");
        }

    }


}
