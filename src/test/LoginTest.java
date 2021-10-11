package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ecommerce.DBConnection;

public class LoginTest {

	private static WebElement check;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String URL="jdbc:mysql://localhost:3306/ecommerce";
		String Username="root";
		String Password="root";
		
		DBConnection c = new DBConnection(URL,Username,Password);
		Statement s = c.getConnection().createStatement();
		
        System.setProperty("webdriver.chrome.drive","chromedriver");
		
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.saucedemo.com/");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);
		
		ResultSet r1 =s.executeQuery("Select * from Login");
		
		String username="";
		String defaultpassword="";
		
		while(r1.next()) {
			username=r1.getString("Username");
			defaultpassword=r1.getString("password");
			
			
		}
		WebElement name1 = driver.findElement(By.xpath("//input[@id='user-name']"));
		name1.sendKeys(username);
		
		WebElement pass = driver.findElement(By.xpath("//input[@id='password']"));
		pass.sendKeys(defaultpassword);
		
		WebElement button = driver.findElement(By.xpath("//input[@id='login-button']"));
		button.click();
		
		WebElement check_status = driver.findElement(By.xpath("//span[text()='Products']"));
		
		if(check_status.equals("Products")) {
			System.out.println("Failed");
		}else {
			System.out.println("Success!");
		}
		
		ResultSet r2 = s.executeQuery("select * from eproduct");
		String Name="";
		Double Price=0.0;
		
		while(r2.next()) {
			Name = r2.getString("name");
			Price = r2.getDouble("price");
		}
		
		WebElement cartlink= driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + Name + "']/following::button[1]"));
		cartlink.click();
		
		WebElement  cartsumm = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
		cartsumm.click();
		
		WebElement price_cart = driver.findElement(By.xpath("//div[@class='inventory_item_price']"));
		
		if(price_cart.equals(Price)) {
			System.out.println("Price Changed "+Price);
		}else {
			System.out.println("Price is Same "+Price);
		}
		
		    WebElement  cb = driver.findElement(By.xpath("//button[@id='checkout']"));
			cb.click();
			
			WebElement Fname = driver.findElement(By.xpath("//*[@id='first-name']"));
			Fname.sendKeys("Deva");
			
			WebElement Lname = driver.findElement(By.xpath("//*[@id='last-name']"));
			Lname.sendKeys("Dharshni");
			
			WebElement pincode = driver.findElement(By.xpath("//*[@id='postal-code']"));
			pincode.sendKeys("604151");
			
			WebElement cb2 = driver.findElement(By.xpath("//input[@id='continue']"));
			cb2.click();
			
			WebElement done = driver.findElement(By.xpath("//*[@id='finish']"));
			done.click();
		
		String Status="Your order has been dispatched, and will arrive just as fast as the pony can get there!";
		check = driver.findElement(By.cssSelector("div[class=complete-text]"));
		if(Status.equals(check)) {
			System.out.println("Not successfull");
		}else {
			System.out.println("Completed Successfull");
		}
		
	

		

	}

}