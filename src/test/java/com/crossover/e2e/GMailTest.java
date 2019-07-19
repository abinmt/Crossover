
package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/*Name:Jisha Anie George
 *Crossover Assignment 
 * 18th July,2019
 * 
 */


public class GMailTest extends TestCase {
    public static WebDriver driver;
    public static  Properties properties = new Properties();
    
    
    @BeforeClass
    public void setUp() throws Exception {
        
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
        
    }
    @AfterClass
    public void tearDown() throws Exception {
       driver.quit();
    }

    /*
     * Please focus on completing the task
     * 
     */
    @Test
    public void testSendEmail() throws Exception {
    	
    	//*Logging in 
        driver.get("https://mail.google.com/");
        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));
        //Entering the username
        driver.findElement(By.id("identifierNext")).click();
        //Need to call and Explicit wait
        WebDriverWait wait=new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("password"))));
        wait=null;
        WebElement passwordElement = driver.findElement(By.name("password"));
        //Entering the password
        passwordElement.sendKeys(properties.getProperty("password"));
        driver.findElement(By.xpath("//*[text()='Next']")).click();
        

        
        //Composing,Labelling as Social and Sending Mail
        WebDriverWait wait1=new WebDriverWait(driver,40); 
        wait1.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div.T-I.J-J5-Ji.T-I-KE.L3"))));
        WebElement composeElement = driver.findElement(By.cssSelector("div.T-I.J-J5-Ji.T-I-KE.L3"));
        composeElement.click();
        String mailid=properties.getProperty("username");
        //Entering the To address
        driver.findElement(By.xpath("//*[@aria-label='To']")).sendKeys(properties.getProperty("username"));
        driver.findElement(By.xpath("//*[@aria-label='To']")).sendKeys(Keys.TAB);
        //Entering the Email Subject
        String emailSubject = properties.getProperty("email.subject");
        //Entering the Email Body
        String emailBody = properties.getProperty("email.body"); 
        driver.findElement(By.name("subjectbox")).click();
        driver.findElement(By.name("subjectbox")).sendKeys(emailSubject);
        driver.findElement(By.cssSelector(".Am.Al.editable.LW-avf")).click();;
        driver.findElement(By.cssSelector(".Am.Al.editable.LW-avf")).sendKeys(emailBody);
      
       
        driver.findElement(By.xpath("//*[@data-tooltip='More options']")).click();
         Actions action =new Actions(driver);
        action.moveToElement(driver.findElement(By.cssSelector("div.J-Ph.Gi.J-N"))).build().perform();
        WebDriverWait ww=new WebDriverWait(driver,20);
        ww.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.bqf")));
        driver.findElement(By.cssSelector("input.bqf")).sendKeys("Social");
        WebElement Labelmenu=driver.findElement(By.cssSelector(".J-M-Jz.aXjCH"));
        Labelmenu.findElement(By.xpath("//*[@title='Social'and @role='menuitemcheckbox']")).click();
        driver.findElement(By.xpath("//*[text()='Send']")).click();
        driver.findElement(By.cssSelector("div.T-I.J-J5-Ji.nu.T-I-ax7.L3")).click();
       driver.findElement(By.xpath("//div[text()='Social']")).click();
       driver.findElement(By.xpath("//input[@aria-label='Search mail']")).sendKeys("Subject of this message");
       driver.findElement(By.xpath("//input[@aria-label='Search mail']")).sendKeys(Keys.ENTER);
     //  driver.findElement(By.xpath("//div[@data-tooltip='Refresh']")).click();
       int totalrows=driver.findElements(By.xpath("//div[@class='Cp']/div/table/tbody/tr[*]")).size();
           for(int i=1;i<totalrows;i++) {
    	   if (driver.findElement(By.xpath("//div[@class='Cp']/div/table/tbody/tr["+i+"]")).getText().contains("Subject of this message"))
    	   {
    		   WebElement starred=driver.findElement(By.xpath("//div[@class='Cp']/div/table/tbody/tr[" +i+"]/td[3]/span"));
    	       Actions act=new Actions(driver);
    	      act.moveToElement(starred).click().perform();
    	      driver.findElement(By.xpath("//div[@class='Cp']/div/table/tbody/tr["+i+"]")).click();
    	      break;
    	   }
    	   
         }
    	   //verifying the label
    	   String social=driver.findElement(By.name("^smartlabel_social")).getText();
    	   String expected="Social";
    	   Assert.assertEquals(expected, social);
    	   //Verifying the subject
    	   String subject= properties.getProperty("email.subject");
    	   String body=properties.getProperty("email.body");
    	   String actsubject=driver.findElement(By.className("hP")).getText();
    	   Assert.assertEquals(subject, actsubject);
    	   String actbody=driver.findElement(By.cssSelector(".ii.gt")).getText();
    	   Assert.assertEquals(body, actbody);
           }
      

 
    }

