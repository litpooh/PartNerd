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

public class EnrollOrBrowse extends AppCompatActivity implements View.OnClickListener{
    String user_id;
    String courseName;
    String courseId;
    String courseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_or_browse);

        Intent intent = this.getIntent();
        user_id = intent.getStringExtra("user_id");
        courseId = intent.getStringExtra("courseId");
        courseCode = intent.getStringExtra("courseCode");
        courseName = intent.getStringExtra("courseName");

        TextView txt_courseName = (TextView) findViewById(R.id.courseName);
        txt_courseName.setText(courseCode + " " + courseName);

        Button btn_enroll = (Button) findViewById(R.id.enroll);
        btn_enroll.setOnClickListener(this);

        Button btn_browse = (Button) findViewById(R.id.browse);
        btn_browse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse:
                Toast.makeText(this, "Browse", Toast.LENGTH_SHORT).show();
                connect(courseId, "browse");

            case R.id.enroll:
                Toast.makeText(this, "Enroll", Toast.LENGTH_SHORT).show();
                connect(courseId, "enroll");
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
        ArrayList<String> userIds = new ArrayList<String>();
        ArrayList<String> userNames = new ArrayList<String>();
        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            JSONArray userIdArray = rootJSONObj.optJSONArray("user_id");
            JSONArray userNameArray = rootJSONObj.optJSONArray("user_name");
            for (int i=0; i<userIdArray.length(); ++i) {
                String user_id = userIdArray.getString(i);
                String user_name = userNameArray.getString(i);
                Log.i(user_id, user_name);
                userIds.add(user_id);
                userNames.add(user_name);
                Log.i(userIds.get(i), userNames.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getBaseContext(), ListCourseUsers.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("courseId", courseId);
        intent.putExtra("courseId", courseCode);
        intent.putExtra("courseName", courseName);
        intent.putStringArrayListExtra("userIds", userIds);
        intent.putStringArrayListExtra("userNames", userNames);
        startActivity(intent);
    }
    public void connect(final String courseId, final String perform){
        final ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();
        final String url = "https://i.cs.hku.hk/~wkng2/project/ListCourseUsers.php"
                + (courseId.isEmpty() ? "" : "?action=select&course_id="
                + android.net.Uri.encode(courseId, "UTF-8"));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                pdialog.setMessage("Before ...");
                pdialog.show();
                final String jsonString = getJsonPage(url);
                if (jsonString.equals("Fail to get other users"))
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