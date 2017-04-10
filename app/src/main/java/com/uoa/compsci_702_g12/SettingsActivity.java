package com.uoa.compsci_702_g12;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.uoa.compsci_702_g12.utilities.AppDataManager;
import com.uoa.compsci_702_g12.utilities.RESTfulService;


public class SettingsActivity extends AppCompatActivity {

    private Activity activity;
    private Button removeButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        activity = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_settings);

        // Get userid from preferences
        final int userID = AppDataManager.getInstance(this).getUserId();
        removeButton = (Button) findViewById(R.id.remove_user_btn);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { new RemoveUser().execute(userID); }
        });
    }

    private class RemoveUser extends AsyncTask<Integer, Void, Boolean> {
        protected void onPreExecute() {
            removeButton.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            return RESTfulService.getInstance().removeUser(params[0]);
        }

        protected void onPostExecute(Boolean success) {
            removeButton.setEnabled(true);
            progressBar.setVisibility(View.GONE);
            if(success) {
                AppDataManager.getInstance(activity).deleteUserData();
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
