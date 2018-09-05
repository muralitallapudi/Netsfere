package completeSanityWebAndroid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;


public class CompleteWebAndAndroidSanity extends QuickMonitoringbase {

	 ChromeDriver webDriver;
	 AndroidDriver<AndroidElement> androidDriver;
//	String username1 = "androiduser@nrathya.netsferetest.org";
//	String username2 = "iosuser@nrathya.netsferetest.org";
//	String username3 = "webuser@nrathya.netsferetest.org";
//	
//	String user1 = "webuser";
//	String user2 ="androiduser";
//	String user3 ="iosuser";
//	
	@BeforeClass
	
	public void OneTimeConfig() throws MalformedURLException, InterruptedException {
		
		startAppiumServerNetsfere();
		androidDriver = initializeAndroidDriverNetsfere();
		webDriver  = chromeDriverInitialize();
		
		
	}
//	@Parameters ({"username1","username2","username3","user1","user2","user3"})
	@Parameters ({"username1","username3"})
	@BeforeMethod
//	,@Optional String username2,@Optional String user1, @Optional String user2,@Optional String user3
	public   void Setup(String username1,String username3) throws InterruptedException
	{
		WebDriverWait androidWait = new WebDriverWait(androidDriver, 120);
		WebDriverWait webWait2 = new WebDriverWait(webDriver, 30);
		WebDriverWait webWait3 = new WebDriverWait(webDriver, 120);	
		WebDriverWait webWait = new WebDriverWait(webDriver, 60);
		if(androidDriver.currentActivity().equals(".modules.MainDrawerActivity"))
		{
			System.out.println("Android user in conversation page");
		}
		else
		{
		if ( androidDriver.currentActivity().equals(".modules.user.IntroScreenActivity")) {
			androidDriver.findElementByAndroidUIAutomator("text(\"Login\")").click();
			Thread.sleep(1000);
			if ( androidDriver.currentActivity().equals(".modules.user.LoginFragmentActivity")) {
				androidDriver.findElementById("com.infinite.netsfere:id/email_text").sendKeys(username1);
				androidDriver.findElementById("com.infinite.netsfere:id/password_text").sendKeys("abcdefgh");
				try {
					androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/login_button' and @text='LOGIN']")));
					if ( androidDriver.findElementByAndroidUIAutomator("text(\"LOGIN\")") != null ) {
						androidDriver.findElementByAndroidUIAutomator("text(\"LOGIN\")").click();
						try {
							Thread.sleep(6000);
							if(androidDriver.findElementByXPath("//android.widget.Button[@index='1']").getText().equals("ALLOW"))
							{
									System.out.println("Allowing access to photo media");
									androidDriver.findElementByXPath("//android.widget.Button[@index='1']").click();
							}
							androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));
//							androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/alertTitle']")));
							if ( androidDriver.findElementByAndroidUIAutomator("text(\"Full Disk Encryption\")") != null) {
								androidDriver.findElementByAndroidUIAutomator("text(\"DON'T SHOW AGAIN\")").click();
							}
							else {
								System.out.println("Full Disk Encryption popup is not visible....Continuing..");
							}
						}catch ( NoSuchElementException e) {
							try {
							if(androidDriver.findElementByXPath("//android.widget.Button[@index='1']").getText().equals("Allow"))
							{
									System.out.println("Allowing access to photo media");
									androidDriver.findElementByXPath("//android.widget.Button[@index='1']").click();
							}
							}catch(Exception e1)
							{
							System.out.println("Unable to Click Full Disk Encryption popup, maybe its not there...");
							}
						}
						try {
						
							androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));
							if( androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
								
								System.out.println("Android Device Logged in...");
							} else {
								
								System.out.println("ERROR -Android Login NOT successful....");
								
								System.out.println("exiting the programe...");
								System.exit(0);
							}
						}catch ( NoSuchElementException e) {
					        
							System.out.println("ERROR -Android Login NOT successful....");
					        
							System.out.println("exiting the programe...");
							System.exit(0);							
						}
					}
				}catch ( NoSuchElementException e) {
			        
					
					
					System.out.println("ERROR -Login Button on Android device not visible.....");
			        
					System.out.println("exiting the programe..");
			        System.exit(0);	        	
				}
			} else {
				
				System.out.println("ERROR - Android Device is not in Login Screen ");
				
				System.out.println("exiting the programe..");
				System.exit(0);
			}
			
		} else {
			
			System.out.println("ERROR - Android Device is not in Launch Screen ");
			
			System.out.println("exiting the programe..");
			System.exit(0);
		}
		
		}
		
		
		//Check web in conversation page or not
		
		System.out.println("Check web page");
		webDriver.get("https://web.netsferetest.com");
		Thread.sleep(10000);
		if(webDriver.getTitle().contains("NetSfere - Secure enterprise messaging"))
		{
			try {
				//changed1
//				webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='https://signup.netsferetest.com']")));
				webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='https://signup.netsferetest.com']")));
				if (webDriver.findElementByXPath("//input[@type='email' and @placeholder='Enter email address']") != null ) {
					webDriver.findElementByXPath("//input[@type='email' and @placeholder='Enter email address']").sendKeys(username3);
					webDriver.findElementByXPath("//input[@type='password']").sendKeys("abcdefgh");
					Thread.sleep(1000);
					webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
					webDriver.findElementByXPath("//button[@type='submit']").click();
					webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Welcome to NetSfere')]")));
					
				}
			} catch ( NoSuchElementException e) {
		        
				System.out.println("ERROR -netsfere Login page Not Loaded. Pls Check Internet Connection...");
		        
				System.out.println("exiting the programe...");
		        System.exit(0);
			}
		}
		else if (webDriver.getTitle().contains("Netsfere"))
		{
		System.out.println("Web user in conversation page");
		}
	}

	
	@Parameters ({"username1","username2","username3","user1","user2","user3"})
	@Test(priority = 0)
	
	public  void TC1(String username1,String username2,String username3,String user1,String user2,String user3) throws InterruptedException, IOException{
//		String udate = new SimpleDateFormat("dd-MM-yy-HHmmss").format(new java.util.Date());
//		System.setOut(new PrintStream(new FileOutputStream("Monitoring-" + udate +".txt")));
	
//		System.out.println("Start Time is: " + ( new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss").format(new java.util.Date())));
		
//		startAppiumServerNetsfere();
//		AndroidDriver<AndroidElement> androidDriver=initializeAndroidDriverNetsfere();
		
		System.out.println("------------------Executing Conversation feature----------------------");
		WebDriverWait androidWait = new WebDriverWait(androidDriver, 120);
		if(androidDriver.isDeviceLocked()) {
			 androidDriver.unlockDevice();
			 androidDriver.pressKeyCode(AndroidKeyCode.HOME);
			 androidDriver.launchApp();
		 }
				
		
		TouchAction taction = new TouchAction(androidDriver);
		Duration press = Duration.ofMillis(1000);
		WaitOptions press_wait = WaitOptions.waitOptions(press);		
		
	
	
		WebDriverWait webWait = new WebDriverWait(webDriver, 60);
		WebDriverWait webWait2 = new WebDriverWait(webDriver, 30);
		WebDriverWait webWait3 = new WebDriverWait(webDriver, 120);		
		// Login -- Web
		System.out.println("Login to Web ...");
//		

		
		try {
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Welcome to NetSfere')]")));
			if( webDriver.findElementByXPath("//div[contains(text(),'Welcome to NetSfere')]") != null ) {
				//System.out.println("Web User Logged in..");
				System.out.println("Web User Logged in..");
			}
		} catch ( NoSuchElementException e) {
	        //System.out.println("Web User couldn't login. Pls Check Internet Connection...");
			System.out.println("ERROR -Web User couldn't login. Pls Check Internet Connection...");
	        //System.out.println("exiting the programe...");
			System.out.println("exiting the programe...");
	        System.exit(0);
		}
		
		String box_username = "convergenceinfinite@gmail.com";
		String box_pwd = "infinite1234";
		
//	    Create a self conversation in android without title
		System.out.println("Android User Starting Self Conversation case.");
		try {
			if ( androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				try {
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
						}
				}
				catch(NoSuchElementException e) {
					System.out.println("Unable to find skip button in conversations page Since it's not there...");
				}
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/new_conversation_fab']").click();
				try {
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
						}
				}
				catch(NoSuchElementException e) {
					System.out.println("Unable to find skip button in New conversation creation page Since it's not there...");
				}
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_create']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
				System.out.println("Android User Self conversation created.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to Create Self Conversation...");	
		}
//		send message
		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
			// Send message from Android
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/send_message_edittext']").sendKeys("Hello from Android");
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/send_button']").click();
			}else {
				System.out.println("Android Device is not in in Chat page.. ");
			}
			if (androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview' and @content-desc='Sent']") != null ) {
				String sendStatus = androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
				System.out.println("Sent status is:-" + sendStatus);
				if(sendStatus.equals("Sent")) {
					System.out.println("Android User Sent first message successfully.");
					}
				}
			}
		catch ( NoSuchElementException e) {
			System.out.println("ERROR- Android Unable to Send message in Self Conversation...");
			System.out.println("exiting the programe...");
			System.exit(0);
		}
//		send image-jpg
		if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/insert_attachment_button']").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/attach_from_cloud']").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Box']")));
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Box']").click();
			try {
				if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
					System.out.println("Allowing Access to Photos, Media.");
					androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
				}
			}
			catch(Exception e) {
				try {
				if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
					System.out.println("Allowing Access to Photos, Media.");
					androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();	
				}
				}catch(Exception e1)
				{
				System.out.println("ERROR- Could not Allow Access to Photos, Media.");
				}
			}
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@resource-id='login']")));
			Thread.sleep(1000);
			if(androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']") != null ) {
				System.out.println("Adding Box account.");
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']").sendKeys(box_username);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='password']").sendKeys(box_pwd);
				androidDriver.findElementByXPath("//android.widget.Button[@text='Authorize']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Grant access to Box']")));
				androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
				System.out.println("Added box account and logged into box successfully.");
			}
			else {
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
				if(androidDriver.findElementByXPath("//android.widget.TextView[@text='All Files']") != null) {
					System.out.println("Logged into box account successfully.");
				}
			}

			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"JpegImage.jpg\"));");
			androidDriver.findElementByAndroidUIAutomator("text(\"JpegImage.jpg\")").click();
			if(androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']"))) != null) {
				androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']").click();
			}
			Thread.sleep(2000);
			if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"JpegImage.jpg\")") != null) {
				List<AndroidElement> androidelems1 = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
				System.out.println("androidelems1 size is :-" + androidelems1.size());
				if(androidelems1.size() > 0) {
					if ( androidelems1.get(androidelems1.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_content_imageview']") != null ) {
						WebElement el1 = androidelems1.get(androidelems1.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview' and @content-desc='Sent']");
						if(el1 != null) {
							String sendStatus = androidelems1.get(androidelems1.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
							System.out.println("sent status is :-" + sendStatus);
							if(sendStatus.equals("Sent")) {						
								System.out.println("Android Sent Jpeg Image Successfully...");
								}
							}	
						}
					}
				}
			}
//		change title
		String ConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/more_menu_item']").click();
		androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/button_details']").click();
		androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edittext']").sendKeys(ConversationTitle);
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/save_item']").click();
		androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/more_menu_item']")));
		if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
//			String AndroidSideChangedTitle = androidDriver.findElementByXPath("//android.widget.TextView[@index='1']").getText();
//			System.out.println("Android side title:-" + AndroidSideChangedTitle);
			System.out.println("Android Changed the title successfully.");
			Thread.sleep(1000);
		}		
//		add web user and verify title on web side
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/more_menu_item']").click();
		androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/button_details']").click();
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/edit_people_textview' and @text='Edit Participants']").click();
		Thread.sleep(3000);
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
		androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(user1);
		Thread.sleep(2000);
		androidDriver.findElementByXPath("//android.widget.CheckBox[@resource-id='com.infinite.netsfere:id/select_checkBox']").click();
		Thread.sleep(1000);
		androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/action_fab']").click();
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/save_item']").click();
		Thread.sleep(2000);
		webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(ConversationTitle);
		Thread.sleep(1000);
		if ( webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").getText().contains("No Conversations Matching Search")) {
			webDriver.findElementByXPath("//button//span[@class='material-icons']").click();
			webDriver.navigate().refresh();
			Thread.sleep(2000);
			webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(ConversationTitle);
			Thread.sleep(2000);
		}
		if(webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]") != null) {
			System.out.println("Web user was successfully added and the conversation exists on webuser side.");	
			webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").click();
		}
		webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").sendKeys("Message from web");
		webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
		Thread.sleep(3000);
		String rcvdMessageAndroid = "";
		List<AndroidElement> androidelems2 = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
		if(androidelems2.size() > 0) {
			rcvdMessageAndroid = androidelems2.get(androidelems2.size() - 1).findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/message_content_textview']").getText();
		}
		System.out.println("Message Recieved from Web is = " + rcvdMessageAndroid);
		if(rcvdMessageAndroid.equals("Message from web")) {
			System.out.println("Android recieved correct message from Web");
		} else {
			System.out.println("ERROR -Android recieved wrong message from Web");
			}
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		webDriver.findElementByXPath("//div[contains(text(),'Cancel')]").click();
		System.out.println("Android User Completed Self Converstion Case.");
	
			
		
		
//		Create a self conversation in web
		System.out.println("Web User Starting Self Conversation case.");
		try {
			webDriver.findElementByXPath("//div[@class='scrollbox']/div/div/button[2]").click();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'create')]")));
			webDriver.findElementByXPath("//span[contains(text(),'create')]").click();
			Thread.sleep(3000);
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']")));
			if(webDriver.findElementByXPath("//textarea[@class='namegenTextLong']") != null) {
				System.out.println("Web created self conversation successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR:-Web was unable to create self conversation.");
		}
		
//		send message
		try {
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").click();
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").sendKeys("Message from web");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			Thread.sleep(3000);
			if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']/following-sibling::div//div/span[@title='Sent']"))) != null) {
				System.out.println("Web Sent first messsage in self conversation successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR-Web was unable to Send first messsage in self conversation successfully.");
		}
		
//		send Png image
		webDriver.findElementByXPath("//span[@class='icon ion-plus-circled']").click();
		Thread.sleep(1000);
		webDriver.findElementByXPath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'From Device')]").click();
		Thread.sleep(1000);
		Runtime.getRuntime().exec("D:\\Netsfere\\PngFileUpload.exe");
		Thread.sleep(3000);
		try {
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
			if ( webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]") != null ) {
				webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]").click();
				// Web to assert the sent Image. 
				Thread.sleep(15000);
				List<WebElement> elems2 = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
				if(elems2.size() > 0) {
					WebElement el1 = elems2.get(elems2.size() - 1).findElement(By.xpath("//div[contains(@style,'blob')]"));
					if ( el1 != null ) {
//						//System.out.println("5");
//						//try {
//						//	System.out.println("6");
//							//if (el1.findElement(By.xpath("/../../../../following-sibling::div/div/div/span[@title='Sent']")) != null) {
//							//if ( el1.findElement(By.xpath("//span[@class='fa fa-check-circle']")) != null ) {								
//								//System.out.println("Image Sent Status : "+el1.findElement(By.xpath("//span[@class='fa fa-check-circle']")).getAttribute("title"));
//								//System.out.println("7");
//								//System.out.println("Image Sent Status : "+el1.findElement(By.xpath("/../../../../following-sibling::div/div/div/span[@title='Sent']")).getAttribute("title"));
								System.out.println("Web sent the Png Image successfully...");
							}							
//						//} catch ( NoSuchElementException e) {
//							//System.out.println("Error While sending the Image...");
//							//System.out.println("ERROR -Error While sending the Image...");
//						//}
//																	
					}
				}
		}  catch ( NoSuchElementException e) {
//			//System.out.println("Web Unable to Attach Image in Conversation...");
			System.out.println("ERROR -Web Unable to Attach Image in Conversation...");
		}
		String WebConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//		change title
		try {
			webDriver.findElementByXPath("//span[@class='icon ion-gear-a']").click();
			Thread.sleep(2000);
			if(webDriver.findElementByXPath("//div/input[contains(@placeholder,'Title (optional)')]") != null){
				webDriver.findElementByXPath("//div/input[contains(@placeholder,'Title (optional)')]").click();
				webDriver.findElementByXPath("//div/input[contains(@class,'namegenTitleReplace')]").sendKeys(WebConversationTitle);
				webDriver.findElementByXPath("//span[contains(text(),'save')]").click();
				if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']"))) != null) {
					System.out.println("Web Changed Conversation title successfully.");
					Thread.sleep(3000);
				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web Unable to Change Conversation Title...");
		}
//		add android user and verify title on android side
		try {
			webDriver.findElementByXPath("//div[text()='Edit Participant(s)']").click();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'save')]")));			
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(user2);
			System.out.println("Before Enter");
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(Keys.RETURN);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(Keys.SPACE);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(Keys.BACK_SPACE);
			System.out.println("After Enter");
			Thread.sleep(4000);
			System.out.println("Before Click");
			webDriver.findElementByXPath("//div[contains(text(),'androiduser')]").click();
			System.out.println("After Click");
			Thread.sleep(1000);
			System.out.println("Before Save");
			webDriver.findElementByXPath("//span[contains(text(),'save')]").click();
			System.out.println("After Save");
			Thread.sleep(2000);
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']")));
			if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']"))) != null) {
				System.out.println("Web User added android user successfully.");
				System.out.println("Checking on android device whether conversation exists or not...");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Unable to add android user.");
		}
		try {		
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(WebConversationTitle);
			Thread.sleep(3000);
			List<AndroidElement> androidelems3 = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/conversation_list']//android.view.ViewGroup[@resource-id='com.infinite.netsfere:id/conversation_row_layout']//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']");
			System.out.println("androidelms3 is:- " + androidelems3.size());
			if(androidelems3.size() == 1) {
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']").click();
				System.out.println("Conversation exists on android side.");
				System.out.println("Android User was added successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Unable to find conversation on android side.");
		}
		
		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
			// Send message from Android
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/send_message_edittext']").sendKeys("Hello from Android");
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/send_button']").click();
				Thread.sleep(5000);
			}else {
				System.out.println("Android Device is not in in Chat page.. ");
			}
			if (androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview' and @content-desc='Read']") != null ) {
				String sendStatus = androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
				System.out.println("Sent status is:-" + sendStatus);
				if(sendStatus.equals("Read")) {
					System.out.println("Android User Sent message to web successfully.");
					}
				}
			}
		catch ( NoSuchElementException e) {
			System.out.println("ERROR- Android Unable to Send message to web.");
		}
		
		try {
			String rcvdMessageFromAndroid = "";
			List<WebElement> elems = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
			if(elems.size() == 0) {
				webDriver.navigate().refresh();
				Thread.sleep(2000);
			}
			if(elems.size() > 0) {
				rcvdMessageFromAndroid= elems.get(elems.size() - 1).getText();
			}
			System.out.println("Message Recieved from Android :" + rcvdMessageFromAndroid);
			if(rcvdMessageFromAndroid.equals("Hello from Android")) {
				System.out.println("web recieved correct message from android");
			} else {
				System.out.println("ERROR -web recieved wrong message from android");
			}	
		}
		catch(Exception e) {
			System.out.println("ERROR- web did not receive message from android");
		}		
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/search_close_btn']").click();
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		webDriver.findElementByXPath("//span[@class='icon ion-ios-chatbubble']").click();
		System.out.println("Web User Completed Self Conversation Case.");

		
