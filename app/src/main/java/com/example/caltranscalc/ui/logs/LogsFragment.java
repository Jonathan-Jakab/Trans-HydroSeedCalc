package com.example.caltranscalc.ui.logs;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.caltranscalc.DatabaseHelper;
import com.example.caltranscalc.R;
import com.example.caltranscalc.ui.calculator.CalculatorFragment;

import java.util.ArrayList;

public class LogsFragment extends Fragment {

    DatabaseHelper db;
    ListView userlist;


    ArrayList<String> listItem;
    ArrayAdapter adapter;

    private LogsViewModel logsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logsViewModel =
                new ViewModelProvider(this).get(LogsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logs, container, false);

        listItem = new ArrayList<>();
        userlist = root.findViewById(R.id.listView);

        db = new DatabaseHelper(this.getContext());
        viewData();

        userlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = String.valueOf(position);
                boolean result = true;
                //Toast.makeText(getContext(),userlist.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                if(result)
                {
                    openDialog(getView(),position);
                }
                return result;
            }
        });

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, userlist.getItemAtPosition(position).toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
        return root;
    }




    public void viewData() {
        Cursor cursor = db.viewData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this.getContext(),"No Data to Show", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {
                listItem.add(cursor.getString(1));
            }
            //adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listItem);
            adapter = new ArrayAdapter<>(this.getContext(), R.layout.custon_textview, listItem);
            userlist.setAdapter(adapter);
        }
    }


     public void openDialog(View v, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //Setting AlertDialog Characteristics
        builder.setTitle("Confirm Deletion of Item?");


        //Set Positive Button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = db.delete(userlist.getItemAtPosition(position).toString());

                if(result) {
                    Toast.makeText(getContext(), "Successfully Deleted the Item", Toast.LENGTH_SHORT).show();
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    viewData();
                }
                else
                {
                    Toast.makeText(getContext(), "Failed to delete item on long press!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Set Negative Button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //Creating AlertDialog
        AlertDialog dialog = builder.create();
        //Displaying AlertDialog
        dialog.show();

    }
}