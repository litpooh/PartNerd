package hk.hkucs.partnerd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_signIn = (Button) findViewById(R.id.signIn);
        btn_signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                EditText txt_email = (EditText) findViewById(R.id.email);
                EditText txt_password = (EditText) findViewById(R.id.password);
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();

                if (email.matches("")){
                    Toast.makeText(this, "You did not enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.matches("")){
                    Toast.makeText(this, "You did not enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                connect(email,password);

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
        String user_id = "";
        String user_name = "";
        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            user_id = rootJSONObj.getString("id");
            user_name = rootJSONObj.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (user_id.equals("")){
            Toast.makeText(this, "Account does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getBaseContext(), menu2.class);
        intent.putExtra("user_id",user_id);
        intent.putExtra("user_name",user_name);
        startActivity(intent);
    }
    public void connect(final String email, final String password){
        final ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();
        final String url = "https://i.cs.hku.hk/~knchu/checkUser.php"
                + (email.isEmpty() ? "" : "?action=select&email="
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