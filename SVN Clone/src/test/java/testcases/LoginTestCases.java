package testcases;


import org.testng.Assert;
import org.testng.annotations.*;
import common.Constant;
import interfaces.EditWebsitePage;
import interfaces.HomePage;
import interfaces.LoginPage;
import org.openqa.selenium.support.PageFactory;
import utils.IWebDriver;
import static utils.IWebDriver.assertTrue;


public class LoginTestCases extends BaseTestCasexx{

	/************************************************
	 * Testcase 01: TC01 - User can log into Twitter with invalid username and password.
	 * 
	 * Steps 1 - Navigate to Skynova Website
	 * Steps 2 - Click on Login tab
	 * Steps 3 - Enter invalid Email and Password
	 * Steps 4 - Click on "Login" button
	 * VP Verify that message "Incorrect e-mail address. Please check the spelling of your e-mail address and try again.."
	 * 
	 ************************************************/
 @Test
  public void LoginTC01(){
	  System.out.println("TC01 - User can log into Twitter with invalid username and password.");
      LoginPage loginPage = homePage.goToLoginPage();     
      String actualMsg = loginPage.LoginInvalid("fdfdsg", Constant.Password);
      System.out.println(actualMsg);      
     // String expectedMsg = "Incorrectly formatted e-mail address. \"@\"-sign missing"; 
      String expectedMsg = "Incorrect e-mail address. Please check the spelling of your e-mail address and try again.";
      System.out.println(expectedMsg);
     assertTrue(loginPage.waitForPageTitleContains("Yahoo Mail", loginPage.getExplicitlyWaitSecond()), "Verify yahoo mailbox is navigated");
      Assert.assertTrue(actualMsg.contains(expectedMsg));
      Assert.assertFalse(true,"Failed feojoreij");
  }
  
  /************************************************
	 * Testcase 02: TC02 - User can log into Twitter with invalid username and password.
	 * 
	 * Steps 1 - Navigate to Skynova Website
	 * Steps 2 - Click on Login tab
	 * Steps 3 - Enter valid Email and Password
	 * Steps 4 - Click on "Login" button
	 * VP Page will navigate to Create Website Page and the email and logout link display
	 * 
	 ************************************************/
 // @Test  
  public void LoginTC02(){
	  System.out.println("TC02 - User can log into Twitter with valid username and password.");
      LoginPage loginPage = homePage.goToLoginPage();     
      EditWebsitePage editPage = loginPage.Login(Constant.UserName, Constant.Password);      
      Assert.assertTrue(editPage.isElementExists(editPage.getControl("lblEmail")));   
      Assert.assertTrue(editPage.isElementExists(editPage.getControl("lnkLogout")));
  }
  
  /************************************************
 	 * Testcase 03: TC03 - Update user profile.
 	 * 
 	 * Steps 1 - Navigate to Skynova Website
 	 * Steps 2 - Click on Login tab
 	 * Steps 3 - Enter valid Email and Password
 	 * Steps 4 - Click on "Login" button
 	 * Steps 5 - Move mouse on Settings menu and select My Profile
 	 * Steps 6 - Enter profile info (name, address, time zone, date formate..)
 	 * Steps 7 - Click on Submit button
 	 * VP: Message"Profile successfully saved " with update successfully icon is displayed
 	 ************************************************/
   //@Test  
   public void LoginTC03(){
 	  System.out.println("TC03 - Update user profile.");
       //LoginPage loginPage = homePage.goToLoginPage();     
      // EditWebsitePage editPage = loginPage.Login(Constant.UserName, Constant.Password);      
     
      LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class );
   }
  @Test  
   public void LoginTC04(){
 	  System.out.println("TC03 - Update user profile.");
      LoginPage loginPage = homePage.goToLoginPage();     
      // EditWebsitePage editPage = loginPage.Login(Constant.UserName, Constant.Password);      
     
     HomePage hp = loginPage.openFooter("Home Page", HomePage.class );
     homePage.isElementExists(homePage.getControl("lnkLoginTab"));
   }
  @Test  
   public void LoginTC06(){
 	  System.out.println("TC06 - Update user profile.");
    
     homePage.isElementExists(homePage.getControl("lnkLoginTab"));
     
     
   }
    @Test  
   public void LoginTC05(){
 	  System.out.println("TC05 - Update user profile.");
      LoginPage loginPage = homePage.goToLoginPage();     
      // EditWebsitePage editPage = loginPage.Login(Constant.UserName, Constant.Password);      
     
     Assert.assertEquals(true, false,"Failed");
     
   }
 
}
