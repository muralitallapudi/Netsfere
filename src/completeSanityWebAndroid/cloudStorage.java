package completeSanityWebAndroid;

import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class cloudStorage {
	
	public static boolean isBoxAccountExist(AndroidDriver<AndroidElement> androidDriver) {
		
		List<AndroidElement> elems = androidDriver.findElementsByAndroidUIAutomator("text(\"BOX\")");
		
		if (elems.size() == 1) {
			return false;			
		} else if ( elems.size() > 1) {
			return true;
		} else {
			return false;
		}	
		
	}
	
	public static  AndroidElement findBoxAccountCredentials(AndroidDriver<AndroidElement> androidDriver , String account) {
		
		AndroidElement returnElement = null ;
		List<AndroidElement> elems = androidDriver.findElementsByAndroidUIAutomator("text(\"BOX\")");
		
		for (AndroidElement elem : elems) {
			if( elem.findElementByXPath("../android.widget.TextView[@resource-id='com.infinite.netsfere:id/account_name']").getText().equals(account)) {
				returnElement = (AndroidElement) elem.findElementByXPath("../android.widget.TextView[@resource-id='com.infinite.netsfere:id/account_name']");
			}
		}
		return  returnElement;
		
	}
	
	public static  void deleteAllBoxAccounts(AndroidDriver<AndroidElement> androidDriver) {
		
		
		List<AndroidElement> elems = androidDriver.findElementsByAndroidUIAutomator("text(\"BOX\")");
		
		for (AndroidElement elem : elems) {
			if( elem.findElementByXPath("../android.widget.TextView[@resource-id='com.infinite.netsfere:id/account_name']").getText().equals("Add Account")) {
				continue;
			} else {
				elem.findElementByXPath("../android.widget.ImageView[@resource-id='com.infinite.netsfere:id/remove_account_button']").click();
			}
		}
		
		
	}
	
	public static  void addBoxAccounts(AndroidDriver<AndroidElement> androidDriver , String account , String password) {
		
		List<AndroidElement> elems = androidDriver.findElementsByAndroidUIAutomator("text(\"BOX\")");
		
		for (AndroidElement elem : elems) {
			if( elem.findElementByXPath("../android.widget.TextView[@resource-id='com.infinite.netsfere:id/account_name']").getText().equals("Add Account")) {
				elem.findElementByXPath("../android.widget.TextView[@resource-id='com.infinite.netsfere:id/account_name']").click();
				androidDriver.findElementByXPath("android.widget.EditText[@resource-id='login']").click();
				androidDriver.findElementByXPath("android.widget.EditText[@resource-id='login']").sendKeys(account);
				androidDriver.findElementByXPath("android.widget.EditText[@resource-id='password']").click();
				androidDriver.findElementByXPath("android.widget.EditText[@resource-id='password']").sendKeys(password);
				androidDriver.findElementByXPath("android.widget.Button[@text='Authorize']").click();
				androidDriver.findElementByXPath("android.widget.Button[@resource-id='consent_accept_button' and @text='Grant access to Box']").click();
				
			} else {
				continue;
			}
		}
		
		
	}
	public static boolean isDracoonAccountExist(AndroidDriver<AndroidElement> androidDriver) {
		
		List<AndroidElement> elems = androidDriver.findElementsByAndroidUIAutomator("text(\"DRACOON\")");
		
		if (elems.size() == 1) {
			return false;			
		} else if ( elems.size() > 1) {
			return true;
		} else {
			return false;
		}	
		
	}

	
}
