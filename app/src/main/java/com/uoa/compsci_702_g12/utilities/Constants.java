package com.uoa.compsci_702_g12.utilities;

import android.content.Context;

import com.uoa.compsci_702_g12.R;

/**
 * Created by z on 31/03/17.
 */

public class Constants {

    public static final String OPTION_SELECTION = "option_selection";
    public static final int INTERNATIONAL_OPTION = 1;
    public static final int REASEARCH_OPTION = 2;
    public static final int TUTORING_OPTION = 3;
    public static final int VIEW_BUDDIES_OPTION = 4;

    public static final String BUDDY_REGISTRATION = "buddy_registration";
    public static final int REGISTER_VOLUNTEER = 1001;
    public static final int REGISTER_NEWBIE = 1002;

    public static String getTitle(Context context, int option) {

        switch (option) {
            case INTERNATIONAL_OPTION:
                return context.getResources().getString(R.string.international_option);
            case REASEARCH_OPTION:
                return context.getResources().getString(R.string.research_option);
            case TUTORING_OPTION:
                return context.getResources().getString(R.string.tutor_option);
            case VIEW_BUDDIES_OPTION:
                return context.getResources().getString(R.string.view_buddies);
            case REGISTER_VOLUNTEER:
                return context.getResources().getString(R.string.register_volunteer);
            case REGISTER_NEWBIE:
                return context.getResources().getString(R.string.register_newbie);
            default:
                return "NO TITLE";
        }
    }

}
