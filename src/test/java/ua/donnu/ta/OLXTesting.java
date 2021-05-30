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

public class OLXTesting {
    public static WebDriver driver;

    @BeforeEach
    public void driverInit(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Checking error message in case no products were found")
    public void ChecknoRes(){
        driver.get("https://www.olx.ua/");
        driver.findElement(By.id("headerSearch")).sendKeys("asdfg1234");
        driver.findElement(By.id("submit-searchmain")).click();
        String noRes = driver.findElement(By.xpath("//*[@class = 'emptynew  large lheight18']/p")).getText();
        Assertions.assertTrue(("Не найдено ни одного объявления, соответствующего параметрам поиска." +
                " Проверьте правильность написания или введите другие параметры поиска").contains(noRes));
    }

    @Test
    @DisplayName("Checking product is in every found title")
    public void CheckProdTitle(){
        driver.get("https://www.olx.ua/");
        String search = "router";
        driver.findElement(By.id("headerSearch")).sendKeys(search);
        driver.findElement(By.id("submit-searchmain")).click();

        List<WebElement> listOfTitles = driver.findElements(By.xpath("//a[@class = 'marginright5 link linkWithHash detailsLink']/strong"));

        int i;

        for (i = 0; i < listOfTitles.size(); i++) {
            Assertions.assertTrue(listOfTitles.get(i).getAttribute("title").contains(search));
        }

    }

    @Test
    @DisplayName("Checking sorting prices on website is correct")
    public void checkSorting(){
        driver.get("https://www.olx.ua/");
        driver.findElement(By.xpath("//*[@class = 'cookie-close abs cookiesBarClose']")).click();
        String search = "router";
        driver.findElement(By.id("headerSearch")).sendKeys(search);
        driver.findElement(By.id("submit-searchmain")).click();

        driver.findElement(By.xpath("//*[@class = 'dropdown fright']/dt")).click();
        driver.findElement(By.xpath("//*[@id= 'targetorder-select-gallery']/dd/ul/li[2]")).click();
        driver.findElement(By.xpath("//a[@class = 'link fright x-normal']")).click();
        List<WebElement> priceTitle = driver.findElements(By.xpath("//p[@class = 'price']/strong"));
        List<Float> priceList = new ArrayList<>();

        int i;
        for (i = 0; i < priceTitle.size(); i++) {
            priceList.add(Float.parseFloat((priceTitle.get(i).getText().replace(" ","").replace("грн", ""))));
        }

        List<Float> priceList2 = priceList;
        Collections.sort(priceList2);
        System.out.print(priceList);
        Assertions.assertEquals(priceList, priceList2);
    }

    @Test
    @DisplayName("Checking wrong email is entered")
    public void checkEmail(){
        driver.get("https://www.olx.ua/");
        driver.findElement(By.xpath("//span[@class = 'link inlblk']/strong")).click();
        driver.findElement(By.id("userEmail")).sendKeys("asdfg1234");
        driver.findElement(By.xpath("//*[@class = 'cookie-close abs cookiesBarClose']")).click();
        driver.findElement(By.id("se_userLogin")).click();
        String error = driver.findElement(By.xpath("//label[@class = 'error']")).getText();
        Assertions.assertEquals("Неправильный формат email или номера телефона", error);
    }

    @AfterEach
    public void tearDown() {driver.close();}

}
