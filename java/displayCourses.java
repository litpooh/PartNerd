package hk.hkucs.partnerd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class displayCourses extends AppCompatActivity  implements View.OnClickListener{
    String user_id;
    String course_id_start;
    String course_id_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_courses);

        Intent intent = this.getIntent();
        user_id = intent.getStringExtra("user_id");
        course_id_start = intent.getStringExtra("course_id_start");
        course_id_end = intent.getStringExtra("course_id_end");

        LinearLayout dynamicView = (LinearLayout) findViewById(R.id.courses);
    }


    @Override
    public void onClick(View v) {

    }
}