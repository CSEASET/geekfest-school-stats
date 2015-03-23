package schstat.helpinghands.com.schoolstatsinindia;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;


public class splash extends Activity {

    Handler handler = new Handler();
    int delay = 2000;
    Runnable runner = new Runnable() {
        public void run() {
            showYearList();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ProgressBar spinner;
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setIndeterminate(true);

        handler.postDelayed(runner, delay);
    }

    Intent intent;


    void startIntent() {
        startActivity(intent);
    }

    public void clickHandler(View view) {
        ;//showYearList();
    }

    protected void showYearList() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void onBackPressed() {
        return;
    }
}