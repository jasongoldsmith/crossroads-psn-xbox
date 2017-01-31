package request_utils;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

import java.util.Map;

public interface DynamicService {
    @GET("/")
    Observable<String> getString();

    @GET("/")
    Observable<Response> getResponse();
}
