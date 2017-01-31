package request_utils;

import retrofit.RequestInterceptor;

import java.util.Arrays;

public class CustomInterceptor implements RequestInterceptor {

    private String[] headerKeys;
    private String[] headerValues;
    private String[] queryKeys;
    private String[] queryValues;

    private CustomInterceptor(Builder builder) {
        this.headerKeys = builder.headerKeys;
        this.headerValues = builder.headerValues;
        this.queryKeys = builder.queryKeys;
        this.queryValues = builder.queryValues;
    }

    public void intercept(RequestFacade request) {
        for (int i = 0; i < headerKeys.length; i++)
            request.addHeader(headerKeys[i], headerValues[i]);
        for (int i = 0; i < queryKeys.length; i++)
            request.addQueryParam(queryKeys[i], queryValues[i]);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomInterceptor that = (CustomInterceptor) o;
        if (!Arrays.equals(headerKeys, that.headerKeys)) return false;
        if (!Arrays.equals(headerValues, that.headerValues)) return false;
        if (!Arrays.equals(queryKeys, that.queryKeys)) return false;
        return Arrays.equals(queryValues, that.queryValues);
    }

    @Override
    public int hashCode() {
        int result = headerKeys != null ? Arrays.hashCode(headerKeys) : 0;
        result = 31 * result + (headerValues != null ? Arrays.hashCode(headerValues) : 0);
        result = 31 * result + (queryKeys != null ? Arrays.hashCode(queryKeys) : 0);
        result = 31 * result + (queryValues != null ? Arrays.hashCode(queryValues) : 0);
        return result;
    }

    public static class Builder {
        private String[] headerKeys;
        private String[] headerValues;
        private String[] queryKeys;
        private String[] queryValues;

        public Builder() {
            headerKeys = new String[0];
            headerValues = new String[0];
            queryKeys = new String[0];
            queryValues = new String[0];
        }

        public CustomInterceptor build() {
            return new CustomInterceptor(this);
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
    }
}
