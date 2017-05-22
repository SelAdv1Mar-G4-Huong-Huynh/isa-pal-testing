package testcases;
import static common.ActionCommon.captureScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import interfaces.BasePage;
import interfaces.EditWebsitePage;
import interfaces.HomePage;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;
import org.uncommons.reportng.HTMLReporter;
public abstract class BaseTestCase {
    /*ExtentReports report;
    ExtentTest logger;*/
    HTMLReporter htmlReport;
    Logger logger;
    public WebDriver driver;
    public HomePage homePage;
    public EditWebsitePage editWebPage;

    /**
     * @author Huong Huynh
     * <summary>
     * Opens multiple browsers and run parallel
     */
    /*Run with multiple browser mode
		 * @Parameters("browser")
		@BeforeMethod
		public void TestInitializeMethod(String browser) {
			System.out.println("Run Test Initialize");
			// Start Firefox browser and maximize window			
			if(browser.equalsIgnoreCase("firefox")){
				driver = new FirefoxDriver();	
			}else if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", "library\\chromedriver.exe");
				driver = new ChromeDriver();
			}else if(browser.equalsIgnoreCase("ie")){
				System.setProperty("webdriver.ie.driver", "library\\IEDriverServer.exe");
				driver= new InternetExplorerDriver();
			}
			System.out.println("System is running with "  + browser);
			driver.manage().window().maximize();	
			BasePage basePage = new BasePage(driver);
			homePage = basePage.Open();
		}*/

    @BeforeMethod
    public void TestInitializeMethod() {
        System.out.println("Run Test Initialize");
        /*String extentReportPath=System.getProperty("user.dir") + "/surefire-reports/extentreports.xml" ;
        report = new ExtentReports(extentReportPath, Boolean.FALSE);
        logger = new ExtentTest("test", "fdsffg");
        logger.log(LogStatus.INFO,"Browser Started");*/
        // Start Firefox browser and maximize window
        logger = Logger.getLogger(BaseTestCase.class);
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        driver = new FirefoxDriver();
        //System.setProperty("webdriver.chrome.driver", "library\\chromedriver.exe");
        //	driver = new ChromeDriver();
        //System.setProperty("webdriver.ie.driver", "library\\IEDriverServer.exe");
        //driver= new InternetExplorerDriver();
        System.out.println("System is running with Firefox");
        driver.manage().window().maximize();
        BasePage basePage = new BasePage(driver);

        homePage = basePage.Open();
    }

    @AfterMethod
    public void TestCleanupMethod(ITestResult iResult) {
        System.out.println("Run Test Cleanup");
        // Close browser
        if (ITestResult.FAILURE == iResult.getStatus()) {
           String path = captureScreenshot(driver);
           logger.info("Please refer the screenshot at " + path + " for detail");
          // logger.log(LogStatus.FAIL, screenshotPath);
        }
        /*report.endTest(logger);
        report.flush();
        driver.get(screenshotPath);*/
        driver.quit();
    }

    /*
		 * public void ConfirmDialog(string buttonName) { switch
		 * (buttonName.ToUpper()) { case "OK": case "YES":
		 * Constant.WebDriver.SwitchTo().Alert().Accept(); break;
		 * 
		 * case "NO": case "CANCEL":
		 * Constant.WebDriver.SwitchTo().Alert().Dismiss(); break; } }
     */
}