//		Create a 1-1 conversation from android with web and with title
		System.out.println("Android User Starting One-To-One Conversation With Web User.");
		String AndroidOneToOneConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		try {
			if ( androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				try {
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
						}
					else {
						System.out.println("No Tips are present, Continuing...");
					}
				}
				catch(NoSuchElementException e) {
					System.out.println("Unable to find skip button in conversations page since it's not there...");
				}
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/new_conversation_fab']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@resource-id='android:id/search_src_text']")));
				try {
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
						}
					else {
						System.out.println("No Tips are present, Continuing...");
					}
				}
				catch(NoSuchElementException e) {
					System.out.println("Unable to find skip button in New conversation creation page since it's not there...");
				}
				
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").click();
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").sendKeys(user1);
				Thread.sleep(5000);
				System.out.println("Entering webuser name...");
				androidDriver.findElementByXPath("//android.widget.CheckBox[@resource-id='com.infinite.netsfere:id/select_checkBox']").click();
				Thread.sleep(3000);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edit_text']").click();
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edit_text']").sendKeys(AndroidOneToOneConversationTitle);;
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_create']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
				if(androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']") != null) {
					System.out.println("Android Created One-To-One Conversation with Web Successfully.");
				}	
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to Create One-To-One Conversation...");	
		}
//		send message
		try {
		// Send message from Android
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/send_message_edittext']").sendKeys("Hello from Android");
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/send_button']").click();
			}else {
				System.out.println("Android Device is not in in Chat page.. ");
			}
			if (androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview' and @content-desc='Sent']") != null ) {
				String sendStatus = androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
				System.out.println("Sent status is:-" + sendStatus);
				if(sendStatus.equals("Sent")) {
					System.out.println("Android User Sent first message in 1-1 conversation successfully.");
					}
				}	
			}
		catch ( NoSuchElementException e) {
			System.out.println("ERROR- Android Unable to Send message in Self Conversation...");
			System.out.println("exiting the programe...");
			System.exit(0);
		}
		
		String rcvdMessageatWeb = "";
		try {
			webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidOneToOneConversationTitle);
			Thread.sleep(1000);
			if ( webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").getText().contains("No Conversations Matching Search")) {
				webDriver.findElementByXPath("//button//span[@class='material-icons']").click();
				webDriver.navigate().refresh();
				Thread.sleep(2000);
				webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidOneToOneConversationTitle);
				Thread.sleep(2000);
			}
			webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").click();
			Thread.sleep(2000);
			List<WebElement> elemsweb = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
			if(elemsweb.size() == 0) {
				webDriver.navigate().refresh();
				Thread.sleep(2000);
			}
			System.out.println("Conversation created by android with web exists on web side.");
			System.out.println("test elems:-" + elemsweb.size());
			if(elemsweb.size() > 0) {
				rcvdMessageatWeb= elemsweb.get(elemsweb.size() - 1).getText();//test1
			}
			System.out.println("Message Recieved from Android :" + rcvdMessageatWeb);
			if(rcvdMessageatWeb.equals("Hello from Android")) {
				System.out.println("web recieved correct message from android");
			} else {
				System.out.println("ERROR -web recieved wrong message from android");
			}	
		}
		catch(Exception e) {
			System.out.println("ERROR- Conversation created by android with web does not exists on web side Or Web did not receive the message sent by android.");
		}
	
//		send location
		try {
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/insert_attachment_button']").click();
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/attach_location_button']").click();
				
				try {
					if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access this device's location?\")")!=null) {
						System.out.println("Allowing Access to Location.");
						androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
					}
					else {
						System.out.println("Allowing Access to Location PopUp Does not exist.");
					}
				}
				catch(Exception e) {
					try {
					if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access this device's location?\")")!=null) {
						System.out.println("Allowing Access to Location.");
						androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();	
					}
					}catch(Exception e1)
					{
					System.out.println("ERROR- Could not Allow Access to Location");
					}
				}
				
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_done']")));
				if(androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_done']") != null) {
					androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_done']").click();
				}
				List<AndroidElement> forlocelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
				System.out.println("androidelems1 size is :-" + forlocelems.size());
				if(forlocelems.size() > 0) {
					if ( forlocelems.get(forlocelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_content_imageview']") != null ) {
						WebElement el1 = forlocelems.get(forlocelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview' and @content-desc='Read']");
						if(el1 != null) {
							String sendStatus = forlocelems.get(forlocelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
							System.out.println("sent status is :-" + sendStatus);
							if(sendStatus.equals("Read")) {						
								System.out.println("Android Sent Location Successfully...");
								}
							}	
						}
					}
				}
			}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to Send Location.");
		}
		
		try {
			if(webDriver.findElementByXPath("//a/img[contains(@src, 'https://maps.googleapis.com')]") != null) {
				System.out.println("Web received the Location sent by Android.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web did not receive the Location sent by Android.");
		}
		
//		send jpeg image
		if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
		androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/insert_attachment_button']").click();
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/attach_from_cloud']").click();
//		androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Box']")));
//		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='android:id/text1' and @text='Box']").click();
		
		try {
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']") !=null) {
				System.out.println("Selecting Convergence Box account since it is already added.");
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']").click();
			}
			else {
				System.out.println("Box account already exists so continuing...");
			}
		}
		catch(Exception e) {
			System.out.println("Unable to select Convergence Box account or maybe box account is not added yet..");
		}
				
		try {
			if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
				System.out.println("Allowing Access to Photos, Media.");
				androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
			}
			else {
				System.out.println("Allowing Access to Photos, Media PopUp does not exist so continuing...");
			}
		}
		catch(Exception e) {
			
			try {
			if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
				System.out.println("Allowing Access to Photos, Media.");
				androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();	
			}
			}catch(Exception e1)
			{
			System.out.println("Allowing Access to Photos, Media PopUp does not exist so continuing...");
			}
		}
		Thread.sleep(7000);
		try {
			if(androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']") != null ) {
				System.out.println("Adding Box account.");
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']").sendKeys(box_username);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='password']").sendKeys(box_pwd);
				androidDriver.findElementByXPath("//android.widget.Button[@text='Authorize']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Grant access to Box']")));
				androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
				System.out.println("Added box account and logged into box successfully.");
			}
			else {
				System.out.println("Box account exists, Continuing...");
			}
		}
		catch(Exception e) {
			System.out.println("Box account exists, Continuing...");
		}

		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='All Files']") != null) {
				System.out.println("Logged into box account successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("Not Logged into box account.");
		}

		androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"JpegImage.jpg\"));");
		androidDriver.findElementByAndroidUIAutomator("text(\"JpegImage.jpg\")").click();
		if(androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']"))) != null) {
			androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']").click();
		}
		Thread.sleep(8000);
		if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"JpegImage.jpg\")") != null) {
			List<AndroidElement> forjpegelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
			System.out.println("forjpegelems size is :-" + forjpegelems.size());
			if(forjpegelems.size() > 0) {
				if ( forjpegelems.get(forjpegelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_content_imageview']") != null ) {
					WebElement el1 = forjpegelems.get(forjpegelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']"); //and @content-desc='Read']");
					if(el1 != null) {
						String sendStatus = forjpegelems.get(forjpegelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
						System.out.println("sent status is :-" + sendStatus);
						if(sendStatus.equals("Read")) {						
							System.out.println("Android Sent Jpeg Image Successfully...");
							}
						}	
					}
				}
			}
		}
//		Verify web received the jpeg file.
		try {
			if(webDriver.findElementByXPath("//div[contains(@style,'blob')]") != null) {
				System.out.println("Web received the Jpeg image sent by Android.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web did not receive the Jpeg image sent by Android.");
		}
		
//		send pdf
		if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
		androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/insert_attachment_button']").click();
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/attach_from_cloud']").click();
		
		try {
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']") !=null) {
				System.out.println("Selecting Convergence Box account.");
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']").click();
			}
			else {
				System.out.println("Box account already exists so continuing...");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Unable to select Convergence Box account or maybe box account is not added yet.");
		}
		
		try {
			if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
				System.out.println("Allowing Access to Photos, Media.");
				androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
			}
			else {
				System.out.println("Allowing Access to Photos, Media popup does not exist so continuing...");
			}
		}
		catch(Exception e) {
			try {
			if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
				System.out.println("Allowing Access to Photos, Media.");
				androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();	
			}
			}catch(Exception e1)
			{
			System.out.println("No PopUp To Allow Access to Photos, Media.");
			}
		}
		Thread.sleep(7000);
		try {
			if(androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']") != null ) {
				System.out.println("Adding Box account.");
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']").sendKeys(box_username);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='password']").sendKeys(box_pwd);
				androidDriver.findElementByXPath("//android.widget.Button[@text='Authorize']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Grant access to Box']")));
				androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
				System.out.println("Added box account and logged into box successfully.");
			}
			else {
				System.out.println("Box account exists, Continuing...");
			}
		}
		catch(Exception e) {
			System.out.println("Box account exists, Continuing...");
		}
		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='All Files']") != null) {
				System.out.println("Logged into box account successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("Not Logged into box account.");
		}
		
		androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
		androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"PDFFile.pdf\"));");
		androidDriver.findElementByAndroidUIAutomator("text(\"PDFFile.pdf\")").click();
		if(androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']"))) != null) {
			androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']").click();
		}
		Thread.sleep(10000);
		if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"PDFFile.pdf\")") != null) {
			List<AndroidElement> forpdfelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
			System.out.println("forpdfelems size is :-" + forpdfelems.size());
			if(forpdfelems.size() > 0) {
				if ( forpdfelems.get(forpdfelems.size() - 1).findElementByXPath("//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/message_box_layout']") != null ) {
					WebElement el1 = forpdfelems.get(forpdfelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']");//and @content-desc='Read']");
					if(el1 != null) {
						String sendStatus = forpdfelems.get(forpdfelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
						System.out.println("sent status is :-" + sendStatus);
						if(sendStatus.equals("Read")) {						
							System.out.println("Android Sent PDF file Successfully...");
							}
						}	
					}
				}
			}
		}
////		Verify web received the pdf file.
		try {
			if(webDriver.findElementByXPath("//div[text()='PDFFile.pdf']") != null) {
				System.out.println("Web received the Pdf file sent by Android.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web did not receive the Pdf file sent by Android.");
		}
		
//		change title and verify on web side
		String AndroidOneToOneChangeConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		try {
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/more_menu_item']").click();
			androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/button_details']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edittext']").sendKeys(AndroidOneToOneChangeConversationTitle);
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/save_item']").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/more_menu_item']")));
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				String AndroidSideTitle = androidDriver.findElementByXPath("//android.widget.TextView[@index='1']").getText();
				System.out.println("Android side title:-" + AndroidSideTitle);
				System.out.println("Android Changed the title successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("Android Unable to Change the title.");
		}
		
		try {
			webDriver.findElementByXPath("//div[contains(text(),'Cancel')]").click();
			webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidOneToOneChangeConversationTitle);
			Thread.sleep(3000);
			if ( webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").getText().contains("No Conversations Matching Search")) {
				webDriver.findElementByXPath("//button//span[@class='material-icons']").click();
				webDriver.navigate().refresh();
				Thread.sleep(2000);
				webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidOneToOneChangeConversationTitle);
				Thread.sleep(2000);
			}
			if(webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]") != null) {
				webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").click();
				System.out.println("Web user is able to find the conversation title changed by andorid.");	
			}
		}
		catch(Exception e) {
			System.out.println("Web user is unable to find the conversation title changed by andorid.");
		}
		
//		Remove web user from android and verify the conversation does not exist on web side		
		try {
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/more_menu_item']").click();
			androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/button_details']").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/edit_people_textview' and @text='Edit Participants']").click();
			Thread.sleep(3000);
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(user1);
			Thread.sleep(3000);
			androidDriver.findElementByXPath("//android.widget.CheckBox[@resource-id='com.infinite.netsfere:id/select_checkBox']").click();
			Thread.sleep(1000);
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/action_fab']").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/save_item']").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				System.out.println("Android Removed web user successfully.");	
			}
		}
		catch(Exception e) {
			System.out.println("Android Unable to remove web user");	
		}
		
		try {
			if(webDriver.findElementByXPath("//div[text()='You have been removed from this conversation.']") != null) {
				webDriver.findElementByXPath("//span[text()='OK']").click();
				System.out.println("You were removed popup exists on web side so clicking OK.");	
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- You were removed popup does not exist on web side which means web user was not removed.");	
		}
		webDriver.findElementByXPath("//div[contains(text(),'Cancel')]").click();
		
		try {
		webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidOneToOneChangeConversationTitle);
		Thread.sleep(3000);
		if ( webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").getText().contains("No Conversations Matching Search")) {
			webDriver.findElementByXPath("//button//span[@class='material-icons']").click();
			webDriver.navigate().refresh();
			Thread.sleep(2000);
			webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidOneToOneChangeConversationTitle);
			Thread.sleep(2000);
		}
		if(webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]") != null) {
//			webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").click();
			if(webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2][text()='No Conversations Matching Search']") != null) {
				System.out.println("Web user is unable to find the conversation which means he was removed..");
				System.out.println("Android User removed web user successfully.");	
			}
			
		}
		else {
			System.out.println("Web user is unable to find the conversation which means he was removed..");
			System.out.println("Android User removed web user successfully.");
		}
	}
	catch(Exception e) {
		System.out.println("ERROR- Web user is able to find the conversation since android was unable to remove webuser.");
	}
		webDriver.findElementByXPath("//div[contains(text(),'Cancel')]").click();
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		System.out.println("Pressing back button at the last in android");
		webDriver.findElementByXPath("//span[@class='icon ion-ios-chatbubble']").click();

		System.out.println("One-To-One Conversation case completed for Android.");
		
//		Create a 1-1 conversation from web with android and with title
		System.out.println("Web User Starting One-To-One Conversation With Android User.");
		Thread.sleep(1000);
		String WebOneToOneConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); 
		try {
			webDriver.findElementByXPath("//div[@class='scrollbox']/div/div/button[2]").click();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'create')]")));
			webDriver.findElementByXPath("//div/input[contains(@placeholder,'Title (optional)')]").click();
			webDriver.findElementByXPath("//div/input[contains(@class,'namegenTitleReplace')]").sendKeys(WebOneToOneConversationTitle);
			Thread.sleep(3000);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(user2);
			Thread.sleep(3000);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(Keys.RETURN);
			webDriver.findElementByXPath("//div[@class='mainDiv']//div[2]//div[@class='scrollbox']/div[@class='table-view']//div[@class='click-ripple']").click();
			Thread.sleep(1000);
			webDriver.findElementByXPath("//span[contains(text(),'create')]").click();
			Thread.sleep(3000);
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']")));
			if(webDriver.findElementByXPath("//textarea[@class='namegenTextLong']") != null) {
				System.out.println("Web created One-To-One conversation with Android successfully.");
				Thread.sleep(2000);
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web Unable to create One-To-One conversation with Android.");
		}
