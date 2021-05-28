package ua.donnu.ta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RozetkaTesting {

    public static WebDriver driver;
    public WebElement searchBox = driver.findElement(By.xpath("//*[@class='search-form__input ng-valid ng-touched ng-dirty']"));
    public WebElement searchButton = driver.findElement(By.xpath("//*[@class='search-form__input ng-valid ng-touched ng-dirty']"));

    @BeforeEach
    public void openChrome(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void CheckNoSearchResultsMessage() {
        driver.get("https://rozetka.com.ua/ua/");
        searchBox.sendKeys("qwertyuiop");
        searchButton.click();
        String noResMessage = "За заданими параметрами не знайдено жодної моделі";
        String actualMessage = driver.findElement(By.xpath("//*[@class = 'catalog-empty']/span")).getText();
        Assertions.assertEquals(noResMessage, actualMessage);

    }

}
