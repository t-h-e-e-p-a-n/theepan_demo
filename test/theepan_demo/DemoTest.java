package theepan_demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoTest {
@Test
	public void validateregno() throws FileNotFoundException {
	// pass the path to the file as a parameter 
    File file = 
      new File("C:\\Users\\veelasha\\Downloads\\car_input.txt"); 
    Scanner sc = new Scanner(file); 
  StringBuilder inputfile = new StringBuilder();
    while (sc.hasNextLine()) 
   inputfile.append(sc.nextLine());
  

//Create a Pattern object
Pattern r = Pattern.compile("registration (\\D{2}\\d{2}\\s?\\D{3})");
ArrayList<String> number = new ArrayList<String>();
// Now create matcher object.
Matcher m = r.matcher(inputfile);
//int count = m.groupCount();
int i=0;
while( m.find()) {
/*for(int i = 0; i < count; i++)*/ 
	String regtext = m.group(i).replace("registration ", "");
	number.add(regtext);
i++;
}
Pattern r1 = Pattern.compile("registrations (\\D{2}\\d{2}\\s?\\D{3}) and (\\D{2}\\d{2}\\s?\\D{3})");
// Now create matcher object.
Matcher m1 = r1.matcher(inputfile);
int count1 = m1.groupCount();
if( m1.find()) {
for(int j = 0; j < count1; j++) {
	number.add(m1.group(j));
}
}

System.out.println(number);
System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
WebDriver driver = new ChromeDriver();
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
driver.get("https://cartaxcheck.co.uk/");
StringBuilder table = new StringBuilder();
table.append("REGISTRATION,MAKE,MODEL,COLOR,YEAR");
//driver.navigate().to("https://cartaxcheck.co.uk/");
int count2 = number.size();
driver.findElement(By.id("cartaxcheck_history_button-vehiclesearch-v1")).click();
for(int k = 0; k < count2; k++) {
	
driver.findElement(By.id("vrm-input")).sendKeys(number.get(k));

//driver.findElement(By.id("vrm-input")).sendKeys(number.get(i));
driver.findElement(By.xpath("//button[@type='submit']")).click();


table.append(driver.findElement(By.xpath("//dt[contains(text(),'Registration')]/following-sibling::dd")).getText());
table.append(",");
String model = driver.findElement(By.xpath("//dt[text()='Vehicle']/following-sibling::dd")).getText();
model = model.replace(" ", ",");
table.append(model);
table.append(",");

table.append(driver.findElement(By.xpath("//dt[text()='Year']/following-sibling::dd")).getText());
table.append(",");

table.append(driver.findElement(By.xpath("//dt[text()='Colour']/following-sibling::dd")).getText());

driver.navigate().back();
driver.findElement(By.id("vrm-input")).clear();



}

System.out.println(table);

File file1 = 
new File("C:\\Users\\veelasha\\Downloads\\car_output.txt"); 
Scanner sc1 = new Scanner(file1); 
StringBuilder outputfile = new StringBuilder();
while (sc1.hasNextLine()) 
outputfile.append(sc1.nextLine());
Assert.assertEquals(table, outputfile);
}




	}
	

