
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
//Created be Den Pavluk 13.02.18
public class OmniUserTest {

    @BeforeClass
            public static void onlyOnce() {
        Configuration.browserSize = "1024x768";
    }
    //The old version of code on domain
    String authPage = "http://less.omniecom.com/auth/signin";
    //New domain - less
    //String authPage = "http://dev.omniecom.com/auth/signin";
    //String urlToAdmin = "http://less.omniecom.com/business";

    @Test
    @DisplayName("Authorization user with invalid login")
    public void userToAuthBadLogin() {
        open(authPage);
        $(By.cssSelector("input[type=text]")).setValue("dennssddfff@ukr.net");
        $(By.cssSelector("input[type=password]")).setValue("testtest").pressEnter();
        $(".modal-text").shouldHave(text("Такого користувача не існує"));
        $("a.confirm").click();
        String actualTitle = $("title").innerText();
        System.out.println("Title of authorization page is: " + actualTitle);
        Assert.assertEquals(actualTitle, "NgFront");
        refresh();
    }

    //Authorization with invalid password
    @Test
    public void userAuthWithBadPass() {
        open(authPage);
        $(By.cssSelector("input[type=text]")).setValue("denpavluk@ukr.net");
        $(By.cssSelector("input[type=password]")).setValue("testtest2").pressEnter();
        $(".modal-text").shouldHave(text("Пароль вказано невірно"));
        $("a.confirm").click();
        refresh();
    }

    //Authorization user with valid login and password
    @Test
    public void ValidAuthAndExit() {
        open(authPage);
        $(By.cssSelector("input[type=text]")).setValue("denpavluk@ukr.net");
        $(By.cssSelector("input[type=password]")).setValue("testtest").pressEnter();
        $(".map_button").isDisplayed();
        $(".log_out").click();
    }

    //Authorisation user with valid login and passwords
    @Test
    public void authValidUser() {
        open(authPage);
        $(By.cssSelector("input[type=text]")).setValue("denpavluk@ukr.net");
        $(By.cssSelector("input[type=password]")).setValue("testtest").pressEnter();
        if($("h1[_ngcontent-c5]").isDisplayed()) {
            System.out.println("The screen is biggest");
        } else {
            $(".map_button").isDisplayed();
            System.out.println("The screen  is smallest then 1000 px");
        }
    }

    //Clicking the first organisation in list and click on the first service in list
    @Test
    public void clickingCategoryAndService() {
        authValidUser();
        $(By.cssSelector("a.menu-mobile-btn")).click();
        open("http://less.omniecom.com/client/organization/dd3d7811-bd8a-4b19-9721-d94e7e431de1");
        //$(By.cssSelector(".main[_ngcontent-c5] ul.tabs_menu li:nth-child(3)")).click();
        $(By.cssSelector("div.backg")).click();
        //$(By.cssSelector("ul.service_item_wrapper li:nth-child(3)")).click();
        $(By.cssSelector("a.nav-menu_category")).click();
    }

    //Making an order
    @Ignore
    @Test
    //Test is not worked - styles have been changed
    public void makeOrder() {
        authValidUser();
        $(By.cssSelector(".main[_ngcontent-c5] ul.tabs_menu li:nth-child(3)")).click();
        $(By.cssSelector("ul.service_item_wrapper li:nth-child(3)")).click();
        //String categ = $(By.cssSelector("ul.service_item_wrapper li:nth-child(3) h2")).innerText();
        String categ = $(By.xpath("/html/body/ng-component/main/service-info/main/div/div/div/div[1]/div/div/h2")).getText();
        System.out.println(categ);
        $(".scheduler_wrapper").shouldHave(text("Оберіть дату та час "));
        $(By.id("nextPage")).click();
        $(".scheduler_wrapper").shouldHave(text("Оберіть додаткову послугу "));
        $(By.id("nextPage")).click();
        $(".scheduler_wrapper").shouldHave(text("Перевірте своє замовлення"));
        $(By.cssSelector("a.confirm")).shouldHave(text("Підтвердити")).click();//Confirm the order
        $(".modal-content").shouldHave(text("Ваше замовлення відправлено на обробку"));
        $(By.cssSelector(".modal-content a.confirm")).shouldHave(text("OK")).click();
        $(By.cssSelector("a.nav-menu_my_order")).click();
        $(By.cssSelector(".my_orders ")).shouldHave(text("Мої замовлення"));//Clicking the "My orders"link
        $(By.cssSelector("ul.block_item_wrapper")).shouldHave(text(categ)).click();
        $(By.cssSelector(".modal-body")).shouldHave(text("Замовлення")).click();
        $(By.cssSelector("a.yellow_button")).shouldHave(text("Скасувати")).click();//Deleting the order
        clearBrowserCookies();
    }

