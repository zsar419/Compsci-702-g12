package com.uoa.compsci_702_g12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.uoa.compsci_702_g12.utilities.Constants;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;

        Button internationalOptionBtn = (Button) findViewById(R.id.international_students_btn) ;
        internationalOptionBtn.setOnClickListener(initializeIntentOptions(Constants.INTERNATIONAL_OPTION));

        Button researchOptionBtn = (Button) findViewById(R.id.research_btn) ;
        researchOptionBtn.setOnClickListener(initializeIntentOptions(Constants.REASEARCH_OPTION));


        Button tutoringOptionBtn = (Button) findViewById(R.id.tutoring_btn) ;
        tutoringOptionBtn.setOnClickListener(initializeIntentOptions(Constants.TUTORING_OPTION));

        Button viewUsersButton = (Button) findViewById(R.id.show_buddies_btn) ;
        // Read user preferences
        // Show only if your a volunteer
    }

    private View.OnClickListener initializeIntentOptions(final int option) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, VolunteerViewActivity.class);
                intent.putExtra(Constants.OPTION_SELECTION, option);
                startActivity(intent);
            }
        };
    }

}

