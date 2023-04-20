package pl.op.danex11.gwselectfromlist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView list;
    TextView ABCTextView;
    ArrayAdapter adapter ;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ABCTextView = findViewById(R.id.textviewABC);
//        Button A =  (Button) findViewById(R.id.buttonA);
//        Button B =  (Button) findViewById(R.id.buttonB);
//        Button C =  (Button) findViewById(R.id.buttonC);

      String[] carsArray = {"AAA", "AAB", "AAC", "AAD", "AAE", "AAF", "AAG", "AAHh", "AAi"};
//
//        ArrayList carList = new ArrayList(Arrays.asList(carsArray));
//        adapter = new ArrayAdapter(this, R.layout.single_row, carList);

        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll( Arrays.asList(carsArray) );
        adapter = new ArrayAdapter<String>(this, R.layout.single_row, carL);

//
        Log.e("mytag", "listview ID: " + String.valueOf(R.id.list_view));
        list = findViewById(R.id.list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                // MyClass selItem = (MyClass) myList.getSelectedItem(); //
                selectedItem = (String) list.getItemAtPosition(position); //getter method
                Toast.makeText(getApplicationContext(), "selected Item Name is " + selectedItem, Toast.LENGTH_LONG).show();
                ABCTextView.setText(selectedItem);
            }
    });

    }

    public void  clickA(View view){
        ABCTextView.setText("A");
    }

   public void  clickB(View view){
       ABCTextView.setText("B");
    }


    public void  clickC(View view){
        ABCTextView.setText("C");
    }



}