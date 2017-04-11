package com.uoa.compsci_702_g12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.uoa.compsci_702_g12.utilities.AppDataManager;
import com.uoa.compsci_702_g12.utilities.Constants;

import static com.uoa.compsci_702_g12.utilities.Constants.REFRESH_VIEW;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button registeredAsVolunteersButton, registeredAsNewbieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;

        Button internationalOptionBtn = (Button) findViewById(R.id.international_students_btn) ;
        internationalOptionBtn.setOnClickListener(initializeActivity(false, Constants.INTERNATIONAL_CATEGORY));

        Button researchOptionBtn = (Button) findViewById(R.id.research_btn) ;
        researchOptionBtn.setOnClickListener(initializeActivity(false, Constants.RESEARCH_CATEGORY));


        Button tutoringOptionBtn = (Button) findViewById(R.id.tutoring_btn) ;
        tutoringOptionBtn.setOnClickListener(initializeActivity(false, Constants.TUTORING_CATEGORY));


        registeredAsVolunteersButton = (Button) findViewById(R.id.show_newbies_btn);
        registeredAsVolunteersButton.setOnClickListener(initializeActivity(true, Constants.REGISTER_VOLUNTEER));

        registeredAsNewbieButton = (Button) findViewById(R.id.show_volunteers_btn);
        registeredAsNewbieButton.setOnClickListener(initializeActivity(true, Constants.REGISTER_NEWBIE));

        displayButtonsForRegisteredTypes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivityForResult(intent, REFRESH_VIEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View.OnClickListener initializeActivity(final boolean viewingUsers, final int option) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                if(viewingUsers)
                    intent = new Intent(context, BuddyViewActivity.class);
                else
                    intent = new Intent(context, VolunteerViewActivity.class);
                intent.putExtra(Constants.CATEGORY_SELECTION, option);
                startActivityForResult(intent, REFRESH_VIEW);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            displayButtonsForRegisteredTypes();
        }
    }

    private void displayButtonsForRegisteredTypes() {
        if(AppDataManager.getInstance(this).getIsNewbie())
            registeredAsNewbieButton.setVisibility(View.VISIBLE);
        else
            registeredAsNewbieButton.setVisibility(View.INVISIBLE);

        if(AppDataManager.getInstance(this).getIsVolunteer())
            registeredAsVolunteersButton.setVisibility(View.VISIBLE);
        else
            registeredAsVolunteersButton.setVisibility(View.INVISIBLE);
    }

}

