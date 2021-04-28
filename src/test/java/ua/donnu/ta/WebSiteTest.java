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

public class WebSiteTest {

    private WebDriver driver;


    @BeforeEach
    public void TestsSetUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
    }

    @Test
    public void CheckThatZeroItemsFound() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys("ahsdvbaksjdvbhasdbhashdb");
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id='nav-search-submit-button']")).click();
        Thread.sleep(300);
        String ZeroValueTemplate1 = driver.findElement(By.xpath("//div[@class='a-row']/span[1]")).getText();
        String ZeroValueTemplate2 = driver.findElement(By.xpath("//div[@class='a-row']/span[2]")).getText();
        String ZeroValueTemplate3 = driver.findElement(By.xpath("//div[@class='a-row']/span[3]")).getText();
        String SearchResult = ZeroValueTemplate1 + ' ' + ZeroValueTemplate2 + ZeroValueTemplate3;
        Assertions.assertEquals("No results for ahsdvbaksjdvbhasdbhashdb.", SearchResult);
    }

    @Test
    public void CheckThatNameOfTheProductIsInEveryResult() throws InterruptedException {
        String value = "knife";
        driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys(value);
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id='nav-search-submit-button']")).click();
        Thread.sleep(300);
        List<WebElement> listOfProducts = driver.findElements(By.xpath("//div[@class='sg-col-4-of-12 s-result-item s-asin sg-col-4-of-16 sg-col sg-col-4-of-20']"));
        for (int i = 1; i < listOfProducts.size(); i++){
            Assertions.assertTrue(listOfProducts.get(i).getText().toLowerCase().contains(value));
        }
    }

    @Test
    public void CheckThatSortByFunctionWorkingCorrectly() throws InterruptedException {
        String value = "knife";
        driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys(value);
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@id='nav-search-submit-button']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//*[@class='a-button-text a-declarative']")).click();
        Thread.sleep(50);
        driver.findElement(By.xpath("//*[@id='s-result-sort-select_1' and contains(@class, 'a-dropdown-link')]")).click();
        List<WebElement> listOfSortedPrices = driver.findElements(By.xpath("//*[@class='a-price']"));
        List<Float> listOfPrices = new ArrayList<Float>();
        for (WebElement e: listOfSortedPrices) {
            listOfPrices.add(Float.parseFloat(e.getText().substring(1, 6)));
        }
        List<Float> pricesList = listOfPrices;
        Collections.sort(pricesList);
        Assertions.assertEquals(listOfPrices, pricesList);
    }

    @Test
    public void CheckIncorrectSignInValues() throws InterruptedException {
        driver.findElement(By.xpath("//a[@class='nav-a nav-a-2   nav-progressive-attribute']")).click();
        driver.findElement(By.xpath("//*[@class='a-input-text a-span12 auth-autofocus auth-required-field']")).sendKeys("ashdiashd@asdasd.aosuie");
        driver.findElement(By.xpath("//div[@class='a-section']/span[1]")).click();
        Thread.sleep(100);
        String errorMessage = driver.findElement(By.xpath("//div[@class='a-box-inner a-alert-container']/h4")).getText();
        Assertions.assertEquals("There was a problem", errorMessage);
    }

    @AfterEach
    public void shutdown() {driver.close();}

}
