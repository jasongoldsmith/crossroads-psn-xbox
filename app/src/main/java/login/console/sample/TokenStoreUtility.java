package login.console.sample;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by peterma on 1/16/17.
 */

public class TokenStoreUtility {
    private static String PSN_TOKEN = "PSN_TOKEN";
    private static String XBOX_LIVE_TOKEN = "PSN_TOKEN";

    public static void storePSNToken(Context context, String oauth, String refresh)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PSN_TOKEN, MODE_PRIVATE).edit();
        editor.putString("oauth", oauth);
        editor.putString("refresh", refresh);
        editor.commit();
    }

    public static String getPSNOauth(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(PSN_TOKEN, MODE_PRIVATE);
        String restoredText = prefs.getString("oauth", null);
        return restoredText;
    }

    public static String getPSNRefresh(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(PSN_TOKEN, MODE_PRIVATE);
        String restoredText = prefs.getString("refresh", null);
        return restoredText;
    }

    public static void storeXBOXToken(Context context, String access_token, String refresh_token)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(XBOX_LIVE_TOKEN, MODE_PRIVATE).edit();
        editor.putString("access_token", access_token);
        editor.putString("refresh_token", refresh_token);
        editor.commit();
    }

    public static String getXBoxAccessToken(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(XBOX_LIVE_TOKEN, MODE_PRIVATE);
        String restoredText = prefs.getString("access_token", null);
        return restoredText;
    }

    public static String getXBoxRefreshToken(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(XBOX_LIVE_TOKEN, MODE_PRIVATE);
        String restoredText = prefs.getString("refresh_token", null);
        return restoredText;
    }
}