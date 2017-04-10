package com.uoa.compsci_702_g12.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.uoa.compsci_702_g12.models.User;

import java.util.ArrayList;
import java.util.List;

import static com.uoa.compsci_702_g12.utilities.Constants.CATEGORY_SELECTION;
import static com.uoa.compsci_702_g12.utilities.Constants.REGISTER_NEWBIE;
import static com.uoa.compsci_702_g12.utilities.Constants.REGISTER_VOLUNTEER;
import static com.uoa.compsci_702_g12.utilities.Constants.USER_ID;

/**
 * Created by z on 5/04/17.
 */

public class AppDataManager {

    private static AppDataManager mInstance = null;
    private SharedPreferences sharedpreferences;

    private AppDataManager(Activity activity){
        sharedpreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public static AppDataManager getInstance(Activity activity){
        if(mInstance == null)
            mInstance = new AppDataManager(activity);
        return mInstance;
    }

    public int getUserId() {
        return sharedpreferences.getInt(USER_ID, 0);
    }

    public void saveUserId(int userId) {
        sharedpreferences.edit().putInt(USER_ID, userId).apply();
    }

    public boolean getIsNewbie() { return sharedpreferences.getBoolean(""+REGISTER_NEWBIE, false); }

    public boolean getIsVolunteer() { return sharedpreferences.getBoolean(""+REGISTER_VOLUNTEER, false); }

    public int getRegisteredCategory() {
        return sharedpreferences.getInt(CATEGORY_SELECTION, 0);
    }

    public int getRoleInCategory(int category) {
        return sharedpreferences.getInt(""+category, 0);
    }

    public void saveRegisteredBuddyForCategory(int category, int typeRegistered) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        int savedCategory = getRegisteredCategory() | category;
        editor.putInt(CATEGORY_SELECTION, savedCategory).apply();
        editor.putInt(""+category, typeRegistered).apply();

        editor.putBoolean(""+typeRegistered, true).apply();
    }

    public Constants.REGISTRATION_STATUS getStatus(int selectedCategory) {
        int savedCategories = getRegisteredCategory();
        boolean isRegistered = (selectedCategory & savedCategories) == selectedCategory;

        if(isRegistered) {
            if(getRoleInCategory(selectedCategory) == REGISTER_NEWBIE)
                return Constants.REGISTRATION_STATUS.NEWBIE;
            else if (getRoleInCategory(selectedCategory) == REGISTER_VOLUNTEER)
                return Constants.REGISTRATION_STATUS.VOLUNTEER;
        }
        return Constants.REGISTRATION_STATUS.NONE;
    }

    public void deleteUserData() {
        sharedpreferences.edit().clear().apply();
    }
}
