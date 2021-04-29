import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Allo_Koval {
    private WebDriver driver;

    /*BeforeAll
    public static void profileSetUp(){
        System.setProperties("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
    }*/
    @BeforeEach
    public void testsSetup() {
        WebDriverManager.chromedriver().version("90.0.4430.24").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }


    @Test
    @DisplayName("TestforNullSearch")
    public void forNullSearch() throws InterruptedException {
        driver.get("https://allo.ua/");
        Thread.sleep(200);
        WebElement inputString = driver.findElement((By.id("search-form__input")));
        WebElement searchButton = driver.findElement(By.xpath("//button[@class = 'search-form__submit-button']")); /*кнопка поиска*/
        new Actions(driver)
                .moveToElement(inputString)
                .doubleClick(inputString)
                .sendKeys(inputString, "SADFNSDHJFGSDU")
                .click(searchButton)
                .build()
                .perform();
        Thread.sleep(500);
        WebElement errorMessage = driver.findElement(By.xpath("//p[@class = 'v-catalog__empty']"));

        Assertions.assertEquals("Нажаль, нічого не знайдено.", errorMessage.getText());

    }


    @Test
    @DisplayName("checkIncorectEmeil")
    public void cheakoforIncorectEmeil() throws InterruptedException {
        driver.get("https://allo.ua/");
        Thread.sleep(200);
        WebElement enterMark = driver.findElement(By.xpath("//button[@class='authentication__button--login']"));
        WebElement inputAuthrization = driver.findElement(By.xpath("//input[@name = 'email']"));
        WebElement enterButton = driver.findElement(By.xpath("//button[@type = 'button']"));

        new Actions(driver)
                .moveToElement(enterMark)
                .doubleClick(enterMark)
                .sendKeys(inputAuthrization, "SADFNSDHJFGSDU")
                .click(enterButton)
                .build()
                .perform();
        Thread.sleep(500);
        WebElement errormassage = driver.findElement(By.xpath("//div[@class = 'modal-input validation-error']"));

        Assertions.assertEquals("Будь ласка, введіть коректний email. Наприклад, johndoe@domain.com.", errormassage.getText());
    }


    @Test
    @DisplayName("checkForCorrectSearch")
    public void checkForCorrectSearch() throws InterruptedException {
        driver.get("https://allo.ua/");
        Thread.sleep(200);
        WebElement inputString = driver.findElement((By.id("search-form__input")));
        WebElement searchButton = driver.findElement(By.xpath("//button[@class = 'search-form__submit-button']")); /*кнопка поиска*/
        new Actions(driver)
                .moveToElement(inputString)
                .doubleClick(inputString)
                .sendKeys(inputString, "Samsung a52")
                .click(searchButton)
                .build()
                .perform();
        Thread.sleep(500);
        List<WebElement> listOfTitles = driver.findElements(By.xpath("//a[@class = 'product-card__title']")); /*viznachaem te elementi kotorie imeut zagolovok*/

        int i;

        for (i = 0; i < listOfTitles.size(); i++) {
            Assertions.assertTrue(listOfTitles.get(i).getAttribute("title").contains("Samsung"));
        }


    }

    @Test
    @DisplayName("checkForCorectSort")
    public void checkForCorectSort() throws InterruptedException {
        driver.get("https://allo.ua/");
        Thread.sleep(200);
        WebElement inputString = driver.findElement((By.id("search-form__input")));
        WebElement searchButton = driver.findElement(By.xpath("//button[@class = 'search-form__submit-button']")); /*кнопка поиска*/
        new Actions(driver)
                .moveToElement(inputString)
                .doubleClick(inputString)
                .sendKeys(inputString, "батарейки panasonic")
                .click(searchButton)
                .build()
                .perform();
        Thread.sleep(500);

        WebElement sortString = driver.findElement(By.xpath("//span[@class = 'sort-by__current']"));

        Actions sorting = new Actions(driver);
        sorting.moveToElement(sortString).build().perform();

        Thread.sleep(500);

        driver.findElement(By.xpath("//*[@class = 'sort-by__item'][1]")).click();
        Thread.sleep(500);


        List<WebElement> priceTitle = driver.findElements(By.xpath("//span[@class = 'sum']"));

        List<Float> priceList = new ArrayList<Float>();


        int i;
        for (i = 0; i < priceTitle.size(); i++) {
            priceList.add(Float.parseFloat((priceTitle.get(i).getText() )));
        }

        List<Float> priceList2 = priceList;
        Collections.sort(priceList2);
        System.out.print(priceList);
        Assertions.assertEquals(priceList, priceList2);
    }





        @AfterEach
    public void tearDown() {driver.close();}
}
