package com.example.caltranscalc.ui.settings;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.caltranscalc.MainActivity;
import com.example.caltranscalc.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    EditText appRateCompostInput;
    EditText appRateFertilizerInput;
    EditText appRateSeedInput;
    EditText waterMixConstant;
    EditText mulchMixConstant;

    Button saveValue;
    Button defaultValue;

    RadioGroup waterTankSize;
    RadioButton fifteenGallon;
    RadioButton thirtyGallon;

    RadioGroup unitSelector;
    RadioButton unitsSqft;
    RadioButton unitsAcres;

    TextView customSeedSwitchText;

    Switch customSeedSwitch;


    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState){
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        appRateCompostInput = (EditText)root.findViewById(R.id.inAppRateCompost);
        appRateFertilizerInput = (EditText)root.findViewById(R.id.inAppRateFertilizer);
        appRateSeedInput = (EditText)root.findViewById(R.id.inAppRateSeed);
        waterMixConstant = (EditText)root.findViewById(R.id.waterMixingRateInput);
        mulchMixConstant = (EditText)root.findViewById(R.id.mulchMixingRateInput);

        Button saveValue = root.findViewById(R.id.saveSettings);
        Button defaultValue = root.findViewById(R.id.defaultSettings);

        waterTankSize = root.findViewById(R.id.tankSizeOption);
        fifteenGallon = root.findViewById(R.id.fifteenButton);
        thirtyGallon = root.findViewById(R.id.thirtyButton);

        unitSelector = root.findViewById(R.id.unitsSelect);
        unitsSqft = root.findViewById(R.id.sqftSelect);
        unitsAcres = root.findViewById(R.id.acresSelect);

        customSeedSwitch = root.findViewById(R.id.customSeedSwitch);
        customSeedSwitchText = root.findViewById(R.id.customMixTextView);

        setHints();

        TextView savedData = (TextView)root.findViewById(R.id.text_settings);




//--------------------------------------WHEN SAVE BUTTON IS PRESSED SAVE THE VALUES-----------------------------------------------------------------------
        saveValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!isEmpty(appRateCompostInput)){
                    MainActivity.compAppRate = Float.parseFloat(appRateCompostInput.getText().toString());
                }

                if(!isEmpty(appRateFertilizerInput)) {
                    MainActivity.fertAppRate = Float.parseFloat(appRateFertilizerInput.getText().toString());
                }

                if (customSeedSwitch.isChecked()) {
                    MainActivity.customSeedMixSelected = true;
                    customSeedSwitchText.setTextColor(Color.parseColor("#FFFFFF"));
                }

                else if (!customSeedSwitch.isChecked()) {
                    MainActivity.customSeedMixSelected = false;
                    customSeedSwitchText.setTextColor(Color.parseColor("#888888"));
                }

                if(!isEmpty(appRateSeedInput)){
                    MainActivity.seedAppRate = Float.parseFloat(appRateSeedInput.getText().toString());
                }

                if (!isEmpty(waterMixConstant)) {
                    MainActivity.wConstant = Float.parseFloat(waterMixConstant.getText().toString());
                }

                if (!isEmpty(mulchMixConstant)) {
                    MainActivity.mConstant = Float.parseFloat(mulchMixConstant.getText().toString());
                }

                if (thirtyGallon.isChecked()){
                    MainActivity.waterTankSize = 3000;
                }

                if (fifteenGallon.isChecked()){
                    MainActivity.waterTankSize = 1500;
                }

                if (unitsSqft.isChecked()) {
                    MainActivity.sqftSelected = true;
                }

                if (unitsAcres.isChecked()) {
                    MainActivity.sqftSelected = false;
                }


                new CountDownTimer(3000, 1000){
                    public void onTick (long millisUntilFinished){
                        savedData.setText("Settings have been saved.");
                    }
                    public void onFinish (){
                        savedData.setText(" ");
                    }
                }.start();
                nullifyInputs();
                setHints();
            }

        });

//--------------------------------WHEN DEFAULT BUTTON IS PRESSED SET APPLICATION RATED TO DEFAULT-----------------------------------------------------------
        defaultValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float defaultVal1 = 0;
                float defaultVal2 = 0;
                float defaultVal3 = 0;
                int defaultVal4 = 3000;
                boolean defaultVal5 = true;
                float defaultVal6 = 2;
                float defaultVal7 = 1;
                boolean defaultVal8 = false;

                MainActivity.compAppRate = defaultVal1;
                MainActivity.fertAppRate = defaultVal2;
                MainActivity.seedAppRate = defaultVal3;
                MainActivity.waterTankSize = defaultVal4;
                MainActivity.sqftSelected = defaultVal5;
                MainActivity.wConstant = defaultVal6;
                MainActivity.mConstant = defaultVal7;
                MainActivity.customSeedMixSelected = defaultVal8;

                new CountDownTimer(3000, 1000){
                    @Override
                    public void onTick (long millisUntilFinished){
                        savedData.setText("Settings have been set to Default.");
                    }
                    @Override
                    public void onFinish (){
                        savedData.setText(" ");
                    }
                }.start();

                setHints();
            }
        });

        customSeedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appRateSeedInput.setHint((String.valueOf(MainActivity.customSeedAppRate)) + " lbs/acre");
                    MainActivity.customSeedMixSelected = true;
                    customSeedSwitchText.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else {
                    appRateSeedInput.setHint((String.valueOf(MainActivity.seedAppRate)) + " lbs/acre");
                    MainActivity.customSeedMixSelected = false;
                    customSeedSwitchText.setTextColor(Color.parseColor("#888888"));
                }
            }
        });


        final TextView textView = root.findViewById(R.id.text_settings);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    public void setHints() {
        appRateCompostInput.setHint(String.valueOf(MainActivity.compAppRate) + " cy/acre");
        appRateFertilizerInput.setHint(String.valueOf(MainActivity.fertAppRate) + " lbs/acre");
        waterMixConstant.setHint(String.valueOf((MainActivity.wConstant)));
        mulchMixConstant.setHint((String.valueOf(MainActivity.mConstant)));

        if (MainActivity.waterTankSize == 1500) {
            fifteenGallon.toggle();
        }
        else if (MainActivity.waterTankSize == 3000) {
            thirtyGallon.toggle();
        }

        if (MainActivity.sqftSelected)
            unitsSqft.toggle();
        else
            unitsAcres.toggle();

        if (MainActivity.customSeedMixSelected) {
            customSeedSwitch.setChecked(true);
            customSeedSwitchText.setTextColor(Color.parseColor("#FFFFFF"));
            appRateSeedInput.setHint((String.valueOf(MainActivity.customSeedAppRate)) + " lbs/acre");
        }
        else if (!MainActivity.customSeedMixSelected){
            customSeedSwitch.setChecked(false);
            customSeedSwitchText.setTextColor(Color.parseColor("#888888"));
            appRateSeedInput.setHint(String.valueOf(MainActivity.seedAppRate) + " lbs/acre");
        }
    }

    public void nullifyInputs() {
        appRateCompostInput.setText(null);
        appRateFertilizerInput.setText(null);
        appRateSeedInput.setText(null);
        waterMixConstant.setText(null);
        mulchMixConstant.setText(null);
    }

}
