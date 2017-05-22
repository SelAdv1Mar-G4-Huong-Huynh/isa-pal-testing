package testcases;

import org.testng.annotations.*;

public class test {
	
	@BeforeMethod
    public void beforeMethod() {
		try{
			  long id = Thread.currentThread().getId();        
		        System.out.println("Before test-method. Thread id is: " + id);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
      
    }
 
    @Test
    public void testMethodsOne() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
    }
 
    @Test
    public void testMethodsTwo() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Two. Thread id is: " + id);
    }
    
  
	@Test
	public void TestOptionValue(String value){
		System.out.println("This is "+ value);
	}
    @AfterMethod
    public void afterMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("After test-method. Thread id is: " + id);
    }
}
