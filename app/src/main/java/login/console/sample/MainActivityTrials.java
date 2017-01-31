package login.console.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import login.console.sample.login.Pcre;
import okhttp3.OkHttpClient;

/*
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microsoft.services.msa.LiveAuthClient;
import com.microsoft.services.msa.LiveAuthException;
import com.microsoft.services.msa.LiveAuthListener;
import com.microsoft.services.msa.LiveConnectSession;
import com.microsoft.services.msa.LiveStatus;
*/
/*
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
*/
//import okhttp3.Cookie;
//import okhttp3.CookieJar;

public class MainActivityTrials extends AppCompatActivity{

    //CookieHandler cookieHandler;
    OkHttpClient client;
/*
    class CustomCookieJar implements CookieJar {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }

        public void clearCookies()
        {
            cookieStore.clear();
        }

    }

    CustomCookieJar cookieJar = new CustomCookieJar();
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        cookieHandler = new CookieManager(
                new PersistentCookieStore(this), CookiePolicy.ACCEPT_ALL);
*/
        client = new OkHttpClient.Builder()
                //.cookieJar(new JavaNetCookieJar(cookieHandler))
                //.cookieJar(cookieJar)
                .build();

        //examples on logging in
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PSNUtility.PSNLogin(MainActivityTrials.this, "tina@crossroadsapp.co", "Traveler1990");
            }
        });

        findViewById(R.id.message).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PSNUtility.PSNMessage(MainActivityTrials.this, "CrossroadsJason", "testing from the phone");
            }
        });

        findViewById(R.id.xbox_login).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {



                //I've copied the entire library for XBox Login, otherwise I'd use call back in stead of observables
/*
                Subscriber<String> loginSubscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Subscribe", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Subscribe", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("Subscribe", s);
                    }
                };

                LoginInformation info = new LoginInformation(
                        "DT2tInjfJePNPB3IQ!L7xqFGakSmopCRReqKiSJGOk1yEms1wZQJNopHi2Qtehcxcdf9gCjGiwuXQtD9TLP0tNc*5qc7xASixVfWBhtT650vivBuTDwzgZ2KGpEPmh2WA17MTEb0gXe4xMroDBprxqgGMUxemnUJHy0AkinX36QktnAdjff9ATcSPcVZ2b!WS6Ld0gb9C*CPq7a737a9aUw$",
                        "https://login.live.com/ppsecure/post.srf?client_id=0000000048093EE3&redirect_uri=https://login.live.com/oauth20_desktop.srf&response_type=token&display=touch&scope=service::user.auth.xboxlive.com::MBI_SSL&locale=en&contextid=00ABE74AB68062C2&bk=1482366177&uaid=dac974eef37f40f39d4dd97271509750&pid=15216"
                        );

                LoginTokenRequest login = new LoginTokenRequest("tina@crossroadsapp.co", "Traveler1990", info);
                //login.execute().subscribe(loginSubscriber);
                login.execute().subscribe(loginSubscriber);
*/
/*
                final Subscriber<String> loginTokenSubscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Subscribe", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Subscribe", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("token", s);
                    }
                };

                final Subscriber<LoginInformation> loginSubscriber = new Subscriber<LoginInformation>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Subscribe", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Subscribe", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(LoginInformation info)
                    {
                        Log.d("Subscribe", "success getting preAuthInfo");
                        Log.d("Subscribe", info.getUrl());
                        Log.d("Subscribe", info.getPpft());

                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this); // this = context
                        try {
                            String requestBody = "login=tina%40crossroadsapp.co&passwd=Traveler1990&PPFT=" + URLEncoder.encode(info.getPpft(), "UTF-8") + "&PPSX=Passpor&SI=Sign+In&type=11&NewUser=1&LoginOptions=1&i3=36728&m1=768&m2=1184&m3=0&i12=1&i17=0&i18=__Login_Host%7C1";
                            Log.d("requestBody", requestBody);

                            StringRequest postRequest = new StringRequest(Request.Method.POST, info.getUrl(),
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
                            ){

                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    return requestBody.getBytes();
                                }


                            };
                            //postRequest.
                            //queue.add(postRequest);
                            queue.start();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        //LoginTokenRequest login = new LoginTokenRequest("tina@crossroadsapp.co", "Traveler1990", info);
                        //login.execute().subscribe(loginTokenSubscriber);
                    }
                };

                LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
                loginInfoRequest.execute().subscribe(loginSubscriber);
*/
                new LoginInfoTaskDeprecated().execute();
                //xboxLoginInfo();
            }
        });

    }


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

    DefaultHttpClient mHttpClient;
    HttpContext context;
    Header[] cookies;

    public String XBoxLoginTakeUsingDeprecatedMethods()
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


        context = new BasicHttpContext();

        HttpUriRequest currentReq = (HttpUriRequest)context.getAttribute(
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


    private class LoginInfoTaskDeprecated extends AsyncTask<URL, Integer, String> {
        protected String doInBackground(URL... urls) {

            return XBoxLoginTakeUsingDeprecatedMethods();

        }

        protected void onPostExecute(String s) {
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
            xboxLoginAccessTokenDeprecated(urlPost, ETTag);

        }
    }

    private void xboxLoginAccessTokenDeprecated(String urlPost, String ETTag)
    {
        new LoginAccessTokenTaskDeprecated(urlPost, ETTag).execute();
    }


    private class LoginAccessTokenTaskDeprecated extends AsyncTask<URL, Integer, String> {
        private String _urlPost;
        private String _ETTag;

        public LoginAccessTokenTaskDeprecated(String urlPost, String ETTag)
        {
            _urlPost = urlPost;
            _ETTag = ETTag;
        }


        protected String doInBackground(URL... urls) {
                Map<String, Object> map = new HashMap<String, Object>();

                String[] queryKeys = new String[]{"login", "passwd", "PPFT", "'PPSX", "SI", "type", "NewUser", "LoginOptions", "i3", "m1", "m2", "m3", "i12",
                        "i17", "i18"};

                String[] queryValues = new String[]{"tina@crossroadsapp.co", "Traveler1990", _ETTag, "Passpor", "Sign in", "11", "1", "1",
                        "36728", "768", "1184", "0", "1", "0", "__Login_Host|1"};

                for (int i = 0; i < queryKeys.length; i++) {
                    map.put(queryKeys[i], queryValues[i]);
                }


                HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
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
                                        retrieveAccessToken(matcher.group(1));
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
                        return null;

                    List<Cookie> cookies = mHttpClient.getCookieStore().getCookies();
/*
            cookies = (Header[]) mLastResponse.getHeaders("Set-Cookie");
            for(int h=0; h<cookies.length; h++){
                Log.e("cookie", cookies[h].getValue());
            }
*//*
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
                    return response;*/

                    return "";

                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch(OutOfMemoryError e)
                {
                    return "";
                }
/*
                RequestBody body = RequestBody.create(TEXT, urlEncodeUTF8(map));
                Request request = new Request.Builder()
                        .url(_urlPost)
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
*/
//                return response.body().string();

            return "failed";
        }

        protected void onPostExecute(String s) {
            Log.e("accessToken", s);
         //   cookieJar.clearCookies();
        }
    }

    private void retrieveAccessToken(String token)
    {
        Log.e("Token", token);
    }



    /*
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType TEXT
            = MediaType.parse("text/plain; charset=utf-8");

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    private void xboxLoginInfo()
    {
        new LoginInfoTask().execute();
    }

    private class LoginInfoTask extends AsyncTask<URL, Integer, String> {
        protected String doInBackground(URL... urls) {
            try {

                // init OkHttpClient


                RequestBody body = RequestBody.create(TEXT, "");
                Request request = new Request.Builder()
                        .url("https://login.live.com/oauth20_authorize.srf?client_id=0000000048093EE3&redirect_uri=https://login.live.com/oauth20_desktop.srf&response_type=token&display=touch&scope=service::user.auth.xboxlive.com::MBI_SSL&locale=en")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "failed";
        }

        protected void onPostExecute(String s) {
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
            xboxLoginAccessToken(urlPost, ETTag);
        }
    }

    private void xboxLoginAccessToken(String urlPost, String ETTag)
    {
        new LoginAccessTokenTask(urlPost, ETTag).execute();
    }

    private class LoginAccessTokenTask extends AsyncTask<URL, Integer, String> {
        private String _urlPost;
        private String _ETTag;

        public LoginAccessTokenTask(String urlPost, String ETTag)
        {
            _urlPost = urlPost;
            _ETTag = ETTag;
        }


        protected String doInBackground(URL... urls) {
            try {
                Map<String,Object> map = new HashMap<String,Object>();

                String[] queryKeys = new String[]{"login", "passwd", "PPFT", "'PPSX", "SI", "type", "NewUser", "LoginOptions", "i3", "m1", "m3", "m3", "i12",
                        "i17", "i18"};

                String[] queryValues = new String[]{"tina@crossroadsapp.co", "Traveler1990", _ETTag, "Passpor", "Sign in", "11", "1", "1",
                        "36728", "768", "1184", "0", "1", "0", "__Login_Host|1"};

                for(int i = 0; i < queryKeys.length; i++)
                {
                    map.put(queryKeys[i], queryValues[i]);
                }

                RequestBody body = RequestBody.create(TEXT, urlEncodeUTF8(map));
                Request request = new Request.Builder()
                        .url(_urlPost)
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "failed";
        }

        protected void onPostExecute(String s) {
            Log.e("accessToken", s);
            //cookieJar.clearCookies();
        }
    }*/

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

}
