package schstat.helpinghands.com.schoolstatsinindia;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class stateview extends ActionBarActivity {
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stateview);
        Intent i = getIntent();
        state = i.getStringExtra("state");
        Log.d("st", state);
    }

    public void clickHandler(View v) {
        Log.d("ss", v.getTag().toString());
        Intent intent = null;
        switch (v.getTag().toString()) {
            case "fact":
                intent = new Intent(this, facilities.class);
                break;
            case "build":
                intent = new Intent(this, building.class);
                break;
            case "manage":
                intent = new Intent(this, management.class);
                break;
            case "cate":
                intent = new Intent(this, categoryschool.class);
                break;
            case "enrol":
                intent = new Intent(this, enrollment.class);
                break;
            case "habitat":
                intent = new Intent(this, habitations.class);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
        }
        intent.putExtra("state", state);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stateview, menu);
        return true;
    }

    String states[] = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakhadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    int stateno;

    //Toast t;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        for (int i = 0; i < states.length; i++) {
            if (states[i].equals(state)) {
                stateno = i + 1;
                Log.d("add",stateno+"");
                break;
            }
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_yes) {
// send yes poll
            Toast.makeText(this,"Sending upvote...", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                public void run() {
                    String url = "https://gs.ng.bluemix.net/trigger/plan/MyGamePlan/event/pollsubmittedpositive?key=f43ba44f-1d4d-4147-8d96-344886c0d453&tenantId=207117a0-a990-4214-9b65-9a72503fc123";
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);
                    try {
                        StringEntity se = new StringEntity("{\"eventSource\": \"pollGame\",\"uid\":\"" + stateno + "\"}");
                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                        httppost.setEntity(se);

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        String temp = EntityUtils.toString(response.getEntity());
                        Log.i("tag", temp);
                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                   // t.makeText(stateview.this,"Sent!", Toast.LENGTH_SHORT).show();
                }
            }).start();

            // Create a new HttpClient and Post Header


            return true;
        } /*else if (id == R.id.action_no) {
// send no poll
              Toast.makeText(this,"Sending downvote...", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                public void run() {
                    String url = "https://gs.ng.bluemix.net/trigger/plan/MyGamePlan/event/pollsubmittednegative?key=f43ba44f-1d4d-4147-8d96-344886c0d453&tenantId=207117a0-a990-4214-9b65-9a72503fc123";
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);
                    int stateno = 1;
                    try {
                        StringEntity se = new StringEntity("{\"eventSource\": \"pollGame\",\"uid\":\"" + stateno + "\"}");
                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                        httppost.setEntity(se);

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        String temp = EntityUtils.toString(response.getEntity());
                        Log.i("tag", temp);
                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                   // t.makeText(stateview.this,"Sent!", Toast.LENGTH_SHORT).show();
                }
            }).start();
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
