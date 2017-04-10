package com.uoa.compsci_702_g12.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uoa.compsci_702_g12.R;
import com.uoa.compsci_702_g12.models.Buddy;
import com.uoa.compsci_702_g12.models.User;

import java.util.List;

/**
 * Created by z on 3/04/17.
 */

public class UsersAdapter extends ArrayAdapter<User> {

    private final Context context;
    private List<User> userList;
    private boolean isBuddyPage;
    private int userId;
    private Constants.REGISTRATION_STATUS status;

    public UsersAdapter(Context context, int userId, List<User> users, Constants.REGISTRATION_STATUS status, boolean isBuddyPage) {
        super(context, R.layout.list_user, users);
        this.context = context;
        this.userId = userId;
        this.userList = users;
        this.isBuddyPage = isBuddyPage;
        this.status = status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user = userList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_user, parent, false);
        RelativeLayout holder = (RelativeLayout) view.findViewById(R.id.user_holder);
        ImageView genderImage = (ImageView) view.findViewById(R.id.gender_indicator);
        ImageView graduationImage = (ImageView) view.findViewById(R.id.grad_indicator);
        final TextView fullName = (TextView) view.findViewById(R.id.username_label);
        TextView ethnicity = (TextView) view.findViewById(R.id.ethnicity_label);
        TextView languages = (TextView) view.findViewById(R.id.languages_label);
        final TextView additionalDetails = (TextView) view.findViewById(R.id.details_label);
        final TextView contactDetails = (TextView) view.findViewById(R.id.contact_details_label);

        fullName.setText(user.getFirstName() + " " + user.getLastName());
        ethnicity.setText(user.getEthnicity());
        languages.setText("Languages: " + user.getLanguages());
        additionalDetails.setText("Additional Details: " + user.getDetails());
        contactDetails.setText("Email: " + user.getEmail() + " | " + "Ph: " + user.getPhoneNumber());

        if(user.getUserId() == userId)
            holder.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));

        if(user.getGender() == 1)
            genderImage.setBackgroundResource(R.drawable.gender_male);
        else
            genderImage.setBackgroundResource(R.drawable.gender_female);

        if(status != Constants.REGISTRATION_STATUS.VOLUNTEER)
            contactDetails.setVisibility(View.GONE);

        if(user.getGradType() == 0)
            graduationImage.setVisibility(View.INVISIBLE);

        if(!(isBuddyPage && status == Constants.REGISTRATION_STATUS.VOLUNTEER))
            contactDetails.setVisibility(View.GONE);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(context, user.getUserId(), fullName.getText().toString(), additionalDetails.getText().toString());
            }
        });

        return view;
    }

    private void showDialog(final Context context, final int buddyId, String title, String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { }
        });

        if(isBuddyPage) {
            builder.setMessage(R.string.remove_user_dialog).setTitle(title);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (status == Constants.REGISTRATION_STATUS.NEWBIE)
                        new RemoveBuddy().execute(buddyId, userId);
                    else if (status == Constants.REGISTRATION_STATUS.VOLUNTEER)
                        new RemoveBuddy().execute(userId, buddyId);
                }
            });
        } else {
            if (status == Constants.REGISTRATION_STATUS.NONE) {
                builder.setMessage(R.string.register_dialog).setTitle(title);
            } else if (status == Constants.REGISTRATION_STATUS.NEWBIE) {
                builder.setMessage("Details: " + description + "\n" + R.string.volunteer_selection_dialog).setTitle(title);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new RegisterBuddy().execute(buddyId, userId);
                    }
                });
            } else if (status == Constants.REGISTRATION_STATUS.VOLUNTEER) {
                builder.setMessage("Details: " + description).setTitle(title);
            }
        }
        builder.create().show();
    }

    private class RegisterBuddy extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... params) {
            return RESTfulService.getInstance().registerBuddy(new Buddy(params[0], params[1]));
        }

        protected void onPostExecute(Boolean success) {
            if(success)
                new AlertDialog.Builder(context).setMessage("Successfully added buddy").setTitle("Success").create().show();
            else
                new AlertDialog.Builder(context).setMessage("Failed to add buddy").setTitle("Error 404").create().show();
        }
    }

    private class RemoveBuddy extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... params) {
            return RESTfulService.getInstance().removeBuddy(params[0], params[1]);
        }

        protected void onPostExecute(Boolean success) {
            if(success) {
                new AlertDialog.Builder(context).setMessage("Successfully removed buddy").setTitle("Success").create().show();
                ((Activity)(context)).finish();
            }
            else
                new AlertDialog.Builder(context).setMessage("Failed to remove buddy").setTitle("Error 404").create().show();
        }
    }
}
