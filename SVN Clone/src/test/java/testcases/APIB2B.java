package testcases;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.media.jfxmedia.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dell
 */
public class APIB2B {
    String apiDomain ="http://apiqat.beatus88.com/";
    String getListPlayerInforUrl = apiDomain +"mwl-sandbox/page/get-player-info";
    String getPlayerInforUrl = apiDomain +"mwl-sandbox/page/get-player-info";
    String playerLoginUrl = apiDomain + "mwl-sandbox/page/get-player-info";
    String playerLogoutUrl = apiDomain + "mwl-sandbox/page/get-player-info";
    String playerUpdateStatusUrl = apiDomain + "mwl-sandbox/page/get-player-info";
    String adjustBalaceUrl = apiDomain + "mwl-sandbox/page/get-player-info";
    String reportWagerListUrl = apiDomain + "mwl-sandbox/page/get-player-info";
    String getTokenUrl = apiDomain + "mwl-api/agent/token";
    String encryptUrl = apiDomain + "mwl-api/sandbox/encrypt";
   
    String agentCode="40B0000";
    String agentKey = "5b922fcdebf54a3690ec4dbb85e54d78";
    String authourizationCode ="kc+SYJpgFNTo7iaF8381he3fzhyxIgPWnxOYSjCwlaYsQ5qZ2ydGBw+o9UQDxU2r";
    public static String agentToken ="fLgOKq0Lns8999aEkdxCEawWG+WPp/h9yoCZBOjQfmh32vbpZnYbldw/DZNLJTjkKakfwzH8HNu5U7TkeiyWKQ==";
    
    String loginPlayerQuery = "{userId:'%s', mobile:'%s', language: '%s'}";
    String logoutPlayerQuery = "{userId:%s}";
    String getPlayerListQuery= "{dateFrom:'%s',dateTo:'%s',rowPerPage:'%s',page:'%s',status:'%s',orderBy='%s',orderType='%s'}";
    String getPlayerQuery = "{userId:'%s'}";
    String adjustBalanceQuery = "{userId:'%s', amount:'%s', transactionId: '%s'}";
    String getWagerQuery = "{userId:'%s', dateFrom:'%s',dateTo:'%s',rowPerPage:'%s',page:'%s',status:'%s',orderBy='%s',orderType='%s'}";
    String updatePlayerQuery = "{userId:'%s', status:'%s'}";
    String encryptPara= "";
    
    @BeforeSuite
    public void beforeMethod() throws UnirestException {
     //  agentToken = getPartnerToken(agentKey, authourizationCode);

       System.out.println(agentCode);   
    }

