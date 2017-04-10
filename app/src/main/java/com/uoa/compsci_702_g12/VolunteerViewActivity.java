package com.uoa.compsci_702_g12;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.uoa.compsci_702_g12.models.User;
import com.uoa.compsci_702_g12.utilities.AppDataManager;
import com.uoa.compsci_702_g12.utilities.Constants;
import com.uoa.compsci_702_g12.utilities.RESTfulService;
import com.uoa.compsci_702_g12.utilities.UsersAdapter;

import java.util.List;

import static com.uoa.compsci_702_g12.utilities.Constants.REFRESH_VIEW;

public class VolunteerViewActivity extends AppCompatActivity {

    private int selectedCategory;
    private int userId;
    private Context context;
    private ListView listView;
    private ProgressBar progressBar;
    private Constants.REGISTRATION_STATUS status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_view);
        context = this;

        selectedCategory = getIntent().getExtras().getInt(Constants.CATEGORY_SELECTION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Constants.getTitle(this, selectedCategory));

        listView = (ListView) findViewById(R.id.option_volunteer_list);

        FloatingActionButton registerVolunteer = (FloatingActionButton) findViewById(R.id.register_volunteer_btn);
        registerVolunteer.setOnClickListener(register(Constants.REGISTER_VOLUNTEER, selectedCategory));

        FloatingActionButton registerNewbie = (FloatingActionButton) findViewById(R.id.register_newbie_btn);
        registerNewbie.setOnClickListener(register(Constants.REGISTER_NEWBIE, selectedCategory));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_volunteer_view);

        status = AppDataManager.getInstance(this).getStatus(selectedCategory);

        userId = AppDataManager.getInstance(this).getUserId();

        loadVolunteers(status != Constants.REGISTRATION_STATUS.NONE);
    }

    private View.OnClickListener register(final int registrationType, final int selectedOption) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterBuddyActivity.class);
                intent.putExtra(Constants.BUDDY_REGISTRATION, registrationType);
                intent.putExtra(Constants.CATEGORY_SELECTION, selectedOption);
                startActivityForResult(intent, REFRESH_VIEW);
            }
        };
    }

    private void loadVolunteers(boolean userIsRegistered) {
        if(userIsRegistered) {
            FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
            floatingActionMenu.setVisibility(View.INVISIBLE);
        }
        new LoadVolunteers().execute();
    }

    private class LoadVolunteers extends AsyncTask<Void, Void, List<User>> {
        protected void onPreExecute() { progressBar.setVisibility(View.VISIBLE); }

        @Override
        protected List<User> doInBackground(Void... params) {
            return RESTfulService.getInstance().getVolunteersForCategory(selectedCategory);
        }

        protected void onPostExecute(List<User> users) {
            listView.setAdapter(new UsersAdapter(context, userId, users, status, false));
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            status = AppDataManager.getInstance(this).getStatus(selectedCategory);
            userId = AppDataManager.getInstance(this).getUserId();
            loadVolunteers(true);
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

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