//		send message
		try {
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").click();
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").sendKeys("Message from web");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			Thread.sleep(3000);
			if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']/following-sibling::div//div/span[@title='Sent']"))) != null) {
				System.out.println("Web Sent messsage in One-To-one conversation with Android successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR-Web was unable to Send messsage in One-To-one conversation with Android.");
		}
		
		String rcvdMessageatAndroid = "";
		try {
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(WebOneToOneConversationTitle);
			List<AndroidElement> androidelems5 = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/conversation_list']//android.view.ViewGroup[@resource-id='com.infinite.netsfere:id/conversation_row_layout']//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']");
			System.out.println("androidelms5 is:- " + androidelems5.size());
			if(androidelems5.size() == 1) {
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']").click();
				System.out.println("One-To-One Conversation created by web exists on android side.");
				Thread.sleep(1000);
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to find One-To-One Conversation created by web on android side.");
		}
		
		try {
			List<AndroidElement> androidelems6 = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
			if(androidelems6.size() > 0) {
				rcvdMessageatAndroid = androidelems6.get(androidelems6.size() - 1).findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/message_content_textview']").getText();
			}
			System.out.println("Message Recieved from Web is = " + rcvdMessageatAndroid);
			if(rcvdMessageatAndroid.equals("Message from web")) {
				System.out.println("Android recieved correct message from Web");
			} else {
				System.out.println("ERROR -Android recieved wrong message from Web");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR -Android did not recieve message from Web");
		}
//		send location
		try {
			webDriver.findElementByXPath("//span[@class='icon ion-plus-circled']").click();
			Thread.sleep(1000);
			webDriver.findElementByXPath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'Share Location')]").click();
			Thread.sleep(2000);
		
			List<WebElement> forweblocelems2 = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
			if(forweblocelems2.size() > 0) {
				WebElement el1 = forweblocelems2.get(forweblocelems2.size() - 1).findElement(By.xpath("//a/img[contains(@src, 'https://maps.googleapis.com')]"));
				if ( el1 != null ) {

						System.out.println("Web Sent the Location Successfully to Android.");

				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web Unable to Send the Location Successfully Android.");
		}
		try {
			if(webDriver.findElementByXPath("//a/img[contains(@src, 'https://maps.googleapis.com')]") != null) {
				System.out.println("Web sent the Location Successfully to Android.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web did not send the Location to Android.");
		}
		
		try {
			List<AndroidElement> forandlocelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
			System.out.println("androidelems1 size is :-" + forandlocelems.size());
			if(forandlocelems.size() > 0) {
				if ( forandlocelems.get(forandlocelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_content_imageview']") != null ) {
					System.out.println("Android received the Location from web Successfully.");
				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android did not receive the Location from web.");
		}
		
//		send png image
		webDriver.findElementByXPath("//span[@class='icon ion-plus-circled']").click();
		Thread.sleep(1000);
		webDriver.findElementByXPath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'From Device')]").click();
		Thread.sleep(1000);

		Runtime.getRuntime().exec("D:\\Netsfere\\PngFileUpload.exe");
		Thread.sleep(3000);
		try {
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
			if ( webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]") != null ) {
				webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]").click();
				// Web to assert the sent Image. 
				Thread.sleep(15000);
				List<WebElement> forwebpngelems = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
				if(forwebpngelems.size() > 0) {
					WebElement el1 = forwebpngelems.get(forwebpngelems.size() - 1).findElement(By.xpath("//div[contains(@style,'blob')]"));
					if ( el1 != null ) {
						System.out.println("Web sent Png image to Android Successfully.");
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web unable to send Png image..");
		}
		
		try {
			if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"PNG.png\")") != null) {
				System.out.println("Android received Png image Successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("Android did not receive Png image sent by web.");
		}
		//send doc
		webDriver.findElementByXPath("//span[@class='icon ion-plus-circled']").click();
		Thread.sleep(1000);
		webDriver.findElementByXPath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'From Device')]").click();
		Thread.sleep(1000);

		Runtime.getRuntime().exec("D:\\Netsfere\\DocFileUpload.exe");
		Thread.sleep(3000);
		try {
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
			if ( webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]") != null ) {
				webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]").click();
				// Web to assert the sent Image. 
				Thread.sleep(10000);
				List<WebElement> forwebpngelems = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
				if(forwebpngelems.size() > 0) {
					WebElement el1 = forwebpngelems.get(forwebpngelems.size() - 1).findElement(By.xpath("//div[text()='DOCFile.doc']"));
					if ( el1 != null ) {
						System.out.println("Web sent Word Document to Android Successfully.");
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web unable to send Word Document to Android.");
		}
		
		try {
			if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"DOCFile.doc\")") != null) {
				System.out.println("Android received Word Document Successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("Android did not receive Word Document sent by web.");
		}
		
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(2000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);

		//web change the conversation title
		String WebOneToOneChangeConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		try {
			webDriver.findElementByXPath("//span[@class='icon ion-gear-a']").click();
			Thread.sleep(2000);
			if(webDriver.findElementByXPath("//div/input[contains(@placeholder,'Title (optional)')]") != null){
				webDriver.findElementByXPath("//div/input[contains(@placeholder,'Title (optional)')]").click();
				webDriver.findElementByXPath("//div/input[contains(@class,'namegenTitleReplace')]").clear();
				webDriver.findElementByXPath("//div/input[contains(@class,'namegenTitleReplace')]").sendKeys(WebOneToOneChangeConversationTitle);
				webDriver.findElementByXPath("//span[contains(text(),'save')]").click();
				if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']"))) != null) {
					System.out.println("Web Changed One-To-One Conversation title successfully.");
					Thread.sleep(3000);					
				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web Unable Change One-To-One Conversation title.");
		}
		
		try {
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(WebOneToOneChangeConversationTitle);
			List<AndroidElement> fortitleelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/conversation_list']//android.view.ViewGroup[@resource-id='com.infinite.netsfere:id/conversation_row_layout']//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']");
			System.out.println("androidelms5 is:- " + fortitleelems.size());
			if(fortitleelems.size() == 1) {
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']").click();
				System.out.println("Android user is able to find the conversation title changed by web");
				Thread.sleep(1000);
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android user is unable to find the conversation title changed by web.");
		}
		
		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
			// Send message from Android
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/send_message_edittext']").sendKeys("Hey web, Thanks for changing the title");
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/send_button']").click();
				Thread.sleep(3000);
			}else {
				System.out.println("Android Device is not in in Chat page.. ");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to send message in conversation created by web.");
		}
		
//		Remove android user from web and verify the conversation does not exist on android side
		webDriver.findElementByXPath("//div[text()='Edit Participant(s)']").click();
		webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'save')]")));	
		webDriver.findElementByXPath("//span[@class='material-icons' and text()='close']").click();
		webDriver.findElementByXPath("//span[contains(text(),'save')]").click();
		Thread.sleep(2000);
		webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']")));
		if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']"))) != null) {
			System.out.println("Web Removed android user successfully.");
			System.out.println("Checking on android device whether the conversation exists or not...");
		}
		
		try {
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='You have been removed from this conversation.']") != null) {
				System.out.println("You have removed popup exists on android side so clicking ok.");
				androidDriver.findElementByXPath("//android.widget.Button[@text='OK']").click();
				Thread.sleep(2000);
			}
		}
		catch(Exception e) {
			System.out.println("You have been removed popup does not exist on android side.");
		}
		
		androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/search_close_btn']")));
		androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/search_close_btn']").click();
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));

		try {
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(WebOneToOneChangeConversationTitle);
			Thread.sleep(2000);

			
			if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"No Conversations\")") != null) {
				System.out.println("Conversation does not exist since android user was removed");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Conversation exists since web was unable to remove android user.");
		}
		
		androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/search_close_btn']").click();
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(1000);
		webDriver.findElementByXPath("//span[@class='icon ion-ios-chatbubble']").click();
		System.out.println("One-To-One conversation case completed for Web.");
		
		
//		Above 4 cases are fine. Modification done for below group cases. Run it once and see.
		
		
//		Create group conversation from android with web and ios users.
		System.out.println("Android User Starting Group Conversation With Web and Ios User.");
		String AndroidGroupConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		try {
			if ( androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				try {	
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
					}
				}
				catch(NoSuchElementException e) {
					System.out.println("Unable to find skip button in conversations page Since it's not there...");
				}
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/new_conversation_fab']").click();
				try {
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
					}
				}
				catch(NoSuchElementException e) {
					System.out.println("Unable to find skip button in New conversation creation page Since it's not there...");
				}
				
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").click();
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").sendKeys(user1);
				androidDriver.pressKeyCode(AndroidKeyCode.ENTER);
				Thread.sleep(2000);
				androidDriver.findElementByXPath("//android.widget.CheckBox[@resource-id='com.infinite.netsfere:id/select_checkBox']").click();
				Thread.sleep(3000);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").click();
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").sendKeys(user3);
				androidDriver.pressKeyCode(AndroidKeyCode.ENTER);
				Thread.sleep(2000);
				androidDriver.findElementByXPath("//android.widget.CheckBox[@resource-id='com.infinite.netsfere:id/select_checkBox']").click();
				Thread.sleep(3000);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edit_text']").click();
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edit_text']").sendKeys(AndroidGroupConversationTitle);;
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_create']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
				if(androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']") != null) {
					System.out.println("Android Created Group Conversation with Web and Ios users Successfully.");
				}	
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to Create Group Conversation...");	
			}
		try {
		// Send message from Android
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/send_message_edittext']").sendKeys("Hello from Android");
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/send_button']").click();
			}else {
				System.out.println("Android Device is not in in Chat page.. ");
			}
			if (androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview' and @content-desc='Sent']") != null ) {
				String sendStatus = androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
				System.out.println("Sent status is:-" + sendStatus);
				if(sendStatus.equals("Sent")) {
					System.out.println("Android User Sent message in Group conversation successfully.");
					Thread.sleep(2000);
					}
				}	
			}
		catch ( NoSuchElementException e) {
			System.out.println("ERROR- Android Unable to Send message in Group Conversation...");
			System.out.println("exiting the programe...");
			System.exit(0);
		}
		
		String rcvdGrpMessageatWeb = "";
		try {
			webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidGroupConversationTitle);
			Thread.sleep(1000);
			if ( webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").getText().contains("No Conversations Matching Search")) {
				webDriver.findElementByXPath("//button//span[@class='material-icons']").click();
				webDriver.navigate().refresh();
				Thread.sleep(2000);
				webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(AndroidGroupConversationTitle);
				Thread.sleep(2000);
			}
			webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").click();
			Thread.sleep(2000);
			List<WebElement> grpelemsweb = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
			if(grpelemsweb.size() == 0) {
				webDriver.navigate().refresh();
				Thread.sleep(2000);
			}
			System.out.println("Group Conversation created by android with web and ios user exists on web side.");
			if(grpelemsweb.size() > 0) {//test
				System.out.println("grpelemsweb size inside is:-" + grpelemsweb.size());//test
				rcvdGrpMessageatWeb= grpelemsweb.get(grpelemsweb.size() - 1).getText();
			}
			System.out.println("Message Recieved from Android :" + rcvdGrpMessageatWeb);
			if(rcvdGrpMessageatWeb.contains("Hello from Android")) {
				System.out.println("web recieved correct message from android in group conversation");
			} else {
				System.out.println("ERROR -web recieved wrong message from android in group conversation");
			}	
		}
		catch(Exception e) {
			System.out.println("ERROR- Group Conversation created by android with web does not exists on web side Or Web did not receive the message sent by android.");
		}
		
		//Web send a message and verify the message on android
		try {
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").click();
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").sendKeys("Message from web");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			Thread.sleep(4000);
//			check this below sent, since chat is open on android side it would have been read.
			if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']/following-sibling::div//div/span[@title='Read by 1']"))) != null) { 
				System.out.println("Web Sent messsage in Group conversation with Android and iOS successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR-Web was unable to Send messsage in One-To-one conversation with Android.");
		}
		
		
		String rcvdGrpMessageatAndroid = "";
		try {
			List<AndroidElement> grpandroidelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
			if(grpandroidelems.size() > 0) {
				rcvdGrpMessageatAndroid = grpandroidelems.get(grpandroidelems.size() - 1).findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/message_content_textview']").getText();
			}
			System.out.println("Message Recieved from Web is = " + rcvdGrpMessageatAndroid);
			if(rcvdGrpMessageatAndroid.equals("Message from web")) {
				System.out.println("Android recieved correct message from Web in group conversation");
			} else {
				System.out.println("ERROR -Android recieved wrong message from Web in group conversation");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR -Android did not recieve message from Web in group conversation");
		}
		
		//Android send a quickreply
		try {
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']").click();
			Thread.sleep(2000);
			androidDriver.findElementByXPath("//android.widget.TextView[@index='0']").click();
			Thread.sleep(1000);
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/send_button']").click();
			Thread.sleep(3000);
			System.out.println("Android User Sent Quick reply in group conversation");
		}
		catch(Exception e) {
			System.out.println("ERROR- Android User was unable to send Quick reply in group conversation");
		}
		
		//Web send a quickreply 
		try {
			webDriver.findElementByXPath("//span[@class='icon ion-flash']").click();
			Thread.sleep(1000);
			webDriver.findElementByXPath("//div[text()='On my way.']").click();
			Thread.sleep(3000);
			System.out.println("Web User Sent Quick reply in group conversation");
		}
		catch(Exception e) {
			System.out.println("Web User unable to send Quick reply in group conversation");
		}
		
		//Android send a ppt and verify it on web
		if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
		androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/insert_attachment_button']").click();
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/attach_from_cloud']").click();
	
		try {
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']") !=null) {
				System.out.println("Selecting Convergence Box account since it is already added.");
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']").click();
			}
			else {
				System.out.println("Box account already exists so continuing...");
			}
		}
		catch(Exception e) {
			System.out.println("Unable to select Convergence Box account or maybe box account is not added yet..");
		}
		
		try {
			if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
				System.out.println("Allowing Access to Photos, Media.");
				androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
			}
			else {
				System.out.println("Allowing Access to Photos, Media PopUp does not exist so continuing...");
			}
		}
		catch(Exception e) {
			try {
			if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
				System.out.println("Allowing Access to Photos, Media.");
				androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();	
			}
			System.out.println("Allowing Access to Photos, Media PopUp does not exist so continuing...");
			
			}
			catch(Exception e1)
			{
				System.out.println("Allowing Access to Photos, Media PopUp does not exist so continuing...");
			}
		}
		Thread.sleep(7000);

//		check this, since box is already loged in previous test cases, we have to change this. check other cases also for this.////////
		try {
			if(androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']") != null ) {
				System.out.println("Adding Box account.");
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']").sendKeys(box_username);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='password']").sendKeys(box_pwd);
				androidDriver.findElementByXPath("//android.widget.Button[@text='Authorize']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Grant access to Box']")));
				androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
				System.out.println("Added box account and logged into box successfully.");
			}
			else {
				System.out.println("Box account exists, Continuing...");
			}
		}
		catch(Exception e) {
			System.out.println("Box account exists, Continuing...");
		}

		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='All Files']") != null) {
				System.out.println("Logged into box account successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("Not Logged into box account.");
		}
		
		androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"PPTFile.ppt\"));");
		androidDriver.findElementByAndroidUIAutomator("text(\"PPTFile.ppt\")").click();
		if(androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']"))) != null) {
			androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']").click();
		}
		Thread.sleep(15000);
		if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"PPTFile.ppt\")") != null) {
			List<AndroidElement> forgrppptelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
			System.out.println("forgrppptelems size is :-" + forgrppptelems.size());
			if(forgrppptelems.size() > 0) {
				if ( forgrppptelems.get(forgrppptelems.size() - 1).findElementByXPath("//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/message_box_layout']") != null ) {
					WebElement el1 = forgrppptelems.get(forgrppptelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']");
					if(el1 != null) {
						String sendStatus = forgrppptelems.get(forgrppptelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
						System.out.println("sent status is :-" + sendStatus);
						if(sendStatus.equals("Read")) {						
							System.out.println("Android Sent PPT file successfully...");
							}
						}	
					}
				}
			}
		}
//		//Verify web received the ppt file.
		try {
			if(webDriver.findElementByXPath("//div[text()='PPTFile.ppt']") != null) {
				System.out.println("Web received the PPT file sent by Android.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web did not receive the PPT file sent by Android.");
		}
		
		//Web send a xlsx and verify it on android
		webDriver.findElementByXPath("//span[@class='icon ion-plus-circled']").click();
		Thread.sleep(1000);
		webDriver.findElementByXPath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'From Device')]").click();
		Thread.sleep(1000);
		Runtime.getRuntime().exec("D:\\Netsfere\\XlsFileUpload.exe");
		Thread.sleep(5000);
		try {
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
			if ( webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]") != null ) {
				webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]").click();
				// Web to assert the sent Image. 
				Thread.sleep(10000);
				List<WebElement> forwebgrpxlselems = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
				System.out.println("forwebgrpxlselems is:- " + forwebgrpxlselems.size());
				if(forwebgrpxlselems.size() > 0) {
					WebElement el1 = forwebgrpxlselems.get(forwebgrpxlselems.size() - 1).findElement(By.xpath("//div[text()='XLSFile.xls']"));
					if ( el1 != null ) {
						System.out.println("Web sent Excel File to Android Successfully.");
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web unable to send Excel File to Android.");
		}
		
		try {
			if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"XLSFile.xls\")") != null) {
				System.out.println("Android received Excel File successfully sent by web.");
			}
		}
		catch(Exception e) {
			System.out.println("Android did not receive Excel File sent by web.");
		}
		
		//Android Transfer ownership to web by leaving the conversation and verify that conversation does not exist on android and verify it exists on web side.
		
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/more_menu_item']").click();
		androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/leave_conversation_button']").click();
		if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Are you sure you want to leave this conversation?']") != null) {
			androidDriver.findElementByXPath("//android.widget.Button[@text='LEAVE']").click();
			Thread.sleep(2000);
		}
		
		androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Transfer Ownership']")));
		try {
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Transfer Ownership']") != null) {
				System.out.println("Transfering Ownership to web user...");

				androidDriver.findElementByAndroidUIAutomator("text(\"webuser\")").click();//test
				System.out.println("Printing 1...");
				androidDriver.findElementByAndroidUIAutomator("text(\"OK\")").click();
				Thread.sleep(3000);
				System.out.println("Printing 2...");

				androidDriver.findElementByAndroidUIAutomator("text(\"OK\")").click();
//				Thread.sleep(3000);
				System.out.println("Printing 3...");
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));
				Thread.sleep(1000);
				System.out.println("Printing 4...");
				System.out.println("Android User Transferred Ownership to web user successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android User Unable to transfer Ownership to web user.");
		}
		
		try {
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(AndroidGroupConversationTitle);
			List<AndroidElement> androidtrnsfrelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/conversation_list']//android.view.ViewGroup[@resource-id='com.infinite.netsfere:id/conversation_row_layout']//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']");
			System.out.println("androidtrnsfrelems is:- " + androidtrnsfrelems.size());
			if(androidtrnsfrelems.size() == 0) {
				System.out.println("Group conversation created by android does not exists on android side since android transfered ownership to web user.");
				Thread.sleep(1000);
			}
		}
		catch(Exception e) {
			System.out.println("Android Unable to transfer ownership to web user since group conversation created by android exists on android side.");
		}
		
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		webDriver.findElementByXPath("//div[contains(text(),'Cancel')]").click();
		webDriver.findElementByXPath("//span[@class='icon ion-ios-chatbubble']").click();
		System.out.println("Android Completed Group Conversation case.");
		
		
		
		//Create group conversation from web with android and ios users.
		System.out.println("Web User Starting Group Conversation With Android and Ios User.");
		String WebGroupConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); 
		try {
			webDriver.findElementByXPath("//div[@class='scrollbox']/div/div/button[2]").click();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'create')]")));
			webDriver.findElementByXPath("//div/input[contains(@placeholder,'Title (optional)')]").click();
			webDriver.findElementByXPath("//div/input[contains(@class,'namegenTitleReplace')]").sendKeys(WebGroupConversationTitle);
			Thread.sleep(3000);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(user2);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(Keys.RETURN);
			Thread.sleep(2000);
			webDriver.findElementByXPath("//div[@class='mainDiv']//div[2]//div[@class='scrollbox']/div[@class='table-view']//div[@class='click-ripple']").click();
			Thread.sleep(2000);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(user3);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../span/../../input").sendKeys(Keys.RETURN);
			Thread.sleep(2000);
			webDriver.findElementByXPath("//div[@class='mainDiv']//div[2]//div[@class='scrollbox']/div[@class='table-view']//div[@class='click-ripple']").click();
			Thread.sleep(2000);
			webDriver.findElementByXPath("//span[contains(text(),'create')]").click();
			Thread.sleep(3000);
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class='namegenTextLong']")));
			if(webDriver.findElementByXPath("//textarea[@class='namegenTextLong']") != null) {
				System.out.println("Web created Group conversation with Android and ios users successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("Web Unable to create group conversation with Android and ios users.");
		}
		//web send a message and verify the message and group conversation on android
		try {
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").click();
			webDriver.findElementByXPath("//textarea[@class='namegenTextLong']").sendKeys("Message from web");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			Thread.sleep(3000);
			if(webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']/following-sibling::div//div/span[@title='Sent']"))) != null) {
				System.out.println("Web Sent messsage in Group conversation successfully.");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR-Web was unable to Send messsage in One-To-one conversation with Android.");
		}
		
		String rcvdGrpMessagebyAndroid = "";
		try {
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys(WebGroupConversationTitle);
			List<AndroidElement> androidgrpconelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/conversation_list']//android.view.ViewGroup[@resource-id='com.infinite.netsfere:id/conversation_row_layout']//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']");
			System.out.println("androidgrpconelems is:- " + androidgrpconelems.size());
			if(androidgrpconelems.size() == 1) {
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']").click();
				System.out.println("Group Conversation created by web exists on android side.");
				Thread.sleep(1000);
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to find Group Conversation created by web on android side.");
		}		
		
		try {
			List<AndroidElement> androidgrpmsgelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
			if(androidgrpmsgelems.size() > 0) {
				rcvdGrpMessagebyAndroid = androidgrpmsgelems.get(androidgrpmsgelems.size() - 1).findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/message_content_textview']").getText();
			}
			System.out.println("Message Recieved from Web in group conversaton is = " + rcvdGrpMessagebyAndroid);
			if(rcvdGrpMessagebyAndroid.equals("Message from web")) {
				System.out.println("Android recieved correct message from Web");
			} else {
				System.out.println("ERROR -Android recieved wrong message in group conversation from Web");
			}
		}
		catch(Exception e) {
			System.out.println("ERROR -Android did not recieve message in group conversation from Web");
		}
		
		//android send a message and verify the message on web
		try {
			// Send message from Android
			if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/send_message_edittext']").sendKeys("Hello from Android");
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/send_button']").click();
				Thread.sleep(3000);
			}else {
				System.out.println("Android User is not in in Chat page.. ");
			}
			if (androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview' and @content-desc='Read']") != null ) {
			String sendStatus = androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
			System.out.println("Sent status is:-" + sendStatus);
			if(sendStatus.equals("Read")) {
				System.out.println("Android User Sent message in group conversation successfully.");
				}
			}	
		}
		catch ( NoSuchElementException e) {
			System.out.println("ERROR- Android Unable to Send message in Group Conversation...");
			System.out.println("exiting the programe...");
			System.exit(0);
		}
		
		String rcvdGrpMessagebyWeb = "";
		try {
			List<WebElement> grpmsgelemsweb = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
			if(grpmsgelemsweb.size() > 0) {
				rcvdGrpMessagebyWeb= grpmsgelemsweb.get(grpmsgelemsweb.size() - 1).getText();
			}
			System.out.println("Message Recieved from Android in group conversation :" + rcvdGrpMessagebyWeb);
			if(rcvdGrpMessagebyWeb.contains("Hello from Android")) {
				System.out.println("web user recieved correct message from android in group conversation");
			} else {
				System.out.println("ERROR -web user recieved wrong message from android in group conversation");
			}	
		}
		catch(Exception e) {
			System.out.println("ERROR- Web User did not receive the message in group conversation sent by android.");
		}
		
		//web send a csv and verify it on android
		
		webDriver.findElementByXPath("//span[@class='icon ion-plus-circled']").click();
		Thread.sleep(1000);
		webDriver.findElementByXPath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'From Device')]").click();
		Thread.sleep(1000);

		Runtime.getRuntime().exec("D:\\Netsfere\\CsvFileUpload.exe");
		Thread.sleep(3000);
		try {
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
			if ( webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]") != null ) {
				webDriver.findElementByXPath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]").click();
				// Web to assert the sent Image. 
				Thread.sleep(15000);
				List<WebElement> forwebgrpcsvelems = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']");
				if(forwebgrpcsvelems.size() > 0) {
					WebElement el1 = forwebgrpcsvelems.get(forwebgrpcsvelems.size() - 1).findElement(By.xpath("//div[text()='CSVFile.csv']"));
					if ( el1 != null ) {
						System.out.println("Web User sent CSV File to Android Successfully.");
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Web User unable to send CSV File to Android.");
		}
		
		try {
			if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"CSVFile.csv\")") != null) {
				System.out.println("Android User received CSV File successfully sent by web.");
			}
		}
		catch(Exception e) {
			System.out.println("Android User did not receive CSV File sent by web.");
		}
		
		//android send a txt file and verify it on web
		
		if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/insert_attachment_button']").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/attach_from_cloud']").click();
			
			try {
				if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']") !=null) {
					System.out.println("Selecting Convergence Box account.");
					androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']").click();
				}
				else {
					System.out.println("Box account already exists so continuing...");
				}
			}
			catch(Exception e) {
				System.out.println("ERROR- Unable to select Convergence Box account or maybe box account is not added yet.");
			}
			
			try {
				if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
					System.out.println("Allowing Access to Photos, Media.");
					androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
				}
				else {
					System.out.println("Allowing Access to Photos, Media popup does not exist so continuing...");
				}
			}
			catch(Exception e) {
				try {
				if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
					System.out.println("Allowing Access to Photos, Media.");
					androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();	
				}
				}catch(Exception e1)
				{
				System.out.println("No PopUp To Allow Access to Photos, Media.");
				}
			}
			Thread.sleep(7000);
			try {
				if(androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']") != null ) {
					System.out.println("Adding Box account.");
					androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']").sendKeys(box_username);
					androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='password']").sendKeys(box_pwd);
					androidDriver.findElementByXPath("//android.widget.Button[@text='Authorize']").click();
					androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Grant access to Box']")));
					androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").click();
					androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
					System.out.println("Added box account and logged into box successfully.");
				}
				else {
					System.out.println("Box account exists, Continuing...");
				}
			}
			catch(Exception e) {
				System.out.println("Box account exists, Continuing...");
			}
			try {
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
				if(androidDriver.findElementByXPath("//android.widget.TextView[@text='All Files']") != null) {
					System.out.println("Logged into box account successfully.");
				}
			}
			catch(Exception e) {
				System.out.println("Not Logged into box account.");
			}
			
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"TextFile.txt\"));");
			androidDriver.findElementByAndroidUIAutomator("text(\"TextFile.txt\")").click();
			if(androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']"))) != null) {
				androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/send_button']").click();
			}
			Thread.sleep(8000);
			if(androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"TextFile.txt\")") != null) {
				List<AndroidElement> fortxtelems = androidDriver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.infinite.netsfere:id/chat_list_view']//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/chat_box']");
				System.out.println("forpdfelems size is :-" + fortxtelems.size());
				if(fortxtelems.size() > 0) {
					if ( fortxtelems.get(fortxtelems.size() - 1).findElementByXPath("//android.widget.RelativeLayout[@resource-id='com.infinite.netsfere:id/message_box_layout']") != null ) {
						WebElement el1 = fortxtelems.get(fortxtelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']");
						if(el1 != null) {
							String sendStatus = fortxtelems.get(fortxtelems.size() - 1).findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/message_status_imageview']").getAttribute("contentDescription") ;
							System.out.println("sent status is :-" + sendStatus);
							if(sendStatus.equals("Read")) {						
								System.out.println("Android User Sent Text file Successfully...");
								}
							}	
						}
					}
				}
			}