    public HttpResponse<JsonNode> postMethod(String postUrl, String agent, String token, String bodyEncrypt) throws UnirestException
    {
          HttpResponse<JsonNode> respone = Unirest.post(postUrl)
                 .header("AgentKey", agent)
                 .header("AgentToken", token)
                 .header("content-type", "application/x-www-form-urlencoded")
                 .header("cache-control", "no-cache")
                 .body(String.format("body=%s",bodyEncrypt))
                 .asJson();
          return respone;
    }
    public String getPartnerToken(String agentKey, String authourization) throws UnirestException{
         HttpResponse<JsonNode> respone = Unirest.post(getTokenUrl)
                 .header("AgentKey", agentKey)
                 .header("Authorization", authourization)
                 .asJson();
      System.out.println(respone.getBody());  
      
       System.out.println("\nFIRST:"+respone.getBody().getArray().get(0));
        return  respone.getBody().toString(); 
    }
    //{dateFrom:'2017-07-19 00:00:00',dateTo:'2017-07-22 23:59:59',rowPerPage:50,page:1,status:'ACTIVE',orderBy='CREATED_DATE',orderType='ASC'}
    public String encryptJsonParameter(String agentKey, String agentToken, String parameterInput) throws UnirestException
    {       
      HttpResponse<JsonNode> respone = postMethod(encryptUrl,agentKey, agentToken, parameterInput);
      System.out.println(respone.getBody());        
      return  respone.getBody().toString(); 
    }
    @Test
    public void PlayerLoginTC01() throws UnirestException
    {
       System.out.print("Player Login TC01: Cannot login with wrong format user id");
       loginPlayerQuery = String.format(loginPlayerQuery, "sfds$1","DESKTOP","ENGLISH");
        encryptPara = encryptJsonParameter(agentKey, agentToken, loginPlayerQuery);
        HttpResponse<JsonNode> respone = postMethod(playerLoginUrl,agentKey, agentToken, encryptPara);
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),301,"Failed status is "+ respone.getStatus());
    }
    
    @Test
    public void PlayerLoginTC02() throws UnirestException
    {
       System.out.print("Player Login TC02: Cannot login with wrong format user id lengh max than 20 chacter");
       loginPlayerQuery = String.format(loginPlayerQuery, "11111111111111111111","DESKTOP","ENGLISH");
       encryptPara = encryptJsonParameter(agentKey, agentToken, loginPlayerQuery);
        HttpResponse<JsonNode> respone = postMethod(playerLoginUrl,agentKey, agentToken, encryptPara);
        System.out.println(respone.getBody());
      //  System.out.println(respone.getBody().getObject().getJSONObject("data"));
        Assert.assertEquals(respone.getStatus(),307,"Failed status is "+ respone.getStatus());
    }
    
    @Test
    public void PlayerLoginTC03() throws UnirestException
    {
       System.out.print("Player Login TC03:Login with valid user id");
       loginPlayerQuery = String.format(loginPlayerQuery, "test.12_23","DESKTOP","ENGLISH");
       encryptPara = encryptJsonParameter(agentKey, agentToken, loginPlayerQuery);
        HttpResponse<JsonNode> respone = postMethod(playerLoginUrl,agentKey, agentToken, encryptPara);
        System.out.println(respone.getBody());
      //  System.out.println(respone.getBody().getObject().getJSONObject("data"));
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       System.out.print("Open browser check page is login successfull");
       
       System.out.print("Check iframe section is limit in 30s from created");
        
    }
    @Test
    public void PlayerLogoutTC04() throws UnirestException
    {
       System.out.print("Player Login TC04: Logout with non-exist user");
       logoutPlayerQuery = String.format(logoutPlayerQuery, "notexist");
       encryptPara = encryptJsonParameter(agentKey, agentToken, loginPlayerQuery);
        HttpResponse<JsonNode> respone = postMethod(playerLogoutUrl, agentKey, agentToken, encryptPara);
        System.out.println(respone.getBody());
      //  System.out.println(respone.getBody().getObject().getJSONObject("data"));
        Assert.assertEquals(respone.getStatus(),301,"Failed status is "+ respone.getStatus());
    }
    @Test
    public void PlayerLogoutTC05() throws UnirestException
    {
       System.out.print("Player Logout TC05: Logout user successfully");
       logoutPlayerQuery = String.format(logoutPlayerQuery, "test.12_23");
       encryptPara = encryptJsonParameter(agentKey, agentToken, loginPlayerQuery);
        HttpResponse<JsonNode> respone = postMethod(playerLogoutUrl, agentKey, agentToken, encryptPara);
        System.out.println(respone.getBody());
      //  System.out.println(respone.getBody().getObject().getJSONObject("data"));
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
    }
    @Test
    public void PlayerLogoutTC06() throws UnirestException
    {
       System.out.print("Player Logout TC06: Logout invalid format user");
       logoutPlayerQuery = String.format(logoutPlayerQuery, "test5^$%#$%$35");
       encryptPara = encryptJsonParameter(agentKey, agentToken, loginPlayerQuery);
        HttpResponse<JsonNode> respone = postMethod(playerLogoutUrl, agentKey, agentToken, encryptPara);
        System.out.println(respone.getBody());
      //  System.out.println(respone.getBody().getObject().getJSONObject("data"));
        Assert.assertEquals(respone.getStatus(),310,"Failed status is "+ respone.getStatus());
    }
     @Test
     public void GetPlayerInfoTC07() throws UnirestException
    {
       System.out.print("GetPlayerInfoTC07: Get Player Info with non exist player");
       getPlayerQuery = String.format(getPlayerQuery, "nonexit");
        HttpResponse<JsonNode> respone = postMethod(getPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),310,"Failed status is "+ respone.getStatus());
    }
     
      @Test
      public void GetPlayerInfoTC08() throws UnirestException
    {
       System.out.print("GetPlayerInfoTC08: Get Player Info with wrong format user id");
       getPlayerQuery = String.format(getPlayerQuery, "%sgertre%ff");
        HttpResponse<JsonNode> respone = postMethod(getPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),310,"Failed status is "+ respone.getStatus());
    }
       @Test
     public void GetPlayerInfoTC09() throws UnirestException
    {
       System.out.print("GetPlayerInfoTC09: Get Player Info with user id exceed limit length");
       getPlayerQuery = String.format(getPlayerQuery, "aaaaaaaaaaaaaaaaaaaa");
        HttpResponse<JsonNode> respone = postMethod(getPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),310,"Failed status is "+ respone.getStatus());
    }
      @Test
    public void GetPlayerInfoTC10() throws UnirestException
    {
       System.out.print("GetPlayerInfoTC10: Get Player Info valid user id");
       getPlayerQuery = String.format(getPlayerQuery, "test.12_23");
        HttpResponse<JsonNode> respone = postMethod(getPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is " + respone.getStatus());
        // check player info display correctly
    }
     @Test
    //"{dateFrom:'%s',dateTo:'%s',rowPerPage:'%s',page:'%s',status:'%s',orderBy='%s',orderType='%s'}";
     public void GetListPlayerInfoTC11() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC11: Get Player Info in valid date range");
       getPlayerListQuery = String.format(getPlayerQuery, "yesterdate", "today", "50", "1", "All", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        // check list display in date range, items display per page
    }
      @Test
      public void GetListPlayerInfoTC12() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC12: Get Player Info without input date range");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "1", "All","CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check get all player
    }
       @Test
    public void GetListPlayerInfoTC13() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC13: Get Player Info “dateFrom” is input and “dateTo” does not input" );
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "1", "All", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //get players from “dateFrom” to current 
    }
     @Test
     public void GetListPlayerInfoTC14() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC14: Get Player Info: “dateFrom” isnot input and “dateTo” input" );
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "1", "All", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //get all player in the past to "dateTo”
    }
      @Test
     public void GetListPlayerInfoTC15() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC15: Cannot get player list when input date from > date to");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "1", "All", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //error code display
    }
      @Test
      public void GetListPlayerInfoTC16() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC16: Cannot get player list when input date from with wrong format");
       getPlayerListQuery = String.format(getPlayerQuery, "sftwrtrw", "", "50", "1", "All", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check message error
    }
       @Test
       public void GetListPlayerInfoTC17() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC17: Cannot get player list when input date to with wrong format");
       getPlayerListQuery = String.format(getPlayerQuery, "", "fdsfwerwr", "50", "1", "All", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check message error
    }
        @Test
    public void GetListPlayerInfoTC18() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC17: Cannot get player list when input date to with wrong format");
       getPlayerListQuery = String.format(getPlayerQuery, "", "fdsfwerwr", "50", "1", "All", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check message error
    }
     @Test
    public void GetListPlayerInfoTC19() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC19: Get user with active status");
       getPlayerListQuery = String.format(getPlayerQuery, "", "fdsfwerwr", "50", "1", "ACTIVE", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check all user displays in active status
    }
     @Test
    public void GetListPlayerInfoTC20() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC20: Get user with Inactive status");
       getPlayerListQuery = String.format(getPlayerQuery, "", "fdsfwerwr", "50", "1", "INACTIVE", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check all user displays in Inactive status
    }
     @Test
    public void GetListPlayerInfoTC21() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC21: Get user with suspended status");
       getPlayerListQuery = String.format(getPlayerQuery, "", "fdsfwerwr", "50", "1", "SUSPENDED", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check all user displays in suspended status
    }
     @Test
    public void GetListPlayerInfoTC22() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC22: Get user with closed status");
       getPlayerListQuery = String.format(getPlayerQuery, "", "fdsfwerwr", "50", "1", "CLOSED","CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check all user displays in closed status
    }
     @Test
    public void GetListPlayerInfoTC23() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC23: Get user with invalid status");
       getPlayerListQuery = String.format(getPlayerQuery, "", "fdsfwerwr", "50", "1", "InvalidStatus","CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check error message
    }
    @Test
    public void GetListPlayerInfoTC24() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC24: Get user id in limit record per page");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "5", "1", "ALL","CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //check user number display within the limit page
    }
    @Test
    public void GetListPlayerInfoTC25() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC25:Get list user when input rowPerPage over max number");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "300", "1", "ALL", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //the list only display less or equal than 200 records
    }
    @Test
    public void GetListPlayerInfoTC26() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC26:Get list user when input wrong format rowPerPage");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "#@3", "1", "ALL", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //the list only display less or equal than 200 records
    }
     @Test
    public void GetListPlayerInfoTC27() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC27:Get list user when input wrong page number");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "eewq", "ALL", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        //the list only display less or equal than 200 records
    }
     @Test
    public void GetListPlayerInfoTC28() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC27:Get list user order asc by CREATED_DATE");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "eewq", "ALL", "", "CREATED_DATE", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
        // check sort asc by CREATED_DATE
    }
    @Test
    public void GetListPlayerInfoTC29() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC29:Get list user order descending by USER_ID");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "eewq", "ALL", "", "USER_ID", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void GetListPlayerInfoTC30() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC30:Get list user when inputing wrong oder by ");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "eewq", "ALL", "", "test", "ASC");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       //check 
    }
    @Test
    public void GetListPlayerInfoTC31() throws UnirestException
    {
       System.out.print("GetListPlayerInfoTC30:Get list user when inputing wrong oder type ");
       getPlayerListQuery = String.format(getPlayerQuery, "", "", "50", "eewq", "ALL", "", "USER_ID", "test");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void UpdatePlayerTC32() throws UnirestException
    {
       System.out.print("UpdatePlayerTC32:Update player to ACTIVE status");
       updatePlayerQuery = String.format(updatePlayerQuery, "test.12_23","ACTIVE");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void UpdatePlayerTC33() throws UnirestException
    {
       System.out.print("UpdatePlayerTC33:Update player to INACTIVE status");
       updatePlayerQuery = String.format(updatePlayerQuery, "test.12_23","INACTIVE");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void UpdatePlayerTC34() throws UnirestException
    {
       System.out.print("UpdatePlayerTC34:Update player to SUSPENDED status");
       updatePlayerQuery = String.format(playerUpdateStatusUrl, "test.12_23","SUSPENDED");
        HttpResponse<JsonNode> respone = postMethod(getListPlayerInforUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void UpdatePlayerTC35() throws UnirestException
    {
       System.out.print("UpdatePlayerTC35:Update player to CLOSED status");
       updatePlayerQuery = String.format(updatePlayerQuery, "test.12_23","CLOSED");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void UpdatePlayerTC36() throws UnirestException
    {
       System.out.print("UpdatePlayerTC36:Update player to CLOSED status");
       updatePlayerQuery = String.format(updatePlayerQuery, "test.12_23","CLOSED");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void UpdatePlayerTC37() throws UnirestException
    {
       System.out.print("UpdatePlayerTC36:Update status for non exist user");
       updatePlayerQuery = String.format(updatePlayerQuery, "non exist","ACTIVE");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, loginPlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),200,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void UpdatePlayerTC38() throws UnirestException
    {
       System.out.print("UpdatePlayerTC38:Update status for user id have exceed the limit");
       updatePlayerQuery = String.format(updatePlayerQuery, "12345678998765432100","ACTIVE");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, updatePlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void UpdatePlayerTC39() throws UnirestException
    {
       System.out.print("UpdatePlayerTC39:Update status for wrong format user id ");
       updatePlayerQuery = String.format(updatePlayerQuery, "%343254%$#","ACTIVE");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, updatePlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void UpdatePlayerTC40() throws UnirestException
    {
       System.out.print("UpdatePlayerTC40:Update status for wrong format user id ");
       updatePlayerQuery = String.format(updatePlayerQuery, "test.12_23","anytest");
        HttpResponse<JsonNode> respone = postMethod(playerUpdateStatusUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, updatePlayerQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void UpdatePlayerTC41() throws UnirestException
    {
       System.out.print("UpdatePlayerTC41:Adjust balance for non exist user");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test","-1","13453445");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void UpdatePlayerTC42() throws UnirestException
    {
       System.out.print("UpdatePlayerTC41:Adjust Balance with user id is limit the length");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "12345678998765432100","-1","13453445");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void UpdatePlayerTC43() throws UnirestException
    {
       System.out.print("UpdatePlayerTC43:Adjust Balance with wrong format user id");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "fewr#@$","-1","13453445");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void AdjustBalanceTC44() throws UnirestException
    {
       System.out.print("UpdatePlayerTC44:Adjust Balance with exist user id, amount is exceed agenet limit");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","100000","13453445");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void AdjustBalanceTC45() throws UnirestException
    {
       System.out.print("UpdatePlayerTC45:Adjust Balance with exist user id, amount is string");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","test","13453445");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void AdjustBalanceTC46() throws UnirestException
    {
       System.out.print("UpdatePlayerTC46:Adjust Balance with exist user id, amount is wrong decimal format");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","0.0001","13453445");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void AdjustBalanceTC47() throws UnirestException
    {
       System.out.print("UpdatePlayerTC47:Adjust Balance with exist user id, amount is wrong format");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","1-1","13453445");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void AdjustBalanceTC48() throws UnirestException
    {
       System.out.print("UpdatePlayerTC48:Adjust Balance with exist user id, valid amount, and exist transaction id");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","1","trretre");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void AdjustBalanceTC49() throws UnirestException
    {
       System.out.print("UpdatePlayerTC49:Adjust Balance with exist user id, valid amount, and wrong format transaction id");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","1","trretre");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void AdjustBalanceTC50() throws UnirestException
    {
       System.out.print("UpdatePlayerTC50:Adjust Balance with exist user id, valid amount, and transaction id is larger numebr");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","1","999999999999999999991");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     @Test
    public void AdjustBalanceTC51() throws UnirestException
    {
       System.out.print("UpdatePlayerTC51:Adjust Balance with exist user id, valid amount, and transaction id is decimal");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","1","123433.2");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
     
    @Test
    public void AdjustBalanceTC52() throws UnirestException
    {
       System.out.print("UpdatePlayerTC52:Deposit user balance successfully");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","1","23213");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
    @Test
    public void AdjustBalanceTC53() throws UnirestException
    {
       System.out.print("UpdatePlayerTC53:Withdraw user balance successfully");
       adjustBalanceQuery = String.format(adjustBalanceQuery, "test.12_23","1","23213");
        HttpResponse<JsonNode> respone = postMethod(adjustBalaceUrl, agentKey, agentToken, encryptJsonParameter(agentKey, agentToken, adjustBalanceQuery));
        System.out.println(respone.getBody());
        Assert.assertEquals(respone.getStatus(),300,"Failed status is "+ respone.getStatus());
       // check sort des by USER_ID
    }
}
