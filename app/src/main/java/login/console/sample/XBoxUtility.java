package login.console.sample;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

public class XBoxUtility {
    /**
     * TODO: This is using some of the deprecated methods to control cookie and redirect,
     * as this page needs to be rewritten in a cleaner way
     */

    public String XBOX_LOGIN_URL = "https://hidden-atoll-21536.herokuapp.com/xbox_live_auth.php?access_token=";
    public String XBOX_SEND_MSG_URL = "https://hidden-atoll-21536.herokuapp.com/xbox_live_send_msg.php";
    private ExecutorService pool;


    private DefaultHttpClient mHttpClient;
    private HttpContext mHttpContext;
    private Context mContext;
    private String mUserName;
    private String mPassword;
    private String mAccessToken;

    XBoxInterface callback;

    public XBoxUtility(Context context, String username, String password)
    {
        mContext = context;
        mUserName = username;
        mPassword = password;
        pool = Executors.newSingleThreadExecutor();
    }

    public void init()
    {
        pool.execute(new LoginInfoService());
    }

    /**
     * Creating initial cookie for the login
     */
    private class LoginInfoService implements Runnable {
        public LoginInfoService(){

        }

        @Override
        public void run() {
            String s = XBoxLoginInfo();
            String urlPost = "";
            String ETTag = "";

            if(Pcre.preg_match("urlPost:'\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", s) > 0)
            {
                urlPost = Pcre.preg_match("urlPost:'\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", s, true);
                urlPost = urlPost.substring(("urlPost:'").length());
                Log.e("urlPost", urlPost);

            }

            if(Pcre.preg_match("sFTTag:'.*value=\\\"(.*)\\\"\\/>'", s) > 0)
            {
                ETTag = Pcre.preg_match("sFTTag:'.*value=\\\"(.*)\\\"\\/>'", s, true);
                ETTag = ETTag.substring(ETTag.indexOf("value=\"") + ("value=\"").length(), ETTag.indexOf("\"/>"));
                Log.e("ETTag", ETTag);
            }

            pool.execute(new GetAccessTokenService(urlPost, ETTag));

        }
    }

    /**
     * Rrequire
     * @return The String that's used
     */
    private String XBoxLoginInfo()
    {
        mHttpClient = new DefaultHttpClient();
        mHttpClient.getCookieSpecs().register("lenient", new CookieSpecFactory()
        {
            public CookieSpec newInstance(HttpParams params)
            {
                return new LenientCookieSpec();
            }
        });
        mHttpClient.getParams().setParameter("http.protocol.max-redirects", 1);

        HttpClientParams.setCookiePolicy(mHttpClient.getParams(), "lenient");


        mHttpContext = new BasicHttpContext();

        HttpUriRequest currentReq = (HttpUriRequest)mHttpContext.getAttribute(
                ExecutionContext.HTTP_REQUEST);


        HttpPost currentHost = new HttpPost("https://login.live.com/oauth20_authorize.srf?client_id=0000000048093EE3&redirect_uri=https://login.live.com/oauth20_desktop.srf&response_type=token&display=touch&scope=service::user.auth.xboxlive.com::MBI_SSL&locale=en");

        TimeZone tz = TimeZone.getDefault();
        int utcOffsetMinutes = tz.getOffset(System.currentTimeMillis()) / (1000 * 60);

        BasicClientCookie cookie = new BasicClientCookie("UtcOffsetMinutes",
                String.valueOf(utcOffsetMinutes));
        cookie.setPath("/");
        cookie.setDomain(".xbox.com");

        mHttpClient.getCookieStore().addCookie(cookie);


        try {
            HttpResponse mLastResponse = mHttpClient.execute(currentHost);
            HttpEntity entity = mLastResponse.getEntity();
            if (entity == null)
                return null;

            List<Cookie> cookies = mHttpClient.getCookieStore().getCookies();
/*
            cookies = (Header[]) mLastResponse.getHeaders("Set-Cookie");
            for(int h=0; h<cookies.length; h++){
                Log.e("cookie", cookies[h].getValue());
            }
*/

            InputStream stream = entity.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream), 10000);
            StringBuilder builder = new StringBuilder(10000);
            int read;
            char[] buffer = new char[1000];

