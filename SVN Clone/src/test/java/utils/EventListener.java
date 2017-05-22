package utils;

//import com.sun.jna.platform.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.apache.commons.io.FileUtils;


public class EventListener extends AbstractWebDriverEventListener {
		@Override
		public void onException(Throwable throwable, WebDriver driver) {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				FileUtils.copyFile(file, new File("src/test/target/" + dateFormat.format(new Date()) + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
