package login.console.sample.login;

import login.console.sample.login.models.LoginPostBody;
import login.console.sample.login.models.LoginResponse;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

interface LoginService {
    @POST("/user/authenticate")
    Observable<LoginResponse> authorize(@Body LoginPostBody body);

    @POST("/xsts/authorize")
    Observable<LoginResponse> authenticate(@Body LoginPostBody body);
}
