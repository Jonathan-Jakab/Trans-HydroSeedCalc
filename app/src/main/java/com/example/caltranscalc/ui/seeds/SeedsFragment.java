package com.example.caltranscalc.ui.seeds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.caltranscalc.MainActivity;
import com.example.caltranscalc.R;

public class SeedsFragment extends Fragment {

    private SeedViewModel seedViewModel;

    EditText seed1Input, seed2Input, seed3Input, seed4Input, seed5Input,
             seed6Input, seed7Input, seed8Input, seed9Input, seed10Input;

    float seed1Value, seed2Value, seed3Value, seed4Value, seed5Value,
          seed6Value, seed7Value, seed8Value, seed9Value, seed10Value;

    Button saveValue, defaultValue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seedViewModel =
                new ViewModelProvider(this).get(SeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_seeds, container, false);
        initializeSeedEditText(root);
        saveValue = root.findViewById(R.id.saveSettings);
        defaultValue = root.findViewById(R.id.defaultSettings);
        setHints();

        saveValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValues();
                nullifyInputs();
                setHints();
            }

        });

        defaultValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultValues();
                setHints();
            }

        });

        return root;
    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void saveValues() {
        if (!isEmpty(seed1Input)) {
            seed1Value = Float.parseFloat(seed1Input.getText().toString());
            MainActivity.customSeedAppRate = 0 + seed1Value;
            MainActivity.seed1 = seed1Value;
        }
        if (!isEmpty(seed2Input)) {
            seed2Value = Float.parseFloat(seed2Input.getText().toString());
            MainActivity.customSeedAppRate += seed2Value;
            MainActivity.seed2 = seed2Value;
        }
        if (!isEmpty(seed3Input)) {
            seed3Value = Float.parseFloat(seed3Input.getText().toString());
            MainActivity.customSeedAppRate += seed3Value;
            MainActivity.seed3 = seed3Value;
        }
        if (!isEmpty(seed4Input)) {
            seed4Value = Float.parseFloat(seed4Input.getText().toString());
            MainActivity.customSeedAppRate += seed4Value;
            MainActivity.seed4 = seed4Value;
        }
        if (!isEmpty(seed5Input)) {
            seed5Value = Float.parseFloat(seed5Input.getText().toString());
            MainActivity.customSeedAppRate += seed5Value;
            MainActivity.seed5 = seed5Value;
        }
        if (!isEmpty(seed6Input)) {
            seed6Value = Float.parseFloat(seed6Input.getText().toString());
            MainActivity.customSeedAppRate += seed6Value;
            MainActivity.seed6 = seed6Value;
        }
        if (!isEmpty(seed7Input)) {
            seed7Value = Float.parseFloat(seed7Input.getText().toString());
            MainActivity.customSeedAppRate += seed7Value;
            MainActivity.seed7 = seed7Value;
        }
        if (!isEmpty(seed8Input)) {
            seed8Value = Float.parseFloat(seed8Input.getText().toString());
            MainActivity.customSeedAppRate += seed8Value;
            MainActivity.seed8 = seed8Value;
        }
        if (!isEmpty(seed9Input)) {
            seed9Value = Float.parseFloat(seed9Input.getText().toString());
            MainActivity.customSeedAppRate += seed9Value;
            MainActivity.seed9 = seed9Value;
        }
        if (!isEmpty(seed10Input)) {
            seed10Value = Float.parseFloat(seed10Input.getText().toString());
            MainActivity.customSeedAppRate += seed10Value;
            MainActivity.seed10 = seed10Value;
        }
    }

    private void defaultValues() {
        MainActivity.seed1 = 0;
        seed1Value = 0;
        MainActivity.seed2 = 0;
        seed2Value = 0;
        MainActivity.seed3 = 0;
        seed3Value = 0;
        MainActivity.seed4 = 0;
        seed4Value = 0;
        MainActivity.seed5 = 0;
        seed5Value = 0;
        MainActivity.seed6 = 0;
        seed6Value = 0;
        MainActivity.seed7 = 0;
        seed7Value = 0;
        MainActivity.seed8 = 0;
        seed8Value = 0;
        MainActivity.seed9 = 0;
        seed9Value = 0;
        MainActivity.seed10 = 0;
        seed10Value = 0;
        MainActivity.customSeedAppRate = 0;
    }

    public void setHints() {
        seed1Input.setHint(String.valueOf(MainActivity.seed1) + " lbs/acre");
        seed2Input.setHint(String.valueOf(MainActivity.seed2) + " lbs/acre");
        seed3Input.setHint(String.valueOf(MainActivity.seed3) + " lbs/acre");
        seed4Input.setHint(String.valueOf(MainActivity.seed4) + " lbs/acre");
        seed5Input.setHint(String.valueOf(MainActivity.seed5) + " lbs/acre");
        seed6Input.setHint(String.valueOf(MainActivity.seed6) + " lbs/acre");
        seed7Input.setHint(String.valueOf(MainActivity.seed7) + " lbs/acre");
        seed8Input.setHint(String.valueOf(MainActivity.seed8) + " lbs/acre");
        seed9Input.setHint(String.valueOf(MainActivity.seed9) + " lbs/acre");
        seed10Input.setHint(String.valueOf(MainActivity.seed10) + " lbs/acre");

    }

    public void nullifyInputs() {
        seed1Input.setText(null);
        seed2Input.setText(null);
        seed3Input.setText(null);
        seed4Input.setText(null);
        seed5Input.setText(null);
        seed6Input.setText(null);
        seed7Input.setText(null);
        seed8Input.setText(null);
        seed9Input.setText(null);
        seed10Input.setText(null);
    }

    private void initializeSeedEditText(View root) {
        seed1Input = root.findViewById(R.id.seed1_editText);
        seed2Input = root.findViewById(R.id.seed2_editText);
        seed3Input = root.findViewById(R.id.seed3_editText);
        seed4Input = root.findViewById(R.id.seed4_editText);
        seed5Input = root.findViewById(R.id.seed5_editText);
        seed6Input = root.findViewById(R.id.seed6_editText);
        seed7Input = root.findViewById(R.id.seed7_editText);
        seed8Input = root.findViewById(R.id.seed8_editText);
        seed9Input = root.findViewById(R.id.seed9_editText);
        seed10Input = root.findViewById(R.id.seed10_editText);
    }
}