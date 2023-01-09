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
import org.testng.annotations.Test;



public class SoloRunner extends BaseTest {

    @DataProvider(name = "getRegistrationTestData")
    public Object[][] getRegistrationTestData() {
        Object usersData[][] = TestUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);
        return usersData;
    }

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                { "T-shirt" },                { "xoxo" }

        };
    }

    @DataProvider
    public Object[][] getProductDataForAdd() {
        return new Object[][] {
                { "T-shirt en coton biologique",3 },
                { "Chaussures Hommes de Ville",3 }
        };
    }

    @Test(dataProvider = "getRegistrationTestData", priority = 1)@Severity(SeverityLevel.NORMAL)
    public void createNewUserTest(String email, String password, String passwordconf) {

    }

//    @Severity(SeverityLevel.BLOCKER) can be catch up with throws InterruptedException

    @Test(priority = 2) @Severity(SeverityLevel.BLOCKER)
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



    @Test(priority = 3,dataProvider = "getProductData")@Severity(SeverityLevel.NORMAL)
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

    @Test(priority = 4,dataProvider = "getProductDataForAdd")@Severity(SeverityLevel.NORMAL)
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

    @Test(priority = 5,dataProvider = "getProductDataForAdd")@Severity(SeverityLevel.NORMAL)
    public void suppressFromCartTest(String productName, int X)  {

        homePage.ClickOnCartIcon();
        for (int i=0;i<X;i++)
        {homePage.DeleteFromCart(productName);
        }
        Assert.assertFalse(homePage.VerifyArticleDeletion(productName),"Article toujours présent dans le panier");

    }

    @Test(priority = 6)@Severity(SeverityLevel.NORMAL)
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
