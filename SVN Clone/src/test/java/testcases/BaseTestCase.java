package testcases;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import static common.ActionCommon.captureScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import interfaces.BasePage;
import interfaces.EditWebsitePage;
import interfaces.HomePage;
import org.testng.ITestResult;
import org.uncommons.reportng.HTMLReporter;

public abstract class BaseTestCase {

    public ExtentReports report;
    public ExtentTest extent;
//    public HTMLReporter htmlReport;
   
    //Logger logger;
    public WebDriver driver;
    public HomePage homePage;
    public EditWebsitePage editWebPage;
   
    @BeforeTest
    public void TestInitalizeTest()
    {
        String extentReportPath = System.getProperty("user.dir") + "\\surefire-reports\\extentreports.html";
        System.out.println(extentReportPath);
        report = new ExtentReports(extentReportPath);
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
        // Close browser
       
        /* if (ITestResult.FAILURE == iResult.getStatus()) {
           path = captureScreenshot(driver,iResult.getName());
          // logger.log(LogStatus.FAIL, screenshotPath);
        }*/
        extent = report.startTest(iResult.getName());
        try {
            switch (iResult.getStatus()) {
                case ITestResult.FAILURE:                    
                    String image = captureScreenshot(driver, iResult.getName());
                    System.out.println(image);
                    String TestCaseName = this.getClass().getSimpleName() + " Test Case Failure and Title/Boolean Value Failed";
                    extent.log(LogStatus.FAIL, TestCaseName + ". Please refer screenshot"+image);
                    extent.addScreencast(image);
                    break;
                case ITestResult.SUCCESS:
                    extent.log(LogStatus.PASS, this.getClass().getSimpleName() + " Test Case Success and Title Verified");
                    break;
                case ITestResult.SKIP:
                    extent.log(LogStatus.SKIP, this.getClass().getSimpleName() + " Test Case Skipped");
                    break;
                default:
                    break;
            }
        } catch (Throwable t) {
            extent.log(LogStatus.ERROR, t.getMessage());
        }
        report.endTest(extent);
        report.flush();
        driver.quit();
    }

}