    //Adding organization to fauvorite
    @Ignore
    @Test
    public void AddToFauvorite() {
        authValidUser();
        String category_title_fauvorite;
        if($(By.cssSelector("ul.tabs_menu li:nth-child(1) img[src*='start_fauvorite.png']")).isDisplayed()){
            $(By.cssSelector("ul.tabs_menu li:nth-child(1) img[src*='start_fauvorite.png']")).click();
            category_title_fauvorite = $(By.cssSelector(("ul.tabs_menu li:nth-child(1) h3"))).innerText();
            //System.out.println("Category clicked to fauvorite:" + category_title_fauvorite);
        }
        else if($(By.cssSelector("ul.tabs_menu li:nth-child(2)  img[src*='start_favorite.png']")).isDisplayed()){
            $(By.cssSelector("ul.tabs_menu li:nth-child(2)  img[src*='start_favorite.png']")).click();
            category_title_fauvorite = $(By.cssSelector(("ul.tabs_menu li:nth-child(2) h3"))).innerText();
            //System.out.println("Category clicked to fauvorite:" + category_title_fauvorite);
        }
        else if($(By.cssSelector("ul.tabs_menu li:nth-child(3)  img[src*='start_favorite.png']")).isDisplayed()){
            $(By.cssSelector("ul.tabs_menu li:nth-child(3)  img[src*='start_favorite.png']")).click();
            category_title_fauvorite = $(By.cssSelector(("ul.tabs_menu li:nth-child(3) h3"))).innerText();
            //System.out.println("Category clicked to fauvorite:" + category_title_fauvorite);
        }
        else {
            $(By.cssSelector("ul.tabs_menu li:nth-child(4)  img[src*='start_favorite.png']")).click();
            category_title_fauvorite = $(By.cssSelector(("ul.tabs_menu li:nth-child(4) h3"))).innerText();
            //System.out.println("Category clicked to fauvorite:" + category_title_fauvorite);
        }
        System.out.println("Category clicked to fauvorite:" + category_title_fauvorite);
        //Go to fauvorite organisation
        $(By.cssSelector("a.nav-menu_love")).click();
        //$(By.text("Улюблене"));
        $(By.id("organization")).shouldHave(text(category_title_fauvorite)).$(By.xpath("//*[@id=\"organization\"]/organization-favorites-list/ul/li[1]/organization-favorite/a/div/div[2]/div/div[2]/div/em/favorite/img")).click();
    }

    //Writing mesage on the page(maesage) of the sidebar
    @Test
    public void wirteMessage() {
        authValidUser();
        $(By.id("page-userMessage")).click();
        //$(byCssSelector("ul.message_list li:nth_child(1)")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[2]/div/ng-component/ul/conversation[1]/li/a/div/div[1]")).click();
        //$(byCssSelector(".text-center")).shouldHave(text("Повідомлення"));
        $(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[1]/div")).shouldHave(text("Повідомлення"));
        //Click the link to enter the message
        $(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[2]/div/ng-component/div/div/div/div[2]/div[2]/label/textarea")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[2]/div/ng-component/div/div/div/div[2]/div[2]/label/textarea")).setValue("auto-test.auto-test.auto-test");
        //Click the button for send the message
        $(By.cssSelector(".send_message")).click();
    }

