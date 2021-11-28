package hk.hkucs.partnerd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailboxAccept extends AppCompatActivity {
    String request_id;
    String user_id;
    static String name;
    static String nationality;
    static String language;
    static String gpa;
    static String phone;
    static String email;
    static Boolean decline_done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox_accept);
        Intent intent = this.getIntent();
        request_id = intent.getStringExtra("request_id");
        user_id = intent.getStringExtra("user_id");
        connect(request_id);
        CookieHandler.setDefault(new CookieManager());
    }

    public void showInfo() {
        TextView nameField = (TextView) findViewById(R.id.name);
        TextView NationField = (TextView) findViewById(R.id.nationality);
        TextView LangField = (TextView) findViewById(R.id.language);
        TextView gpaField = (TextView) findViewById(R.id.gpa);
        TextView phoneField = (TextView) findViewById(R.id.phone);
        TextView emailField = (TextView) findViewById(R.id.email);
        phoneField.setText("Phone Number: " + phone);
        emailField.setText("Email Address: " + email);
        nameField.setText("Name: " + name);
        NationField.setText("Nationality: " + nationality);
        LangField.setText("Language: " + language);
        gpaField.setText("GPA: " + gpa);
    }

    public void btn_accept(View v) {
        if (v.getId() == R.id.btn_accept) {
            TextView phoneField = (TextView) findViewById(R.id.phone);
            TextView emailField = (TextView) findViewById(R.id.email);
            phoneField.setVisibility(View.VISIBLE);
            emailField.setVisibility(View.VISIBLE);

            Button accept_btn = (Button) findViewById(R.id.btn_accept);
            Button decline_btn = (Button) findViewById(R.id.btn_decline);
            TextView query = (TextView) findViewById(R.id.query);
            accept_btn.setVisibility(View.GONE);
            decline_btn.setVisibility(View.GONE);
            query.setText("Request Accepted!");

            TextView reminder = (TextView) findViewById(R.id.reminder);
            reminder.setText("Contact Your groupmate as soon as possible! :) You can also screenshot this page.");
        }
    }

    public void btn_decline(View v) {
        if (v.getId() == R.id.btn_decline) {
            decline(request_id);
            if (decline_done) {
                finish();
            }
        }
    }

    public String ReadBufferedHTML(BufferedReader reader, char [] htmlBuffer, int bufSz) throws java.io.IOException {
        htmlBuffer[0] = '\0';
        int offset = 0;
        do {
            int cnt = reader.read(htmlBuffer, offset, bufSz - offset);
            if (cnt > 0) {
                offset += cnt;
            } else {
                break;
            }
        } while (true);
        return new String(htmlBuffer);
    }
    public String getJsonPage(String url) {
        HttpURLConnection conn_object = null;
        final int HTML_BUFFER_SIZE = 2*1024*1024;
        char htmlBuffer[] = new char[HTML_BUFFER_SIZE];
        try {
            URL url_object = new URL(url);
            conn_object = (HttpURLConnection) url_object.openConnection();
            conn_object.setInstanceFollowRedirects(true);
            BufferedReader reader_list = new BufferedReader
                    (new InputStreamReader(conn_object.getInputStream()));
            String HTMLSource = ReadBufferedHTML(reader_list, htmlBuffer,
                    HTML_BUFFER_SIZE);
            reader_list.close();
            return HTMLSource;
        } catch (Exception e) {
            Log.e("MYAPP", "exception", e);
            return "Fail to login";
        } finally {
// When HttpClient instance is no longer needed,
// shut down the connection manager to ensure
// immediate deallocation of all system resources
            if (conn_object != null) {
                conn_object.disconnect();
            }
        }
    }
    protected void alert(String title, String mymessage){
        new AlertDialog.Builder(this)
                .setMessage(mymessage)
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton){}
                        }
                )
                .show();}

    public void parse_JSON_String_and_Switch_Activity(String JSONString) {
        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            name = rootJSONObj.getString("name");
            nationality = rootJSONObj.getString("nationality");
            language = rootJSONObj.getString("language");
            gpa = rootJSONObj.getString("gpa");
            phone = rootJSONObj.getString("phone");
            email = rootJSONObj.getString("email");
            showInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void connect(final String id) {
        final ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();
        final String url = "https://i.cs.hku.hk/~kcjleung/requesterInfo.php?id="
                + android.net.Uri.encode(id, "UTF-8");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                pdialog.setMessage("Before ...");
                pdialog.show();
                final String jsonString = getJsonPage(url);
                final String jsonString2 = getJsonPage(url);
                try {
                    Thread.sleep(500);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonString.equals("Fail to login"))
                    success = false;
                final boolean finalSuccess = success;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalSuccess) {
                            if (jsonString.isEmpty()) {
                                parse_JSON_String_and_Switch_Activity(jsonString);
                            }
                            else {
                                parse_JSON_String_and_Switch_Activity(jsonString2);
                            }
                        } else {
                            alert( "Error", "Fail to connect" );
                        }
                        pdialog.hide();
                    }
                });
            }
        });
    }
    public void decline(final String id) {
        final ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();
        final String url = "https://i.cs.hku.hk/~kcjleung/declineRequest.php?id="
                + android.net.Uri.encode(id, "UTF-8");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                pdialog.setMessage("Before ...");
                pdialog.show();
                final String jsonString = getJsonPage(url);
                if (jsonString.equals("Fail to login"))
                    success = false;
                final boolean finalSuccess = success;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalSuccess) {
                            decline_done = true;
                        } else {
                            alert( "Error", "Fail to connect" );
                        }
                        pdialog.hide();
                    }
                });

                finish();
            }
        });
    }

}