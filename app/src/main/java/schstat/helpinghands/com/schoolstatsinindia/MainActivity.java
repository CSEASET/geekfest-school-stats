package schstat.helpinghands.com.schoolstatsinindia;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
int alpha;
    Handler handler = new Handler();
    int delay = 200;
    Runnable runner = new Runnable() {
        public void run() {
            //Log.d("ss","ss");
            setStates();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                states=null;
               // Toast.makeText(MainActivity.this, "" + position,
                  //      Toast.LENGTH_SHORT).show();
                alpha = position;
                showFragment(position);
            }
        });
    }

    void showFragment(int pos){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        statelistFragment hello = new statelistFragment();
        fragmentTransaction.add(R.id.fragment_container, hello, "HELLO");
        fragmentTransaction.addToBackStack("HELLO");
        fragmentTransaction.commit();
        readfile(pos);
        //setStates();
        handler.postDelayed(runner,delay);
    }
    void setStates() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.subject_list);
        int count = states.length;
        Log.d("s",count+"");
        TextView t;
        float scale = getResources().getDisplayMetrics().density;
        for (int i = 0; i < count; i++) {
            t = (TextView) ll.getChildAt(i+1);
            t.setTag(states[i]);
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) t.getLayoutParams();
            p.height = (int) (60 * scale + 0.5f);
            t.setLayoutParams(p);
            t.setText(states[i]);
        }
    }

    String states[];
    String state[] = {"a","b","c","d","g","h","j","k","l","m","n","o","p","r","s","t","u","w"};
    void readfile(int pos){
String file = state[pos];
        Log.d("yo",file);
        BufferedReader reader = null;
    List<String> lines = new ArrayList<String>();
        try {
            reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(getResources().getIdentifier("raw/"+file, "raw", getPackageName()))));

            String line = "";
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
states = lines.toArray(new String[lines.size()]);
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        Log.d("yo",states.length+"");
    }
    public void closeFragment(View v){
        int id = v.getId();
        if(id != R.id.stateBtn) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            statelistFragment hello = (statelistFragment) fragmentManager.findFragmentByTag("HELLO");
            fragmentTransaction.remove(hello);
            fragmentTransaction.commit();
        }
        Log.d("yo", "yeye" );
    }

    public void clickHandler(View v){
String tag = v.getTag().toString();
        Log.d("yo", "yeye" +tag );
        Intent intent = new Intent(this, stateview.class);
        intent.putExtra("state", v.getTag().toString());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_poll) {
            Intent intent = new Intent(this, leaderboard.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed(); // not calling the default onBackPressed
            //android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(1);
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to leave", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }
}