    //Writing message on the organization page
    @Test
    public void writeMessageOnOrg() {
        authValidUser();
        //$(By.cssSelector("ul.tabs_menu li:nth-child(2)")).click();
        $(By.cssSelector("input[type=text]")).hover().click();
        open("http://less.omniecom.com/client/organization/dcdb52dc-6bb2-4bbd-bf7c-52608ddcee63");
        $(By.cssSelector("a.message")).click();
        //$(byCssSelector("img.footer_button")).click();
        $(By.cssSelector("h1")).shouldHave(text("Повідомлення"));
        $(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[2]/div/ng-component/div/div/div/div[2]/div[2]/label/textarea")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[2]/div/ng-component/div/div/div/div[2]/div[2]/label/textarea")).
                setValue("Auto-test set the text to this field 189 Українська Росы !@#$%^&**()_=-");
        $(By.cssSelector(".send_message")).click();
    }

    //Clicking all categories in the main list and testing search categories by word
    @Test
    public void clickingCategories() {
        authValidUser();
        //Condtition for small screen size
        //$$("span").findBy(text("Краса")).shouldBe(visible);
        System.out.println("Script running");
        if($$("span").findBy(text("Краса")).is(visible)) {
            System.out.println("Clicking the side bar categories");
            $(By.cssSelector("div.side_bar ul li:nth-child(1)")).hover().click();
            $(By.cssSelector("div.side_bar ul li:nth-child(2)")).hover().click();
            $(By.cssSelector("div.side_bar ul li:nth-child(3)")).click();
            $(By.cssSelector("div.side_bar ul li:nth-child(4)")).click();
            $(By.cssSelector("div.side_bar ul li:nth-child(5)")).click();
            $(By.cssSelector("div.side_bar ul li:nth-child(6)")).click();
            $(By.cssSelector("input[placeholder]")).hover().setValue("Трускавка").pressEnter();
            $(By.cssSelector("ul.tabs_menu")).shouldHave(text("Трускавка"));
        } else {
            //Clicking all elements in dropdownlist
            Select dropdownCat = new Select($(By.cssSelector(".select2-hidden-accessible")));
            dropdownCat.selectByVisibleText("Краса");
            dropdownCat.selectByVisibleText("Медицина");
            dropdownCat.selectByVisibleText("Спорт");
            dropdownCat.selectByVisibleText("Транспорт");
            dropdownCat.selectByVisibleText("Розваги");
            dropdownCat.selectByVisibleText("Здоров`я");
        }
            }

    //Change user name
    //The test is not working - because function is not working for change username
    @Ignore
    @Test
    public void changeUserName() {
        authValidUser();
        $(By.xpath("/html/body/ng-component/user-menu/header/div/div/div[2]/div[2]/div/div/a/i[3]")).click();
        /*$(By.cssSelector("a.my-menu_my_cabinet")).click();
        System.out.println("work");
        $(By.cssSelector(".yellow")).shouldHave(text("Мій профіль"));*/
        open("http://less.omniecom.com/client/profile");
        //Copy the used user name
        String nameUser = $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[1]/label[1]/input")).getValue();
        //Print the using name
        System.out.println("The user nick is:" + nameUser);
        //clear the user name field
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[1]/label[1]/input")).clear();
        //set the new name to field
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[1]/label[1]/input")).setValue("Ururu");
        //Click the button for saving after changing user's name
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[4]/div[2]/input")).click();
        refresh();
        open("http://less.omniecom.com/client/profile");
        //$(By.cssSelector("a.my-menu_my_cabinet")).click();
        $(By.cssSelector(".yellow")).shouldHave(text("Мій профіль"));
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[1]/label[1]/input")).clear();
        //set the old name from string to field
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[1]/label[1]/input")).setValue(nameUser);
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[4]/div[2]/input")).click();
    }

    //Change user number phone and gender
    @Test
    public void changeGenderPhone() {
        authValidUser();
        $(By.xpath("/html/body/ng-component/user-menu/header/div/div/div[2]/div[2]/div/div/a/i[3]")).click();
        //$(By.cssSelector("a.my-menu_my_cabinet")).click();
        open("http://less.omniecom.com/client/profile");
        $(By.cssSelector("h1.yellow")).shouldHave(text("Мій профіль"));
        //set the new number phone to field
        $(By.cssSelector("input.phone")).clear();
        //$(byXpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[1]/phone-input/om-inputm/label/input")).clear();
        $(By.cssSelector("input.phone")).setValue("0660552523");
        //$(byXpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[1]/phone-input/om-inputm/label/input")).setValue("938799112233");
        //change the gender to female
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[2]/div/label[2]")).click();
        //click the save button for saving changes
        $(By.cssSelector("input.input_submit")).click();
        open("http://less.omniecom.com/client/profile");
        //$(By.cssSelector("a.my-menu_my_cabinet")).click();
        $(By.cssSelector(".yellow")).shouldHave(text("Мій профіль"));
        $(By.xpath("//*[@id=\"personal_data\"]/div/div[2]/div/div[2]/div/label[1]")).click();
        $(By.cssSelector("input.input_submit")).click();
    }

    //Change setting on the setting page
    @Test
    public void changeSetting() {
        authValidUser();
        //click the "settings" link in the side menu
        $(By.xpath("/html/body/ng-component/user-menu/header/div/div/div[2]/div[2]/div/div/a/i[3]")).click();
        open("http://less.omniecom.com/client/settings");
        //$(By.cssSelector("a.my-menu_my_settings")).click();
        $(".yellow").shouldHave(text("Налаштування"));
        //Click the 1 item of menu
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div/ul/li[2]/settings-block/div[1]/div[2]/div/div/div")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div/ul/li[2]/settings-block/div[1]/div[2]/div/div/div")).click();
        System.out.println("The end");
    }

    //Register new user without saving on auth page
    @Test
    public void registerNoSaving() {
        open(authPage);
        $(By.xpath("/html/body/ng-component/main/content/div/div/div/div[2]/div[2]/div/p[1]/span/a")).shouldHave(text("Зареєструватися")).click();
        $(By.cssSelector("input")).setValue("Test");
        $("input").setValue("Etest");
        $("input[type=password]").setValue("abraabra");
        $("input[type=password]").setValue("abraabra");
    }

    //Checking page of the omniecard
    @Test
    public void clickOmnieCard() {
        authValidUser();
        $("a.nav-menu_omnie_card").click();
        $(By.cssSelector(".omnie_card")).isDisplayed();
        $(By.cssSelector(".log_out")).click();
    }

    //Checking the order
    @Test
    public void checkMyOrder() {
        authValidUser();
        $(By.cssSelector("a.nav-menu_my_order")).click();
        if($(By.cssSelector(".nav-tabs")).shouldHave(text("Мої замовлення")).isDisplayed()) {
            System.out.println("Ukrainian is enabled");
        } else {
            $(By.xpath("//*[@id=\"mCSB_3_container\"]/div[3]/div/a[1]/span")).click();
            System.out.println("Clicked to Ukrainian");
        }
        //click the calendar link for choosing the first day
        $(By.xpath("//*[@id=\"start_period\"]")).click();
        //$(byXpath("//*[@id=\"start_period\"]")).click();
        //click the 1 day of the month
        $(By.linkText("5")).hover().click();
        //old version of clicking
        //$(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[1]/td[4]/a")).click();
        //$(byXpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[1]/td[5]/a")).click();
        //click the calendar link for choosing the last day
        $(By.xpath("//*[@id=\"end_period\"]")).hover().click();
        //click the date (28) of the month
        $(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[5]/td[3]/a")).hover().click();
        //click the starus of orders
        Select statusSelect = new Select($(By.cssSelector(".select2-hidden-accessible")));
        //Choice status "Підтверджено організацією"
        statusSelect.selectByVisibleText("Підтверджено організацією");
    }

    //Check the card holder on the sidebar
    @Test
    public void ClickCardHolder() {
        authValidUser();
        $(By.cssSelector("a.nav-menu_card")).click();
        String inscription = $(By.cssSelector("h1")).getValue();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[2]/ul/li[2]/a")).click();
        System.out.println(inscription + " this is result");
    }

    //Click the gallery
    @Test
    public void clickTheGallery() {
        authValidUser();
        $(By.cssSelector("a.nav-menu_category")).click();
        $(By.cssSelector("h3")).shouldHave(text("test")).click();
        //$(By.linkText("Аеробус")).click();
        //$(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[4]/div/user-gallery/div/div/div/div/div[7]")).click();
        //open the img in gallery
        $(By.xpath("/html/body/ng-component/main/ng-component/main/div/div[4]/div/user-gallery/div/div/div/div/div[5]/img")).click();
        //click the next img
        $(By.cssSelector("div.lg-next")).click();
        //close the open img
        $(By.cssSelector("span.lg-close")).hover().click();
        //click the next img
        if($(By.cssSelector("button.slick-prev")).isDisplayed()) {
            $(By.cssSelector("button.slick-prev")).hover().click();
            $(By.cssSelector("button.slick-prev")).hover().click();
            $(By.cssSelector("button.slick-next")).hover().click();
        } else {
            System.out.println("Screen is small, buttons don't showing");
        }
    }

    //Open the help page (information page)
    @Test
    public void openTheHelpPage() {
        authValidUser();
        //$(By.cssSelector("a.my-menu_my_reference")).click();
        $(By.cssSelector("a.menu-mobile-btn")).click();
        open("http://less.omniecom.com/client/help");
        $(By.cssSelector("ul.reference_list")).isDisplayed();
        $(By.linkText("Особисті дані")).click();
        $(By.linkText("Як змінити пароль?")).click();
        $(By.id("scrollUp")).click();
    }

    //Click the feed page
    @Test
    public void clickFeed() {
        authValidUser();
        $(By.cssSelector("a.nav-menu_news")).click();
        $(By.linkText("Новини")).isDisplayed();
        System.out.println("News is displayed");
        $(By.linkText("Новини")).click();
        if($(By.cssSelector("ul.news_wrapper li img")).isDisplayed()) {
            $(By.cssSelector("ul.news_wrapper li img")).hover().click();
            System.out.println("The 1 new is opened");
        } else {
            System.out.println("The news are empty");
        }
        $(By.cssSelector("a.nav-menu_news")).click();
        $(By.linkText("Акції")).click();
        System.out.println("Shares is opened");
        //$(By.cssSelector("ul.news_wrapper li p")).click();
        //$(By.cssSelector("ul.news_wrapper li:nth-child(1)")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div[3]/div/news-list/ul/li[1]/news-item/div[2]/div/p")).click();
    }

    //check the order templates page
    @Test
    public void checkTemplates() {
        authValidUser();
        $(By.cssSelector("a.nav-menu_my_order")).click();
        $(By.linkText("Мої шаблони")).click();
        //$(byCssSelector("ul[_ngcontent-c32] li:nth-child(2)")).click();
        $(By.xpath("//*[@id=\"my_templates\"]/div/order-templates/div/ul/li[2]")).click();
        $(By.linkText("Використати як шаблон")).isDisplayed();
        $(By.xpath("//*[@id=\"my_templates\"]/div/order-templates/div/ul/li[2]/div[1]")).click();
    }

    //Using the template of order
    @Test
    public void useTheTemplate() {
        authValidUser();
        $(By.cssSelector("a.nav-menu_my_order")).hover().click();
        $(By.linkText("Мої шаблони")).click();
        if($(By.cssSelector("div.image_opened_tempelate")).isDisplayed()) {
            $(By.cssSelector("div.image_opened_tempelate")).hover().click();
        } else {
            $(By.xpath("/html/body/ng-component/main/ng-component/div/div[2]/div[2]/div/order-templates/div/ul/li[1]/div[1]/div[2]/div/div/h3")).click();
        }
        Date current = new Date();
        System.out.println(current);
        $(By.cssSelector("a.confirm")).click();
        //<a class="ui-state-default" href="#">26</a>
        $(By.xpath("/html/body/modal-container/div/div/div/div/calendar/div/div/table/tbody/tr[5]/td[1]/a")).hover().click();
        $(By.xpath("/html/body/modal-container/div/div/div/div/div/div/div/div[2]/a")).hover().click();
        //Select the date - 26 of the month
        $(By.xpath("/html/body/ng-component/main/service-info/main/div/div/div[2]/user-order/div/div[3]/div[1]/div[1]/calendar/div/div/table/tbody/tr[5]/td[1]/a")).click();
        $(By.linkText("Далі")).click();

        $(By.xpath("/html/body/ng-component/main/service-info/main/div/div/div[2]/user-order/div/div[6]/div[2]/a")).click();
        $(By.xpath("/html/body/ng-component/main/service-info/main/div/div/div[2]/user-order/div/div[4]/div/div[6]/div/div/div[2]/a")).click();
        //Click OK button on the modal window
        $(By.xpath("/html/body/info-modal/div/div/div/div/div[2]/div/div/div/a")).click();
    }

    //Searching words on the services page
    @Test
    public void checkingSearch() {
        authValidUser();
        //click the dropdown list
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div[2]/div/div[3]/label/omnie-select/span/span[1]/span/span[2]")).hover().click();
        //Choose the link for viewing services
        $(By.xpath("/html/body/span/span/span[2]/ul/li[2]")).click();
        $(By.cssSelector("input")).val("Delete Trump").pressEnter();
        //Equals the result of search to word
        $$("li").findBy(text("Delete Trump")).shouldBe(visible);
        //click the dropdown list
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/div[2]/div[2]/div/div[3]/label/omnie-select/span/span[1]/span/span[2]")).hover().click();
        //Choose the link for viewing organisation
        $(By.xpath("/html/body/span/span/span[2]/ul/li[1]")).click();
        $(By.cssSelector("input")).val("Pentagon").pressEnter();
        $$("li").findBy(text("Pentagon")).shouldBe(visible);
    }
}