//			Verify web received the Text file.
			try {
				if(webDriver.findElementByXPath("//div[text()='TextFile.txt']") != null) {
					System.out.println("Web User received the Text file sent by Android.");
				}
			}
			catch(Exception e) {
				System.out.println("ERROR- Web User did not receive the Text file sent by Android.");
			}
			//web make group conversation as favorite
			System.out.println("Web User Favorite group conversation.");
			try {
				webDriver.findElementByXPath("//div[text()='Favorite']").click();
				Thread.sleep(2000);
				webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Unfavorite']")));
				if(webDriver.findElementByXPath("//div[text()='Unfavorite']") != null) {
					System.out.println("Web User made group conversation as Favorite.");
				}
			}
			catch(Exception e) {
				System.out.println("Web User unable to make group conversation as Favorite.");
			}
			
			//web make group conversation as Unfavorite
			System.out.println("Web User Unfavorite group conversation.");
			try {
				webDriver.findElementByXPath("//div[text()='Unfavorite']").click();
				Thread.sleep(2000);
				webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Favorite']")));
				if(webDriver.findElementByXPath("//div[text()='Favorite']") != null) {
					System.out.println("Web User made group conversation as Unfavorite.");
				}
			}
			catch(Exception e) {
				System.out.println("Web User unable to make group conversation as Unfavorite.");
			}
			
			//web Mute the group conversation
			System.out.println("Web User Muting group conversation.");
			try {
				webDriver.findElementByXPath("//div[text()='Mute']").click();
				Thread.sleep(2000);
				webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Unmute']")));
				if(webDriver.findElementByXPath("//div[text()='Unmute']") != null) {
					System.out.println("Web User muted the group conversation.");
				}
			}
			catch(Exception e) {
				System.out.println("ERROR- Web User unable to mute group conversation.");
			}
			
			//web Unmute the group conversation
			System.out.println("Web User Unmuting group conversation.");
			try {
				webDriver.findElementByXPath("//div[text()='Unmute']").click();
				Thread.sleep(2000);
				webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Mute']")));
				if(webDriver.findElementByXPath("//div[text()='Mute']") != null) {
					System.out.println("Web User Unmuted the group conversation.");
				}
			}
			catch(Exception e) {
				System.out.println("ERROR- Web User unable to Unmute group conversation.");
			}
			
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			 Thread.sleep(1000);
			AndroidElement conele = androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text']");
			PointOption<ElementOption> P1 = ElementOption.element(conele);	
			taction.longPress(P1).waitAction(press_wait).release().perform();
			
			//android make group conversation favorite
			System.out.println("Android User favorite group conversation.");
			try {
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Favorite']").click();
			    Thread.sleep(1000);
			    if(androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/favorite_image']") != null) {
			    	System.out.println("Android User made the group conversation as Favorite.");
			    }
			}
			catch(Exception e) {
				System.out.println("ERROR- Android User unable to Favorite group conversation.");
			}
			
			//android make group conversation Unfavorite
			System.out.println("Android User Unfavorite group conversation.");
			taction.longPress(P1).waitAction(press_wait).release().perform();
			try {
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Unfavorite']").click();
			    Thread.sleep(3000);

			    	System.out.println("Android User made group conversation as Unfavorite.");

			}
			catch(Exception e) {
				System.out.println("ERROR- Android User unable to Unfavorite the group conversation.");
			}
			
			//android mute group conversation
			System.out.println("Android User muting group conversation.");
			taction.longPress(P1).waitAction(press_wait).release().perform();
			try {
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Mute']").click();
			    Thread.sleep(1000);
			    if(androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/mute_image']") != null) {
			    	System.out.println("Android User muted group conversation.");
			    }
			}
			catch(Exception e) {
				System.out.println("ERROR- Android User Unable to mute group conversation.");
			}
			
			//android Unmute group conversation
			System.out.println("Android Unmuting group conversation.");
			taction.longPress(P1).waitAction(press_wait).release().perform();
			try {
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Unmute']").click();
			    Thread.sleep(3000);
			    	System.out.println("Android User Unmuted group conversation.");

			}
			catch(Exception e) {
				System.out.println("ERROR- Android User unable to Unmute group conversation.");
			}
			
			//web Transfer ownership to android by leaving the conversation and verify that conversation does not exist on web and verify it exists on android side.
			System.out.println("Web User Transfering Ownership To Android User.");
			webDriver.findElementByXPath("//span[@class='fa fa-sign-out']").click();
			Thread.sleep(1000);
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Leave conversation?']")));
			if(webDriver.findElementByXPath("//div[text()='Leave conversation?']") != null) {
				webDriver.findElementByXPath("//span[text()='leave']").click();
			}
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Please select a new owner']")));
			
			try {
				if(webDriver.findElementByXPath("//div[text()='Please select a new owner']") != null) {
					System.out.println("Web User is in Transfer ownership page.");
				}
		//		webDriver.findElementByXPath("/html//div[@class='mainDiv']/div[1]/div[3]/div[1]/div[2]/div/div[6]/div[2]/div/div/div[@class='table-view']/div[2]/div/div[@class='click-ripple']/..//div[3]/div[text()='and1']").click();
				webDriver.findElementByXPath("//div[text()='Please select a new owner']/..//div//div//div[@displayname='androiduser']").click();
				webDriver.findElementByXPath("//span[text()='confirm']").click();
				Thread.sleep(3000);
				System.out.println("Web User Transfered ownership to Android User Successfully.");
			}
			catch(Exception e) {
				System.out.println("ERROR- Web is unable to Transfer ownership to android.");
			}
			
			try {
				webDriver.findElementByXPath("//button[@title='Start Conversation']/../div/input").sendKeys(WebGroupConversationTitle);
				Thread.sleep(3000);
				if ( webDriver.findElementByXPath("//div[@class='scrollbox']//div[@class='table-view']/div[2]").getText().contains("No Conversations Matching Search")) {
					System.out.println("Web user is unable to find the group since he left and transfered ownership to android user...");
					System.out.println("Web User Transfered ownership to Android User Successfully.");
				}
			}
			catch(Exception e) {
				System.out.println("ERROR- Web user is able to find the conversation since web was unable to transfer ownership.");
			}
			
			Thread.sleep(1000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(1000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			webDriver.findElementByXPath("//div[contains(text(),'Cancel')]").click();
			webDriver.findElementByXPath("//span[@class='icon ion-ios-chatbubble']").click();
			System.out.println("Web User Completed group conversation case.");
		
			
//			Change password in android
			System.out.println("Android User Changing Password.");
			String currentpwd="abcdefgh";
			String newpwd="abcd1234";
			String confirmpwd="abcd1234";
			androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Open navigation drawer']").click();
//			androidDriver.findElementByXPath("//android.widget.CheckedTextView[@resource-id='com.infinite.netsfere:id/design_menu_item_text' and text='Settings']").click();
			androidDriver.findElementByAndroidUIAutomator("text(\"Settings\")").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Change Password']")));
			
			try {
				if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Change Password']") != null) {
					System.out.println("Android User In settings Page to change password");
					androidDriver.findElementByXPath("//android.widget.TextView[@text='Change Password']").click();
					Thread.sleep(2000);
					androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/current_password_text']").sendKeys(currentpwd);
					androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/new_password_text']").sendKeys(newpwd);
					androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/re_password_text']").sendKeys(confirmpwd);
					androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/button_change_password']").click();
					androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Change Password']")));
					System.out.println("Android User Changed password successfully.");
				}
			}
			catch(Exception e) {
				System.out.println("ERROR- Android User Unable to Change password.");
			}
//			change back to old passowrd again in android.
			try {
				androidDriver.findElementByXPath("//android.widget.TextView[@text='Change Password']").click();
				Thread.sleep(1000);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/current_password_text']").sendKeys(newpwd);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/new_password_text']").sendKeys(currentpwd);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/re_password_text']").sendKeys(currentpwd);
				androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/button_change_password']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Change Password']")));
				System.out.println("Android User Changed back to old password successfully.");
			}
			catch(Exception e) {
				System.out.println("ERROR- Android Unable to change to old password.");
			}
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			System.out.println("Android User completed password change case.");
			
//Additional
			if(androidDriver.currentActivity().contains(".modules.user.SettingsFragmentActivity"))
			{
				androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']").click();
			}
			
			 try {
					androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Open navigation drawer']").click();
					androidDriver.findElementByXPath("//android.support.v7.widget.LinearLayoutCompat[@index='1']").click();
					androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));
					System.out.println("In conversation page");
				}catch(Exception e)
				{
					System.out.println("Cannot navigate to conversation page");
				}
			
//			Change password in web
			System.out.println("Web User Changing Password");
			webDriver.findElementByXPath("//div[@title='Settings']").click();
			Thread.sleep(2000);
			if(webDriver.findElementByXPath("//span[text()='Profile']")!= null){
				webDriver.findElementByXPath("//span[text()='Settings']").click();
			}
			else {
				System.out.println("Profile Page does not exist hence continuing...");
			}
			
			webDriver.findElementByXPath("//div[text()='Change Password']").click();
			Thread.sleep(2000);
			webDriver.findElementByXPath("//input[@placeholder='Enter current password']").sendKeys(currentpwd);
			webDriver.findElementByXPath("//input[@placeholder='Enter new Password']").sendKeys(newpwd);
			webDriver.findElementByXPath("//input[@placeholder='Confirm Password']").sendKeys(confirmpwd);
			Thread.sleep(1000);
			webDriver.findElementByXPath("//span[text()='Submit']").click();
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Password Changed Successfully']")));
			if(webDriver.findElementByXPath("//span[text()='Password Changed Successfully']") != null) {
				System.out.println("Web User Changed Password Successfully.");
			}
			else {
				System.out.println("ERROR- Web User Unable to change password.");
			}
			webDriver.findElementByXPath("//span[@class='icon ion-chevron-left']").click();
//			web user change back to old password again.
			Thread.sleep(1000);
			webDriver.findElementByXPath("//div[text()='Change Password']").click();
			Thread.sleep(2000);
			webDriver.findElementByXPath("//input[@placeholder='Enter current password']").sendKeys(newpwd);
			webDriver.findElementByXPath("//input[@placeholder='Enter new Password']").sendKeys(currentpwd);
			webDriver.findElementByXPath("//input[@placeholder='Confirm Password']").sendKeys(currentpwd);
			Thread.sleep(1000);
			webDriver.findElementByXPath("//span[text()='Submit']").click();
			webWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Password Changed Successfully']")));
			if(webDriver.findElementByXPath("//span[text()='Password Changed Successfully']") != null) {
				System.out.println("Web User Changed back to old password Successfully.");
			}
			else {
				System.out.println("ERROR- Web User Unable to change back the password.");
			}
			
			webDriver.findElementByXPath("//span[text()='close']").click();
			Thread.sleep(2000);
			
