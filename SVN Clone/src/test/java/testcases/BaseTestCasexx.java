package testcases;

/*import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;*/
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import static common.ActionCommon.captureScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import interfaces.BasePage;
import interfaces.EditWebsitePage;
import interfaces.HomePage;
import org.testng.ITestResult;
import org.uncommons.reportng.HTMLReporter;
import utils.ExtentReportNG1;

public abstract class BaseTestCasexx {

    ExtentReports report;
    ExtentTest extent;
    HTMLReporter htmlReport;
    ExtentHtmlReporter htmlReporter;
    //Logger logger;
    public WebDriver driver;
    public HomePage homePage;
    public EditWebsitePage editWebPage;
   
    @BeforeTest
    public void TestInitalizeTest()
    { 
        report = ExtentReportNG1.GetExtent();
    }
   
   @BeforeMethod
    public void TestInitializeMethod() {
        
        System.out.println("Run Test Initialize");
        // Start Firefox browser and maximize window
      //  logger = Logger.getLogger(BaseTestCase.class);
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
        System.out.println("Please refer test report at: "+ ExtentReportNG1.getFilePath());
        
        extent = report.createTest(iResult.getName(), "Verify HomePage");
        try {
            switch (iResult.getStatus()) {
                case ITestResult.FAILURE:                    
                    String image = captureScreenshot(driver, iResult.getName());
                    System.out.println(image);
                    extent.fail("Test Case Failed" +this.getClass().getSimpleName() +"_"+ iResult.getName());
                    extent.addScreenCaptureFromPath(image);
                    break;
                case ITestResult.SUCCESS:
                    extent.pass( this.getClass().getSimpleName() +"_"+iResult.getName() + " Test Case Success and Title Verified");
                    break;
                case ITestResult.SKIP:
                    extent.skip(this.getClass().getSimpleName() +"_"+ iResult.getName() + " Test Case Skipped");
                    break;
                default:
                    break;
            }
        } catch (Throwable t) {
            extent.error(t.getMessage());
        }
        //report.removeTest(extent);
        report.flush();
        driver.quit();
    }
}
