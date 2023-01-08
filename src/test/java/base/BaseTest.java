package base;

import Factory.PlaywrightFactory;

import Pages.HomePage;
import Pages.LoginPage;
import Pages.SignInPage;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import java.io.File;
import java.util.Properties;

public class BaseTest {
    PlaywrightFactory pf;
    public Page page;
    protected Properties prop;

    public HomePage homePage;
    public LoginPage loginPage;
    public SignInPage signInPage;


    @Parameters({ "browser" })
    @BeforeTest
    public void beforeTest(String browserName) {
        pf = new PlaywrightFactory();

        prop = pf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        page = pf.initBrowser(prop);
        homePage = new HomePage(page);
        loginPage = new LoginPage(page);
        signInPage = new SignInPage(page);

        File index = new File("C:/Users/hambe/Desktop/Native Playright/Native-Playwright/allure-results");
        if (index.exists()) {
            String[]entries = index.list();
            for(String s: entries){
                File currentFile = new File(index.getPath(),s);
                currentFile.delete();
            }
            index.delete();
        }
        File index2 = new File("C:/Users/hambe/Desktop/Native Playright/Native-Playwright/screenshot");
        if (index2.exists()) {
            String[]entries = index2.list();
            for(String s: entries){
                File currentFile = new File(index2.getPath(),s);
                currentFile.delete();
            }
            index2.delete();
        }
        File index3 = new File("C:/Users/hambe/Desktop/Native Playright/Native-Playwright/images");
        if (index3.exists()) {
            String[]entries = index3.list();
            for(String s: entries){
                File currentFile = new File(index3.getPath(),s);
                currentFile.delete();
            }
            index3.delete();
        }

    }

    @AfterTest
    public void afterTest() {
        page.context().browser().close();
    }
}
