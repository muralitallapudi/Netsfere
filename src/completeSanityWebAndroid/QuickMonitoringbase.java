package completeSanityWebAndroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class QuickMonitoringbase {
	
	public static ChromeDriver Instance;
	
	public static void startAppiumServerNetsfere() throws InterruptedException{
		
		Runtime runtime = Runtime.getRuntime();
		
		// Check if any appium session running & kill it. 
		try {
			System.out.println("Killing the existing chrome browsers...");
			runtime.exec("taskkill /F /IM chromedriver.exe /T");
			System.out.println("Killing the existing appium sessions...");
			runtime.exec("taskkill /F /IM node.exe /T");
			Thread.sleep(2000);
			runtime.exec("taskkill /F /IM adb.exe /T");
			Thread.sleep(2000);
			runtime.exec("taskkill /F /IM cmd.exe /T");
			Thread.sleep(2000);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		// start appium server
		try {
			System.out.println("Starting appium server...");
			runtime.exec("cmd.exe /c start cmd.exe /k \"adb devices ");
			Thread.sleep(10000);
			runtime.exec("taskkill /F /IM cmd.exe /T");
			Thread.sleep(5000);
			runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 ");
			Thread.sleep(10000);
			
			String line;
			String pidInfo ="";
			do {
				System.out.println("Waiting for appium server to start.....");
				Thread.sleep(3000);
				Process p =Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
				BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					pidInfo+=line; 
				}
				input.close();

				if(pidInfo.contains("node.exe"))
				{
					System.out.println("appium server started.....");
				} 
				
			}while(!(pidInfo.contains("node.exe")));
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread.sleep(3000);
		
	}
	
	public void stopAppiumServerNetsfere() throws InterruptedException{
		Runtime runtime = Runtime.getRuntime();		
		 
		try {
			System.out.println("Killing the appium process");
			runtime.exec("taskkill /F /IM node.exe /T");
			// kill the cmd windows
			System.out.println("Killing the cmd windows opened");
			runtime.exec("taskkill /F /IM cmd.exe /T");
			Thread.sleep(2000);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
public static  AndroidDriver<AndroidElement> initializeAndroidDriverNetsfere() throws MalformedURLException  {
		
		File f = new File("src");
		File app_path = new File(f,"NetSfere.apk");
		
		DesiredCapabilities capabilities = new DesiredCapabilities() ;
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
		capabilities.setCapability(CapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.7.2");		
		
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"3300f7968b2c63c9"); // NCO Samsung Phone
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"7.0");
		
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"4200d75a6a97949b"); // Moto G3
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"7.0");
		
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"7ad390bf7d25"); // Nexus 
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"7.1.2");
		
		
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.infinite.netsfere");
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.infinite.netsfere.SplashActivity");
		
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "180000");
//		capabilities.setCapability(MobileCapabilityType.APP , app_path.getAbsolutePath());
		
		System.out.println("Connecting to Android device & Installing the App");
		
		AndroidDriver<AndroidElement>  driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub") , capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
		return driver ;		
	}

public static ChromeDriver chromeDriverInitialize() {
	
	System.setProperty("webdriver.chrome.driver", "D:\\NCO_Monitoring_Script_software\\NCO_Monitoring_Script_software\\chromedriver.exe");	
	
	ChromeOptions options=new ChromeOptions();
	Map<String, Object> prefs=new HashMap<String,Object>();
	prefs.put("profile.default_content_setting_values.notifications", 1);
	options.setExperimentalOption("prefs",prefs);		
	options.addArguments("use-fake-ui-for-media-stream");			
	Instance = new ChromeDriver(options);
	(Instance).manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	(Instance).manage().window().maximize();
	return Instance;	
}	
	
}
