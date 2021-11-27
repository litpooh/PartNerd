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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mailbox extends ListActivity {
    String user_id;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> requestid = new ArrayList<String>();
    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        user_id = intent.getStringExtra("user_id");
        connect(user_id.toString());
    }

    public void showRequest(ArrayList<String> name, ArrayList<String> requestid) {
        for (int i = 0; i < name.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", requestid.get(i));
            map.put("Requesters", name.get(i));
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_mailbox, new String[]{"Requesters"}, new int[]{R.id.requester}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                View acceptView = view.findViewById(R.id.btn_accept);
                acceptView.setTag("accept" + position);
                acceptView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), MailboxAccept.class);
                        intent.putExtra("request_id", requestid.get(position));
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                    }
                });
                return view;
            }
        };
        setListAdapter(adapter);
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
            JSONArray jsonArray = rootJSONObj.optJSONArray("requesters");
            for (int i=0; i<jsonArray.length(); ++i) {
                JSONArray requestInfo = jsonArray.getJSONArray(i);
                name.add(requestInfo.getString(1));
                requestid.add(requestInfo.getString(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showRequest(name, requestid);
    }
    public void connect(final String id) {
        final ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();
        final String url = "https://i.cs.hku.hk/~kcjleung/mailbox.php?id="
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