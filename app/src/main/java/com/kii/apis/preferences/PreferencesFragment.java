package com.kii.apis.preferences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kii.apis.R;
import com.kii.cloud.storage.Kii;

public class PreferencesFragment extends Fragment {

    public static PreferencesFragment newInstance() {
        PreferencesFragment fragment = new PreferencesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    EditText accessTokenExpiration;
    Button okButton;
    Button cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.preferences, container, false);

        Context context = root.getContext();
        accessTokenExpiration = (EditText) root.findViewById(R.id.access_token_expiration);
        accessTokenExpiration.setText(String.valueOf(Kii.getAccessTokenExpiration()));
        okButton = (Button) root.findViewById(R.id.buttonOK);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = accessTokenExpiration.getText().toString();
                if (!TextUtils.isEmpty(value)) {
                    try {
                        Kii.setAccessTokenExpiration(Long.valueOf(value));
                        Toast.makeText(getActivity(), "Set AccessTokenExpiration=" + value + " seconds", Toast.LENGTH_LONG).show();
                    } catch (Exception ignore) {
                        Toast.makeText(getActivity(), "Failed to set AccessTokenExpiration err=" + ignore.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        cancelButton = (Button) root.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return root;
    }
}
