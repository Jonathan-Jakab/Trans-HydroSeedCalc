package com.example.caltranscalc.ui.calculator;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.caltranscalc.DatabaseHelper;
import com.example.caltranscalc.MainActivity;
import com.example.caltranscalc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel calculatorViewModel;
    DatabaseHelper db;

    float compRate, fertRate, seedRate;
    int tankSize;

    double hydroRate, compTotal, fertTotal, fertBags, acreSize, seedTotal, hsFiberTotal, hsFiberBags, waterTotal, waterTrucks;

    ListView listview;

    TextView compostResult;
    TextView fertilizerResult;
    TextView seedResult;
    TextView hsFiberResult;
    TextView waterTruckResult;

    EditText acreInput;
    TextView unitsInput;
    EditText lbsInput;
    EditText add_name;
    Button calculate;

    String compString;
    String fertString;
    String seedString;
    String hsString;
    String waterString;

    String sqftUnits;
    String acresUnits;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {


        db = new DatabaseHelper(this.getContext());
        calculatorViewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        Resources res = getResources();

        unitsInput = (TextView)root.findViewById(R.id.UnitsTextView);


        acreInput = (EditText)root.findViewById(R.id.inAcre);

        if (MainActivity.sqftSelected == true) {
            acreInput.setHint("Input sqft");
            unitsInput.setText("sqft");
        }
        else {
            unitsInput.setText(res.getString(R.string.acres));
            acreInput.setHint(res.getString(R.string.acresHint));
        }

        lbsInput = (EditText)root.findViewById(R.id.inLBS);
        Button calculate = root.findViewById(R.id.calculate);
        Button add_data = root.findViewById(R.id.buttonPrompt);//button to add tata to table

        listview = root.findViewById(R.id.listView);


        listItem = new ArrayList<>();

        compostResult = (TextView)root.findViewById(R.id.compostResult);
        fertilizerResult = (TextView)root.findViewById(R.id.fertilizerResult);
        seedResult = (TextView)root.findViewById(R.id.seedResult);
        hsFiberResult = (TextView)root.findViewById(R.id.hsFiberResult);
        waterTruckResult = (TextView)root.findViewById(R.id.waterTruckResults);

        compRate = MainActivity.compAppRate;
        fertRate = MainActivity.fertAppRate;
        tankSize = MainActivity.waterTankSize;

        if (MainActivity.customSeedMixSelected)
            seedRate = MainActivity.customSeedAppRate;
        else
            seedRate = MainActivity.seedAppRate;


//---------------------WHEN CALCULATE BUTTON IS PRESSED. RUN CALCULATIONS---------------------------
        calculate.setOnClickListener(v -> {
            if(!isEmpty(acreInput)&&!isEmpty(lbsInput)) {
                calculateResults();
                //double acreSize = Double.parseDouble(acreInput.getText().toString());
                //if (MainActivity.sqftSelected == true) {
                    //acreSize = (acreSize / 43650);
                    //acreSize = round(acreSize);
                //}

                //double hydroRate = Double.parseDouble(lbsInput.getText().toString());



                //double compTotal = acreSize * compRate;
               // compTotal = round(compTotal);
                //float compDaily = (float) (compTotal / (acreSize / .25));



                //double fertTotal = acreSize * fertRate;

                //int fertBags = (int) (fertTotal / 50);

                //double seedTotal = acreSize * seedRate;
                //seedTotal = round(seedTotal);
                //float seedDaily = (float) (seedTotal / (acreSize / .25));

                //float hsFiberDaily = (float)(.25* hydroRate);
                //double hsFiberTotal = (acreSize * hydroRate);
                //hsFiberTotal = round(hsFiberTotal);
                //float hsFiberDaily = (float)(hsFiberTotal / (acreSize / .25));
                //double hsFiberBags = round(hsFiberTotal / 50);

                //double waterTotal = round(((MainActivity.mConstant * hsFiberTotal) * MainActivity.wConstant) / 8.34);

                //double waterTrucks = round(waterTotal / tankSize);

//-------------------------SETS TEXT FIELDS TO OUTPUT VALUES----------------------------------------
                compostResult.setText("Compost Total: " + String.valueOf(compTotal) + "c.y.");
                fertilizerResult.setText("Fertilizer Total: " + String.valueOf(fertTotal) + "lbs\n" +
                        "\tBags Total: " + String.valueOf(fertBags) + " bags");
                seedResult.setText("Seed Total: " + String.valueOf(seedTotal) + "lbs");
                hsFiberResult.setText("H.S. Fiber Total: " + String.valueOf(hsFiberTotal) + "lbs\n" +
                                      "\tBags Total: " + String.valueOf(hsFiberBags) + " bags");
                waterTruckResult.setText("Water:" + String.valueOf(waterTotal) + " gal\n" + "Water Trucks: " + waterTrucks +  " trucks");

                compString = compostResult.getText().toString();
                fertString = fertilizerResult.getText().toString();
                seedString = seedResult.getText().toString();
                hsString = hsFiberResult.getText().toString();
                waterString = waterTruckResult.getText().toString();

            }
        });



        final Context context = root.getContext();
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                //Window window = dialog.getWindow();
                //window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                add_name = (EditText)dialog.findViewById(R.id.projInputName);
                // if button is clicked, close the custom dialog and save results to logs
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //PUT CODE TO Transfer Results to list on Logs fragment
                        String name = add_name.getText().toString();
                        String Full = name + "\n" +compString+"\n" +fertString+"\n" +seedString+"\n" +hsString+"\n" +waterString;
                        //String compostres = compostResult.getText().toString();
                        //String seedres = seedResult.getText().toString();
                        //String fertres = fertilizerResult.getText().toString();
                        if(!name.equals("")) {
                            db.addData(Full);
                            Toast.makeText(getContext(), "Results Saved to Logs", Toast.LENGTH_SHORT).show();
                            add_name.setText("");
                            listItem.clear();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getContext(), "You must put something in the project name", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });

        return root;
    }

    double round(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void calculateResults(){
        hydroRate = Double.parseDouble(lbsInput.getText().toString());
        acreSize = Double.parseDouble(acreInput.getText().toString());
        if (MainActivity.sqftSelected == true) {
            acreSize = (acreSize / 43650);
            acreSize = round(acreSize);
        }
        compTotal = round(acreSize * compRate);
        fertTotal = round(acreSize * fertRate);
        fertBags = round(fertTotal / 50);
        seedTotal = round(acreSize * seedRate);
        hsFiberTotal = round(acreSize * hydroRate);
        hsFiberBags = round(hsFiberTotal / 50);
        waterTotal = round(((MainActivity.mConstant * hsFiberTotal) * MainActivity.wConstant) / 8.34);
        waterTrucks = round(waterTotal / tankSize);

    }

}