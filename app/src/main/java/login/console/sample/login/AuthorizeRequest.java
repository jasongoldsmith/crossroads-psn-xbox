package login.console.sample.login;

import login.console.sample.login.models.AuthorizeProperties;
import login.console.sample.login.models.LoginPostBody;
import login.console.sample.login.models.LoginResponse;
import login.console.sample.request_utils.CustomInterceptor;
import login.console.sample.request_utils.Header;
import retrofit.RestAdapter;
import rx.Observable;

class AuthorizeRequest {

    private static final String AUTHORIZE_URL = "https://user.auth.xboxlive.com";
    private RestAdapter.Builder builder;
    private String accessToken;

    public AuthorizeRequest(String accessToken) {
        this.accessToken = accessToken;
        this.builder = new RestAdapter.Builder();
    }

    public AuthorizeRequest(String accessToken, RestAdapter.Builder builder) {
        this.accessToken = accessToken;
        this.builder = builder;
    }

    public Observable<LoginResponse> execute() {
        RestAdapter adapter = getBaseBuilder().setEndpoint(AUTHORIZE_URL).build();
        LoginService service = adapter.create(LoginService.class);
        AuthorizeProperties properties = new AuthorizeProperties(accessToken);
        LoginPostBody postBody = new LoginPostBody(properties);
        return service.authorize(postBody);
    }

    public RestAdapter.Builder getBaseBuilder() {
        Header header = new LoginHeader();
        CustomInterceptor interceptor = new CustomInterceptor.Builder()
                .headerKeys(header.keys())
                .headerValues(header.values())
                .build();
        return builder.setRequestInterceptor(interceptor);
    }
}
