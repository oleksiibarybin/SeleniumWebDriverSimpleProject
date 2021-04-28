package ua.donnu.ta;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EBay_Testing {
    private WebDriver driver;

    @BeforeEach
    public void testsSetUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.ebay.com/");
    }

    @Test
    public void zeroItemsFoundCheck() throws InterruptedException {
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id='gh-ac']")).sendKeys("somethingThatWon`tBeFound");
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id=\'gh-btn\']")).click();
        String searchOutput = driver.findElement(By.xpath("//*[contains(@class, \'srp-save-null-search__heading\')]")).getText();
        Assertions.assertEquals("No exact matches found", searchOutput);
    }

    @Test
    public void checkProductNameInEveryResult() throws InterruptedException {
        String search_query = "phone";
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id='gh-ac']")).sendKeys(search_query);
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id=\'gh-btn\']")).click();
        List<WebElement> listOfProducts = driver.findElements(By.xpath("//*[@class = \'s-item__title\']"));
        for (int i = 1; i < listOfProducts.size(); i++) {
            Assertions.assertTrue(listOfProducts.get(i).getText()
                    .toLowerCase().contains(search_query));

        }

    }

    @Test
    public void checkSorting() throws InterruptedException {
        String search_query = "phone";
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id='gh-ac']")).sendKeys(search_query);
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id=\'gh-btn\']")).click();
        driver.findElement(By.xpath("//*[@id=\"s0-14-11-5-1[0]\"]/div[3]/div[1]")).click();
        driver.findElement(By.xpath("//*[@class = 'fake-menu-button__item'][4]")).click();
        driver.findElement(By.xpath("//*[@class=\"s-message__content\"]/a")).click();
        List<WebElement> listOfPrices = driver.findElements(By.xpath("//*[@class=\"s-item__price\"]"));
        List<Float> pricesList = new ArrayList<Float>();
        for (WebElement e : listOfPrices) {
            pricesList.add(Float.parseFloat(e.getText().substring(1, 5)));
        }
        List<Float> pricesList2 = pricesList;
        Collections.sort(pricesList2);
        Assertions.assertEquals(pricesList, pricesList2);

    }

    @Test
    public void checkIncorrectEmailFormat() throws InterruptedException{
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"userid\"]")).sendKeys("icorrectemailformat@check.com");
        Thread.sleep(300);
        String searchOutput = driver.findElement(By.xpath("//*[@id=\"signin-error-msg\"]")).getText();
        Assertions.assertEquals("Oops, that's not a match.", searchOutput);
    }

}


