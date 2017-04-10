package com.uoa.compsci_702_g12;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.uoa.compsci_702_g12.models.User;
import com.uoa.compsci_702_g12.utilities.AppDataManager;
import com.uoa.compsci_702_g12.utilities.Constants;
import com.uoa.compsci_702_g12.utilities.RESTfulService;

import java.util.ArrayList;
import java.util.List;

public class RegisterBuddyActivity extends AppCompatActivity {

    private int selectedCategory, registrationType;
    private Activity activity;
    private ProgressBar progressBar;
    private EditText firstName, lastName, email, phoneNumber, ethnicity, languages, details;
    private Spinner gender;
    private CheckBox gradStatus;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_buddy);
        activity = this;

        selectedCategory = getIntent().getExtras().getInt(Constants.CATEGORY_SELECTION);
        registrationType = getIntent().getExtras().getInt(Constants.BUDDY_REGISTRATION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Constants.getTitle(this, registrationType));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_register);


        // Initialize all input vars
        firstName = (EditText) findViewById(R.id.input_fname);
        lastName = (EditText) findViewById(R.id.input_lname);
        email = (EditText) findViewById(R.id.input_email);
        phoneNumber = (EditText) findViewById(R.id.input_phone_number);
        ethnicity = (EditText) findViewById(R.id.input_ethnicity);
        languages = (EditText) findViewById(R.id.input_languages);
        details = (EditText) findViewById(R.id.input_details);
        gender = (Spinner) findViewById(R.id.input_gender);
        gradStatus = (CheckBox) findViewById(R.id.input_grad_status);

        // Fill required fields list
        final List<EditText> required = new ArrayList<>();
        required.add(firstName);
        required.add(lastName);
        required.add(email);
        required.add(phoneNumber);
        required.add(ethnicity);
        required.add(languages);
        required.add(details);

        register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { validateFields(selectedCategory, registrationType, required); }
        });
    }

    private void validateFields(int selectedOption, int registrationType, List<EditText> required) {
        Boolean valid = true;
        for(EditText et : required) {
            if(et.getText().toString().isEmpty()) {
                et.setError("This is a required field");
                valid = false;
            }
        }
        if(!valid) return;

        User user = new User(selectedOption, registrationType, firstName.getText().toString(),
                lastName.getText().toString(), gender.getSelectedItemPosition()+1,
                email.getText().toString(), phoneNumber.getText().toString(),
                ethnicity.getText().toString(), languages.getText().toString(),
                details.getText().toString(), gradStatus.isChecked()?1:0);

        new RegisterUser().execute(user);
    }

    private class RegisterUser extends AsyncTask<User, Void, Integer> {
        protected void onPreExecute() {
            register.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(User... params) {
            return RESTfulService.getInstance().createUser(params[0]);
        }

        protected void onPostExecute(Integer returnedId) {
            register.setEnabled(true);
            progressBar.setVisibility(View.GONE);
            if(returnedId != 0) {
                AppDataManager.getInstance(activity).saveUserId(returnedId);
                AppDataManager.getInstance(activity).saveRegisteredBuddyForCategory(selectedCategory, registrationType);
                activity.setResult(RESULT_OK);
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
