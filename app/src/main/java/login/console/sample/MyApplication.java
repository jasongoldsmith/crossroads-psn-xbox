package login.console.sample;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookiePolicy;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();

        // enable cookies
        java.net.CookieManager cookieManager = new java.net.CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }
}