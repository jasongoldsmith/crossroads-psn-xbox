package login.console.sample;

import android.content.Context;
import android.net.Uri;
import android.net.http.LoggingEventHandler;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import login.console.sample.login.Pcre;

/**
 * Created by peterma on 12/12/16.
 */

public class XBoxLiveUtility {
    /**
     * TODO: This is using some of the deprecated methods to control cookie and redirect,
     * as this page needs to be rewritten in a cleaner way
     */
    public String XBOX_SEND_MSG_URL = "https://hidden-atoll-21536.herokuapp.com/xbox_live_send_msg.php";

    /**
     * Msg being sent
     * @param message
     * @param user user needs to be split with comma (,)
     */
    public static void sendMessages(Context context, String user, String message)
    {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(new XBoxLiveUtility.RefreshTokenService(context));
        pool.execute(new XBoxLiveUtility.SendMessageService(context, user, message));

    }

    public static Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }


    /**
     * Creating initial cookie for the login
     */
    private static class RefreshTokenService implements Runnable {
        Context mContext;
        public RefreshTokenService(Context context){
            mContext = context;
        }

        @Override
        public void run() {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("login.live.com")
                    .appendPath("oauth20_authorize.srf")
                    .appendQueryParameter("client_id", "0000000048093EE3")
                    .appendQueryParameter("redirect_uri", "https://login.live.com/oauth20_desktop.srf")
                    .appendQueryParameter("grant_type", "refresh_token")
                    .appendQueryParameter("response_type", "token")
                    .appendQueryParameter("scope", "service::user.auth.xboxlive.com::MBI_SSL")
                    .appendQueryParameter("refresh_token", TokenStoreUtility.getXBoxRefreshToken(mContext));

            String MessageUrl = builder.build().toString();


            try {
                URLConnection con = new URL(MessageUrl).openConnection();
                con.connect();
                InputStream is = con.getInputStream();
                Log.e("redirect", con.getURL().toString());
                is.close();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creating initial cookie for the login
     */
    private static class SendMessageService implements Runnable {
        Context mContext;
        String mUser;
        String mMessage;

        public SendMessageService(Context context, String user, String message){
            mContext = context;
            mUser = user;
            mMessage = message;
        }

        @Override
        public void run() {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("hidden-atoll-21536.herokuapp.com")
                    .appendPath("xbox_live_send_msg.php")
                    .appendQueryParameter("access_token", TokenStoreUtility.getXBoxAccessToken(mContext))
                    .appendQueryParameter("gamertags", mUser)
                    .appendQueryParameter("message", mMessage);
            String MessageUrl = builder.build().toString();

            RequestQueue queue = Volley.newRequestQueue(mContext); // this = context

            StringRequest postRequest = new StringRequest(Request.Method.GET, MessageUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", "having error");
                        }
                    }
            );

            queue.add(postRequest);
            queue.start();
        }
    }
}
