package login.console.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //examples on logging in
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //PSNUtility.PSNLogin(MainActivity.this, "tina@crossroadsapp.co", "Traveler1990");
                Intent intent = new Intent(MainActivity.this, PSNLoginActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.message).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PSNUtility.PSNMessage(MainActivity.this, "CrossroadsJason", "testing from the phone");
            }
        });

        findViewById(R.id.group_message).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PSNUtility.PSNMessageGroup(MainActivity.this, "CrossroadsJason,tinacplays", "Group msg from the phone, lol");
            }
        });

        findViewById(R.id.xbox_login).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, XBLiveLoginActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.xbox_msg).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
/*
                XBoxUtility xbox = new XBoxUtility(MainActivity.this, "tina@crossroadsapp.co", "Traveler1990");
                xbox.callback = new XBoxInterface() {
                    @Override
                    public void getAccessToken() {
                        XBoxUtility xbox = new XBoxUtility(MainActivity.this, "tina@crossroadsapp.co", "Traveler1990");
                        xbox.callback = new XBoxInterface() {
                            @Override
                            public void getAccessToken() {
                                xbox.sendMessages("CrossroadsJason", "testing from API");
                            }
                        };
                        xbox.init();
                    }
                };
                xbox.init();
                */

                XBoxLiveUtility.sendMessages(MainActivity.this, "CrossroadsJason", "testing from API");

            }
        });

        findViewById(R.id.xbox_group_msg).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
/*
                XBoxUtility xbox = new XBoxUtility(MainActivity.this, "tina@crossroadsapp.co", "Traveler1990");
                xbox.callback = new XBoxInterface() {
                    @Override
                    public void getAccessToken() {
                        XBoxUtility xbox = new XBoxUtility(MainActivity.this, "tina@crossroadsapp.co", "Traveler1990");
                        xbox.callback = new XBoxInterface() {
                            @Override
                            public void getAccessToken() {
                                xbox.sendMessages("CrossroadsJason,CrossroadsApp", "testing group from API");
                            }
                        };
                        xbox.init();
                    }
                };
                xbox.init();

*/

                XBoxLiveUtility.sendMessages(MainActivity.this, "CrossroadsJason,CrossroadsApp", "testing group from API");
            }
        });

    }


    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

}
