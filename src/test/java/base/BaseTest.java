package base;

import Factory.PlaywrightFactory;

import Pages.HomePage;
import Pages.LoginPage;
import Pages.SignInPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.testng.annotations.*;


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
    public  Page page;
    protected Properties prop;

    public HomePage homePage;
    public LoginPage loginPage;
    public SignInPage signInPage;

    public String mail;

    public static String dataSheet;
    public String psswd;

    @Parameters({ "browser", "mail","psswd", "datasheet"})
    @BeforeTest
    public void beforeTest(String browserName, String email, String pwd, @Optional String sheet) {
        deleteScript();
        pf = new PlaywrightFactory();
        mail = email;
        psswd = pwd;
        dataSheet = sheet;
        prop = pf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        page = pf.initBrowser(prop);

        homePage = new HomePage(page);
        loginPage = new LoginPage(page);
        signInPage = new SignInPage(page);


    }

    @AfterTest
    public void afterTest() {

        pf.getBrowserContext().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("Traces/trace.zip")));
        byte[] byteArr = new byte[0];
        try {
            Path content = Paths.get("Traces/trace.zip");
//            i++;
            Allure.addAttachment("trace de l'execution de la suite", Files.newInputStream(content) );
            // file to byte[], Path
//            byteArr = Files.readAllBytes(content);
//            Allure.addAttachment("Trace", "archive/zip", new ByteArrayInputStream(byteArr), "zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        page.close();
        pf.getBrowserContext().close();



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

public void deleteScript(){

    File index = new File("allure-results");
    if (index.exists()) {
        String[]entries = index.list();
        for(String s: entries){
            if (!s.equals("environment.xml")){
                File currentFile = new File(index.getPath(),s);
                currentFile.delete();}
        }
//            index.delete();
    }
    File index2 = new File("screenshot");
    if (index2.exists()) {
        String[]entries = index2.list();
        for(String s: entries){
            File currentFile = new File(index2.getPath(),s);
            currentFile.delete();
        }
        index2.delete();
    }
    File index3 = new File("images");
    if (index3.exists()) {
        String[]entries = index3.list();
        for(String s: entries){
            File currentFile = new File(index3.getPath(),s);
            currentFile.delete();
        }
        index3.delete();
    }
    File index4 = new File("Traces");
    if (index4.exists()) {
        String[]entries = index4.list();
        for(String s: entries){
            File currentFile = new File(index4.getPath(),s);
            currentFile.delete();
        }
        index4.delete();
    }

//    File index5 = new File("C:/ProgramData/Jenkins/.jenkins/workspace/PW_CI_CD/allure-results");
//    if (index5.exists()) {
//        String[]entries = index5.list();
//        for(String s: entries){
//            if (!s.equals("environment.xml")){
//                File currentFile = new File(index5.getPath(),s);
//                currentFile.delete();
//            }
//        }
////            index5.delete();
//    }
//    File index7 = new File("C:/ProgramData/Jenkins/.jenkins/workspace/PW_CI_CD/target/videos");
//    if (index7.exists()) {
//        String[]entries = index7.list();
//        for(String s: entries){
//            File currentFile = new File(index7.getPath(),s);
//            currentFile.delete();
//        }
//        index7.delete();
//    }
    File index8 = new File("target/videos");
    if (index8.exists()) {
        String[]entries = index8.list();
        for(String s: entries){
            File currentFile = new File(index8.getPath(),s);
            currentFile.delete();
        }
        index8.delete();
    }
}


}

