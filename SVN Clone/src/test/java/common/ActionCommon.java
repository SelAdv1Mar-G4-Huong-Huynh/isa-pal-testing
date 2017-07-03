package common;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ActionCommon {

    public static String GenrateRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String RandomDay(int day) {
        Random rnd = new Random();
        Date date = new Date(Math.abs(System.currentTimeMillis() - rnd.nextLong()));
        System.out.println(date.toString());
        return date.toString();

    }

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String date = dateFormat.format(new Date());
            String filepath = System.getProperty("user.dir") + "/src/test/target/"+testName+"_"+ date + ".png";          
            FileUtils.copyFile(file, new File(filepath));
            System.out.println("Please refer the screenshot at " + filepath + " for detail");
            return System.getProperty("user.dir") +("\\src\\test\\target\\"+ testName+"_"+ date + ".png");
        } catch (IOException e) {
            System.err.println("Exception while taking screenshot" + e.getMessage());
            return e.getMessage();
        }
    }
   
}

