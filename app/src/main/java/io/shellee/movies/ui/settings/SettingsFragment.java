package io.shellee.movies.ui.settings;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import io.shellee.movies.R;

public class SettingsFragment  extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movies);
    }
}
