package com.example.android.aficion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;

public class TeamsFollowingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_aficion);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        CheckBoxPreference preference = new CheckBoxPreference(preferenceScreen.getContext());
        preference.setKey("argentina");
        preference.setDefaultValue(false);
        preference.setSummaryOff("no argentina");
        preference.setSummaryOn("yes argentina");
        preference.setTitle("ARGENTINA");
        preferenceScreen.addPreference(preference);
    }
}
