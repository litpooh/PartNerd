package hk.hkucs.partnerd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class menu2 extends AppCompatActivity implements View.OnClickListener{
    String user_id;
    String user_name;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        Intent intent = this.getIntent();
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");

        TextView txt_welcome = (TextView) findViewById(R.id.welcome);
        txt_welcome.setText("Welcome, "+user_name);
        Toast.makeText(this, "Welcome,"+user_name+" ,user id :" + user_id, Toast.LENGTH_SHORT).show();

        Button btn_profile = (Button) findViewById(R.id.profile);
        btn_profile.setOnClickListener(this);

        Button btn_courses = (Button) findViewById(R.id.courseList);
        btn_courses.setOnClickListener(this);

        Button btn_mailbox = (Button) findViewById(R.id.mailBox);
        btn_mailbox.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile:
                Toast.makeText(this, "update user profile", Toast.LENGTH_SHORT).show();
                return;
            case R.id.courseList:
                Intent intent = new Intent(getBaseContext(), Courses.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            case R.id.mailBox:
                Toast.makeText(this, "open mailbox", Toast.LENGTH_SHORT).show();
                return;

        }


    }
}