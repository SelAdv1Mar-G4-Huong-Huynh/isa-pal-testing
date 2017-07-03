/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

/**
 *
 * @author Dell
 */
public class ExtentReportNG1 {
      private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;
        //private static String filePath = System.getProperty("user.dir") + "\\surefire-reports\\STMExtentReport.html";
	private static String filePath = System.getProperty("user.dir") + "\\surefire-reports\\extentreports.html";
	public ExtentReportNG1 (){
    }
    
    public static ExtentReports getReportInstance(){
        return extent;
    }
    public static String getFilePath(){
        return filePath;
    }
    public static ExtentTest getReportLogger(){
        return test;
    }
	public static ExtentReports GetExtent(){
		if (extent != null)
                    return extent; //avoid creating new instance of html file
                extent = new ExtentReports();		
		extent.attachReporter(getHtmlReporter());
		return extent;
	}
 
	private static ExtentHtmlReporter getHtmlReporter() {
        htmlReporter = new ExtentHtmlReporter(filePath);
	htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "\\surefire-reports\\extent-config.xml");	
	// make the charts visible on report open
        htmlReporter.config().setChartVisibilityOnOpen(true);
		
        htmlReporter.config().setDocumentTitle("QAV automation report");
        htmlReporter.config().setReportName("Regression cycle");
        return htmlReporter;
	}
	
	public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		return test;
    }
       

}
