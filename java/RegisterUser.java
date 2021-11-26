package hk.hkucs.partnerd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    TextView banner;
    EditText text_fullName;
    EditText text_age;
    EditText text_email;
    EditText text_password;
    ProgressBar progressBar;

    Button registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        banner = (TextView) findViewById(R.id.banner);
        text_fullName = (EditText)findViewById(R.id.fullName);
        text_age = (EditText)findViewById(R.id.age);
        text_email = (EditText)findViewById(R.id.email);
        text_password = (EditText)findViewById(R.id.password);
        registerUser = (Button)findViewById(R.id.registerUser);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullName = text_fullName.getText().toString();
        String age = text_age.getText().toString().trim();
        String email = text_email.getText().toString().trim();
        String password = text_password.getText().toString().trim();

        if (fullName.isEmpty()){
            text_fullName.requestFocus();
            text_fullName.setError("Full name is required");
            return;
        }
        if (age.isEmpty()){
            text_age.requestFocus();
            text_age.setError("Age is required");
            return;
        }
        if (email.isEmpty()){
            text_email.requestFocus();
            text_email.setError("Email is required");
            return;
        }
        if (password.isEmpty()){
            text_password.requestFocus();
            text_password.setError("Password is required");
            return;
        }

        if (password.length() < 6){
            text_password.requestFocus();
            text_password.setError("Min password length should be 6 characters!");
            return;
        }

//        connect(fullName, age, email, password);
        startActivity(new Intent(this, MainActivity.class));
    }
    public String ReadBufferedHTML(BufferedReader reader,
                                   char [] htmlBuffer, int bufSz) throws java.io.IOException {
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
        ArrayList<String> role = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();
        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            String instructor = rootJSONObj.getString("teacher_1");
            role.add("Instructor");
            name.add(instructor);
            String teaching_assistant1 = rootJSONObj.getString("teacher_2");
            role.add("Teaching Assistant");
            name.add(teaching_assistant1);
            String teaching_assistant2 = rootJSONObj.getString("teacher_3");
            role.add("Teaching Assistant");
            name.add(teaching_assistant2);
            JSONArray jsonArray = rootJSONObj.optJSONArray("students");
            for (int i=0; i<jsonArray.length(); ++i) {
                String studentName = jsonArray.getString(i);
                role.add("Student " + (i+1));
                name.add(studentName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
    public void connect(final String fullName,final String age,final String email, final String password){
        final ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();
        final String url = "https://i.cs.hku.hk/~knchu/registerUser.php"
                + (fullName.isEmpty() ? "" : "?action=insert&fullname="
                + android.net.Uri.encode(fullName, "UTF-8"))
                + (age.isEmpty() ? "" : "&age="
                + android.net.Uri.encode(age, "UTF-8"))
                + (email.isEmpty() ? "" : "&email="
                + android.net.Uri.encode(email, "UTF-8"))
                + (password.isEmpty() ? "" : "&password="
                + android.net.Uri.encode(password, "UTF-8"));
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
                            parse_JSON_String_and_Switch_Activity(jsonString);
//                            startActivity(new Intent(, MainActivity.class));
                        } else {
                            alert( "Error", "Fail to connect" );
                        }
                        pdialog.hide();
                    }
                });
            }
        });
    }
}