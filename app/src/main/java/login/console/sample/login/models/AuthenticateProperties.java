package login.console.sample.login.models;

public class AuthenticateProperties implements Properties {
    public String AuthMethod;
    public String SiteName;
    public String RpsTicket;

    public AuthenticateProperties(String accessToken) {
        AuthMethod = "RPS";
        SiteName = "user.auth.xboxlive.com";
        RpsTicket = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticateProperties that = (AuthenticateProperties) o;
        if (AuthMethod != null ? !AuthMethod.equals(that.AuthMethod) : that.AuthMethod != null) return false;
        if (SiteName != null ? !SiteName.equals(that.SiteName) : that.SiteName != null) return false;
        return !(RpsTicket != null ? !RpsTicket.equals(that.RpsTicket) : that.RpsTicket != null);

    }

    @Override
    public int hashCode() {
        int result = AuthMethod != null ? AuthMethod.hashCode() : 0;
        result = 31 * result + (SiteName != null ? SiteName.hashCode() : 0);
        result = 31 * result + (RpsTicket != null ? RpsTicket.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthenticateProperties{" +
                "AuthMethod='" + AuthMethod + '\'' +
                ", SiteName='" + SiteName + '\'' +
                ", RpsTicket='" + RpsTicket + '\'' +
                '}';
    }
}
