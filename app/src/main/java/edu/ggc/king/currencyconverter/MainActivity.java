package edu.ggc.king.currencyconverter;
/**
 * Created by King Oruga
 * 10/26/16
 *
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import static android.R.attr.name;
import static android.R.attr.permission;

public class MainActivity extends AppCompatActivity  {

    String from;
    String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText amount = (EditText) findViewById(R.id.txtTitle);

        Spinner group1 = (Spinner) findViewById(R.id.txtGroup1);
        //adapter to tie spinner with array of currency strings
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.txtGroup1, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        group1.setAdapter(adapter);


        group1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner group2 = (Spinner) findViewById(R.id.txtGroup2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.txtGroup2, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
       // adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        group2.setAdapter(adapter2);

        group2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // parent.getItemAtPosition(position);
                to = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Button convert = (Button) findViewById(R.id.btnConvert);
        final TextView result = (TextView) findViewById(R.id.txtResult);


        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double userIn = Double.parseDouble(amount.getText().toString());
                FetchAsyncTask task = new FetchAsyncTask(result, userIn);
                task.execute(from,to);
            }
        });

    }

}

