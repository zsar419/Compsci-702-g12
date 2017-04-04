package com.uoa.compsci_702_g12;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.uoa.compsci_702_g12.models.User;
import com.uoa.compsci_702_g12.utilities.Constants;
import com.uoa.compsci_702_g12.utilities.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

public class VolunteerViewActivity extends AppCompatActivity {

    Context context;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_view);
        context = this;

        int selectedOption = getIntent().getExtras().getInt(Constants.OPTION_SELECTION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Constants.getTitle(this, selectedOption));
        listView = (ListView) findViewById(R.id.option_volunteer_list);

        FloatingActionButton registerVolunteer = (FloatingActionButton) findViewById(R.id.register_volunteer_btn);
        registerVolunteer.setOnClickListener(register(Constants.REGISTER_VOLUNTEER, selectedOption));

        FloatingActionButton registerNewbie = (FloatingActionButton) findViewById(R.id.register_newbie_btn);
        registerNewbie.setOnClickListener(register(Constants.REGISTER_NEWBIE, selectedOption));

        // Load view of volunteers which have registered for that option type
        new LoadVolunteers().execute();
    }

    private View.OnClickListener register(final int registrationType, final int selectedOption) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterBuddyActivity.class);
                intent.putExtra(Constants.BUDDY_REGISTRATION, registrationType);
                intent.putExtra(Constants.OPTION_SELECTION, selectedOption);
                startActivity(intent);
            }
        };
    }

    private class LoadVolunteers extends AsyncTask<Void, Void, List<User>> {
        protected void onPreExecute() {
            // Set refreshing state
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            // Get users from internet - this is dummy user
            List<User> retUsers = new ArrayList<>();
            User test = new User(1,1,"f","l",0,"a", "b","c","d","e",1);
            retUsers.add(test);
            return retUsers;
        }

        protected void onPostExecute(List<User> users) {
            listView.setAdapter(new UsersAdapter(context, users));
            // Stop refreshing state
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
