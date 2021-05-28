package ua.donnu.ta;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RozetkaTesting {

    public static WebDriver driver;

    @BeforeEach
    public void openChrome(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void CheckNoSearchResultsMessage() throws InterruptedException{
        Thread.sleep(3000);
        driver.get("https://rozetka.com.ua/ua/");
        Thread.sleep(3000);
        WebElement searchBox = driver.findElement(By.name("search"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@class='button button_color_green button_size_medium search-form__submit " +
                "ng-star-inserted']"));
        searchBox.sendKeys("qwertyuiop");
        searchButton.click();
        String noResMessage = "За заданими параметрами не знайдено жодної моделі";
        String actualMessage = driver.findElement(By.xpath("//*[@class = 'catalog-empty']/span")).getText();
        Assertions.assertEquals(noResMessage, actualMessage);

    }

    @Test
    public void checkSorting() throws InterruptedException{
        Thread.sleep(3000);
        driver.get("https://rozetka.com.ua/ua/");
        String input = "mirror";
        WebElement searchBox = driver.findElement(By.name("search"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@class='button button_color_green button_size_medium search-form__submit " +
                "ng-star-inserted']"));
        searchBox.sendKeys(input);
        searchButton.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@class = 'catalog-settings__sorting']/select")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@class = 'catalog-settings__sorting']/select/option[2]")).click();
        Thread.sleep(3000);
        List<WebElement> output = driver.findElements(By.xpath("//*[@class = 'goods-tile__price-value']"));
        List<Float> prices = new ArrayList<>();
        List<Float> sortPrices;
        for (WebElement element : output) {
            prices.add(Float.parseFloat(element.getText()));
        }
        sortPrices = prices;
        sortPrices.sort(Comparator.naturalOrder());
        Assertions.assertEquals(prices, sortPrices);

    }

    @Test
    public void titlesCheck() throws InterruptedException{
        Thread.sleep(3000);
        driver.get("https://rozetka.com.ua/ua/");
        String input = "mirror";
        WebElement searchBox = driver.findElement(By.name("search"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@class='button button_color_green button_size_medium search-form__submit " +
                "ng-star-inserted']"));
        searchBox.sendKeys(input);
        searchButton.click();
        Thread.sleep(3000);
        List<WebElement> output = driver.findElements(By.xpath("//*[@class='goods-tile__title']"));
        List<String> title = new ArrayList<>();
        for (WebElement element: output){
            title.add(element.getText());
        }
        for (String element: title){
            Assertions.assertTrue(element.contains(input));
        }

    }

    @Test
    public void checkEmail() throws InterruptedException{
        Thread.sleep(3000);
        driver.get("https://rozetka.com.ua/ua/");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@class = 'header__button ng-star-inserted']")).click();
        driver.findElement(By.id("auth_email")).sendKeys("11232erwef3");
        Thread.sleep(3000);
        String actual = driver.findElement(By.xpath("//*[@class= 'form__row validation_type_error']/p")).getText();
        Assertions.assertEquals("Введено невірну адресу ел. пошти або номер телефону", actual);
    }

    @AfterEach
    public void browserClose(){
        driver.close();
    }

}
