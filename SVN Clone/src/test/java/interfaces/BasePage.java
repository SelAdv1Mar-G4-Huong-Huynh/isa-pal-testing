package interfaces;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import common.Constant;
import utils.ControlFactory;

public class BasePage extends ControlFactory {
	public BasePage(WebDriver wdriver) {
		super(wdriver);
		// TODO Auto-generated constructor stub
		
	}	
	public Logger log = Logger.getLogger(HomePage.class);	
	
	public HomePage Open()
    {
    	openURL(Constant.HomePageUrl);     
        return new HomePage(driver);
    }
	
	/*
    public void openBrowser(String browserName)
    {
    	if(browserName.equalsIgnoreCase("Firefox")){
    		driver = new FirefoxDriver();
    	}
    	if(browserName.equalsIgnoreCase("Chrome")){
    		driver = new ChromeDriver();
    	}
    	if(browserName.equalsIgnoreCase("IE")){
    		driver = new InternetExplorerDriver();
    	}        
    	driver.manage().window().maximize();
    }
*/
}
