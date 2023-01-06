package Pages;

import com.microsoft.playwright.Page;

public class HomePage {
    private Page page;

    // 1. String Locators - OR
    String pageTitle = "id=style_content_logo__pkvMP";
    String searchBar = "id=style_input_navbar_search__Scaxy";
    String addproduct01 = "text=Ampoule Vecteur Incandescent30.99 € T-shirt en coton biologique8.99 € Chaussures >> span >> :nth-match(img, 2)";
    String verifyproduct = "text=Votre panier à été mis à jour";
    String handle_mouse = "id=style_avatar_wrapper__pEGIQ";
    String logout_bouton = "text=Se déconnecter";
    String failSearch = "id=style_empty_result___y6P_";
    String searchResult = "#style_popular_product_wrapper__z6J0h div div";


    // 2. page constructor:
    public HomePage(Page page) {
        this.page = page;
    }

    // 3. page actions/methods:
    public String getHomePageTitle() {
        String title =  page.textContent(pageTitle);
        System.out.println("page title: " + title);
        return title;
    }

    public String getHomePageURL() {
        String url =  page.url();
        System.out.println("page url : " + url);
        return url;
    }

    public boolean doSearch(String productName) {
        page.waitForTimeout(10000);
        page.fill(searchBar, productName);
        page.locator(searchResult).waitFor();
        if(page.locator(searchResult).isVisible()) {
            System.out.println("the research was successful....");
            return true;
        }else {
            System.out.println("the search has failed....");
            return false;
        }
    }


    public LoginPage navigateToLoginPage() {
        page.click(handle_mouse);
        page.click(logout_bouton);
        return new LoginPage(page);
    }
}

