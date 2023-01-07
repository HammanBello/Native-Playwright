package tests;

import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Properties;

import static java.lang.Integer.parseInt;

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
        homePage.emptyTheCart();
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
                { "Ampoule Vecteur Incandescent",3 },
                { "T-shirt en coton biologique",3 },
                { "Chaussures Hommes de Ville",3 }
        };
    }

    @Test(priority = 2,dataProvider = "getProductData")@Ignore
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
    public void addToCartTest(String productName, int X) {
     homePage.page.fill("id=style_input_navbar_search__Scaxy","");
//     homePage.emptyTheCart();
     homePage.ClickOnAnArticle(productName);
     homePage.ClickOnAddToCart(X);
        Assert.assertTrue(homePage.VerifyArticleInCart(productName),"Article absent du panier");
    homePage.page.click("text=LES PRODUITS");
//        Locator p = homePage.page.locator(homePage.searchResult)
//                .filter(new Locator.FilterOptions().setHasText(productName));
    }

    @Test(priority = 4,dataProvider = "getProductDataForAdd")
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
