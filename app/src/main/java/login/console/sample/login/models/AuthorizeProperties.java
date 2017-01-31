package login.console.sample.login.models;

import java.util.Arrays;

public class AuthorizeProperties implements Properties {
    public String[] UserTokens;
    public String SandboxId;

    public AuthorizeProperties(String token) {
        UserTokens = new String[]{token};
        SandboxId = "RETAIL";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizeProperties that = (AuthorizeProperties) o;
        if (!Arrays.equals(UserTokens, that.UserTokens)) return false;
        return !(SandboxId != null ? !SandboxId.equals(that.SandboxId) : that.SandboxId != null);
    }

    @Override
    public int hashCode() {
        int result = UserTokens != null ? Arrays.hashCode(UserTokens) : 0;
        result = 31 * result + (SandboxId != null ? SandboxId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthorizeProperties{" +
                "UserTokens=" + Arrays.toString(UserTokens) +
                ", SandboxId='" + SandboxId + '\'' +
                '}';
    }
}
