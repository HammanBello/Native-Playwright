package tests;

import base.BaseTest;
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
                { "Macbook" },
                { "iMac" },
                { "Samsung" }
        };
    }

    @Test(priority = 2,dataProvider = "getProductData")
    public void searchTest(String productName) throws InterruptedException {
//        Thread.sleep(5000);
        homePage.Idoasearch(productName);
        Assert.assertTrue("Erreur au niveau du résultat de la recherche",homePage.getResultSearch(productName));

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
