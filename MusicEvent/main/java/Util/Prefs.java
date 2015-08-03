package Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {

    SharedPreferences preferences;

    public Prefs(Activity activity) {
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setCity(String city) {
        preferences.edit().putString("city", city).commit();
    }

    //If user has n ot chose a city, return default!
    public String getCity() {
        return preferences.getString("city", "Spokane");
    }
}
