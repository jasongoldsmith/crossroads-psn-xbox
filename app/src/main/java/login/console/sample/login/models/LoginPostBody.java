package login.console.sample.login.models;

public class LoginPostBody {
    public String TokenType;
    public String RelyingParty;
    public Properties Properties;

    public LoginPostBody(Properties properties) {
        TokenType = "JWT";
        RelyingParty = "http://xboxlive.com";
        Properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginPostBody that = (LoginPostBody) o;
        if (TokenType != null ? !TokenType.equals(that.TokenType) : that.TokenType != null) return false;
        if (RelyingParty != null ? !RelyingParty.equals(that.RelyingParty) : that.RelyingParty != null) return false;
        return !(Properties != null ? !Properties.equals(that.Properties) : that.Properties != null);

    }

    @Override
    public int hashCode() {
        int result = TokenType != null ? TokenType.hashCode() : 0;
        result = 31 * result + (RelyingParty != null ? RelyingParty.hashCode() : 0);
        result = 31 * result + (Properties != null ? Properties.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginPostBody{" +
                "TokenType='" + TokenType + '\'' +
                ", RelyingParty='" + RelyingParty + '\'' +
                ", Properties=" + Properties +
                '}';
    }
}