//			webDriver.findElementByXPath("//span[text()='close']").click();
//			Thread.sleep(2000);
//			webDriver.findElementByXPath("//span[@class='icon ion-ios-chatbubble']").click();
			System.out.println("Web User completed password change case.");	
			
	}	
	@Parameters ({"username1","username2","username3","user1","user2","user3"})
	@Test(priority = 1)
	public  void Channel() throws InterruptedException, IOException, NullPointerException
	{
		 
		 
		 
		 System.out.println("----------------Executing Channel feature----------------------");
		 
		 	

			WebDriverWait androidWait = new WebDriverWait(androidDriver, 120);
			if(androidDriver.isDeviceLocked()) {
				 androidDriver.unlockDevice();
				 androidDriver.pressKeyCode(AndroidKeyCode.HOME);
				 androidDriver.launchApp();
			 }
					
			
			TouchAction taction = new TouchAction(androidDriver);
			Duration press = Duration.ofMillis(1000);
			WaitOptions press_wait = WaitOptions.waitOptions(press);		
			
			
			try
			{
			if (androidDriver.currentActivity().equals(".modules.MainDrawerActivity"))
				{
					androidDriver.findElementByXPath("//android.widget.FrameLayout[@index='3']").click();
				}
			
			}catch(Exception e)
			{
				System.out.println("Not in coversation page");
				
			}
		//Android writes to a writable channel. Web read the message.
		
		String Channel_name = "Channel 1-Writable";
		String android_message = "Lets begin";
		String message=null;
		String Webmessage="Ok lets do it";
		String ReceivedWeb=null;
		WebDriverWait andwait = new WebDriverWait(androidDriver, 60);
		try {
		andwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/secure_image")));
		if (androidDriver.currentActivity().equals(".modules.MainDrawerActivity"))
		{
			System.out.println("Search for a writable channel in android");
			
			androidDriver.findElementById("com.infinite.netsfere:id/action_search").click();
			System.out.println("Enter channel name");
			Thread.sleep(2000);
			androidDriver.findElementById("com.infinite.netsfere:id/search_src_text").sendKeys(Channel_name);
			Thread.sleep(3000);

				androidDriver.findElementById("com.infinite.netsfere:id/conversation_name_text").click();
				Thread.sleep(3000);
			System.out.println("Writable channel found");
			System.out.println("Send message from android writable channel");
			androidDriver.findElementById("com.infinite.netsfere:id/send_message_edittext").sendKeys(android_message);
			androidDriver.findElementById("com.infinite.netsfere:id/send_button").click();
			androidDriver.findElementById("com.infinite.netsfere:id/quick_reply_button").click();
			Thread.sleep(2000);
			System.out.println("Send quick reply from android writable channel");
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Thank you']").click();
			androidDriver.findElementById("com.infinite.netsfere:id/send_button").click();
			System.out.println("Message sent from android writable channel");

		}
		}catch(Exception e)
		{
			System.out.println("Unable to find writable channel");
		}
		
		
		
		System.out.println("Login to Web");
		
		

		WebDriverWait webwait = new WebDriverWait(webDriver, 60);
		WebDriverWait webwait2 = new WebDriverWait(webDriver, 60);
		WebDriverWait webwait3 = new WebDriverWait(webDriver, 60);

		
		try {
			//changed2
			webDriver.get("https://web.netsferetest.com/#/conversations");
		//	webDriver.get("https://web.netsferebeta.com/#/conversations");
		WebDriverWait wait=new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Welcome to NetSfere')]")));
//		System.out.println("identified the element");
		
		System.out.println("Search for a writable channel in web");
		
		webDriver.findElement(By.xpath("//div[contains(@title,'Broadcast')]")).click();
			webDriver.findElement(By.xpath("//input[contains(@type,'text')]")).sendKeys(Channel_name);
			
			Thread.sleep(3000);
			
//			if (webDriver.findElement(By.xpath("//div[contains(text(),'No Channels Matching Search']")).getText().equals("No Channels Matching Search"))
//			{
//				System.out.println("No matching channel");
//			}
			Thread.sleep(2000);
			
			//webDriver.findElement(By.xpath("//div[contains(text(),'Channel')]")).click();
			
			List<WebElement> elt = webDriver.findElements(By.xpath("//div[@class='bubbleWrap']"));
			
			
			if(elt.size() > 0) {
			message = elt.get(elt.size() - 1).getText();
			System.out.println("Verify message received in web from android");
			System.out.println("Message received from android is" +message);
			if(message.equals("Thank you"))
			{
				System.out.println("Correct message received from android writable channel");
			}
			
		}
	
	
		}catch(Exception e) {
			System.out.println("Writable channel not found");
		}
		//Send message from web
	
		System.out.println("Sending message from a writable channel in web");
		try {
		webDriver.findElement(By.xpath("//textarea[@placeholder='Type Message Here...']")).sendKeys(Webmessage);
		webDriver.findElement(By.xpath("//span[@title='Send Message']")).click();
		webDriver.findElement(By.xpath("//span[contains(text(),'close')]")).click();
		
		//verify message in android
		Thread.sleep(4000);
		System.out.println("Verfiying message in android");
		Thread.sleep(3000);
//		androidDriver.wait(3000);
		List<AndroidElement> androidelt = androidDriver.findElementsById("com.infinite.netsfere:id/message_content_textview");
		int length = androidelt.size();
		
		if(length>0)
		{
			ReceivedWeb = androidelt.get(length-1).getText();
		}
		System.out.println("Message received from web in android is " +ReceivedWeb);
		if (ReceivedWeb.equals(Webmessage))
		{
			System.out.println("Correct message received from web writable channel in android");
		}
		}catch(Exception e)
		{
			System.out.println("Unable to send message from web in a writable channel");
		}
		//Android send  all types of attachments to  writable channel. Web receives them.
		
			System.out.println("Android sends image from a writable channel");
		
		try {
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
//			androidDriver.findElementById("com.infinite.netsfere:id/insert_photo_button").click();
			
//			if (androidDriver.findElementById("com.android.packageinstaller:id/permission_message").getText().equals("Allow NetSfere to access photos, media and files on your device?"))
//			{
//				androidDriver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
//			}
			
			
			Thread.sleep(2000);
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();//Click on cloud storage
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				try {
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
				}
				catch(Exception e)
				{
					System.out.println("Box not logged in..");
				}
				try {
					
					
					 if(androidDriver.findElementById("android:id/text1").getText().equals("Box"))
							
						{
							androidDriver.findElementByXPath("//android.widget.TextView[@text='Box']").click();
							Thread.sleep(2000);
							try {
								if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
									System.out.println("Allowing Access to Photos, Media.");
									androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
								}
							}
							catch(Exception e) {
								try {
								if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
									System.out.println("Allowing Access to Photos, Media.");
									androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();	
								}
								}
								catch(Exception e1)
								
								{
								System.out.println("ERROR- Could not Allow Access to Photos, Media.");
								}
							}

							Thread.sleep(4000);
						andwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.View[@text='Log in to grant access to Box']")));
						Thread.sleep(2000);
						androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']").sendKeys("convergenceinfinite@gmail.com");
						androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='password']").sendKeys("infinite1234");
						androidDriver.findElementByXPath("//android.widget.Button[@text='Authorize']").click();
						if(androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").getText().equals("Grant access to Box"))
						{
							androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").click();
						}
						}
				}catch(Exception e)
				{
					System.out.println("Cannot login to box");
				}
				
				}
				
		
//			else if(androidDriver.findElementById("android:id/text1").getText().equals("Box"))
//				
//			{
//				androidDriver.findElementByXPath("//android.widget.TextView[@text='Box']").click();
//				Thread.sleep(2000);
//				try {
//					if(androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to access photos, media, and files on your device?\")")!=null) {
//						System.out.println("Allowing Access to Photos, Media.");
//						androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();	
//					}
//				}
//				catch(Exception e) {
//					System.out.println("ERROR- Could not Allow Access to Photos, Media.");
//				}
//
//				Thread.sleep(4000);
//			andwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.View[@text='Log in to grant access to Box']")));
//			Thread.sleep(2000);
//			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='login']").sendKeys("convergenceinfinite@gmail.com");
//			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='password']").sendKeys("infinite1234");
//			androidDriver.findElementByXPath("//android.widget.Button[@text='Authorize']").click();
//			if(androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").getText().equals("Grant access to Box"))
//			{
//				androidDriver.findElementByXPath("//android.widget.Button[@text='Grant access to Box']").click();
//			}
//			}
			else
			{
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']").click();	
			}
			
			
			System.out.println("Sending a .csv file");
			WebDriverWait andwait1 = new WebDriverWait(androidDriver, 60);
			andwait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='CSVFile.csv']")));
		
			androidDriver.findElementByXPath("//android.widget.TextView[@text='CSVFile.csv']").click();
			Thread.sleep(3000);
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			
			androidDriver.findElementByXPath("//android.widget.Button[@text='Send']").click();
			Thread.sleep(3000);
		
					
			System.out.println("Sending DOCFile.doc file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
				androidDriver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"DOCFile.doc\")").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
				androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
				Thread.sleep(12000);
	
//				
			System.out.println("Sending Giff.gif file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Giff.gif\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);

		
			System.out.println("Sending JpegImage.jpg file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"JpegImage.jpg\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);
					
		
			System.out.println("Sending MPEGVideo.mp4 file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"MPEGVideo.mp4\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);				
			
//			
			System.out.println("Sending PDFFile.pdf file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"PDFFile.pdf\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);	
			
			
	//--		
			System.out.println("Sending PNG.png file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"PNG.png\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);	
			
		//--	
			
			System.out.println("Sending PPTFile.ppt file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"PPTFile.ppt\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);	
			

			//--
			System.out.println("Sending TextFile.txt file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"TextFile.txt\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);
//			
	//--		
//			
			System.out.println("Sending XLSFile.xls file");
			androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
			androidDriver.findElementByXPath("//android.widget.TextView[@text='Choose From Cloud Storage']").click();
			if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().equals("Pick a Cloud Storage"))
			{
				androidDriver.findElementByXPath("//android.widget.TextView[@text = 'Box (convergenceinfinite@gmail.com)']").click();
			}
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"XLSFile.xls\"));").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/send_button")));
			androidDriver.findElement(By.id("com.infinite.netsfere:id/send_button")).click();
			Thread.sleep(12000);
//			
		}
		catch(Exception e)
		{
			System.out.println("Unable to send images from a writable channel in android");
		}
			
			System.out.println("Validate attachments in web received from android");
			
				
		//Verify attachment in web
		Thread.sleep(2000);
		String WebReceive = null;

		
		List<WebElement> elt2 = webDriver.findElements(By.xpath("//div[@class='bubbleWrap']"));
		System.out.println(elt2.size());
		for(int j = 1; j<elt2.size();j++)
		
        {
			WebReceive = elt2.get(elt2.size() - j).getText();
			
			if (WebReceive.contains("XLS"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}
			else if(WebReceive.contains("Text"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}
			else if(WebReceive.contains("Tar"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}

			else if(WebReceive.contains("sample"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}

			else if(WebReceive.contains("PPT"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}

			else if(WebReceive.contains("PNG"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}

			else if(WebReceive.contains("PDF"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}

			else if(WebReceive.contains("MPEG"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}
			else if(WebReceive.contains("Jpeg"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}
			else if(WebReceive.contains("Giff"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}
			else if(WebReceive.contains("DOC"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}
			else if(WebReceive.contains("CSV"))
			{
				System.out.println("attachment received from android "+WebReceive);
			}
       }
		//Save attachment in BOX and device
		String uuid = UUID.randomUUID().toString();
		System.out.println("Save attachment to BOX and device from a writable channel in web");
		try {
		
		if(elt2.get(elt2.size() - 1).getText().contains("XLS"))
				{
					webDriver.findElement(By.xpath("//div[contains(text(),'XLSFile.xls')]")).click();
					webwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='fa fa-cloud-download']")));
					
					webDriver.findElement(By.xpath("//span[@class='fa fa-cloud-download']")).click();
					Thread.sleep(2000);
					webDriver.findElement(By.xpath("//div[contains(text(),'Device')]")).click();
					Thread.sleep(2000);
					webDriver.findElement(By.xpath("//span[@class='fa fa-cloud-download']")).click();
					Thread.sleep(2000);
					webDriver.findElement(By.xpath("//div[contains(text(),'Cloud Storage')]")).click();
					webDriver.findElement(By.xpath("//div[contains(text(),'convergenceinfinite@gmail.com')]")).click();
					
					Thread.sleep(3000);
					webDriver.findElement(By.xpath("//div[contains(text(),'Saved files')]")).click();
					Thread.sleep(3000);
					webDriver.findElement(By.xpath("//input[@placeholder='XLSFile.xls']")).sendKeys(uuid);
					//input[@placeholder='XLSFile.xls']
					webDriver.findElement(By.xpath("//span[contains(text(),'Save')]")).click();
					webwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'close')]")));
					webDriver.findElement(By.xpath("//span[contains(text(),'close')]")).click();
					
					System.out.println("Attachment saved to BOX and device in web writable channel");
				}
		}
		catch(Exception e)
		{
			System.out.println("Save to BOX not successful in web writable channel");
		}
       
		
		//Web sends all types of attachment and android receives them
		
		
		System.out.println("Send attachments from a writable channel in web");
		
		ArrayList<String> arrlist = new ArrayList<String>();
		arrlist.add("D:\\Netsfere\\JpegFileUpload.exe");
		arrlist.add("D:\\Netsfere\\CsvFileUpload.exe");
		arrlist.add("D:\\Netsfere\\DocFileUpload.exe");		
		arrlist.add("D:\\Netsfere\\GifFileUpload.exe");
		arrlist.add("D:\\Netsfere\\PdfFileUpload.exe");
		arrlist.add("D:\\Netsfere\\XlsFileUpload.exe");
		try {
			
		
		 for (String i : arrlist) { 		      
	          
	          System.out.println("Sending"+i); 
	        
	           webDriver.findElement(By.xpath("//span[@class='icon ion-plus-circled']")).click();
	           Thread.sleep(2000);
	           
	           webDriver.findElement(By.xpath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'From Device')]")).click();
          Runtime.getRuntime().exec(i);
	        webDriver.findElement(By.xpath("//span[starts-with(text(),'send')]")).click();
	        Thread.sleep(20000);
	        
	        
	        
	        List<AndroidElement>  s1 = androidDriver.findElementsById("com.infinite.netsfere:id/file_name_textview");
	        if(s1.size()>0)
	        {
	        	String andReceive = s1.get(s1.size()-1).getText();
	        	
	        	System.out.println(andReceive+"received by android");
	        }
	     }
		}catch(Exception e)
		{
			System.out.println("Unable to send attachments from a web writable channel ");
		}
	        
		

		 ArrayList<String> arrlist2 = new ArrayList<String>();


		 arrlist2.add("D:\\Netsfere\\SqlFileUpload.exe");
		 arrlist2.add("D:\\Netsfere\\PptFileUpload.exe");
		 arrlist2.add("D:\\Netsfere\\PngFileUpload.exe");
		 try {
		 for (String j : arrlist2)
		 {
			 System.out.println("Sending"+j);
			 webDriver.findElement(By.xpath("//span[@class='icon ion-plus-circled']")).click();
	           Thread.sleep(2000);
	           
	           webDriver.findElement(By.xpath("//div[contains(text(),'Add Attachment')]/../following-sibling::div//div[contains(text(),'From Device')]")).click();
	           Thread.sleep(2000);
	           Runtime.getRuntime().exec(j);
	           webDriver.findElement(By.xpath("//span[starts-with(text(),'send')]")).click();
	           Thread.sleep(30000);
		 
		 List<AndroidElement> s2 = androidDriver.findElementsById("com.infinite.netsfere:id/file_name_textview");
		 if(s2.size()>0)
	        {
	        	String andReceive1 = s2.get(s2.size()-1).getText();
	        	
	        	System.out.println(andReceive1+"received by android");
	        }
	         
		 }
		 }
		 catch(Exception e)
		 {
			 System.out.println("Unable to send video files from a web writable channel");
		 }
		
		 //Save attachment in BOX in android
		 try {
		 System.out.println("Save attachment to BOX in android");
		 androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"PNG.png (42.94 KB)\"));");
		 androidDriver.findElementById("com.infinite.netsfere:id/download_imageview").click();
		 Thread.sleep(12000);
//		 androidDriver.findElementById("com.infinite.netsfere:id/message_content_text_icon").click();
		 
		 androidDriver.findElementByXPath("//android.widget.ImageView[@bounds='[201,1019][779,1544]']").click();
		 
//		 androidDriver.pressKeyCode(AndroidKeyCode.ENTER);
		 androidDriver.findElementById("com.infinite.netsfere:id/action_save_cloud").click();
		 
		 if(androidDriver.findElementByXPath("//android.widget.TextView[@text='Pick a Cloud Storage']").getText().contains("Pick a Cloud Storage"))
		 {
			 androidDriver.findElementByXPath("//android.widget.TextView[@text='Box (convergenceinfinite@gmail.com)']").click();
		 }
		 androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='All Files']")));
		 androidDriver.findElementByXPath("//android.widget.TextView[@text='Saved files']").click();
		 androidDriver.findElementByXPath("//android.widget.Button[@text='SELECT THIS FOLDER']").click();
		 Thread.sleep(5000);
		 
		 androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']").click();
		 
		 }
		 catch(Exception e)
		 {
			// System.out.println("Unable to save attachment to BOX in android");
		 }
		 
		 
		//Web sends all types of attachment from Box and android receives them
		 
		 System.out.println("Sending attachments from BOX in a web writable channel"); 
		 try {
		 JavascriptExecutor js = (JavascriptExecutor) webDriver;
			 
		 
		 webDriver.findElement(By.xpath("//span[@class='icon ion-plus-circled']")).click();
		 Thread.sleep(2000);
		 webDriver.findElement(By.xpath("//div[contains(text(),'From Cloud Storage')]")).click();
		 Thread.sleep(2000);
		 webDriver.findElement(By.xpath("//div[contains(text(),'Add Box Account')]")).click();
//		 webwait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='box_logo']")));
		 Thread.sleep(2000);
//		 webDriver.findElement(By.xpath("//input[@id='login']")).sendKeys("convergenceinfinite@gmail.com");
		 

		 for (String handle : webDriver.getWindowHandles()) {

			    webDriver.switchTo().window(handle);}
		 
		 
		 webDriver.findElement(By.xpath("//input[@type='email']")).sendKeys("convergenceinfinite@gmail.com");
		 webDriver.findElement(By.xpath("//input[@id='password']")).sendKeys("infinite1234");
		 webDriver.findElement(By.name("login_submit")).click();
		 Thread.sleep(2000);
		 webDriver.findElement(By.xpath("//button[@id='consent_accept_button']")).click();
		 

		 
		 for (String handle1 : webDriver.getWindowHandles())
		 {
			 webDriver.switchTo().window(handle1);
		 }
		 webDriver.findElement(By.xpath("//span[@class='icon ion-cloud']")).click();
		 Thread.sleep(3000);
		 webDriver.findElement(By.xpath("//div/span[contains(text(),'949 KB')]")).click();
		 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'Attach')]")).click();
		 webwait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
		 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")).click();
		 Thread.sleep(10000);
		 
		 String and1 = androidDriver.findElement(By.xpath("//android.widget.TextView[@text='AviFileUpload.wav (948.71 KB)']")).getText();
		 if(and1.contains("AviFileUpload.wav (948.71 KB)"))
		 {
			 System.out.println(and1+"received from web");
		 }
		 	webDriver.findElement(By.xpath("//span[@class='icon ion-plus-circled']")).click();
			 Thread.sleep(2000);
			 webDriver.findElement(By.xpath("//div[contains(text(),'From Cloud Storage')]")).click();
			 Thread.sleep(2000);
			 webDriver.findElement(By.xpath("//span[@class='icon ion-cloud']")).click();
			 
			 webDriver.findElement(By.xpath("//div/span[contains(text(),'32 KB')]")).click();
			 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'Attach')]")).click();
			 webwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
			 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")).click();
			 Thread.sleep(10000);
			 
			 
		if(androidDriver.findElementByXPath("//android.widget.TextView[@text='TextFile.txt (31.64 KB)']").getText().contains("TextFile.txt (31.64 KB)"))
		{
			System.out.println("TextFile.txt (31.64 KB) received from web");
		}
		
		 
		
	 	webDriver.findElement(By.xpath("//span[@class='icon ion-plus-circled']")).click();
		 Thread.sleep(2000);
		 webDriver.findElement(By.xpath("//div[contains(text(),'From Cloud Storage')]")).click();
		 Thread.sleep(2000);
		 webDriver.findElement(By.xpath("//span[@class='icon ion-cloud']")).click();
		 
		 WebElement element = webDriver.findElement(By.xpath("//div/span[contains(text(),'11 KB')]"));
		 js.executeScript("arguments[0].scrollIntoView();", element);
		 element.click();
		 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'Attach')]")).click();
		 webwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
		 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")).click();
		 Thread.sleep(10000);
		 
	if(androidDriver.findElementByXPath("//android.widget.TextView[@text='JpegImage.jpg (10.71 KB)']").getText().contains("JpegImage.jpg (10.71 KB)"))
	{
		System.out.println("JpegImage.jpg (10.71 KB) received from web");
	}
	
	
  	webDriver.findElement(By.xpath("//span[@class='icon ion-plus-circled']")).click();
	 Thread.sleep(2000);
	 webDriver.findElement(By.xpath("//div[contains(text(),'From Cloud Storage')]")).click();
	 Thread.sleep(2000);
	 webDriver.findElement(By.xpath("//span[@class='icon ion-cloud']")).click();
	 
	 WebElement element1 = webDriver.findElement(By.xpath("//div/span[contains(text(),'125 KB')]"));
	 js.executeScript("arguments[0].scrollIntoView();", element1);
	 element1.click();
	 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'Attach')]")).click();
	 webwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")));
	 webDriver.findElement(By.xpath("//div[@class='click-ripple']/following-sibling::span[contains(text(),'send')]")).click();
	 Thread.sleep(10000);
	 
