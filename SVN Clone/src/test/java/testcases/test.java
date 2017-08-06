package testcases;

import com.google.common.collect.Ordering;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import interfaces.LoginPage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;


public class test {
	private static String BASE_URL="http://jsonplaceholder.typicode.com";
         String b = "gSD4L8wQst3W2NcYXwF8Tck9g4D6WZQjYh6YdyQrMPXTWEgIkC3wL8MM5qv9uIhAX7azl5Afv1Vt7h8sfrT4yQ%3D%3D";
        String a = "gSD4L8wQst3W2NcYXwF8Tck9g4D6WZQjYh6YdyQrMPXTWEgIkC3wL8MM5qv9uIhAX7azl5Afv1Vt7h8sfrT4yQ==";
        
          private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a";

    public static void main(String[] args) {

        /*String dateInString = "22-1-2015 10:15:55 AM";
        LocalDateTime ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(DATE_FORMAT));

        ZoneId singaporeZoneId = ZoneId.of("Asia/Singapore");
        System.out.println("TimeZone : " + singaporeZoneId);

        //LocalDateTime + ZoneId = ZonedDateTime
        ZonedDateTime asiaZonedDateTime = ldt.atZone(singaporeZoneId);
        System.out.println("Date (Singapore) : " + asiaZonedDateTime);

        ZoneId newYokZoneId = ZoneId.of("America/New_York");
        System.out.println("TimeZone : " + newYokZoneId);

        ZonedDateTime nyDateTime = asiaZonedDateTime.withZoneSameInstant(newYokZoneId);
        System.out.println("Date (New York) : " + nyDateTime);

        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        System.out.println("\n---DateTimeFormatter---");
        System.out.println("Date (Singapore) : " + format.format(asiaZonedDateTime));
        System.out.println("Date (New York) : " + format.format(nyDateTime));*/
         System.out.println("Is List Sort: ");
        List<String> lstValue = new ArrayList<String>();
        lstValue.add("2017-8-01 22:10:00");
        lstValue.add("2017-8-01 22:11:00");
        lstValue.add("2017-8-01 22:19:00");
        
        boolean aaa = Ordering.natural().isOrdered(lstValue);
        System.out.println("Is List Sort: ffff"+ aaa);
    }
    public boolean checkListContainsExpectedValue(HttpResponse<JsonNode> apiResult, String property, String expectedValue)
    {
    	JSONArray ja_data = apiResult.getBody().getObject().getJSONArray("data");
    	for(int i=0;i < ja_data.length(); i++)
    	{
    		JSONObject obj = ja_data.getJSONObject(i);
    		if(!obj.getString(property).equals(expectedValue))
    			return false;
    	}
    	return true;
    }
    public List<String> getListValues(HttpResponse<JsonNode> apiResult, String property)
    {
    	List<String> lstValue = new ArrayList<String>();
    	JSONArray ja_data = apiResult.getBody().getObject().getJSONArray("data");
    	 for (Object o : ja_data) {
    	        JSONObject jsonLineItem = (JSONObject) o;
    	        lstValue.add(jsonLineItem.getString("property"));
    	    }
    	return lstValue;
    }
    
    public boolean isListSorted(List<String> list, String order){
         return Ordering.natural().isOrdered(list);
    }
  /* public boolean isWithinRange(Date testDate) {
    return testDate.getTime() >= startDate.getTime() &&
             testDate.getTime() <= endDate.getTime();
}*/
        public void encode8uf()
        {
             System.out.println("BEfore method");
            try {
                System.out.println(URLEncoder.encode(a,"UTF-8").equals(b));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	@BeforeMethod
    public void beforeMethod() {
        System.out.println("BEfore method");
       encode8uf();
    }
    //{"id":101,"body":"nothing to add","title":"QAGroup","userId":1000}
 //{"status": 1, "data": {"userId": "isa.test11","username": "04B000000A","status": "ACTIVE","balance": 0,"exposure": 0,"createdDate": "2017-07-17 08:41:40"}}
 //@Test
    public void testGetMethod() throws UnirestException
    {
        JSONObject obkj = new JSONObject().put("dfdf","");
        final HttpResponse<JsonNode> respone = Unirest.get(BASE_URL+"/users").asJson();
        System.out.println(respone.getBody());
        System.out.println("\nFIRST:"+respone.getBody().getArray().get(0));
        System.out.println("\ntitle" + respone.getBody().getObject().getString("title"));
        //System.out.println("\ntitle" + respone.getBody().getObject().getJSONObject("data").getString("fdsfsd"));
        Assert.assertEquals(respone.getStatus(), 200,"Failed status is "+ respone.getStatus());
    }
  //  @Test
    public void testPostMethod() throws UnirestException
    {
        HttpResponse<JsonNode> respone = Unirest.post(BASE_URL+"/posts")
                .field("title", "QAGroup")
                .field("body", "nothing to add")
                .field("userId", 1000).asJson();
        System.out.println(respone.getBody());
        System.out.println("\nFIRST:"+respone.getBody().getArray().get(0));
        Assert.assertEquals(respone.getStatus(), 201,"Failed status is "+ respone.getStatus());
        Assert.assertEquals(respone.getStatus(), 200,"Failed status is "+ respone.getStatus());
    }
//@Test
    public void testPutMethod() throws UnirestException
    {
        HttpResponse<String> respone = Unirest.put(BASE_URL+"/post/1")
                .body("{\"id\":\"1\",\"title\":\"QA Group\",\"body\":\"Nothing happen 1\",\"userId\":\"1\"}").asString();
        System.out.println(respone.getBody());
       
        Assert.assertEquals(respone.getStatusText(),"OK","Failed status is "+ respone.getStatusText());
    }
  //  @Test
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