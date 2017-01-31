package login.console.sample.login;


import login.console.sample.request_utils.Header;

class LoginHeader implements Header {

    @Override
    public String[] keys() {
        return new String[]{"Content-Type"};
    }

    @Override
    public String[] values() {
        return new String[]{"application/json"};
    }
}