            while ((read = reader.read(buffer)) >= 0)
                builder.append(buffer, 0, read);
            stream.close();
            entity.consumeContent();

            String response = builder.toString();

            Log.e("response", response);
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(OutOfMemoryError e)
        {
            return null;
        }
        return null;
    }

    /**
     * Getting the Access token to access the account
     */
    private class GetAccessTokenService implements Runnable {
        private String _urlPost;
        private String _ETTag;

        public GetAccessTokenService(String urlPost, String ETTag)
        {
            _urlPost = urlPost;
            _ETTag = ETTag;
        }

        @Override
        public void run() {

            Map<String, Object> map = new HashMap<String, Object>();

            String[] queryKeys = new String[]{"login", "passwd", "PPFT", "'PPSX", "SI", "type", "NewUser", "LoginOptions", "i3", "m1", "m2", "m3", "i12",
                    "i17", "i18"};

            String[] queryValues = new String[]{mUserName, mPassword, _ETTag, "Passpor", "Sign in", "11", "1", "1",
                    "36728", "768", "1184", "0", "1", "0", "__Login_Host|1"};

            for (int i = 0; i < queryKeys.length; i++) {
                map.put(queryKeys[i], queryValues[i]);
            }


            HttpUriRequest currentReq = (HttpUriRequest) mHttpContext.getAttribute(
                    ExecutionContext.HTTP_REQUEST);


            TimeZone tz = TimeZone.getDefault();
            int utcOffsetMinutes = tz.getOffset(System.currentTimeMillis()) / (1000 * 60);

            BasicClientCookie cookie = new BasicClientCookie("UtcOffsetMinutes",
                    String.valueOf(utcOffsetMinutes));
            cookie.setPath("/");
            cookie.setDomain(".xbox.com");

            mHttpClient.getCookieStore().addCookie(cookie);
            HttpPost currentHost = new HttpPost(_urlPost);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("login", "tina@crossroadsapp.co"));
            params.add(new BasicNameValuePair("passwd", "Traveler1990"));
            params.add(new BasicNameValuePair("PPFT", _ETTag));
            params.add(new BasicNameValuePair("PPSX", "Passpor"));
            params.add(new BasicNameValuePair("SI", "Sign in"));
            params.add(new BasicNameValuePair("type", "11"));
            params.add(new BasicNameValuePair("NewUser", "1"));
            params.add(new BasicNameValuePair("LoginOptions", "1"));
            params.add(new BasicNameValuePair("i3", "36728"));
            params.add(new BasicNameValuePair("m1", "768"));
            params.add(new BasicNameValuePair("m2", "1184"));
            params.add(new BasicNameValuePair("m3", "0"));
            params.add(new BasicNameValuePair("i12", "1"));
            params.add(new BasicNameValuePair("i17", "0"));
            params.add(new BasicNameValuePair("i18", "__Login_Host|1"));
            try {
                currentHost.setEntity(new UrlEncodedFormEntity(params));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //

            try {

                mHttpClient.setRedirectHandler(new RedirectHandler() {
                    @Override
                    public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
                        for(int i = 0; i < httpResponse.getAllHeaders().length; i++)
                        {
                            if(httpResponse.getAllHeaders()[i].getName().equalsIgnoreCase("Location"))
                            {
                                Log.e("Location", httpResponse.getAllHeaders()[i].getValue());

                                Pattern pattern = Pattern.compile(".*access_token=(\\S*)");
                                Matcher matcher = pattern.matcher(httpResponse.getAllHeaders()[i].getValue());
                                if(matcher.matches())
                                {
                                    //retrieveAccessToken(matcher.group(1));
                                }
                            }

                        }

                        return false;
                    }

                    @Override
                    public URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
                        return null;
                    }
                });

                HttpResponse mLastResponse = mHttpClient.execute(currentHost);
                HttpEntity entity = mLastResponse.getEntity();
                if (entity == null)
                    return;

                List<Cookie> cookies = mHttpClient.getCookieStore().getCookies();
                return;

            } catch (IOException e) {
                e.printStackTrace();
            }
            catch(OutOfMemoryError e)
            {
                return;
            }

            return;
        }
    }

