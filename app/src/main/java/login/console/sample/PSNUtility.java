package login.console.sample;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peterma on 12/12/16.
 */

public class PSNUtility {
    public static String PSN_LOGIN_URL = "https://hidden-atoll-21536.herokuapp.com/psn_auth.php?npsso=";
    public static String PSN_SSO_URL = "https://auth.api.sonyentertainmentnetwork.com/2.0/ssocookie";

    public static void PSNLogin(final Context context, final String login, final String password) {

        RequestQueue queue = Volley.newRequestQueue(context); // this = context

        StringRequest postRequest = new StringRequest(Request.Method.POST, PSN_SSO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject data = new JSONObject(response);
                            String npsso = data.getString("npsso");
                            Log.d("npsso", npsso);
                            ServerLogin(context, npsso);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("authentication_type", "password");
                params.put("username", login);
                params.put("password", password);
                params.put("client_id", "71a7beb8-f21a-47d9-a604-2e71bee24fe0");
                return params;
            }
        };

        queue.add(postRequest);
        queue.start();
    }

    public static void ServerLogin(final Context context, String npsso)
    {
        RequestQueue queue = Volley.newRequestQueue(context); // this = context

        StringRequest postRequest = new StringRequest(Request.Method.GET, PSN_LOGIN_URL + npsso,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject data = new JSONObject(response);
                            if(data.has("oauth") && data.has("refresh"))
                            {
                                String oauth = data.getString("oauth");
                                String refresh = data.getString("refresh");

                                TokenStoreUtility.storePSNToken(context, oauth, refresh);
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

    public static void PSNMessage(final Context context, final String user, final String message)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("hidden-atoll-21536.herokuapp.com")
                .appendPath("messaging.php")
                .appendQueryParameter("oauth", TokenStoreUtility.getPSNOauth(context))
                .appendQueryParameter("refresh", TokenStoreUtility.getPSNRefresh(context))
                .appendQueryParameter("user", user)
                .appendQueryParameter("message", message);
        String PSNMessageUrl = builder.build().toString();

        RequestQueue queue = Volley.newRequestQueue(context); // this = context

        StringRequest postRequest = new StringRequest(Request.Method.GET, PSNMessageUrl,
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

    public static void PSNMessageGroup(final Context context, final String users, final String message)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("hidden-atoll-21536.herokuapp.com")
                .appendPath("psn_group_messaging.php")
                .appendQueryParameter("oauth", TokenStoreUtility.getPSNOauth(context))
                .appendQueryParameter("refresh", TokenStoreUtility.getPSNRefresh(context))
                .appendQueryParameter("user", users)
                .appendQueryParameter("message", message);
        String PSNMessageUrl = builder.build().toString();

        RequestQueue queue = Volley.newRequestQueue(context); // this = context

        StringRequest postRequest = new StringRequest(Request.Method.GET, PSNMessageUrl,
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