if(androidDriver.findElementByXPath("//android.widget.TextView[@text='PNG.png (125.31 KB)']").getText().contains("PNG.png (125.31 KB)"))
{
	System.out.println("PNG.png (125.31 KB) received from web");
}
		 }catch(Exception e)
		 {
			 System.out.println("Unable to send attachment from box in a web writable channel");
		 }
		 //Share location from android and web
		 //share location from android
	System.out.println("Share location from android in a writable channel");
	try
	{
		androidDriver.findElementById("com.infinite.netsfere:id/insert_attachment_button").click();
		androidDriver.findElementByXPath("//android.widget.TextView[@text='Share Location']").click();
		try {
		if(androidDriver.findElementByXPath("//android.widget.Button[@text='ALLOW']").getText().contains("ALLOW"))
		{
			androidDriver.findElementByXPath("//android.widget.Button[@text='ALLOW']").click();
		}
		}
		catch(Exception e)
		{
			try {
			if(androidDriver.findElementByXPath("//android.widget.Button[@text='Allow']").getText().contains("Allow"))
			{
				androidDriver.findElementByXPath("//android.widget.Button[@text='Allow']").click();
			}
			}
			catch(Exception e1)
			{
			
			System.out.println("Location allow permission doesnt exist");
			}
		}
//		Thread.sleep(2000);
		androidDriver.findElementById("com.infinite.netsfere:id/action_done").click();
		Thread.sleep(2000);
	}
	
	catch(Exception e)
	{
		System.out.println("Unable to send location from android writable channel");
	}
	
	
	//Share location from Web
	System.out.println("Share location from Web writable channel");
	
	try {
		
		webDriver.findElement(By.xpath("//span[@class='icon ion-plus-circled']")).click();
		Thread.sleep(2000);
		webDriver.findElement(By.xpath("//div/div[contains(text(),'Share Location')]")).click();
		Thread.sleep(3000);
	}
	catch(Exception e )
	{
		System.out.println("Unable to send location from a web writable channel");
	}
	
	//To validate number of writable users 
		 try {
		 System.out.println("Android validates the number of writable users in a channel");
		 
		 String andnum = androidDriver.findElementByXPath("//android.widget.TextView[@index='2']").getText();
		 System.out.println(andnum);
		 
		 System.out.println("Web validates the number of writable users in a channel ");
		 String Webnum = webDriver.findElement(By.xpath("//span/span[contains(text(),'6')]")).getText();
		 String Webnum1 = webDriver.findElement(By.xpath("//span/span[contains(text(),'Writable users in this channel')]")).getText();
		 System.out.println( Webnum+""+Webnum1 );
		 
		 
		 }
		 catch(Exception e)
		 {
			 System.out.println("Unable to find number of writable users in a channel");
		 }
		 
		  
		 //Android can’t write to a readable channel.
		 try {
			 
		 System.out.println("Validating Read only access of a channel in android");
		 String Channel_name1 = "Channel 2-Readable";
		 androidDriver.findElement(By.xpath("//android.widget.ImageButton[@index='0']")).click();
		 androidDriver.findElementById("com.infinite.netsfere:id/action_search").click();
		 androidDriver.findElementById("com.infinite.netsfere:id/search_src_text").sendKeys(Channel_name1);
		 androidDriver.findElementByXPath("//android.widget.TextView[@index='2']").click();
		 String s1 = androidDriver.findElementById("com.infinite.netsfere:id/read_only_text").getText();
		 System.out.println(s1);
		 System.out.println("Android cannot write to a readable channel");
		 androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']").click();
		 Thread.sleep(2000);
		 androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Collapse']").click();
		 Thread.sleep(2000);
		 
		 try {
				androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Open navigation drawer']").click();
				androidDriver.findElementByXPath("//android.support.v7.widget.LinearLayoutCompat[@index='1']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));
				System.out.println("In conversation page");
			}catch(Exception e)
			{
				System.out.println("Cannot navigate to conversation page");
			}
//		 androidDriver.findElementByXPath("//android.widget.FrameLayout[@index='0']").click();
		 androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));
//		 
		 // Web cant write to a readable channel
		 System.out.println("Validating Read only access of a channel in web");
		 webDriver.findElement(By.xpath("//div[contains(text(),'Cancel')]")).click();
		 webDriver.findElement(By.xpath("//input[@type='text']")).sendKeys(Channel_name1);
		 webDriver.findElement(By.xpath("//div[contains(text(),'Channel 2-Readable')]")).click();
		  if( webDriver.findElement(By.xpath("//textarea[@class='namegenTextLong']")).isEnabled())
		  {
			  System.out.println("Web can write into channel");
			  
		  }
		  else
		  {
			  System.out.println("Web cannot write to a readable channel");
		  }
		  
		  webDriver.findElement(By.xpath("//div[contains(text(),'Conversations')]")).click();
		  webwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Welcome to NetSfere')]")));
		 }
		 catch(Exception e)
		 {
			 System.out.println("Android cannot find a readable channel");
		 }
		
		
	}
	
	
			
	
	@Parameters ({"username1","username2","username3","user1","user2","user3"})
	@Test(priority=2)
	public void TC11(String username1,String username2,String username3,String user1,String user2,String user3) throws InterruptedException, IOException,NullPointerException
	{
//		String udate = new SimpleDateFormat("dd-MM-yy-HHmmss").format(new java.util.Date());
		String conversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//		System.setOut(new PrintStream(new FileOutputStream("Monitoring-" + udate +".txt")));
		
//		System.out.println("Start TIme : " + ( new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss").format(new java.util.Date())));
//		startAppiumServerNetsfere();
//		AndroidDriver<AndroidElement> androidDriver=initializeAndroidDriverNetsfere();
		
		System.out.println("----------------------------Executing HD Voice calls feature------------------------");
		
		try
		{
		if (androidDriver.currentActivity().equals(".modules.MainDrawerActivity"))
			{
				//androidDriver.findElementByXPath("//android.widget.FrameLayout[@index='3']").click();
			System.out.println("Android user in conversation page");
			
			}
		
		
		
		}catch(Exception e)
		{
			System.out.println("Not in coversation page");
			
		}
		WebDriverWait androidWait = new WebDriverWait(androidDriver, 400);
		if(androidDriver.isDeviceLocked()) {
			 androidDriver.unlockDevice();
			 androidDriver.pressKeyCode(AndroidKeyCode.HOME);
			 androidDriver.launchApp();
		 }
				
		TouchAction taction = new TouchAction(androidDriver);
		Duration press = Duration.ofMillis(1000);
		WaitOptions press_wait = WaitOptions.waitOptions(press);
		
	
		
		
//		System.out.println("Web user in conversation page");
//		ChromeDriver webDriver = chromeDriverInitialize();
		WebDriverWait webWait = new WebDriverWait(webDriver, 60);
		WebDriverWait webWait2 = new WebDriverWait(webDriver, 30);
		WebDriverWait webWait3 = new WebDriverWait(webDriver, 120);		
		webDriver.get("https://web.netsferetest.com/#/conversations");
		webDriver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
		try {
//			webDriver.findElement(By.xpath("//input[@type='email']")).sendKeys(username3);
//			webDriver.findElement(By.xpath("//input[@type='password']")).sendKeys("abcdefgh");
//			webDriver.findElement(By.xpath("//button[@type='submit']")).click();
			webWait3.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Welcome to NetSfere')]")));
			System.out.println("Web user in conversation page");
		} catch ( NoSuchElementException e) {
	        
			System.out.println( "ERROR - netsfere Login page Not Loaded. Pls Check Internet Connection...");
	        
			System.out.println("exiting the programe...");
	        System.exit(0);
		}
				
		/*
		 * Web make a call to android.. 
		 */
		
		
		try {
		Thread.sleep(2000);
		System.out.println("Click on contacts page");
		webDriver.navigate().to("https://web.netsferetest.com/#/contacts");
		
		Thread.sleep(4000);
		System.out.println("Web Searching  in Contacts page for Android User");
		webDriver.findElementByXPath("//span[@class='material-icons' and contains(text(),'search')]/../../..//input").sendKeys(user2);
		webDriver.findElementByXPath("//div[@class='table-view']//div[@displayname='"+user2+"']").click();
		System.out.println("Clicking on call button");
		webDriver.findElementByXPath("//span[@class='icon ion-android-call' and @title='Call']").click();
		}
		catch(Exception e)
		{
			System.out.println("Contact searche failed");
		}
		
		try {
			webWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Ringing...')]")));
		
			if ( webDriver.findElementByXPath("//span[contains(text(),'Ringing...')]") != null ) {
				
				System.out.println(" HD call is ringing..");
			}
		} catch ( NoSuchElementException e) {
			
			System.out.println("Web Can not establish the HD  call");
		}
		

		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_answer']")));
			System.out.println("Android Receive Call from Web");
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_answer']").click();
		} catch ( NoSuchElementException e) {
			
			System.out.println( "ERROR - Android did not See any incoming call");
		}
		
		
		try {
			if ( androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to record audio?\")") != null ) {
				webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();
			}			
		}  catch ( NoSuchElementException e) {
			try {
			if ( androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to record audio?\")") != null ) {
				webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();
			}	
			}catch(Exception e1)
			{
			
			System.out.println("Access to make HD Calls is already available to Netsfere.. ");
			}
		}
		
		try {
			if ( androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to make and manage phone calls?\")") != null ) 
			{
				webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				androidDriver.findElementByAndroidUIAutomator("text(\"ALLOW\")").click();
			}			
		}  catch ( NoSuchElementException e) {
			
			try {
			if ( androidDriver.findElementByAndroidUIAutomator("text(\"Allow NetSfere to make and manage phone calls?\")") != null ) 
			{
				webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				androidDriver.findElementByAndroidUIAutomator("text(\"Allow\")").click();
			}
			}
			catch(Exception e1)
			{
			System.out.println("MicroPhone Access Already available to Netsfere.. ");
			}
		}
		
		if ( androidDriver.findElementByAndroidUIAutomator("text(\"In Call\")") != null ) {
			
			System.out.println("Android Joined to HD Call");
			Thread.sleep(10000);
		
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_hangup']").click();
			
			System.out.println("Android terminated the call");
			}
			Thread.sleep(3000);
			
		/*
		 * Web Client call to Android Client from 1-1 conversation
		 */
			webDriver.navigate().to("https://web.netsferetest.com/#/conversations");
			Thread.sleep(5000);
			webDriver.findElement(By.xpath("//div[@class='scrollbox']/div/div/button[2]")).click();
			webDriver.findElement(By.xpath("//div[@class='mainDiv']//div[@class='scrollbox']/div[1]/div[1]/div[2]//input[@type='text']")).sendKeys(user2);
			
			Actions act = new Actions(webDriver);
			Thread.sleep(2000);
			act.sendKeys(Keys.SPACE).perform();
			Thread.sleep(2000);
			act.sendKeys(Keys.BACK_SPACE).perform();
		
			//webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mainDiv']/div[1]/div[3]/div[1]/div[2]/div/div[6]/div[1]/div[@class='scrollbox']/div[@class='table-view']/div[3]/div/div[3]/div[.='u2']")));
			
			Thread.sleep(8000);
			webDriver.findElement(By.xpath("//div[@class='mainDiv']/div[1]/div[3]/div[1]/div[2]/div/div[6]/div[1]/div[@class='scrollbox']/div[@class='table-view']/div[4]/div/div[@class='click-ripple']")).click();
			Thread.sleep(2000);
			webDriver.findElement(By.xpath("//span[text()='create']")).click();
			Thread.sleep(4000);
			webDriver.findElement(By.xpath("//span[@class='icon ion-gear-a']")).click();
			webDriver.findElement(By.xpath("/html//div[@class='mainDiv']//div[2]/input[@type='text']")).sendKeys(conversationTitle);
			webDriver.findElement(By.xpath("//span[text()='save']")).click();
			//webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Thread.sleep(10000);
			System.out.println("Conversation Created with Android client");
			


			webDriver.findElement(By.xpath("//div[@class='mainDiv']//button[@title='Make Call']")).click();
			System.out.println("Web calling to Android Client from 1-1 conversation");
		try {
			webWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Ringing...')]")));
		
			if ( webDriver.findElementByXPath("//span[contains(text(),'Ringing...')]") != null ) {
				
				System.out.println(" HD call is ringing..");
			}
		} catch ( NoSuchElementException e) {
			
			System.out.println("Web Can not establish the HD  call");
		}
		

		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_answer']")));
			
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_answer']").click();
		} catch ( NoSuchElementException e) {
			
			System.out.println( "ERROR - Android did not See any incoming call");
		}
		//Need to end call from web
		
		/*
		 * Web Calling Android Client from call log page
		 */
		try{
			Thread.sleep(2000);
			webDriver.findElement(By.xpath("//button[@title='End call']")).click();
			System.out.println("Web Navigated to Calls page");
			webDriver.navigate().to("https://web.netsferetest.com/#/calls");
			Thread.sleep(5000);
			webDriver.findElement(By.xpath("//div[@class='table-view']/div[2]/div[3]/div/div[2]/div/span[2]")).click();
		}
		catch(NoSuchElementException e){
			System.out.println("ERROR - Unable to call from log page");
		}
		try {
			webWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Ringing...')]")));
		
			if ( webDriver.findElementByXPath("//span[contains(text(),'Ringing...')]") != null ) {
				
				System.out.println(" HD call is ringing..");
			}
		} catch ( NoSuchElementException e) {
			
			System.out.println("Web Can not establish the HD  call");
		}
		

		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_answer']")));
			
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_answer']").click();
		} catch ( NoSuchElementException e) {
			
			System.out.println( "ERROR - Android did not See any incoming call");
		}
			if ( androidDriver.findElementByAndroidUIAutomator("text(\"In Call\")") != null ) {
			
			System.out.println("Android Joined to HD Call");
			Thread.sleep(10000);
		
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_hangup']").click();
			
			System.out.println("Android terminated the call");
			}
		

		
		/*
		 * Android is making HD call to Web from contact page.
		 */
		
		  
		
			androidDriver.findElementByXPath("//android.widget.FrameLayout[@resource-id='com.infinite.netsfere:id/bottom_navigation_small_container' and @index='2']").click();
		try {
			System.out.println("Android searching for web client in Contact page");
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
			Thread.sleep(1000);
			androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text']").sendKeys("webuser");
			Thread.sleep(9000);
			try {
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.LinearLayout[@resource-id='com.infinite.netsfere:id/contact_list_cell']")));
				androidDriver.findElementByXPath("//android.widget.LinearLayout[@resource-id='com.infinite.netsfere:id/contact_list_cell']").click();
				Thread.sleep(2000);
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/call_item']")));
				Thread.sleep(9000);
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/call_item']").click();	
				System.out.println(" Android make a HD call to Web from Contact page");
				if ( androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/call_label' and @text='Ringing…']") != null ) {
					
					System.out.println(" Android is getting Ringing tone");
					
				} else {
					
					System.out.println( "ERROR -  Android unable to make HD Call");
					
					System.out.println("exiting the Programe...");
					System.exit(0);
				}
								
				webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Incoming call...')]")));
				
				if ( webDriver.findElementByXPath("//span[contains(text(),'Incoming call...')]") != null ) {
					
					System.out.println(" Web Received the HD call from Android");
				}
				
				if(webDriver.findElementByXPath("//button[@title='Accept']") != null) {
					webDriver.findElementByXPath("//button[@title='Accept']").click();
					
					System.out.println("Web Accepted the call");
					Thread.sleep(5000);
					
					System.out.println("Web User is Terminating the HD Call...");
					webDriver.findElementByXPath("//button[@title='End call']").click();
					Thread.sleep(2000);
					
				}
			} catch ( NoSuchElementException e) {
				
				System.out.println( "ERROR - Error occurred while making call from Android to Web.. ");
			}
		}catch ( NoSuchElementException e) {
			
			System.out.println( "ERROR - Contact Page not Loaded in Android.. ");
		}	
		
		System.out.println("Äctivity just beofre the logout : " + androidDriver.currentActivity());

		
		Thread.sleep(5000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/search_close_btn']").click();
		Thread.sleep(1000);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
		System.out.println("Android Navigating to Conversation Page for 1-1 conversation with Web client");
		androidDriver.findElementByXPath("//android.widget.FrameLayout[@resource-id='com.infinite.netsfere:id/bottom_navigation_small_container' and @index='0']").click();
		
		/*
		 * Android is creating conv with web client
		 */
		
	try{

		String AndroidOneToOneConversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		try {
			if ( androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				try {
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
						}
				}
				catch(NoSuchElementException e) {
					System.out.println("Unable to find skip button in conversations page...");
				}
				androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/new_conversation_fab']").click();
				try {
					if(androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']")!= null) {
						System.out.println("Tips are present and pressing skip...");
						androidDriver.findElementByXPath("//android.widget.Button[@resource-id='com.infinite.netsfere:id/tip_skip_button' and @text='SKIP']").click();
						}
				}
				catch(NoSuchElementException e) {
					System.out.println("ERROR- Unable to find skip button in New conversation creation page...");
				}
				
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").click();
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/search_src_text']").sendKeys(user1);
				Thread.sleep(5000);
				System.out.println("Entering webuser name...");
				androidDriver.findElementByXPath("//android.widget.CheckBox[@resource-id='com.infinite.netsfere:id/select_checkBox']").click();
				Thread.sleep(5000);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edit_text']").click();
				System.out.println("Entering title");
				Thread.sleep(7000);
				androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/conversation_title_edit_text']").sendKeys(AndroidOneToOneConversationTitle);;
				androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_create']").click();
				androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']")));
				if(androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/quick_reply_button']") != null) {
					System.out.println("Android Created One-To-One Conversation with Web Successfully.");
				}	
			}
		}
		catch(Exception e) {
			System.out.println("ERROR- Android Unable to Create One-To-One Conversation...");	
		}
		
			Thread.sleep(1000);
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/menu_call_item' and @index='0']").click();
			if ( androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/call_label' and @text='Ringing…']") != null ) {
				
				System.out.println(" Android is getting Ringing tone");
			} else {
				
				System.out.println( "ERROR -  Android unable to make HD Call");
				
				System.out.println("exiting the Programe...");
				System.exit(0);
			}
							
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Incoming call...')]")));
			
			if ( webDriver.findElementByXPath("//span[contains(text(),'Incoming call...')]") != null ) {
				
				System.out.println(" Web Received the HD call from Android");
			}
			
			if(webDriver.findElementByXPath("//button[@title='Accept']") != null) {
				webDriver.findElementByXPath("//button[@title='Accept']").click();
				
				System.out.println("Web Accepted the call");
				Thread.sleep(5000);
				
				System.out.println("Web User is Terminating the HD Call...");
				webDriver.findElementByXPath("//button[@title='End call']").click();
				Thread.sleep(2000);
				
			}
		} 
	catch ( NoSuchElementException e) {
			
			System.out.println( "ERROR - Error occurred while making call from Android to Web.. ");
		}
	
		System.out.println("Android Navigating to Calls page");
		androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']").click();
		androidDriver.findElementByXPath("//android.widget.FrameLayout[@resource-id='com.infinite.netsfere:id/bottom_navigation_small_container' and @index='1']").click();
		androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/action_search']").click();
		androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='com.infinite.netsfere:id/search_src_text' and @text='Search Contacts']").sendKeys(user1);
		System.out.println("Android searched Web CLient");
		boolean flag = true;
		flag = androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/contact_name_textview' and @text='"+user1+"' and @index='0']").isDisplayed();
		if (flag){
			androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/contact_name_textview' and @text='"+user1+"' and @index='0']").click();
		}
		else{
			System.out.println("Network Slow unable to search Contact");
		}
	
		androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/call_button' and @index='2']").click();
		webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Incoming call...')]")));
		
		if ( webDriver.findElementByXPath("//span[contains(text(),'Incoming call...')]") != null ) {
			
			System.out.println(" Web Received the HD call from Android");
		}
		
		if(webDriver.findElementByXPath("//button[@title='Accept']") != null) {
			webDriver.findElementByXPath("//button[@title='Accept']").click();
			
			System.out.println("Web Accepted the call");
			Thread.sleep(5000);
			
			System.out.println("Web User is Terminating the HD Call...");
			webDriver.findElementByXPath("//button[@title='End call']").click();
			Thread.sleep(2000);
			
		}
		/*
		 * To test ENds
		 */
		
		/*
		 * Web Dial to Android and Before android joins disconnect call
		 */
		webDriver.navigate().to("https://web.netsferetest.com/#/calls");
		webDriver.navigate().refresh();
		Thread.sleep(10000);
		webDriver.findElement(By.xpath("//div[@class='table-view']/div[2]/div[3]/div/div[2]/div/span[2]")).click();
		Thread.sleep(3000);
		webDriver.findElement(By.xpath("//div[@class='mainDiv']/div[2]//button[@title='Cancel']")).click();
		System.out.println("Web disconnected the call");
		
		/*
		 * Web dial to android and android  won’t pick the call. Validate the call log on both.
		 */
		Thread.sleep(5000);
		webDriver.findElement(By.xpath("//div[@class='table-view']/div[2]/div[3]/div/div[2]/div/span[2]")).click();
		System.out.println("Waiting for the call details page list");
		Thread.sleep(15000);
		System.out.println("Web Called again");
		androidWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Call Details' and @index='1']")));
		
		
		System.out.println("Not in Chat log details page");
		
		String Date_of_Call = androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/call_time_textview' and @index='0']").getText();
		String Call_status = androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/call_info_textview' and @index='1']").getText();
		if ((Date_of_Call.contains("Today Now")) && (Call_status .contains("Missed Call")))
		{
			System.out.println("Android missed call");
		}
		
		/*
		 * Android is calling and web will not pick the call
		 */
		androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id='com.infinite.netsfere:id/call_button' and @index='2']").click();
		

		webWait3.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Accept']")));
		Thread.sleep(90000);
		try{			
				String miss_call_web_text = webDriver.findElement(By.xpath("/html//div[@class='mainDiv']//div[@class='table-view']/div[6]//div[1]/span[.='Missed Call']")).getText();
				if (miss_call_web_text.contains("Missed Call")){
					System.out.println("missed call is coming in the call log");				
					}		
			}
		catch(Exception e){
			System.out.println("Call logs is not working");
		}
		/*
		 * Web dial to android and android disconnect the call.
		 */
		Thread.sleep(10000);
		webDriver.findElementByXPath("//div[@class='mainDiv']//div[@class='scrollbox']//div[@class='table-view']/div[2]/div[3]/div/div[2]/div").click();
		
		try {
			webWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Ringing...')]")));
		
			if ( webDriver.findElementByXPath("//span[contains(text(),'Ringing...')]") != null ) {
				
				System.out.println(" HD call is ringing..");
			}
		} catch ( NoSuchElementException e) {
			
			System.out.println("Web Can not establish the HD  call");
		}
		

		try {
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_decline']")));
			
			androidDriver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.infinite.netsfere:id/call_decline']").click();
		} catch ( NoSuchElementException e) {
			
			System.out.println( "ERROR - Android did not See any incoming call");
		}
		
		String Mobile_CLient_Declined = androidDriver.findElementByXPath("//android.widget.TextView[@resource-id = 'com.infinite.netsfere:id/call_info_textview']").getText();
		Assert.assertEquals(Mobile_CLient_Declined, "Declined Call" ,"Declined call log is not registered in Call log");
		
		
		
		/*
		 * Android call to web and web disconnect the call
		 */
		try{
			androidDriver.findElementByXPath("//android.widget.ImageView[@resource-id= 'com.infinite.netsfere:id/call_button' and @index='2']").click();
		}
		catch(Exception e){
			System.out.println("Android unable to place call to Web client");
		}
		/*
		 * Switching instance to web now and decline the call
		 */
		webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Incoming call...')]")));
		
		if ( webDriver.findElementByXPath("//span[contains(text(),'Incoming call...')]") != null ) {
			
			System.out.println(" Web Received the HD call from Android");
		}
		
		if(webDriver.findElementByXPath("//button[@title='Decline']") != null) {
			webDriver.findElementByXPath("//button[@title='Decline']").click();
			
			System.out.println("Web Declined the call");
			Thread.sleep(5000);
			
		}
		String web_Declined_Call = webDriver.findElement(By.xpath("//div[@class='mainDiv']//div[@class='table-view']//span[.='Declined Call']")).getText();
		Assert.assertEquals(web_Declined_Call, "Declined Call" ,"Declined call log is not coming in web call log");
		
		androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']").click();
		androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Collapse']").click();
		
		try {
			androidDriver.findElementByXPath("//android.widget.ImageButton[@content-desc='Open navigation drawer']").click();
			androidDriver.findElementByXPath("//android.support.v7.widget.LinearLayoutCompat[@index='1']").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.infinite.netsfere:id/conversation_name_text' and @text='Welcome to NetSfere']")));
			System.out.println("In conversation page");
		}catch(Exception e)
		{
			System.out.println("Cannot navigate to conversation page");
		}
		

	}

