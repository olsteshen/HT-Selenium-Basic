package first_test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class BookDepositoryTest {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup() {
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
    }

    @Test(description = "Add product to basket")
    public void addProductToBasket() {
        driver.get("https://www.bookdepository.com");

        WebElement logo = driver.findElement(By.xpath("//*[@class='brand-link']"));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(logo));

        WebElement signInJoin = driver.findElement(By.xpath("//div[@class='user-nav-wrap ']/descendant::*[@href='/account/login/to/account']"));
        WebElement navMenu = driver.findElement(By.xpath("//li[@class='tbd-dropdown category-dropdown mob-nav-shop dropdown']"));
        WebElement bannerReadCreate = driver.findElement(By.xpath("//div[@class='paragraph-content']/a[@href='/readcreate']"));
        signInJoin.isDisplayed();
        navMenu.isDisplayed();
        bannerReadCreate.isDisplayed();
        driver.findElement(By.xpath("//input[@name='searchTerm']")).sendKeys("manager");
        driver.findElement(By.xpath("//button[@class='header-search-btn']")).click();
        List<WebElement> searchResults = driver.findElements(By.xpath("//*[@class='book-item']"));
        WebElement addToCartProductToBuy = driver.findElement(By.xpath("//div[@class='btn-wrap']/descendant::*[@data-isbn='9780008128043']"));
        new WebDriverWait(driver, 1)
                .until(ExpectedConditions.visibilityOfAllElements(searchResults));
        addToCartProductToBuy.click();
        new WebDriverWait(driver, 2)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-dialog modal-md']")));
        driver.findElement(By.xpath("//div[@class='modal-dialog modal-md']/descendant::button[@class='close']")).click();
        WebElement productToBuy = driver.findElement(By.xpath("//div[@class='item-img' and a[contains(@href,'New-One-Minute-Manager')]]"));
        productToBuy.click();
        String itemsInCart = driver.findElement(By.xpath("//div[@class='secondary-header']/descendant::span[@class='item-count']")).getText();
        Assert.assertEquals(itemsInCart, "1");
    }

    @AfterMethod(alwaysRun = true)
    public void browserCloseDown() {
        driver.quit();
        driver = null;
    }

}
