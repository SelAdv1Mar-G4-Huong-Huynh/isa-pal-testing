package testcases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import interfaces.LoginPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;


public class test {
	private static String BASE_URL="http://jsonplaceholder.typicode.com";
	@BeforeMethod
    public void beforeMethod() {
        System.out.println("BEfore method");
		
      
    }
 @Test
    public void testGetMethod() throws UnirestException
    {
        HttpResponse<JsonNode> respone = Unirest.get(BASE_URL+"/users").asJson();
        System.out.println(respone.getBody());
        System.out.println("\nFIRST:"+respone.getBody().getArray().get(0));
        Assert.assertEquals(respone.getStatus(), 200,"Failed status is "+ respone.getStatus());
    }
    @Test
    public void testPostMethod() throws UnirestException
    {
        HttpResponse<JsonNode> respone = Unirest.post(BASE_URL+"/post")
                .field("title", "QAGroup")
                .field("body", "nothing to add")
                .field("userId", 1000).asJson();
        System.out.println(respone.getBody());
        System.out.println("\nFIRST:"+respone.getBody().getArray().get(0));
        Assert.assertEquals(respone.getStatus(), 201,"Failed status is "+ respone.getStatus());
        Assert.assertEquals(respone.getStatus(), 200,"Failed status is "+ respone.getStatus());
    }
 @Test
    public void testPutMethod() throws UnirestException
    {
        HttpResponse<String> respone = Unirest.put(BASE_URL+"/post/1")
                .body("{\"id\":\"1\",\"title\":\"QA Group\",\"body\":\"Nothing happen 1\",\"userId\":\"1\"}").asString();
        System.out.println(respone.getBody());
       
        Assert.assertEquals(respone.getStatusText(),"OK","Failed status is "+ respone.getStatusText());
    }
    @Test
    public void testDeleteMethod() throws UnirestException
    {
        HttpResponse<String> respone = Unirest.delete(BASE_URL+"/post/1")
                .body("{\"id\":\"1\",\"title\":\"QA Group\",\"body\":\"Nothing happen 1\",\"userId\":\"1\"}").asString();
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
    }
    //@Test
    public void testMethodsTwo() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Two. Thread id is: " + id);
    }
    
  
	//@Test
	public void TestOptionValue(String value){
		System.out.println("This is "+ value);
	}
    @AfterMethod
    public void afterMethod() {
        System.out.println("BEfore method");
    }

    private Object body(String id1titleQA_GroupbodyNothing_happen_1userI) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
//http://robertjliguori.blogspot.com/2016/11/tesseract-optical-character-recognition.html