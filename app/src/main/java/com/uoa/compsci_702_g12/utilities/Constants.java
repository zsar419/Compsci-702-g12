package com.uoa.compsci_702_g12.utilities;

import android.content.Context;

import com.uoa.compsci_702_g12.R;

/**
 * Created by z on 31/03/17.
 */

public class Constants {

    public enum REGISTRATION_STATUS { NONE, NEWBIE, VOLUNTEER }

    public static String DATABASE_API_URL = "http://uoabuddyapp.azurewebsites.net/api/";

    public static final String CATEGORY_SELECTION = "category_selection";
    public static final int INTERNATIONAL_CATEGORY = 1;
    public static final int RESEARCH_CATEGORY = 2;
    public static final int TUTORING_CATEGORY = 4;

    public static final String BUDDY_REGISTRATION = "buddy_registration";
    public static final String USER_ID = "user_id";
    public static final int REGISTER_VOLUNTEER = 1001;
    public static final int REGISTER_NEWBIE = 1002;

    public static final int REFRESH_VIEW = 9001;

    public static String getTitle(Context context, int option) {
        switch (option) {
            case INTERNATIONAL_CATEGORY:
                return context.getResources().getString(R.string.international_option);
            case RESEARCH_CATEGORY:
                return context.getResources().getString(R.string.research_option);
            case TUTORING_CATEGORY:
                return context.getResources().getString(R.string.tutor_option);
            case REGISTER_VOLUNTEER:
                return context.getResources().getString(R.string.register_volunteer);
            case REGISTER_NEWBIE:
                return context.getResources().getString(R.string.register_newbie);
            default:
                return "NO TITLE";
        }
    }

    public static String getBuddyViewTitle(Context context, int option) {
        switch (option) {
            case REGISTER_VOLUNTEER:
                return context.getResources().getString(R.string.view_newbies);
            case REGISTER_NEWBIE:
                return context.getResources().getString(R.string.view_volunteers);
            default:
                return "NO TITLE";
        }
    }

}
