package login.console.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XBLiveLoginActivity extends AppCompatActivity{

    WebView webView1;
    private String TAG = "PSNLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        webView1 = (WebView)findViewById(R.id.webView1);
        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView1.clearFormData();
        webView1.clearCache(true);
        webView1.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        webView1.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here

                Log.e(TAG, "url:" + url);

                if(url.contains("access_token"))
                {
                    try {
                        Map<String, List<String>> lc = XBoxLiveUtility.splitQuery(
                                new URL(url.replace(url.substring(url.indexOf("lc="), url.indexOf("#") + 1), ""))
                        );

                        String access_token = lc.get("access_token").get(0);
                        String refresh_token = lc.get("refresh_token").get(0);

                        Log.e("access_token", access_token);
                        Log.e("refresh_token", refresh_token);

                        TokenStoreUtility.storeXBOXToken(XBLiveLoginActivity.this, access_token, refresh_token);
                        XBLiveLoginActivity.this.finish();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        webView1.loadUrl("https://login.live.com/oauth20_authorize.srf?client_id=0000000048093EE3&redirect_uri=https://login.live.com/oauth20_desktop.srf&response_type=token&display=touch&scope=service::user.auth.xboxlive.com::MBI_SSL&locale=en");
    }

}
