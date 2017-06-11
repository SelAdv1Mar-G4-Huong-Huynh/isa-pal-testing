/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.util.List;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;

/**
 *
 * @author Dell
 */
public class ExtentReportNG extends TestListenerAdapter implements IReporter {

    private static ExtentReports extent;
    private static ExtentTest test;
    
    public ExtentReportNG (){
        
    }
    
    public static ExtentReports getReportInstance(){
        return extent;
    }
    public static ExtentTest getReportLogger(){
        return test;
    }
 
    
    public void generateReport(List<XmlSuite> list, List<ISuite> list1, String outputDirectory) {
        /*   DisplayOrder NetworkMode = null;
        extent = new ExtentReports(outputDirectory + File.separator + "Extent.htlm",true,NetworkMode.OFFLINE,Locale.ENGLISH);
        for(ISuite suite:suites)
        {
            Map<String,ISuiteResult> result = suite.getResults();
            for(ISuiteResult r:result.values()){
                ITestContext context = r.getTestContext();
                buildTestNode(context.getFailedTests(),LogStatus.FAIL);
                buildTestNode(context.getFailedTests(),LogStatus.SKIP);
                buildTestNode(context.getFailedTests(),LogStatus.PASS);
            }
            extent.flush();
        }*/
    }
    private void buildTestNode(IResultMap tests,LogStatus status)
    {
        if(tests.size()>0)
        {
            for(ITestResult result: tests.getAllResults()){
                String methodName = result.getMethod().getMethodName();
                test = extent.startTest(methodName);
                String message = "Test " + status.toString().toLowerCase() +"ed";
                if(result.getThrowable()!=null){
                    message = result.getThrowable().getMessage();
                   
                }
                test.log(status,message);
                if(status.equals(LogStatus.FAIL)){
                    test.log(LogStatus.INFO, "Sceen shot of failed test:"+ test.addScreenCapture(methodName+"screenshot.png"));
                }
                extent.endTest(test);
            }
        }
    }
    public void onTestStart(ITestResult testResult){
        //To Do
    }
    public void onTestSuccess(ITestResult testResult){
        // To do
    }
    public void onTestFailuer(ITestResult testResult){
      //  WebDriver diver = WebDriverManager.getDriverInstance();
        //hjhkjh
    }
}