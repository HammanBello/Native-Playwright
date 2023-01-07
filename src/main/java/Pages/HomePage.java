package Pages;



import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Selectors;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import io.qameta.allure.Allure;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static com.microsoft.playwright.options.LoadState.NETWORKIDLE;

public class HomePage {

    public Page page;

// Locator — — — -
    String usedIDs = "text=Cet utilisateur existe déjà";

    String incorrectIDs = "text=Email ou mot de passe incorrect";
    String siteLogo ="id=style_header_home__8t_ie";

    String addToCartBtn ="id=style_btn_add_cart__gTXM7";

    String notifOfAdd = "text=Votre panier à été mis à jour";

    String badgeOfAdd = "#style_content_cart_wrapper__mqNbf >> text=0";

    public String cartIcon = "id=style_content_cart_wrapper__mqNbf";

    String suppressIcon = "text=Ampoule Vecteur...30.99 €1 >> :nth-match(div, 4)";

    String searchBar = "id=style_input_navbar_search__Scaxy";

    public String searchResult = ".style_card__gNEqX";

    String erreurNonConnected = "text=Connexion";


    String Compte ="text=Compte";
    String Deco = "text=Se déconnecter";



//initialize Page using constructor

    public HomePage(Page page) {

        this.page =page;}

//Method

    public String productName() {

        String productName = page.textContent(siteLogo);

        return productName;}

    public String getProductName() {
        String productName = page.textContent(siteLogo);

        return productName;
    }

