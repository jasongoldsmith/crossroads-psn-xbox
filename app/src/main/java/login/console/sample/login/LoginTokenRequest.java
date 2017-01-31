package login.console.sample.login;

import android.text.TextUtils;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import login.console.sample.request_utils.DynamicRequestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import rx.Observable;
import rx.exceptions.OnErrorThrowable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginTokenRequest {
    private DynamicRequestAdapter.Builder builder;
    private String username;
    private String password;
    private LoginInformation loginInfo;

    public LoginTokenRequest(String username, String password, LoginInformation loginInfo){
        this.username = username;
        this.password = password;
        this.loginInfo = loginInfo;
        this.builder = new DynamicRequestAdapter.Builder();
    }

    public LoginTokenRequest(String username, String password, LoginInformation loginInfo, DynamicRequestAdapter.Builder builder) {
        this.username = username;
        this.loginInfo = loginInfo;
        this.password = password;
        this.builder = builder;
    }

    public Observable<String> execute() {
        String[] cookieHeader = new String[]{"Cookie"};
        String cookie = TextUtils.join(",", loginInfo.getCookies());;

        String[] cookieBody = new String[]{cookie};
        DynamicRequestAdapter adapter = builder.url(loginInfo.getUrl())
                .queryKeys(getQueryKeys())
                .queryValues(getQueryValues())
                .headerKeys(cookieHeader)
                .headerValues(cookieBody)
                .build();
        return adapter.getResponse().map(this::getToken);
    }

    private String getToken(Response response) {
        Header header = getHeader(response);
        Pattern pattern = Pattern.compile(".*access_token=(\\S*)");
        Matcher matcher = pattern.matcher(header.getValue());
        if(matcher.matches())
            return matcher.group(1);
        throw OnErrorThrowable.from(new Throwable("Could not parse access token from: " + header.getValue()));
    }

    private Header getHeader(Response response) {
        Header header = null;
        try {
            Log.e("stuff", IOUtils.toString(response.getBody().in(), "UTF-8").substring(0, 4000));
            Log.e("stuff", IOUtils.toString(response.getBody().in(), "UTF-8").substring(4001));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.getHeaders().size(); i++) {
            if(response.getHeaders().get(i).getName() != null && response.getHeaders().get(i).getName().equals("Location"))
                header = response.getHeaders().get(i);
        }
        return header;
    }

    private String[] getQueryKeys() {
        return new String[]{"login", "passwd", "PPFT", "'PPSX", "SI", "type", "NewUser", "LoginOptions", "i3", "m1", "m3", "m3", "i12",
                "i17", "i18"};
    }

    private String[] getQueryValues() {
        return new String[]{username, password, loginInfo.getPpft(), "Passpor", "Sign in", "11", "1", "1",
                "36728", "768", "1184", "0", "1", "0", "__Login_Host|1"};
    }
}
