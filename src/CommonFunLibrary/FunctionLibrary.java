package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import com.gargoylesoftware.htmlunit.javascript.host.file.File;
import com.gargoylesoftware.htmlunit.javascript.host.file.FileReader;

import Utilities.PropertyFileUtil;

public class FunctionLibrary 
{
	static WebDriver driver;

	// method for launching browser
	public static WebDriver startBrowser() throws Throwable

	{
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver","D:\\Selenium_Evening\\ERP_Stock\\CommonDriver\\chromedriver.exe");
			// C:\Users\rajeev\Desktop\chromedriver.exe
			driver = new ChromeDriver();
		} 
		else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) 
		{
			System.setProperty("webderiver.gickok.driver", " ");
			driver = new FirefoxDriver();
		}
		return driver;
	}

	// launch url
	public static void openApplication(WebDriver driver) throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
		driver.manage().window().maximize();
		System.out.println("Executing openApplication method");
	}

	// method for wait for elemnt
	public static void waitforElement(WebDriver driver, String locatortype, String locatorvalue, String timewait) 
	{
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(timewait));
		if (locatortype.equalsIgnoreCase("id")) 
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		} 
		else if (locatortype.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		} 
		else if (locatortype.equalsIgnoreCase("xpath")) 
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
	}

	// method for close browser
	public static void closeBrowser(WebDriver driver) 
	{
		driver.quit();
	}

	public static void typeAction(WebDriver driver, String locatortype, String locatorvalue, String testdata) 
	{
		if (locatortype.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		} 
		else if (locatortype.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
		} 
		else if (locatortype.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
		}
	}

	// click action method
	public static void clickAction(WebDriver driver, String locatortype, String locatorvalue) 
	{
		if (locatortype.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
		} 
		else if (locatortype.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(locatorvalue)).click();
		} 
		else if (locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}
	}

	public static String generateDate() 
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_ss");
		return sdf.format(date);
	}

	// method for capture data
	public static  void CaptureData(WebDriver driver,String locatortype,String locatorvalue) throws IOException
	{
		String snumber="";
		if(locatortype.equalsIgnoreCase("id"))
		{
			snumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		else if (locatortype.equalsIgnoreCase("name"))
		{
			snumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		else if (locatortype.equalsIgnoreCase("xpath"))
		{
		snumber=driver.findElement(By.xpath(locatortype)).getAttribute("value");
		}
			//write supplier no into note pad
		FileWriter fw=new FileWriter("D:\\Selenium_Evening\\ERP_Stock\\CaptureData\\supplier.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(snumber);
		bw.flush();
		bw.close();

	}
//method for close browser
public static void tableValidation(WebDriver driver,String testdata)throws Throwable
{

	java.io.FileReader fr =new java.io.FileReader("D:\\Selenium_Evening\\ERP_Stock\\CaptureData\\supplier.txt");
	
	BufferedReader br=new BufferedReader(fr);
	String exp_data=br.readLine();
	//convert column into integer
	int column=Integer.parseInt(testdata);
	if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed())
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
	Thread.sleep(2000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
	Thread.sleep(2000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(exp_data);
	Thread.sleep(2000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
	WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("supp_table")));
	List<WebElement>rows=table.findElements(By.tagName("tr"));
	System.out.println("no of rows are::"+rows.size());
	for(int i=1;i<rows.size();i++)
	{
		String act_data=driver.findElement(By.xpath("//table[@id='tab_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
		Thread.sleep(2000);
		System.out.println(exp_data+"     "+act_data);
		Assert.assertEquals(act_data, exp_data,"snumber is not matching");
		break;
	}
}
}