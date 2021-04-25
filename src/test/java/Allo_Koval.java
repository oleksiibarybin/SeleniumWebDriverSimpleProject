import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Allo_Koval {
    private WebDriver driver;
    /*BeforeAll
    public static void profileSetUp(){
        System.setProperties("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
    }*/
    @BeforeEach
    public void testsSetup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.donnu.edu.ua/uk/");
    }

    @Test
    public void Teardown() throws InterruptedException{

    }
    @AfterEach
    public void tearDown() {driver.close();}
}
