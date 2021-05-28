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
import java.util.Comparator;
import java.util.List;

public class PromTesting {
    private WebDriver driver;

    @BeforeEach
    public void testsSetUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void ZeroProductsMessageCheck() throws InterruptedException {
        driver.get("https://prom.ua/");
        String query = "qasxcvhtrfghjkiu";
        driver.findElement(By.xpath("//*[@class = 'searchForm__input--3iHT-']")).sendKeys(query);
        driver.findElement(By.xpath("//*[@class = 'searchForm__button--2PLbd']")).click();
        Thread.sleep(3000);
        String result = driver.findElement(By.xpath("//*[@data-qaid='bad_search_title']")).getText();
        Assertions.assertEquals("На жаль, по запиту \"qasxcvhtrfghjkiu\" ми нічого не знайшли.", result);
    }

    @Test
    public void QueryInProductTitleCheck() throws InterruptedException{
        driver.get("https://prom.ua/");
        Thread.sleep(2000);
        String query = "sausage";
        driver.findElement(By.xpath("//*[@class = 'searchForm__input--3iHT-']")).sendKeys(query);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@class = 'searchForm__button--2PLbd']")).click();
        Thread.sleep(2000);
        List<WebElement> prodTitles = driver.findElements(By.xpath("//*[@data-qaid='product_name']"));
        for (WebElement e: prodTitles){
            Assertions.assertTrue((e.getText().toLowerCase()).contains(query));
        }
    }

    @Test
    public void CheckSort() throws InterruptedException{
        driver.get("https://prom.ua/");
        Thread.sleep(2000);
        String query = "sausage";
        driver.findElement(By.xpath("//*[@class = 'searchForm__input--3iHT-']")).sendKeys(query);
        driver.findElement(By.xpath("//*[@class = 'searchForm__button--2PLbd']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@data-qaid='sort_by_price']")).click();
        Thread.sleep(3000);
        List<WebElement> prodPrices = driver.findElements(By.xpath("//*[@data-qaid='product_price']"));
        List<Float> prices = new ArrayList();
        for (WebElement e: prodPrices){
            prices.add(Float.parseFloat(e.getText().replace(" грн.", "")));
        }
        List<Float> sortedPrices = prices;
        sortedPrices.sort(Comparator.naturalOrder());
        Assertions.assertEquals(sortedPrices,prices);
    }

    @Test
    public void CheckEmail() throws InterruptedException{
        driver.get("https://prom.ua/");
        Thread.sleep(2000);
        String incorrectEmail = "asdfghjjk";
        driver.findElement(By.xpath("//*[@data-qaid='sign-in']")).click();
        driver.findElement(By.xpath("//*[@data-qaid='go_sign_in_customer']")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("phone_email")).sendKeys(incorrectEmail);
        driver.findElement(By.id("phoneEmailConfirmButton")).click();
        Thread.sleep(3000);
        String errorMessage = driver.findElement(By.xpath("//*[@data-qaid='error_field']")).getText();
        Assertions.assertEquals("Введите email в формате example@email.com", errorMessage);
    }
    @AfterEach
    public void tearDown() {driver.close();}
}