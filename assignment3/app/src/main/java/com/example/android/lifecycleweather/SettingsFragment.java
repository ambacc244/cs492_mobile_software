package com.example.android.lifecycleweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences prefs;
    private ListPreference mUnitListPreference;
    private EditTextPreference mLocationTextPreference;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.prefs);

        mUnitListPreference = (ListPreference) findPreference(getString(R.string.pref_unit_key));
        mLocationTextPreference = (EditTextPreference) findPreference(getString(R.string.pref_location_key));

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mUnitListPreference.setSummary(mUnitListPreference.getEntry());
        mLocationTextPreference.setSummary(mLocationTextPreference.getText());

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_unit_key))) {
            mUnitListPreference.setSummary(mUnitListPreference.getEntry());
        } else if (s.equals(getString(R.string.pref_location_key))) {
            mLocationTextPreference.setSummary(mLocationTextPreference.getText());
        }

     //  (( MainActivity) getActivity()).loadForecast();
    }
/**
    @Override
    public boolean onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_unit_key))) {
            mUnitListPreference.setSummary(mUnitListPreference.getEntry());
            return true;
        } else if (s.equals(getString(R.string.pref_location_key))) {
            mLocationTextPreference.setSummary(mLocationTextPreference.getText());
            return true;
        }
        return false;
    }
**/
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}

