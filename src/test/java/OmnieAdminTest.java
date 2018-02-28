
//Author Den Pavluk 09.02.18
import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;


import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class OmnieAdminTest {
    String authPage = "http://less.omniecom.com/auth/signin";
    String urlToAdmin = "http://less.omniecom.com/business";
    @Before
    public void beforeTest() {
        System.setProperty("webriver.chrome.driver", "C:/Users/den/Downloads/chromedriver_win32/chromedriver.exe");
        Configuration.browser = "chrome";
        Configuration.browserSize = "1024x768";
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


    @Test
    public void clickTheCustomerBlackList() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_my_customers")).click();
        $$("h1").findBy(text("Клієнти")).shouldBe(visible);
        $(By.linkText("Чорний список")).click();
        $$("p").findBy(text("Чорний список клієнтів")).shouldBe(visible);
        //Click the button for sorting date period from
        //This part of code is not working
        //html body.admin.admin_customers ng-component main ng-component div.container div#client_base.tabs_content.active customer-list div.row div.col-xs-12 div.clr.wraper_filter div.col-sm-6.col-lg-2 p date-input.ng-untouched.ng-pristine.ng-valid label input#dp1519725687987.calendar_input.hasDatepicker
        //$(By.cssSelector("input.hasDatepicker")).click();
        //$(By.linkText("5")).hover().click();
    }

    //Send the message in the admin side
    @Test
    public void ClickTheMesage() {
        AuthToAdminPage();
        String testing_word = "Testing message..///Rui Rubur Duba 123-645790 Каротьо Ьолтои Больо. Ы и торот рота - 6785-0987";
        $(By.cssSelector("a.nav-menu_message")).click();
        $$("h1").findBy(text("Повідомлення")).shouldBe(visible);
        $(By.cssSelector("h2")).click();
        //Set value in the field
        $(By.cssSelector("textarea")).setValue(testing_word);
        //Click the send button
        $(By.cssSelector("a.send_message")).click();
        $$("p").findBy(text(testing_word)).shouldBe(visible);
    }

    @Test
    public void chickingTheRewievs() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_message")).click();
        $(By.linkText("Відгуки")).click();
        //Click the dropdownlist button
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[2]/div[2]/business-review/div/div[1]/div/label/omnie-select/span/span[1]/span/span[2]")).click();
        //Choose the the link "міні кальян через мобайл"
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[2]/div[2]/business-review/div/div[1]/div/label/omnie-select/select/option[2]")).click();
        //Choose the link "розирення 768х1024, браузер Firefox 56.0.2 (64-біт)"
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[2]/div[2]/business-review/div/div[1]/div/label/omnie-select/select/option[3]")).click();
       /*Select selopt = new Select($(By.cssSelector("розирення 768х1024, браузер Firefox 56.0.2 (64-біт)")));
        selopt.selectByValue("dummy");
        //selopt.selectByVisibleText("міні кальян через мобайл");*/
    }


    @Test
    public void createNewsinAdmin() {
        AuthToAdminPage();
        $(By.cssSelector(".nav-menu_news")).click();
        $$("p").findBy(text("Усі новини")).shouldBe(visible);
        $(By.linkText("Додати новину")).click();
        $$("p").findBy(text("Додати новину")).shouldBe(visible);
        $(By.cssSelector("input[type=text]")).val("Header of this article --TEST---");
        $(By.cssSelector("textarea")).val("Description for this article? filling automation tools");
        //Open the dropdown list for choose hours
        $(By.xpath("/html/body/ng-component/main/ng-component/div/ng-component/div[1]/div/div[3]/div/div/div[1]/div[2]/div[2]/label")).click();
        $(By.xpath("/html/body/span/span/span[2]/ul/li[9]")).click();
        //Open the dropdown-list for choose minutes
        $(By.xpath("/html/body/ng-component/main/ng-component/div/ng-component/div[1]/div/div[3]/div/div/div[1]/div[2]/div[3]/label")).click();
        $(By.xpath("/html/body/span/span/span[2]/ul/li[20]")).click();
        //Click the checkbox stock
        //$(By.id("hot_news_sale")).click();
        $(By.linkText("Зберегти")).click();
    }


    @Test
    public void createNewEmployee() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_personal")).click();
        $$("h1").findBy(text("Управління персоналом")).shouldBe(visible);
        /*$(By.xpath("/html/body/ng-component/main/ng-component/div/div[3]/div/div/div[1]/div/p/label")).click();*/
        $(By.cssSelector("input[type=text]")).click();
        $(By.cssSelector("input[type=text]")).val("pochta@mail.ua");
        //click button for saving
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[3]/div/div/div[2]/div/a")).click();
        //Change the employee position
        $(By.cssSelector(".button_open")).click();
        $(By.linkText("ватрушка")).click();
    }

    @Test
    public void createNewPosition() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_personal")).click();
        //Click button for adding position
        $(By.cssSelector(".button_add")).click();
        $$("p").findBy(text("Увага!")).shouldBe(visible);
        //filling the field
        $(By.cssSelector("div.head_table input[type=text]")).val("вантажник");
        //Click the checkbox
        $(By.xpath("/html/body/ng-component/main/ng-component/div/role-manage/div[2]/div/column-table/div[2]/div[1]/ul/li[4]")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/role-manage/div[2]/div/column-table/div[2]/div[1]/ul/li[5]")).click();
    }

    @Test
    public void clickStatistic() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_statistic")).click();
        $$("h4").findBy(text("Замовлення")).shouldBe(visible);
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div/customer-favorite-stats/div[1]/div[3]/div[1]/div[2]/div[2]/date-input/label")).click();
        $(By.linkText("5")).click();
        //Select the calendar last day
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div/customer-favorite-stats/div[1]/div[3]/div[2]/div/div[2]/date-input/label/input")).click();
        $(By.linkText("22")).click();
        //Click the button for change service
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div/customer-favorite-stats/div[1]/div[4]/div[1]/div/div[2]/multiple-select/div/button")).click();
        //click the checkbox for change viewing of result
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div/customer-favorite-stats/div[1]/div[4]/div[2]/ul/li[3]")).click();
    }

    @Test
    public void clickingSideBarInStatistic() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_statistic")).click();
        $(By.linkText("Звіти по перегляду новин")).click();
        $(By.linkText("Звіти по даних користувачів (замовленя)")).click();
        $(By.linkText("Продуктивність адмінів")).click();
        $(By.linkText("Замовлення (оброблені,необроблені, відмінені)")).click();
        $(By.linkText("i")).hover();
        $(By.linkText("Замовлення адміністраторів")).click();
        $(By.linkText("Відгуки")).click();
        /*$(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div/stats-customer-review/div[1]/div[3]/div[1]/div[2]/div[2]/date-input/label")).click();
        //Choose the date
        $(By.linkText("6")).click();
        //Open the calendar
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div/stats-customer-review/div[1]/div[3]/div[2]/div/div[2]/date-input/label")).click();
        //Choose the last day in calendar
        $(By.linkText("23")).click();*/
    }

    @Test
    public void createTheDiscount() {
        AuthToAdminPage();
        $(By.cssSelector("a.nav-menu_sale")).click();
        $$("h1").findBy(text("Знижки")).shouldBe(visible);
        $(By.cssSelector("input[type=text]")).val("Header of discount");
        $(By.cssSelector("input[type=number]")).val("10");
        //Choose the end date
        $(By.xpath("/html/body/ng-component/main/ng-component/div/discount-form/div[1]/div/div[2]/div[3]/div[2]/label/date-input/label/input")).click();
        $(By.linkText("14")).click();
        $(By.cssSelector(".legend_block")).click();
        //Click the button for add clients
        $(By.linkText("Вибрати клієнтів")).click();
        $$("h4").findBy(text("Додати клієнта")).shouldBe(visible);
    }
}