/*
    private void retrieveAccessToken(String access_token)
    {
        Log.e("AccessToken", access_token);
        mAccessToken = access_token;
        TokenStoreUtility.storeXBOXToken(mContext, access_token);
        callback.getAccessToken();
    }
*/

    private static class LenientCookieSpec extends BrowserCompatSpec
    {
        private static final DateFormat LIVE_COOKIE_DATE_FORMAT =
                new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss zzz", Locale.US);

        public LenientCookieSpec()
        {
            super();

            registerAttribHandler(ClientCookie.EXPIRES_ATTR,
                    new BasicExpiresHandler(DATE_PATTERNS)
                    {
                        @Override
                        public void parse(SetCookie cookie, String value)
                                throws MalformedCookieException
                        {
                            // THEIRS: Wed, 30-Dec-2037 16:00:00 GMT
                            // PROPER: Tue, 15 Jan 2013 21:47:38 GMT

                            Date expires = null;

                            String language = Locale.getDefault().getLanguage();
                            String country = Locale.getDefault().getCountry();

                            if ("en".equals(language) && "US".equals(country))
                            {
                                // US only

                                if (value != null && value.contains("-"))
                                {
                                    try
                                    {
                                        expires = LIVE_COOKIE_DATE_FORMAT.parse(value);
                                    }
                                    catch (Exception e)
                                    {
                                        /*
                                        if (App.getConfig().logToConsole())
                                        {
                                            App.logv("Got an error during expiration cookie parse:");
                                            e.printStackTrace();
                                        }*/
                                    }
                                }
                            }

                            if (expires != null)
                            {
                                /*
                                if (App.getConfig().logToConsole())
                                    App.logv("Setting expiration date explicitly");
*/
                                cookie.setExpiryDate(expires);
                            }
                            else
                            {
                                /*
                                if (App.getConfig().logToConsole())
                                    App.logv("Using default expiration date handler");
*/
                                super.parse(cookie, value);
                            }
                        }
                    });
        }
    }


    public void getTokenAndHeader()
    {
        Log.e("URL", XBOX_LOGIN_URL + mAccessToken);
        if(mAccessToken == null || mAccessToken.isEmpty())
        {
            mAccessToken = TokenStoreUtility.getXBoxAccessToken(mContext);
        }
        RequestQueue queue = Volley.newRequestQueue(mContext); // this = context

        StringRequest postRequest = new StringRequest(Request.Method.GET, XBOX_LOGIN_URL + mAccessToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject data = new JSONObject(response);
                            if(data.has("gamertag") && data.has("xuid") && data.has("authorization_header") && data.has("authorization_expires"))
                            {
                                Log.e("gamertag", data.getString("gamertag"));
                                Log.e("xuid", data.getString("xuid"));
                                Log.e("authorization_header", data.getString("authorization_header"));
                                Log.e("authorization_expires", data.getString("authorization_expires"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    /**
     * Msg being sent
     * @param message
     * @param user user needs to be split with comma (,)
     */
    public void sendMessages(String user, String message)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("hidden-atoll-21536.herokuapp.com")
                .appendPath("xbox_live_send_msg.php")
                .appendQueryParameter("access_token", mAccessToken)
                .appendQueryParameter("gamertags", user)
                .appendQueryParameter("message", message);
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
