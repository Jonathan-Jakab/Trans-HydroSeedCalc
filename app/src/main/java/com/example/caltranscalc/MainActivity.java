package com.example.caltranscalc;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static float compAppRate, fertAppRate, seedAppRate, hydroAppRate, customSeedAppRate;
    public static int waterTankSize;
    public static boolean sqftSelected;
    public static boolean customSeedMixSelected;

    public static float seed1, seed2, seed3, seed4, seed5,
                        seed6, seed7, seed8, seed9, seed10;

    // wConstant = Mix rate constant for water
    // mConstant = Mix rate constant for mulch
    public static float wConstant, mConstant;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    //public SpannableString s;

    public static DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_seeds,
                R.id.navigation_calculator,
                R.id.navigation_logs,
                R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        //ab.setDisplayShowCustomEnabled(true);
        //this.getActionBar().setDisplayShowTitleEnabled(false);
        //LayoutInflater inflater = LayoutInflater.from(this);
        //View v = inflater.inflate(R.layout.titleview, null);
        //((TextView)v.findViewById(R.id.title)).setText(this.getTitle());
        //ab.setCustomView(v);
        //s.equals(ab.getTitle());


        findViewById(R.id.nav_view).bringToFront();


//-------------------------------RETRIEVED SAVED PREFERENCES EVERY TIME APP IS LAUNCHED-------------------------------------------------

        SharedPreferences preferences = getSharedPreferences("com.hydroseedcalc.save", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = preferences.edit();


        float restoredCompostAppRate = preferences.getFloat("restCompostValue", 0);
        float restoredFertilizerAppRate = preferences.getFloat("restFertilizerValue", 0);
        float restoredSeedAppRate = preferences.getFloat("restSeedValue", 0);
        float restoredCustomSeedAppRate = preferences.getFloat("restCustomSeedValue", 0);
        float restoredHydroAppRate = preferences.getFloat("restHydroValue", 0);
        int restoredWaterTankSize = preferences.getInt(("restWaterTankSize"), 3000);
        boolean restoredSqftSelected = preferences.getBoolean("restSqftChecked", true);
        boolean restoredCustomSeedMixSelected = preferences.getBoolean("restCustomSeedSelected", false);
        float restored_wRate = preferences.getFloat("rest_wRate", 2);
        float restored_mRate = preferences.getFloat("rest_mRate", 1);
        float restoredSeed1 = preferences.getFloat("restSeed1", 0);
        float restoredSeed2 = preferences.getFloat("restSeed2", 0);
        float restoredSeed3 = preferences.getFloat("restSeed3", 0);
        float restoredSeed4 = preferences.getFloat("restSeed4", 0);
        float restoredSeed5 = preferences.getFloat("restSeed5", 0);
        float restoredSeed6 = preferences.getFloat("restSeed6", 0);
        float restoredSeed7 = preferences.getFloat("restSeed7", 0);
        float restoredSeed8 = preferences.getFloat("restSeed8", 0);
        float restoredSeed9 = preferences.getFloat("restSeed9", 0);
        float restoredSeed10 = preferences.getFloat("restSeed10", 0);
        //double restored_wRate = getDouble(preferences, "rest_wRate", 2.0);
        //double restored_mRate = getDouble(preferences, "rest_mRate", 1.0);



//----------------------------------SETS GLOBAL VARIABLES EQUAL TO RETRIEVED VALUES-----------------------------------------------------
        compAppRate = restoredCompostAppRate;
        fertAppRate = restoredFertilizerAppRate;
        seedAppRate = restoredSeedAppRate;
        customSeedAppRate = restoredCustomSeedAppRate;
        hydroAppRate = restoredHydroAppRate;
        waterTankSize = restoredWaterTankSize;
        sqftSelected = restoredSqftSelected;
        customSeedMixSelected = restoredCustomSeedMixSelected;
        wConstant = restored_wRate;
        mConstant = restored_mRate;
        seed1 = restoredSeed1;
        seed2 = restoredSeed2;
        seed3 = restoredSeed3;
        seed4 = restoredSeed4;
        seed5 = restoredSeed5;
        seed6 = restoredSeed6;
        seed7 = restoredSeed7;
        seed8 = restoredSeed8;
        seed9 = restoredSeed9;
        seed10 = restoredSeed10;
    }



    //----------------------------------WHENEVER APP IS CLOSED SAVE THESE VARIABLES---------------------------------------------------------
    @Override
    protected void onPause(){
        super.onPause();

        SharedPreferences preferences = getSharedPreferences("com.hydroseedcalc.save", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat("restCompostValue", compAppRate);
        editor.putFloat("restFertilizerValue", fertAppRate);
        editor.putFloat("restSeedValue", seedAppRate);
        editor.putFloat("restCustomSeedValue", customSeedAppRate);
        editor.putFloat("restHydroValue", hydroAppRate);
        editor.putInt("restWaterTankSize", waterTankSize);
        editor.putBoolean("restSqftChecked", sqftSelected);
        editor.putBoolean("restCustomSeedSelected", customSeedMixSelected);
        editor.putFloat("rest_wRate", wConstant);
        editor.putFloat("rest_mRate", mConstant);
        editor.putFloat("restSeed1", seed1);
        editor.putFloat("restSeed2", seed2);
        editor.putFloat("restSeed3", seed3);
        editor.putFloat("restSeed4", seed4);
        editor.putFloat("restSeed5", seed5);
        editor.putFloat("restSeed6", seed6);
        editor.putFloat("restSeed7", seed7);
        editor.putFloat("restSeed8", seed8);
        editor.putFloat("restSeed9", seed9);
        editor.putFloat("restSeed10", seed10);
        //putDouble(editor, "rest_wRate", wConstant);
        //putDouble(editor, "rest_mRate", mConstant);


        editor.commit();
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }


}