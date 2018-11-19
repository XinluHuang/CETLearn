package com.xinluhuang.englishlearn.module.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.util.CacheDataManager;

public class GeneralPreferenceFragment extends PreferenceFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public GeneralPreferenceFragment() {
        // Required empty public constructor
    }

    public static GeneralPreferenceFragment newInstance(String param1, String param2) {
        GeneralPreferenceFragment fragment = new GeneralPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        addPreferencesFromResource(R.xml.pref_general);

        findPreference("text_size").setOnPreferenceClickListener(preference -> {
            return true;
        });
        Preference clearCache = findPreference("clear_cache");
        clearCache.setSummary(getCacheSizeString());
        clearCache.setOnPreferenceClickListener(preference -> {
            CacheDataManager.clearAllCache(getActivity());
            Snackbar.make(getView(), "清理成功", Snackbar.LENGTH_SHORT).show();
            clearCache.setSummary(getCacheSizeString());
            return true;
        });
        findPreference("sourceCode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))));
                return true;
            }
        });
    }

    private String getCacheSizeString() {
        return CacheDataManager.getFormatSize(CacheDataManager.getTotalCacheSize(getActivity()));
    }

}
