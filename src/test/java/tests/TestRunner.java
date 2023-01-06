package tests;

import base.BaseTest;
import com.microsoft.playwright.Locator;
import constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Properties;

public class TestRunner extends BaseTest {


//    @Test
//    public void homePageTitleTest() {
//        String actualTitle = homePage.getHomePageTitle();
//        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
//    }
//
//    @Test
//    public void homePageURLTest() {
//        String actualURL = homePage.getHomePageURL();
//        Assert.assertEquals(actualURL, prop.getProperty("url"));
//    }
//


    @Test(priority = 1)
    public void loginPageNavigationTest() {
        homePage.page.navigate(prop.getProperty("url").trim());
        homePage.page.waitForURL(prop.getProperty("url").trim());
        loginPage.loginIntoApplication(prop.getProperty("username").trim(), prop.getProperty("password").trim());
        String s = homePage.getSiteLogoVision();
        if(s.equals("ok"))
            System.out.println("ok");
        else if (s.equals("wrong_IDs")) {
            Assert.fail("Informations de connexion Incorrects");
        } else if (s.equals("no_logo_seen")) {
            Assert.fail("Impossible d'acceder à la page Home");
        }else if (s.equals("used_IDs")) {
            Assert.fail("L'utilisateur existe déjà");

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
                { "Ampoule Vecteur Incandescent" },
                { "T-shirt en coton biologique" },
                { "Chaussures Hommes de Ville" }
        };
    }

    @Test(priority = 2,dataProvider = "getProductData")
    public void searchTest(String productName)  {
        homePage.Idoasearch(productName);
        Locator p = homePage.page.locator(homePage.searchResult)
                .filter(new Locator.FilterOptions().setHasText(productName));

        int count = p.count();
        if (count  == 0 )
            count++;
        for (int i = 0; i < count; ++i) {
            String s = homePage.getResultSearch(i, productName);

            if (s.equals("ok"))
                System.out.println("ok");
            else if (s.equals("Aucun_produit_trouvé")) {
                Assert.fail("Produit inexistant dans la base de donnée");
            } else if (s.equals("not_ok")) {
                Assert.fail("Pas de correspondance entre le resulat et l'élement recherché");
            }
//        Assert.assertTrue(homePage.getResultSearch(productName),"Erreur au niveau du résultat de la recherche");
        }
    }

    @Test(priority = 3,dataProvider = "getProductDataForAdd")
    public void addToCartTest(String productName) {
     homePage.page.fill("id=style_input_navbar_search__Scaxy","");
     homePage.emptyTheCart();
     homePage.ClickOnAnArticle(productName);
     homePage.ClickOnAddToCart();
        Assert.assertTrue(homePage.VerifyArticleInCart(productName),"Article absent du panier");

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
