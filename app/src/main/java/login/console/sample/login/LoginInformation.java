package login.console.sample.login;

import java.util.ArrayList;
import java.util.List;

public class LoginInformation {
    private String ppft;
    private String url;
    private List<String> cookies = new ArrayList<String>();
    public LoginInformation(String ppft, String url, List<String> cookies) {
        this.ppft = ppft;
        this.url = url;
        this.cookies = cookies;
    }

    public String getPpft() {
        return ppft;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getCookies() {
        return cookies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginInformation that = (LoginInformation) o;
        if (ppft != null ? !ppft.equals(that.ppft) : that.ppft != null) return false;
        return !(url != null ? !url.equals(that.url) : that.url != null);
    }

    @Override
    public int hashCode() {
        int result = ppft != null ? ppft.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginInformation{" +
                "ppft='" + ppft + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
