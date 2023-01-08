package tests;
import Utils.TestUtil;
import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import constants.AppConstants;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;



public class TestRunner extends BaseTest {

    @DataProvider(name = "getRegistrationTestData")
    public Object[][] getRegistrationTestData() {
        Object usersData[][] = TestUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);
        return usersData;
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




    @Test(priority = 2) @Severity(SeverityLevel.BLOCKER)@Ignore
    public void loginPageNavigationTest() {
        homePage.page.navigate(prop.getProperty("url").trim());
        try{
        homePage.page.waitForURL(prop.getProperty("url").trim(), new Page.WaitForURLOptions().setTimeout(10000));}
        catch (TimeoutError ignored){}
        loginPage.loginIntoApplication(prop.getProperty("username").trim(), prop.getProperty("password").trim());
        String s = homePage.getSiteLogoVision();
        homePage.emptyTheCart();
        switch (s) {
            case "ok":
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

    @Test(priority = 3,dataProvider = "getProductData")@Ignore
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
//        Assert.assertTrue(homePage.getResultSearch(productName),"Erreur au niveau du résultat de la recherche");
        }
    }

    @Test(priority = 4,dataProvider = "getProductDataForAdd")
    public void addToCartTest(String productName, int X) {
        try{
     homePage.page.fill("id=style_input_navbar_search__Scaxy","", new Page.FillOptions().setTimeout(2000));}
        catch (TimeoutError error){
            Assert.fail("Impossible d'acceder à la page accueil");
        }
//     homePage.emptyTheCart();
     Boolean b = homePage.ClickOnAnArticle(productName);
        Assert.assertTrue(b,"Article inexistant ou non localisable");
        homePage.ClickOnAddToCart(X);
        Assert.assertTrue(homePage.VerifyArticleInCart(productName),"Article absent du panier");
    homePage.page.click("text=LES PRODUITS");
//        Locator p = homePage.page.locator(homePage.searchResult)
//                .filter(new Locator.FilterOptions().setHasText(productName));
    }

    @Test(priority = 5,dataProvider = "getProductDataForAdd")@Ignore
    public void suppressFromCartTest(String productName, int X) throws InterruptedException {
//        String s = homePage.page.textContent(".style_quantity__qJbQ3");
        homePage.ClickOnCartIcon();
        for (int i=0;i<X;i++)
            {homePage.DeleteFromCart(productName);
            }
        Assert.assertFalse(homePage.VerifyArticleDeletion(productName),"Article toujours présent dans le panier");
//        homePage.page.click("text=LES PRODUITS");
//        Locator p = homePage.page.locator(homePage.searchResult)
//                .filter(new Locator.FilterOptions().setHasText(productName));
    }

    @Test(priority = 6)
    public void LOGOUT()  {
//        String s = homePage.page.textContent(".style_quantity__qJbQ3");

        try{
        homePage.page.click("text= LES PRODUITS", new Page.ClickOptions().setTimeout(5000));
        homePage.disconnect();
        homePage.page.waitForSelector("text=Connexion",new Page.WaitForSelectorOptions().setTimeout(4000));}
        catch (TimeoutError error){
            Assert.fail("Impossible de cliquer sur le boutton déconnexion");
        }
//        Assert.assertTrue(homePage.page.isVisible("text=Connexion"),"Impossible de se déconnecter");









//        homePage.page.click("text=LES PRODUITS");
//        Locator p = homePage.page.locator(homePage.searchResult)
//                .filter(new Locator.FilterOptions().setHasText(productName));
    }
//    @Test(priority = 2)@Ignore
//    public void forgotPwdLinkExistTest() {
//        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
//    }
//
//    @Test(priority = 1)
//    public void appLoginTest() {
//        Assert.assertTrue(loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim()));
//    }

}
