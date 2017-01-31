package login.console.sample.login;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import login.console.sample.request_utils.DynamicRequestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import rx.Observable;
import rx.exceptions.OnErrorThrowable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginInfoRequest {
    private static final String URL = "https://login.live.com/oauth20_authorize.srf";

    private DynamicRequestAdapter.Builder restAdapterBuilder;

    public LoginInfoRequest() {
        restAdapterBuilder = new DynamicRequestAdapter.Builder();
    }

    public LoginInfoRequest(DynamicRequestAdapter.Builder restAdapterBuilder) {
        this.restAdapterBuilder = restAdapterBuilder;
    }

    public Observable<LoginInformation> execute() {
        DynamicRequestAdapter adapter = restAdapterBuilder.queryKeys(getQueryKeys())
                .queryValues(getQueryValues())
                .url(URL)
                .build();

        return adapter.getResponse().map(this::parseLoginInfo);
    }

    private LoginInformation parseLoginInfo(Response response) {
//        Pattern pattern = Pattern.compile(".*sFTTag.*value=\"(.*)\"/>.*urlPost: '(\\S*)'.*");
//        Matcher matcher = pattern.matcher(s);
        List<String> cookies = getCookies(response);

        try {
            String s = IOUtils.toString(response.getBody().in(),"UTF-8");

            String urlPost = "";
            String ETTag = "";

            if(Pcre.preg_match("urlPost:'\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", s) > 0)
            {
                urlPost = Pcre.preg_match("urlPost:'\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", s, true);
                urlPost = urlPost.substring(("urlPost:'").length());
            }

            if(Pcre.preg_match("sFTTag:'.*value=\\\"(.*)\\\"\\/>'", s) > 0)
            {
                ETTag = Pcre.preg_match("sFTTag:'.*value=\\\"(.*)\\\"\\/>'", s, true);
                ETTag = ETTag.substring(ETTag.indexOf("value=\"") + ("value=\"").length(), ETTag.indexOf("\"/>"));
            }

            if(!urlPost.isEmpty() && !ETTag.isEmpty())
            {
                return new LoginInformation(ETTag, urlPost, cookies);
            }

            throw OnErrorThrowable.from(new Throwable("Could not parse login info from: " + s));
        } catch (IOException e) {
            e.printStackTrace();
            throw OnErrorThrowable.from(new Throwable("Got exception"));
        }

    }

    private List<String> getCookies(Response response) {
        List<String> cookies = new ArrayList<String>();
        for (int i = 0; i < response.getHeaders().size(); i++) {
            if(response.getHeaders().get(i).getName() != null && response.getHeaders().get(i).getName().equals("Set-Cookie"))
            {
                cookies.add(response.getHeaders().get(i).getValue());
            }
        }
        return cookies;
    }

    private String[] getQueryKeys() {
        return new String[]{"client_id", "redirect_uri", "response_type", "display", "scope", "locale"};
    }

    private String[] getQueryValues() {
        return new String[]{"0000000048093EE3",
                "https://login.live.com/oauth20_desktop.srf",
                "token",
                "touch",
                "service::user.auth.xboxlive.com::MBI_SSL",
                "en"};
    }
}


