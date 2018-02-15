
//Author Den Pavluk 09.02.18
import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class OmnieAdminTest {
    String authPage = "http://less.omniecom.com/auth/signin";
    String urlToAdmin = "http://less.omniecom.com/business";
    @Before
    public void beforeTest() {
        System.setProperty("webriver.chrome.driver", "C:/Users/den/Downloads/chromedriver_win32/chromedriver.exe");
        Configuration.browser = "chrome";
        Configuration.browserSize = "800x480";
    }

    @Test
    public void AuthToAdminPage() {
        open(authPage);
        $(By.cssSelector("input[type=text]")).val("denpavluk@ukr.net");
        $(By.cssSelector("input[type=password]")).val("testtest").pressEnter();
        $(By.cssSelector("ul.tabs_menu")).isDisplayed();
        //$("h1").shouldHave(text("Пошук"));
        $(By.cssSelector("a.menu-mobile-btn")).click();
        open(urlToAdmin);
        $("h1").shouldHave(text("Мої організації"));
        System.out.println("Open admin side of omniecommerce");
    }


    @Test
    public void ClickTheOrganisation() {
        AuthToAdminPage();
        //Click the organisation (Кальян Shaman)
        $(By.cssSelector("div.adress_org_wrap")).shouldHave(text("Кальян Shaman")).click();
        $("h1").shouldHave(text("Детальна інформація про організацію "));
        $(By.id("adress_organization")).click();
        //Click the OK button - for close modal map
        $(By.xpath("//*[@id=\"map_wrapp\"]/div/div/div[2]/div/a")).click();
        //Click the 1 number in the list
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[3]/div/div/div[4]/div[2]/div/div/p/label[1]/phone-input/om-inputm/label/input")).click();
        //Click the filed (topic) - clear  - and put words
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[5]/div/label[1]/input")).hover().click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[5]/div/label[1]/input")).clear();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[5]/div/label[1]/input")).setValue("AaAaAa");
        $(By.cssSelector("a.confirm")).hover();
        //Click the button for editing images for gallery
        $(By.cssSelector("a.edit_photo_image")).click();
        //Hover the 1 service of the organisation
        $(By.cssSelector(".item_service_admin")).hover();
        //Click the button (add service)
        $(By.cssSelector(".button_add")).hover();
        //Hover the button (font view)
        $(By.linkText("Попередній перегляд")).hover().click();
    }

    @Test
    public void ClickTheOrders() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_my_order")).click();
        $("h1").shouldHave(text("Замовлення"));
        //Click the dropdown list
        $(By.xpath("//*[@id=\"services_list\"]/span/span[1]/span/span[2]")).click();
        //Check the organisation "Кальян КАЛЯН'
        $(By.xpath("//*[@id=\"services_list\"]/select/option[4]")).click();
        //Click the 13 date on calendar
        $(By.linkText("13")).click();
        //Click the 27 date on calendar
        $(By.linkText("20")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[3]/div[2]/div[3]/div/a[2]")).click();
        //Open the order for details
        $(By.xpath("//*[@id=\"f13dae24-7c4e-425a-9e25-ccb7f8364ef5\"]/td[3]")).click();
        $("h3").shouldHave(text("Замовник"));
    }

    //Creating the service
    @Test
    public void createService() {
        AuthToAdminPage();
        $(By.cssSelector("div.adress_org_wrap")).shouldHave(text("Кальян Shaman")).click();
        $("h1").shouldHave(text("Детальна інформація про організацію "));
        //Click link for adding new service for organisation
        $(By.linkText("Додати послугу")).click();
        $("h1").shouldHave(text("Детальна інформація про послугу"));
        //Fill the field (topic) for organisation
        $(By.cssSelector("input[type=text]")).val("123456Qwerty");
        //Fill the field (description of service
        $(By.tagName("textarea")).val("Text for textarea. 1223EEEW op");
        //$(By.cssSelector("label.css-label[_ngcontent-c17]")).shouldHave(text("Min тривалість")).hover().click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[4]/div[2]/form/div/div[2]/div/div/label")).click();
        //Click the save button in the header
        $(By.cssSelector("a.confirm")).click();
    }

    //click the discount page
    @Test
    public void createNews(){
        AuthToAdminPage();
        $(By.xpath("//*[@id=\"page-newsList\"]/a")).click();
        $(By.cssSelector("div.all_articles")).isDisplayed();
        $(By.linkText("Додати новину")).click();
        $(By.cssSelector("input[type=text]")).val("asd FRED asdp 12. ФБОты тыром");
        $(By.cssSelector("textarea")).val("ssd description.ІФВВ Злолп. 14 . Фыва елор");
        //open the calendar
        $(By.xpath("//*[@id=\"date_news\"]")).click();
        //Clicking the 20 date of the month
        $(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[4]/td[2]/a")).click();
        //Clicking the hours field
        $(By.xpath("/html/body/ng-component/main/ng-component/div/ng-component/div[1]/div/div[3]/div/div/div[1]/div[2]/div[2]/label/range-select/span/span[1]/span/span[2]")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/ng-component/div[1]/div/div[3]/div/div/div[1]/div[2]/div[3]/label/range-select/span/span[1]/span/span[2]")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/ng-component/div[1]/div/div[3]/div/div/div[1]/div[2]/div[3]/label/range-select/span/span[1]/span/span[2]")).click();
    }
}
