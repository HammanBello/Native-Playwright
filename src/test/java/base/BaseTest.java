package base;

import Factory.PlaywrightFactory;

import Pages.HomePage;
import Pages.LoginPage;
import Pages.SignInPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static Factory.PlaywrightFactory.i;

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
        File index4 = new File("C:/Users/hambe/Desktop/Native Playright/Native-Playwright/Traces");
        if (index4.exists()) {
            String[]entries = index4.list();
            for(String s: entries){
                File currentFile = new File(index4.getPath(),s);
                currentFile.delete();
            }
            index4.delete();
        }

    }

    @AfterTest
    public void afterTest() {

        pf.getBrowserContext().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("Traces/trace.zip")));
        byte[] byteArr = new byte[0];
        try {
            Path content = Paths.get("Traces/trace.zip");
//            i++;
            Allure.addAttachment("trace: ", Files.newInputStream(content) );
            // file to byte[], Path
//            byteArr = Files.readAllBytes(content);
//            Allure.addAttachment("Trace", "archive/zip", new ByteArrayInputStream(byteArr), "zip");
        } catch (IOException e) {
            e.printStackTrace();
        }

        page.context().browser().close();


        byte[] byteArr2 = new byte[0];
        try {
            Path path = page.video().path();
            // file to byte[], Path
            byteArr2 = Files.readAllBytes(path);
            Allure.addAttachment("Video", "video/mp4", new ByteArrayInputStream(byteArr2), "mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

