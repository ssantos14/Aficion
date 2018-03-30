package com.example.android.aficion;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.TeamsColumns;

public class TeamsFollowingFragment extends PreferenceFragmentCompat implements LoaderManager.LoaderCallbacks<Cursor>{
    int TEAMS_LOADER_ID = 43;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_aficion);
        getLoaderManager().initLoader(TEAMS_LOADER_ID,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String selection = TeamsColumns.TYPE + "=? AND (" + TeamsColumns.AREA_ID + "=? OR "
                + TeamsColumns.AREA_ID + "=? OR " + TeamsColumns.AREA_ID + "=? OR " +
                TeamsColumns.AREA_ID + "=? OR " + TeamsColumns.AREA_ID + "=?)";
        String[] selectionArgs = new String[]{"Club", "68","176","80","100","76"};
        return new CursorLoader(getContext(),AficionProvider.Teams.TEAMS_CONTENT_URI,null,selection,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor != null && cursor.moveToFirst()){
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            for(int i=0; i<cursor.getCount(); i++){
                cursor.moveToPosition(i);
                String teamName = cursor.getString(0);
                CheckBoxPreference preference = new CheckBoxPreference(preferenceScreen.getContext());
                preference.setKey(teamName);
                preference.setDefaultValue(false);
                preference.setTitle(teamName);
                preferenceScreen.addPreference(preference);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