//==================================================BroadcastLive Sanity=================================================================

		@SuppressWarnings({ "rawtypes", "unused", "deprecation" })
		@Parameters ({"username1","username2","username3","user1","user2","user3"})
		@Test(priority = 3)
		public  void LiveScenarios(String username1,String username2,String username3,String user1,String user2,String user3) throws InterruptedException, IOException {
			
	System.out.println("---------------------------------Executing Live scenarios-------------------------------------");
			
			String channel1="Channel 1-Writable";
//			String channel2="Jubu";
			
			
			WebDriverWait androidWait = new WebDriverWait(androidDriver, 120);
			WebDriverWait androidWait2 = new WebDriverWait(androidDriver, 230);
			if(androidDriver.isDeviceLocked()) {
				 androidDriver.unlockDevice();
				 androidDriver.pressKeyCode(AndroidKeyCode.HOME);
				 androidDriver.launchApp();
			 }
			
			
//			/*
//			 * Login -- Web
//			 */
			System.out.println("Login to Web ...");
			
			WebDriverWait webWait = new WebDriverWait(webDriver, 60);
			WebDriverWait webWait2 = new WebDriverWait(webDriver, 30);
			WebDriverWait webWait3 = new WebDriverWait(webDriver, 240);			
	        

//			}	
			WebElement we = webDriver.findElementByXPath("//div[contains(text(),'Welcome to NetSfere')]");

			
//			//Create a conversation with Web.
			if(androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				androidDriver.findElementById("com.infinite.netsfere:id/new_conversation_fab").click();
			}
			System.out.println("Android Device Create Conversation with Web.");
			String conversationTitle = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
			
			Thread.sleep(500);
			if(androidDriver.currentActivity().equals(".modules.conversation.CreateConversationActivity")) {
				try{
					if(androidDriver.findElementById("com.infinite.netsfere:id/tip_skip_button").getText().equals("SKIP")){
						androidDriver.findElementById("com.infinite.netsfere:id/tip_skip_button").click();
						System.out.println("Tips at select contact");
						androidDriver.findElementById("com.infinite.netsfere:id/conversation_title_edit_text").sendKeys(conversationTitle);
						Thread.sleep(500);
					}
				}
				catch(NoSuchElementException e){
					androidDriver.findElementById("com.infinite.netsfere:id/conversation_title_edit_text").sendKeys(conversationTitle);
				}
				
				
			}
				
			androidDriver.findElementById("android:id/search_src_text").sendKeys(user1);
			Thread.sleep(6000);
			androidDriver.findElementByXPath("//android.widget.CheckBox[@index='0']").click();
			androidDriver.findElementById("com.infinite.netsfere:id/action_create").click();
			Thread.sleep(2000);
			try{
			
				if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementById("com.infinite.netsfere:id/send_message_edittext").sendKeys("Hello");
				androidDriver.findElementById("com.infinite.netsfere:id/send_button").click();
			}
				else {
				System.out.println("Android-Unable to Send Message and not in chat page");
				}
			}
			catch ( NoSuchElementException e) {
		        
				System.out.println( "ERROR - Android Unable to Create Conversation...");
		        
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 
			webDriver.navigate().refresh();
			
			//Verify in Web that message was sent by android
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button>svg")));
			String rcvdMessageWeb =null;
			String rcvdMessageWeb1 =null;
			String rcvdMessageAndroid =null;
			Thread.sleep(5000);
			webDriver.findElementByXPath("//div/input[@type='text']").sendKeys(conversationTitle);
			Thread.sleep(2000);
			webDriver.findElementByXPath("//div/img[contains(@style,'width: 42px')]").click();
			List<WebElement> elems = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']/div");
			if(elems.size() == 0) {
				webDriver.navigate().refresh();
				Thread.sleep(2000);
			}
			
			if(elems.size() > 0) {
				rcvdMessageWeb= elems.get(elems.size() - 1).getText();
			}
			System.out.println("Message received from Android is:" + rcvdMessageWeb);
			if(rcvdMessageWeb.equals("Hello")) {
				System.out.println("Web received correct message from Android");
			} else {
				
				System.out.println( "ERROR - Web received wrong message from Android");
			}	
			Thread.sleep(2000);
			//Send Message from web.
			if(webDriver.findElementByXPath("//div[text()='Cancel']").getText().equals("Cancel")){
			webDriver.findElementByXPath("//div[text()='Cancel']").click();	
			}
			Thread.sleep(2000);
			webDriver.findElementByXPath("//div/input[@type='text']").sendKeys(conversationTitle);
			Thread.sleep(2000);
			webDriver.findElementByXPath("//div/img[contains(@style,'width: 42px')]").click();	
			webDriver.findElementByXPath("//div/textarea[@autofocus='true']").sendKeys("Hello2");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			Thread.sleep(4000);
			List<AndroidElement>elems2 = androidDriver.findElementsById("com.infinite.netsfere:id/message_content_textview");
			if(elems2.size() > 0) {
				rcvdMessageAndroid = elems2.get(elems2.size() - 1).getText();
			}
			
			System.out.println("Message received from Web is:" + rcvdMessageAndroid);
			
			if(rcvdMessageAndroid.equals("Hello2")) {
				System.out.println("Android received correct message from Web");
			}
			else {
				System.out.println( "ERROR - Android received wrong message from Web");
			}	
			
			//Start a Live session in Android
			
			//Broadcast Live
			
			String LiveText = null;
			String Permission1=null;
			
			androidDriver.findElementById("com.infinite.netsfere:id/more_menu_netstream_item").click();
			
			androidDriver.findElementById("com.infinite.netsfere:id/broadcast_netstream_text").click();
			try {
//			if(androidDriver.findElementById("com.android.packageinstaller:id/permission_message").getText().equals("Allow NetSfere to record audio?"))
				if(androidDriver.findElementById("com.android.packageinstaller:id/permission_message").getText().contains("Allow NetSfere to"))
			{
				androidDriver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
				try {
					if(androidDriver.findElementById("com.android.packageinstaller:id/permission_message").getText().contains("Allow NetSfere to"))
					{
						androidDriver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
						//
					}
				}catch(Exception e)
				{
					System.out.println("Allow permission pop up doesnt exist");
				}
				
			}	
			}catch(Exception e)
			{
				System.out.println("Allow permission pop up doesnt exist");
			}
			Thread.sleep(9000);
			LiveText = androidDriver.findElementById("com.infinite.netsfere:id/stop_button").getText();
			
			if (LiveText.equalsIgnoreCase("Stop"))
			{
				try {
					androidDriver.findElementById("com.infinite.netsfere:id/cameraSwitch_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/cameraSwitch_button").click();
					Thread.sleep(500);
					androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
					Thread.sleep(500);
					androidDriver.findElementById("com.infinite.netsfere:id/mute_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/mute_button").click();
					Thread.sleep(500);
				
				}
				
				catch ( NoSuchElementException e) {
			        
					System.out.println( "ERROR - Android Unable to Start a Live session...");
			        
					System.out.println("exiting the programe...");
			        System.exit(0);
				} 
			}
			
//			webDriver.navigate().refresh();
//			Thread.sleep(8000);
//			
//			webDriver.findElementByXPath("//div/input[@type='text']").sendKeys(conversationTitle);
//			Thread.sleep(2000);
//			webDriver.findElementByXPath("//div/img[contains(@style,'width: 42px')]").click();	
			
			//switch to web to check whether the broadcast is reflected there or not.
			try
				{
				if(webDriver.findElementByCssSelector("g>path[d^='M18']").getText().equals("LIVE")){
					System.out.println("Android has started a broadcast");
				}
			}
			
			catch ( NoSuchElementException e) {
		        
				System.out.println( "ERROR - Check Internet connection for Web");
		        
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 
//			
			//Web Join the broadcast.
			webDriver.findElementByXPath("//span[contains(@class,'videocam') and not (contains(@class,'outline'))]").click();
			Thread.sleep(12000);
			try
			{
				if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter"))
				{
					Thread.sleep(4000);
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					Thread.sleep(500);
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					Thread.sleep(500);
					webDriver.findElementByCssSelector("div>img[src*='leave']").click();
					Thread.sleep(500);
					System.out.println("Web user is able to view the broadcast");
				}
			}
			
			catch ( NoSuchElementException e) {
		        if(androidDriver.findElementById("android:id/message").getText().contains("interrupted"))
		        {
		        	System.out.println( "ERROR - Connection timed out for Android/LiveBroadcast affected by Network issues");
		        }
		        else
		        {
		        System.out.println("ERROR- Check Internet Connection for Web");	
		        }
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 
			Thread.sleep(2000);
			//android end the broadcast
			androidDriver.findElementById("com.infinite.netsfere:id/stop_button").click();
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			System.out.println("Android ended the broadcast with Web");
			
			
			//Web create a conversation with android.
			String conversationTitle2 = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
			webDriver.navigate().refresh();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button>svg")));
			
			webDriver.findElementByCssSelector("button>svg").click();
			Thread.sleep(4000);
			webDriver.findElementByXPath("//input[@class='namegenTitleReplace']").sendKeys(conversationTitle2);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../../input").sendKeys(user2);
			Thread.sleep(4000);
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@selectedindex='-1']/div[4]/div[@displayname]/div[2]/div[1]"))).getText().contains(user2);
			
			webDriver.findElementByXPath("//div[@selectedindex='-1']/div[4]/div[@displayname]").click();
			webDriver.findElementByXPath("//span[text()='create']").click();
			
			webWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Participant')]"))).getText().contains("Participant");
			try {
			if(webDriver.findElementByXPath("//span[contains(text(),'People in this')]/preceding-sibling::span").getText().equals("2"))
			webDriver.findElementByXPath("//div/input[@type='text']").sendKeys(conversationTitle2);
			Thread.sleep(2000);
			webDriver.findElementByXPath("//div/img[contains(@style,'width: 42px')]").click();	
			}
		
			catch (NoSuchElementException e){
			System.out.println("Make sure web has an active internet connection");
			System.exit(0);
			}
			
			//Send a message from Web to android
			Thread.sleep(5000);
			webDriver.findElementByXPath("//div/textarea[@autofocus='true']").sendKeys("Hello3");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			
			//Android verify the conversation and message.
			try {
			if(androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				androidDriver.findElementById("com.infinite.netsfere:id/action_search").click();
				Thread.sleep(2000);
				androidDriver.findElementById("com.infinite.netsfere:id/search_src_text").sendKeys(conversationTitle2);
				androidDriver.findElementById("com.infinite.netsfere:id/message_time_text").click();
			
			List<AndroidElement>elems3 = androidDriver.findElementsById("com.infinite.netsfere:id/message_content_textview");
			if(elems3.size() > 0) {
				rcvdMessageAndroid = elems3.get(elems3.size() - 1).getText();
				}
			if(rcvdMessageAndroid.equals("Hello3"))
				System.out.println("Android received correct message from Web");
			else
				System.out.println("Android received incorrect message from Web");
			}
		}
			
			catch (NoSuchElementException e){
				System.out.println("Check internet connection for Android");
				System.exit(0);
				}
			
			//Switch to Web to start the broadcast.
			
			webDriver.findElementByXPath("//span[contains(@class,'videocam-outline')]").click();
			webDriver.findElementByXPath("//span[text()='Broadcast Live']").click();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'started a NetSfere ')]")));
			System.out.println("Web started a broadcast with android");
			try{
			if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter")){
				webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
				webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
				Thread.sleep(500);
				webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
				webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
				Thread.sleep(500);	
			}
		}
			catch (NoSuchElementException e){
				System.out.println("Live could not start for Web");
				System.exit(0);
				}
			
			//Android check the broadcast.
			Thread.sleep(6000);
			androidDriver.findElementById("com.infinite.netsfere:id/more_menu_netstream_item").click();
			try{
			androidWait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/participants_button")));
			}
			catch (NoSuchElementException e){
				System.out.println("Live has not started for Android");
				System.exit(0);
			}
			
			androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
			androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
			Thread.sleep(500);
			androidDriver.findElementById("com.infinite.netsfere:id/speaker_button").click();
			androidDriver.findElementById("com.infinite.netsfere:id/speaker_button").click();
			Thread.sleep(500);
			androidDriver.findElementById("com.infinite.netsfere:id/stop_button").click();
			System.out.println("Android user left the broadcast");
			
			//Web User Stops the broadcast
			
			try{
				if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter")) {
					Thread.sleep(4000);
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					Thread.sleep(500);
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					Thread.sleep(500);
					webDriver.findElementByCssSelector("div>img[src*='BroadcastBtn']").click();
					System.out.println("Web ended the broadcast");
				}
			}
			
			catch (NoSuchElementException e){
				System.out.println("Live has been terminated for Web owing to Network issues");
				System.exit(0);
			}
			
			webDriver.navigate().refresh();
			try{
				if(we==null) {
					androidWait2.until(ExpectedConditions.visibilityOf(we));
				}
			}
			catch ( NoSuchElementException e) {
				System.out.println( "ERROR - Unable to refresh in Web");       
			} 
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			androidWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageView[@index='2']")));
			Thread.sleep(5000);
			//PATCH CODE.(Since Ravi's Fix is missing).