    public String getSiteLogoVision() {
//        page.waitForTimeout(10000);

        try {
            page.waitForURL("**/home", new Page.WaitForURLOptions().setTimeout(15000));
            page.waitForURL("**/home", new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
//            page.waitForTimeout(3000);
        } catch (TimeoutError e) {
            System.out.println("Timeout!");
        }
        if (page.isVisible(incorrectIDs))
            return  ("wrong_IDs");
        else{
        if (page.isVisible(siteLogo))
            return  ("ok");
        else{
            if (page.isVisible(usedIDs))
                return  ("used_IDs");
            else
            return ("no_logo_seen");

        }
        }



    }



    public void takeScreenshot() {
        Allure.addAttachment("screenshot", new ByteArrayInputStream(page.screenshot()));
    }

    public void takeScreenVideoCapture() {


        byte[] byteArr = new byte[0];
        try {
            Path path = page.video().path();
            // file to byte[], Path
            byteArr = Files.readAllBytes(path);
            Allure.addAttachment("Video", "video/mp4", new ByteArrayInputStream(byteArr), "mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ClickOnDisconnect(){
        page.waitForSelector(siteLogo);
        page.hover(Compte);
        page.click(Compte);
        page.waitForSelector(Deco);
        page.click(Deco);
    }

    public void ClickOnAnArticle(String articleToAdd) {

        try {
            Locator p = page.locator(searchResult)
                    .filter(new Locator.FilterOptions().setHasText(articleToAdd)).first();
            p.waitFor(new Locator.WaitForOptions().setTimeout(15000));
            p.click();
        } catch (TimeoutError e) {
            System.out.println("Timeout to click on article");
        }

    }

    public void ClickOnAddToCart(int X) {


        try {
            page.waitForSelector(addToCartBtn, new Page.WaitForSelectorOptions().setTimeout(15000));
            page.fill(".style_input_quantity__xZDIb",String.valueOf(X));
            page.waitForLoadState(NETWORKIDLE);
            page.click(addToCartBtn);
        } catch (TimeoutError e) {
            System.out.println("Timeout to click on article");
        }
    }

    public String VerifyNotification(){
        page.waitForSelector(notifOfAdd);
        return page.textContent(notifOfAdd);


    }

    public String VerifyBadge() {
        page.waitForSelector(badgeOfAdd);
        return page.textContent(badgeOfAdd);
    }

    public Boolean VerifyArticleInCart(String productName) {

        try {
            page.click("#style_content_cart_wrapper__mqNbf > span");
            String[] productNames = productName.split(" ");
            Locator p = page.locator(".style_card__JLMp6")
                    .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productNames[0])));
            p.waitFor();
            return p.isVisible();
        } catch (TimeoutError e) {
            System.out.println("Timeout when looking in cart!");
            return page.locator(".style_card__JLMp6")
                    .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productName))).isVisible();

        }

    }


    public void emptyTheCart() {
        if (!page.isVisible(badgeOfAdd)){
            try {
                page.waitForSelector(cartIcon, new Page.WaitForSelectorOptions().setTimeout(15000));
                page.click(cartIcon);
                page.click("text=Vider le panier");
//                page.waitForSelector(badgeOfAdd , new Page.WaitForSelectorOptions().setTimeout(15000));
            } catch (TimeoutError e) {
                System.out.println("Timeout to empty the cart!");
            }
}
    }

    public void removeOnItemFromCart() {
        page.waitForSelector(cartIcon);
        page.click(cartIcon);
        page.waitForSelector(suppressIcon);
        page.click(suppressIcon);
    }


    public void Idoasearch(String searchTerm) {
        try {
            page.waitForLoadState(NETWORKIDLE);
            page.waitForSelector(searchBar, new Page.WaitForSelectorOptions().setTimeout(15000));
        } catch (TimeoutError e) {
            System.out.println("Timeout pour la barre de recherche!");
        }
        page.fill(searchBar, searchTerm);}

    public String getResultSearch(int x, String searchedTerms) {
        Locator p = page.locator(searchResult)
                .filter(new Locator.FilterOptions().setHasText(searchedTerms)).nth(x);


        try {
//            page.waitForSelector("class=style_card__gNEqX",new Page.WaitForSelectorOptions().setTimeout(15000));
            p.waitFor(new Locator.WaitForOptions().setTimeout(15000));
//            page.waitForSelector(searchResult, new Page.WaitForSelectorOptions().setTimeout(15000));
        } catch (TimeoutError e) {
            System.out.println("Timeout pour le résultat de la recherche!");
        }
        if(page.isVisible("text=Aucun produit trouvé"))
            return ("Aucun_produit_trouvé");
        else if (p.isVisible()) {
            return ("ok");
        }
        else
            return ("not_ok");
    }

    public Boolean isErreurNonConnected(){
        return page.isVisible(erreurNonConnected);
    }


    public void DeleteFromCart(String s) throws InterruptedException {
        try {

            String[] productNames = s.split(" ");
            Locator p = page.locator(".style_card__JLMp6")
                    .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productNames[0])));
            p.locator(".style_quantity_dec__nm5ig").click();
        } catch (TimeoutError e) {
            System.out.println("Timeout to press on reduce button!");
        }

        page.waitForTimeout(3000);
    }
    public void ClickOnCartIcon() {
        try {
            page.waitForTimeout(2000);
            page.waitForSelector(cartIcon, new Page.WaitForSelectorOptions().setTimeout(1500));
            if(page.isVisible(cartIcon))
                page.click(cartIcon, new Page.ClickOptions().setTimeout(100));
        } catch (TimeoutError e) {
            System.out.println("Timeout to press on cart icon!");
        }
    }


    public Boolean VerifyArticleDeletion(String s) {
        String[] productNames = s.split(" ");
        Locator p = page.locator(".style_card__JLMp6")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productNames[0])));
        try {
//            page.waitForSelector("class=style_card__gNEqX",new Page.WaitForSelectorOptions().setTimeout(15000));
            p.waitFor(new Locator.WaitForOptions().setTimeout(2000));
//            page.waitForSelector(searchResult, new Page.WaitForSelectorOptions().setTimeout(15000));
        } catch (TimeoutError e) {
            System.out.println("Timeout pour le résultat de la recherche!");
        }
        if(p.isVisible())
            return (true);
        else
            return (false);

    }
}

