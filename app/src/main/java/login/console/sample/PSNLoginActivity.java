package login.console.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class PSNLoginActivity extends AppCompatActivity{

    WebView webView1;
    private String TAG = "PSNLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        webView1 = (WebView)findViewById(R.id.webView1);
        WebSettings webSettings = webView1.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webView1.clearFormData();
        webView1.clearCache(true);
        webView1.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        webView1.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                String cookies = CookieManager.getInstance().getCookie(url);
                Log.e(TAG, "All the cookies in a string:" + cookies);

                if(CookieManager.getInstance().getCookie(url).contains("npsso"))
                {
                    try {
                        HttpCookie cookie = parseRawCookieNpsso(cookies);
                        Log.e("npsso", cookie.getValue());

                        //PSNUtility.PSNLogin(PSNLoginActivity.this, "tina@crossroadsapp.co", "Traveler1990");
                        PSNUtility.ServerLogin(PSNLoginActivity.this, cookie.getValue());
                        PSNLoginActivity.this.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        webView1.loadUrl("https://auth.api.sonyentertainmentnetwork.com/2.0/oauth/authorize?response_type=code&client_id=b7cbf451-6bb6-4a5a-8913-71e61f462787&scope=capone:report_submission,psn:sceapp,user:account.get,user:account.settings.privacy.get,user:account.settings.privacy.update,user:account.realName.get,user:account.realName.update,kamaji:get_account_hash,kamaji:ugc:distributor,oauth:manage_device_usercodes&request_locale=en&grant_type=authorization_code");
    }

    public HttpCookie parseRawCookieNpsso(String rawCookie) throws Exception {
        String[] rawCookieParams = rawCookie.split(";");

        String[] rawCookieNameAndValue = rawCookieParams[0].split("=");
        if (rawCookieNameAndValue.length != 2) {
            throw new Exception("Invalid cookie: missing name and value.");
        }

        for (int i = 0; i < rawCookieParams.length; i++) {
            String rawCookieParamNameAndValue[] = rawCookieParams[i].trim().split("=");

            String paramName = rawCookieParamNameAndValue[0].trim();


            if (paramName.equalsIgnoreCase("npsso")) {
                HttpCookie ret = new HttpCookie(rawCookieParamNameAndValue[0].trim(), rawCookieParamNameAndValue[1].trim());
                return ret;
            }
        }

        return null;
    }
}
