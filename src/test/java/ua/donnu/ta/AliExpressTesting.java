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
import java.util.List;

public class AliExpressTesting {
    private WebDriver driver;

    @BeforeEach
    public void testsSetUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.aliexpress.com/");
        driver.findElement(By.xpath("//*[@class='btn-close']")).click();
    }

    @Test
    public void CheckErrorMessageIfNoItemsFound() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.id("search-key"));
        String searchWord = "0";
        searchBox.sendKeys(searchWord);
        WebElement searchButton = driver.findElement(By.xpath("//*[@class = 'search-button']"));
        searchButton.click();
        String zeroResultsText = driver.findElement(By.xpath("//*[@class= 'query-help']/span")).getText();
        Assertions.assertEquals(String.format("Sorry, your search \"%s\" did not match any products. Please " +
                "try again.", searchWord), zeroResultsText);
    }
    @Test
    public void checkSearchWordInEveryResult() throws InterruptedException {
        String searchWord = "screen";
        WebElement searchBox = driver.findElement(By.id("search-key"));
        searchBox.sendKeys(searchWord);
        WebElement searchButton = driver.findElement(By.xpath("//*[@class = 'search-button']"));
        searchButton.click();
        List<WebElement> productsList = driver.findElements(By.xpath("//*[@class = 'item-title']"));
        for (int i = 1; i < productsList.size(); i++) {
            Assertions.assertTrue(productsList.get(i).getText().toLowerCase().contains(searchWord));
        }
    }

    @Test
    public void checkPriceSortIsCorrect() throws InterruptedException {
        String searchWord = "screen";
        WebElement searchBox = driver.findElement(By.id("search-key"));
        searchBox.sendKeys(searchWord);
        WebElement searchButton = driver.findElement(By.xpath("//*[@class = 'search-button']"));
        searchButton.click();
        driver.findElement(By.xpath("//*[@class = 'sort-item'][3]")).click();
        List<WebElement> prices = driver.findElements(By.xpath("//*[@class = 'price-current']"));
        List<Float> pricesList = new ArrayList<Float>();
        for (int i = 0; i<prices.size();i++) {
            pricesList.add(Float.parseFloat(prices.get(i).getText().substring(4)));
        }
        List<Float> sortedPricesList = pricesList;
        Collections.sort(sortedPricesList);
        Assertions.assertEquals(pricesList, sortedPricesList);
    }

    @Test
    public void checkIncorrectEmail() throws InterruptedException{
        driver.findElement(By.xpath("//*[@class='_34l2i']")).click();
        Thread.sleep(3000);
        WebElement emailBox = driver.findElement(By.xpath("//*[@id='fm-login-id']"));
        WebElement passwordBox = driver.findElement(By.xpath("//*[@id='fm-login-password']"));
        String email = "afichwihf";
        String password = "dscfsv";
        emailBox.sendKeys(email);
        passwordBox.sendKeys(password);
        driver.findElement(By.xpath("//*[@class = 'fm-button']")).click();
        String errorMessage = driver.findElement(By.xpath("//*[@class = 'fm-error-message']")).getText();
        Assertions.assertEquals("Your account name or password is incorrect.", errorMessage);
    }

    @AfterEach
    public void shutdown() {driver.close();}
}