//			androidDriver.findElementByXPath("//android.widget.ImageView[@index='2']").click();
//			Thread.sleep(3000);
//			androidDriver.findElementByXPath("//android.widget.ImageView[@index='2']").click();
//			Thread.sleep(5000);
			//Android creates a group conversation and sends a message.
			
			if(androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				androidDriver.findElementById("com.infinite.netsfere:id/new_conversation_fab").click();
			}
			System.out.println("Android Device Create Group Conversation");
			String conversationTitle3 = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
			
			Thread.sleep(7000);
			if(androidDriver.currentActivity().equals(".modules.conversation.CreateConversationActivity")) {
				try{
					if(androidDriver.findElementById("com.infinite.netsfere:id/tip_skip_button").getText().equals("SKIP")){
						androidDriver.findElementById("com.infinite.netsfere:id/tip_skip_button").click();
						System.out.println("Tips at select contact");
						androidDriver.findElementById("com.infinite.netsfere:id/conversation_title_edit_text").sendKeys(conversationTitle3);
						Thread.sleep(500);
					}
				}
				catch(NoSuchElementException e){
					androidDriver.findElementById("com.infinite.netsfere:id/conversation_title_edit_text").sendKeys(conversationTitle3);
				}
				
				
			}
			
			androidDriver.findElementByXPath("//android.widget.EditText[@text='Search Name or Invite by Email']").sendKeys(user3);
			androidWait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/profile_imageview")));
			Thread.sleep(3000);
			androidDriver.findElementById("com.infinite.netsfere:id/profile_imageview").click();
			Thread.sleep(3000);
			
			androidDriver.findElementByXPath("//android.widget.EditText[@text='Search Name or Invite by Email']").sendKeys(user1);
			androidWait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/profile_imageview")));
			Thread.sleep(3000);
			androidDriver.findElementById("com.infinite.netsfere:id/profile_imageview").click();
			Thread.sleep(3000);
			
			androidDriver.findElementById("com.infinite.netsfere:id/action_create").click();
			Thread.sleep(2000);
			try{
			
				if(androidDriver.currentActivity().equals(".modules.conversation.chat.ChatFragmentActivity")) {
				androidDriver.findElementById("com.infinite.netsfere:id/send_message_edittext").sendKeys("Hello4");
				androidDriver.findElementById("com.infinite.netsfere:id/send_button").click();
			}
				else {
				System.out.println("Android-Unable to Send Message and not in chat page");
				}
			}
			catch ( NoSuchElementException e) {
		        
				System.out.println( "ERROR - Android Unable to Create Conversation...");
		        
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 
			System.out.println("Android created a group conversation");
			//Web verify the message for the group conversation.
			
			webDriver.findElementByXPath("//div/input[@type='text']").sendKeys(conversationTitle3);
			Thread.sleep(4000);
			webDriver.findElementByXPath("//span[text()='Now']").click();
			List<WebElement> elems4 = webDriver.findElementsByXPath("//div[@class='resize-sensor']/../following-sibling::div/div//div[@class='bubbleWrap']/div");
			if(elems4.size() == 0) {
				webDriver.navigate().refresh();
				Thread.sleep(2000);
			}
			
			if(elems4.size() > 0) {
				rcvdMessageWeb1= elems4.get(elems4.size() - 1).getText();
			}
			Thread.sleep(2000);
			System.out.println("Message received from Android is:" + rcvdMessageWeb1);
			if(rcvdMessageWeb1.equals("Hello4")) {
				System.out.println("Web received correct message from Android");
			} else {
				
				System.out.println( "ERROR - Web received wrong message from Android");
			}	
			Thread.sleep(2000);
			//Send Message from web.
//			if(webDriver.findElementByXPath("//div[text()='Cancel']").getText().equals("Cancel")){
//			webDriver.findElementByXPath("//div[text()='Cancel']").click();	
//			}
//			Thread.sleep(2000);
//			webDriver.findElementByXPath("//div/input[@type='text']").sendKeys(conversationTitle3);
//			Thread.sleep(2000);
//			webDriver.findElementByXPath("//span[contains(text(),'min')]").click();	
			webDriver.findElementByXPath("//div/textarea[@autofocus='true']").sendKeys("Hello5");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			Thread.sleep(4000);
			List<AndroidElement> elems41 = androidDriver.findElementsById("com.infinite.netsfere:id/message_content_textview");
			if(elems41.size() > 0) {
				rcvdMessageAndroid = elems41.get(elems41.size() - 1).getText();
			}
			
			System.out.println("Message received from Web is:" + rcvdMessageAndroid);
			
			if(rcvdMessageAndroid.equals("Hello5")) {
				System.out.println("Android received correct message from Web");
			}
			else {
				System.out.println( "ERROR - Android received wrong message from Web");
			}	
			
			androidDriver.findElementById("com.infinite.netsfere:id/more_menu_netstream_item").click();
			
			androidDriver.findElementById("com.infinite.netsfere:id/broadcast_netstream_text").click();
//			if(androidDriver.findElementById("com.android.packageinstaller:id/permission_message").getText().equals("Allow NetSfere to record audio?"))
//			{
//				androidDriver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
//				androidDriver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
//			}	
			Thread.sleep(9000);
			LiveText = androidDriver.findElementById("com.infinite.netsfere:id/stop_button").getText();
			
			if (LiveText.equalsIgnoreCase("Stop"))
			{
				try {
					androidDriver.findElementById("com.infinite.netsfere:id/cameraSwitch_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/cameraSwitch_button").click();
					Thread.sleep(500);
					androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
					Thread.sleep(500);
					androidDriver.findElementById("com.infinite.netsfere:id/mute_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/mute_button").click();
					Thread.sleep(500);
				
				}
				
				catch ( NoSuchElementException e) {
			        
					System.out.println( "ERROR - Android Unable to Start a Live session...");
			        
					System.out.println("exiting the programe...");
			        System.exit(0);
				} 
			}
			Thread.sleep(4000);
			//switch to web to check whether the broadcast is reflected there or not.
			try
				{
				if(webDriver.findElementByCssSelector("g>path[d^='M18']").getText().equals("LIVE")){
					System.out.println("Android started broadcast in group conversation");
				}
			}
			
			catch ( NoSuchElementException e) {
		        
				System.out.println( "ERROR - Check Internet connection for Web");
		        
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 
			
			//Web Join the broadcast.
			webDriver.findElementByXPath("//span[contains(@class,'videocam') and not (contains(@class,'outline'))]").click();
			Thread.sleep(12000);
			try
			{
				if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter"))
				{
					Thread.sleep(4000);
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					Thread.sleep(500);
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					Thread.sleep(2000);
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					Thread.sleep(500);
					webDriver.findElementByCssSelector("div>img[src*='leave']").click();
					Thread.sleep(500);
					System.out.println("Web user is able to view the broadcast");
				}
			}
			
			catch ( NoSuchElementException e) {
		        if(androidDriver.findElementById("android:id/message").getText().contains("interrupted"))
		        {
		        	System.out.println( "ERROR - Connection timed out for Android/LiveBroadcast affected by Network issues");
		        }
		        else
		        {
		        System.out.println("ERROR- Check Internet Connection for Web");	
		        }
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 
			Thread.sleep(2000);
			//android end the broadcast
			androidDriver.findElementById("com.infinite.netsfere:id/stop_button").click();
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			System.out.println("Android ended the broadcast for group conversation");
			
			
			//Web Create Group Conversation.
			String conversationTitle4 = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
			webDriver.navigate().refresh();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button>svg")));
			
			//Add 1st participant
			webDriver.findElementByCssSelector("button>svg").click();
			webDriver.findElementByXPath("//input[@class='namegenTitleReplace']").sendKeys(conversationTitle4);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../../input").sendKeys(user2);
			Thread.sleep(4000);
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@selectedindex='-1']/div[4]/div[@displayname]/div[2]/div[1]"))).getText().contains(user2);
			webDriver.findElementByXPath("//div[@selectedindex='-1']/div[4]/div[@displayname]").click();
			
			//Add 2nd participant
			Thread.sleep(4000);
			webDriver.findElementByXPath("//span[text()='Search Contacts']/../../input").sendKeys(user3);
			Thread.sleep(6000);
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@selectedindex='-1']/div[4]/div[@displayname]/div[2]/div[1]"))).getText().contains(user3);
			webDriver.findElementByXPath("//div[@selectedindex='-1']/div[4]/div[@displayname]").click();
			
			//Click on create to Create conversation.
			webDriver.findElementByXPath("//span[text()='create']").click();
			
			webWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Participant')]"))).getText().contains("Participant");
			try {
			if(webDriver.findElementByXPath("//span[contains(text(),'People in this')]/preceding-sibling::span").getText().equals("3"))
			System.out.println("Web created a group conversation");
			}
		
			catch (NoSuchElementException e){
			System.out.println("Make sure web has an active internet connection");
			System.exit(0);
			}
			
			//Send a message from Web to android
			Thread.sleep(5000);
			webDriver.findElementByXPath("//div/textarea[@autofocus='true']").sendKeys("Hello6");
			webDriver.findElementByXPath("//span[@class='fa fa-paper-plane']").click();
			
			//Android verify the conversation and message.
			try {
			if(androidDriver.currentActivity().equals(".modules.MainDrawerActivity")) {
				androidDriver.findElementById("com.infinite.netsfere:id/action_search").click();
				Thread.sleep(2000);
				androidDriver.findElementById("com.infinite.netsfere:id/search_src_text").sendKeys(conversationTitle4);
				androidDriver.findElementById("com.infinite.netsfere:id/message_time_text").click();
			
			List<AndroidElement>elems6 = androidDriver.findElementsById("com.infinite.netsfere:id/message_content_textview");
			if(elems6.size() > 0) {
				rcvdMessageAndroid = elems6.get(elems6.size() - 1).getText();
				}
			if(rcvdMessageAndroid.equals("Hello6"))
				System.out.println("Android received correct group message from Web");
			else
				System.out.println("Android received incorrect message from Web");
			}
		}
			
			catch (NoSuchElementException e){
				System.out.println("Check internet connection for Android");
				System.exit(0);
				}
			
			//Switch to Web to start the broadcast.
			
			webDriver.findElementByXPath("//span[contains(@class,'videocam-outline')]").click();
			webDriver.findElementByXPath("//span[text()='Broadcast Live']").click();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'started a NetSfere ')]")));
			try{
			if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter")){
				webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
				webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
				Thread.sleep(500);
				webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
				webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
				Thread.sleep(500);	
			}
		}
			catch (NoSuchElementException e){
				System.out.println("Live could not start for Web");
				System.exit(0);
				}
			
			//Android check the broadcast.
			Thread.sleep(6000);
			androidDriver.findElementById("com.infinite.netsfere:id/more_menu_netstream_item").click();
			try{
			androidWait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/participants_button")));
			System.out.println("Web started broadcast for Group conversation");
			}
			catch (NoSuchElementException e){
				System.out.println("Live has not started for Android");
				System.exit(0);
			}
			
			androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
			androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
			Thread.sleep(500);
			androidDriver.findElementById("com.infinite.netsfere:id/speaker_button").click();
			androidDriver.findElementById("com.infinite.netsfere:id/speaker_button").click();
			Thread.sleep(500);
			androidDriver.findElementById("com.infinite.netsfere:id/stop_button").click();
			System.out.println("Android user left the broadcast");
			
			//Web User Stops the broadcast
			
			try{
				if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter")) {
					Thread.sleep(4000);
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					Thread.sleep(500);
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					Thread.sleep(500);
					webDriver.findElementByCssSelector("div>img[src*='BroadcastBtn']").click();
					System.out.println("Web ended the broadcast in group conversation");
				}
			}
			
			catch (NoSuchElementException e){
				System.out.println("Live has been terminated for Web owing to Network issues");
				System.exit(0);
			}
			
			webDriver.navigate().refresh();
			try{
				if(we==null) {
					androidWait2.until(ExpectedConditions.visibilityOf(we));
				}
			}
			catch ( NoSuchElementException e) {
				System.out.println( "ERROR - Unable to refresh in Web");       
			} 
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			
			//Switch to Channels.
			Thread.sleep(5000);
			androidDriver.findElementByXPath("//android.widget.FrameLayout[@index='3']").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Broadcast']")));
			//Search for a channel in which Android user has writable access.
			androidDriver.findElementById("com.infinite.netsfere:id/action_search").click();
			androidDriver.findElementById("com.infinite.netsfere:id/search_src_text").sendKeys(channel1);
			androidDriver.findElementById("com.infinite.netsfere:id/message_time_text").click();
			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/quick_reply_button")));
			
			//Start a Live session in Android
			
			androidDriver.findElementById("com.infinite.netsfere:id/more_menu_netstream_item").click();
			androidDriver.findElementById("com.infinite.netsfere:id/broadcast_netstream_text").click();
			
			
			Thread.sleep(9000);
			LiveText = androidDriver.findElementById("com.infinite.netsfere:id/stop_button").getText();
			
			if (LiveText.equalsIgnoreCase("Stop"))
			{
				try {
					androidDriver.findElementById("com.infinite.netsfere:id/cameraSwitch_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/cameraSwitch_button").click();
					Thread.sleep(500);
					androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
					Thread.sleep(500);
					androidDriver.findElementById("com.infinite.netsfere:id/mute_button").click();
					androidDriver.findElementById("com.infinite.netsfere:id/mute_button").click();
					Thread.sleep(500);
					System.out.println("Android user started a broadcast in Channel");
				}
				
				catch ( NoSuchElementException e) {
			        
					System.out.println( "ERROR - Android Unable to Start a Live session...");
			        
					System.out.println("exiting the programe...");
			        System.exit(0);
				} 
			}
			
//			Web user goes to channels.
			
			webDriver.findElementByXPath("//span[@class='icon ion-speakerphone']").click();
			webDriver.findElementByXPath("//input[@type='text']").sendKeys(channel1);
			Thread.sleep(4000);
			webDriver.findElementByXPath("//div[contains(@class,'Component') and contains(@style,'height: 42px')]").click();
			
			//switch to web to check whether the broadcast is reflected there or not.
			try
				{
				if(webDriver.findElementByCssSelector("g>path[d^='M18']").getText().equals("LIVE")){
					System.out.println("Android has started a broadcast");
				}
			}
			
			catch ( NoSuchElementException e) {
		        
				System.out.println( "ERROR - Check Internet connection for Web");
		        
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 

			
			
			
			//Web Join the broadcast.
			Thread.sleep(3000);
			webDriver.findElementByXPath("//span[contains(@class,'videocam') and not (contains(@class,'outline'))]").click();
			Thread.sleep(12000);
			try
			{
				if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter"))
				{
					Thread.sleep(4000);
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					Thread.sleep(500);
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					Thread.sleep(500);
					webDriver.findElementByCssSelector("div>img[src*='leave']").click();
					Thread.sleep(500);
					System.out.println("Web user is able to view the broadcast");
				}
			}
			
			catch ( NoSuchElementException e) {
		        if(androidDriver.findElementById("android:id/message").getText().contains("interrupted"))
		        {
		        	System.out.println( "ERROR - Connection timed out for Android/LiveBroadcast affected by Network issues");
		        }
		        else
		        {
		        System.out.println("ERROR- Check Internet Connection for Web");	
		        }
				System.out.println("exiting the programe...");
		        System.exit(0);
			} 
			Thread.sleep(2000);
			//android end the broadcast
			androidDriver.findElementById("com.infinite.netsfere:id/stop_button").click();
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			System.out.println("Android ended the broadcast in Channel");
			
			
			//web clears the channel title
			webDriver.findElementByXPath("//div[text()='Cancel']").click();
			Thread.sleep(4000);
			
			
			//Web starts the broadcast.
			webDriver.findElementByXPath("//input[@type='text']").sendKeys(channel1);
			Thread.sleep(2000);
			webDriver.findElementByXPath("//div[contains(@class,'Component') and contains(@style,'height: 42px')]").click();
			Thread.sleep(4000);
			webDriver.findElementByXPath("//span[contains(@class,'videocam-outline')]").click();
			Thread.sleep(2000);
			webDriver.findElementByXPath("//span[text()='Broadcast Live']").click();
			webWait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'started a NetSfere ')]")));
			System.out.println("Web started a broadcast in Channels");
			try{
			if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter")){
				webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
				webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
				Thread.sleep(500);
				webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
				webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
				Thread.sleep(500);	
			}
		}
			catch (NoSuchElementException e){
				System.out.println("Live could not start for Web");
				System.exit(0);
				}
			
			//Android search the channel
			androidDriver.findElementById("com.infinite.netsfere:id/action_search").click();
			androidDriver.findElementById("com.infinite.netsfere:id/search_src_text").sendKeys(channel1);
//			androidWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/conversation_live_badge")));
			androidDriver.findElementById("com.infinite.netsfere:id/message_time_text").click();
			
			//Android check the broadcast.
			
			androidDriver.findElementById("com.infinite.netsfere:id/more_menu_netstream_item").click();
			if(androidDriver.findElementById("com.infinite.netsfere:id/view_netstream_text").getText().equals("View Live Broadcast")) {
				androidDriver.findElementById("com.infinite.netsfere:id/view_netstream_text").click();
			}
			else {
				System.out.println("Do nothing");
			}
			try{
			androidWait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.infinite.netsfere:id/participants_button")));
			}
			catch (NoSuchElementException e){
				System.out.println("Live has not started for Android");
				System.exit(0);
			}
			
			androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
			androidDriver.findElementById("com.infinite.netsfere:id/zoom_button").click();
			Thread.sleep(500);
			androidDriver.findElementById("com.infinite.netsfere:id/speaker_button").click();
			androidDriver.findElementById("com.infinite.netsfere:id/speaker_button").click();
			Thread.sleep(500);
			androidDriver.findElementById("com.infinite.netsfere:id/stop_button").click();
			System.out.println("Android user left the broadcast");
			
			//Web User Stops the broadcast
			
			try{
				if(webDriver.findElementByXPath("//span[text()='Presenter']").getText().equals("Presenter")) {
					Thread.sleep(4000);
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					webDriver.findElementByCssSelector("div>img[src*='zoom']").click();
					Thread.sleep(500);
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					webDriver.findElementByXPath("//button[contains(@title,'chat')]/div/img").click();
					Thread.sleep(500);
					webDriver.findElementByCssSelector("div>img[src*='BroadcastBtn']").click();
					System.out.println("Web ended the broadcast in channels");
				}
			}
			
			catch (NoSuchElementException e){
				System.out.println("Live has been terminated for Web owing to Network issues");
				System.exit(0);
			}
			
			webDriver.navigate().refresh();
			try{
				if(we==null) {
					androidWait2.until(ExpectedConditions.visibilityOf(we));
				}
			}
			catch ( NoSuchElementException e) {
				System.out.println( "ERROR - Unable to refresh in Web");       
			} 
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(2000);
			androidDriver.pressKeyCode(AndroidKeyCode.BACK);
			androidWait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageView[@index='2']")));
			Thread.sleep(5000);
		}
}