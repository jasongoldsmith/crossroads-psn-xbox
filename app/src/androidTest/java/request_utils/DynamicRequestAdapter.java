package request_utils;

import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.Observable;

public class DynamicRequestAdapter {

    private RestAdapter.Builder restAdapterBuilder;
    private String[] headerKeys;
    private String[] headerValues;
    private String[] queryKeys;
    private String[] queryValues;
    private String url;

    private DynamicRequestAdapter(Builder builder) {
        this.restAdapterBuilder = builder.restAdapterBuilder;
        this.headerKeys = builder.headerKeys;
        this.headerValues = builder.headerValues;
        this.queryKeys = builder.queryKeys;
        this.queryValues = builder.queryValues;
        this.url = builder.url;
    }

    public Observable<String> getString() {
        RestAdapter.Builder builder = getBaseBuilder()
                .setConverter(new StringConverter());
        return createService(builder).getString();
    }

    public Observable<Response> getResponse() {
        return createService(getBaseBuilder()).getResponse();
    }

    private RestAdapter.Builder getBaseBuilder() {
        return restAdapterBuilder.setEndpoint(url)
                .setRequestInterceptor(getInterceptor());
    }

    private DynamicService createService(RestAdapter.Builder builder) {
        return builder.build().create(DynamicService.class);
    }

    private CustomInterceptor getInterceptor() {
        return new CustomInterceptor.Builder()
                .headerKeys(headerKeys)
                .headerValues(headerValues)
                .queryKeys(queryKeys)
                .queryValues(queryValues)
                .build();
    }

    public static class Builder {
        private RestAdapter.Builder restAdapterBuilder;
        private String[] headerKeys;
        private String[] headerValues;
        private String[] queryKeys;
        private String[] queryValues;
        private String url;

        public Builder() {
            restAdapterBuilder = new RestAdapter.Builder();
            headerKeys = new String[0];
            headerValues = new String[0];
            queryKeys = new String[0];
            queryValues = new String[0];
        }

        public DynamicRequestAdapter build() {
            return new DynamicRequestAdapter(this);
        }

        public Builder restAdapterBuilder(RestAdapter.Builder restAdapterBuilder) {
            this.restAdapterBuilder = restAdapterBuilder;
            return this;
        }

        public Builder headerKeys(String[] headerKeys) {
            this.headerKeys = headerKeys;
            return this;
        }

        public Builder headerValues(String[] headerValues) {
            this.headerValues = headerValues;
            return this;
        }

        public Builder queryKeys(String[] queryKeys) {
            this.queryKeys = queryKeys;
            return this;
        }

        public Builder queryValues(String[] queryValues) {
            this.queryValues = queryValues;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }
    }
}
