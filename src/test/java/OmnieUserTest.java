
//Author Den Pavluk 09.02.18
import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class OmnieUserTest {
    String urlToAdmin = "http://less.omniecom.com/business";
    @Before
    public void beforeTest() {
        System.setProperty("webriver.chrome.driver", "C:/Users/den/Downloads/chromedriver_win32/chromedriver.exe");
        Configuration.browser = "chrome";
    }

    @Test
    public void AuthToAdminPage() {
        open(urlToAdmin);
        $(By.cssSelector("input[type=text]")).val("denpavluk@ukr.net");
        $(By.cssSelector("input[type=password]")).val("testtest").pressEnter();
        $(By.cssSelector("a.my-menu_my_admin")).click();
    }

    @Ignore
    @Test
    public void ClickTheOrganisation() {
        AuthToAdminPage();
        //Can't working this test - not working xpath
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/organizations-list/div/organization-list-item[1]/div/div/a/div[2]")).click();
        $(By.xpath("/html/body/ng-component/main/ng-component/div/div/organizations-list/div/organization-list-item[1]/div/div/a")).click();
        $(By.cssSelector("h1")).shouldHave(text("Детальна інформація про організацію "));
        $(By.cssSelector("div.map_open_button")).click();
        $(By.cssSelector(".confirm")).click();
        $(By.cssSelector("h1")).shouldHave(text("Детальна інформація про організацію "));
    }

    @Test
    public void ClickTheOrders() {
        AuthToAdminPage();
        //Not working link to orders
        $(By.cssSelector("a.my-organizations")).click();

    }

}
