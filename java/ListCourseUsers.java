package hk.hkucs.partnerd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class ListCourseUsers extends AppCompatActivity {
    String user_id;
    String courseId;
    String courseCode;
    String courseName;
    ArrayList<String> userIds = new ArrayList<String>();
    ArrayList<String> userNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course_users);

        Intent intent = this.getIntent();
        user_id = intent.getStringExtra("user_id");
        courseId = intent.getStringExtra("courseId");
        courseName = intent.getStringExtra("courseName");
        courseCode = intent.getStringExtra("courseCode");
        ArrayList<String> userIds = intent.getStringArrayListExtra("userIds");
        ArrayList<String> userNames = intent.getStringArrayListExtra("userNames");

        Display display = getWindowManager().getDefaultDisplay();
        double width = display.getWidth() / 1.5;
        double ratio = ((float) (width)) / 300.0;
        int height = (int) (ratio * 50);

        LinearLayout dynamicView = (LinearLayout) findViewById(R.id.otherusers);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) width, height);
        params.topMargin = 10;
        params.gravity = Gravity.CENTER;

        for (int i = 0; i < userIds.size(); i++) {
            Button btn = new Button(this);
            btn.setId(i+1);
            btn.setText(userNames.get(i));
            btn.setLayoutParams(params);
            btn.setBackgroundColor(Color.parseColor("#0ED689"));
            btn.setTextColor(Color.parseColor("#ffffff"));
            dynamicView.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Clicked" + btn.getId(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        dynamicView.setOrientation(LinearLayout.VERTICAL);
    }


    public void onClick(View v) {

    }

}