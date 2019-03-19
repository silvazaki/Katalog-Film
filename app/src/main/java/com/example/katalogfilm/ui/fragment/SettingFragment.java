package com.example.katalogfilm.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.katalogfilm.R;
import com.example.katalogfilm.data.local.SharedPrefHelper;
import com.example.katalogfilm.ui.activity.MainActivity;
import com.example.katalogfilm.ui.activity.SettingNotifActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.btn_change_lang)
    Button btnChangeLang;
    @BindView(R.id.btn_change_notif)
    TextView btnChangeNotif;
    private Unbinder unbinder;

    private SharedPrefHelper prefHelper;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);

        prefHelper = new SharedPrefHelper(getContext());

        setOnClick();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (prefHelper.getSettingLanguage(SharedPrefHelper.FLAG)) {
            prefHelper.setSettingLanguage(SharedPrefHelper.FLAG, false);
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    void setOnClick(){
        btnChangeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                prefHelper.setSettingLanguage(SharedPrefHelper.FLAG, true);
            }
        });

        btnChangeNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingNotifActivity.class));
            }
        });
    }
}
