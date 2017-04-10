package com.uoa.compsci_702_g12;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.uoa.compsci_702_g12.models.User;
import com.uoa.compsci_702_g12.utilities.AppDataManager;
import com.uoa.compsci_702_g12.utilities.Constants;
import com.uoa.compsci_702_g12.utilities.RESTfulService;
import com.uoa.compsci_702_g12.utilities.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

public class BuddyViewActivity extends AppCompatActivity {

    private Context context;
    private ListView listView;
    private Constants.REGISTRATION_STATUS status;
    private ProgressBar progressBar;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_view);
        context = this;

        int selectedOption = getIntent().getExtras().getInt(Constants.CATEGORY_SELECTION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Constants.getBuddyViewTitle(this, selectedOption));
        listView = (ListView) findViewById(R.id.buddy_list);

        // Set status from preferences
        status = getStatusFromRegisteredType(selectedOption);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_buddy);

        userId = AppDataManager.getInstance(this).getUserId();
        new LoadBuddies().execute(status);
    }

    private class LoadBuddies extends AsyncTask<Constants.REGISTRATION_STATUS, Void, List<User>> {
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<User> doInBackground(Constants.REGISTRATION_STATUS... params) {
            if(params[0] == Constants.REGISTRATION_STATUS.VOLUNTEER)
                return RESTfulService.getInstance().getNewbies(userId);
            else if (params[0] == Constants.REGISTRATION_STATUS.NEWBIE)
                return RESTfulService.getInstance().getVolunteers(userId);
            return new ArrayList<>();
        }

        protected void onPostExecute(List<User> users) {
            listView.setAdapter(new UsersAdapter(context, userId, users, status, true));
            progressBar.setVisibility(View.GONE);
        }
    }

    private Constants.REGISTRATION_STATUS getStatusFromRegisteredType(int type) {
        if(type == Constants.REGISTER_NEWBIE)
            return Constants.REGISTRATION_STATUS.NEWBIE;
        else if(type == Constants.REGISTER_VOLUNTEER)
            return Constants.REGISTRATION_STATUS.VOLUNTEER;
        return Constants.REGISTRATION_STATUS.NONE;
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

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
