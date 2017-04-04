package com.uoa.compsci_702_g12.utilities;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uoa.compsci_702_g12.R;
import com.uoa.compsci_702_g12.models.User;

import java.util.List;

/**
 * Created by z on 3/04/17.
 */

public class UsersAdapter extends ArrayAdapter<User> {

    private final Context context;
    private List<User> userList;

    public UsersAdapter(Context context, List<User> users) {
        super(context, R.layout.list_user, users);
        this.context = context;
        this.userList = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_user, parent, false);
        ImageView genderImage = (ImageView) view.findViewById(R.id.gender_indicator);
        TextView fullName = (TextView) view.findViewById(R.id.username_label);
        ImageView graduationImage = (ImageView) view.findViewById(R.id.grad_indicator);

        // TextView textView = (TextView) view.findViewById(R.id.label);

        for(User user : userList) {
            fullName.setText(user.getFirstName() + " " + user.getLastName());
            /*
            if(user.getGender() == 0)
                genderImage.setBackgroundResource();
            else
                genderImage.setBackgroundResource();
            */


            if(user.getGradType() == 0)
                graduationImage.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
