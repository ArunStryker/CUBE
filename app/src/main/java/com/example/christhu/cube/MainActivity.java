package com.example.christhu.cube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerDropDown;
    String[] grades ={"Biriyani"};

    String[] credits ={"1","2","3","4"};
    double [] points1 = {120 };


    Spinner spinner, spin2;
    TextView TextView1;
    TextView TextView2;
    Double point = -1.0;
    Double value = -1.0;
    String[] cities = {
            "Star Biriyani",
            "Yaa Mohideen",
            "Sukhuboi",
            "Hyderabad Biriyani",

    };


    List<String> listItems = new ArrayList<>();
    List<String> keys = new ArrayList<>();
    Button SubmitButton ;

    Button DisplayButton;

    EditText NameEditText, PhoneNumberEditText;

    // Declaring String variable ( In which we are storing firebase server URL ).
    public static final String Firebase_Server_URL = "https://cube-d3c5a.firebaseio.com/";

    // Declaring String variables to store name & phone number get from EditText.
    String NameHolder, NumberHolder;

    Firebase firebase;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Food_Details_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(MainActivity.this);
        spinnerDropDown =(Spinner)findViewById(R.id.spinner1);


        TextView1 = (TextView) findViewById(R.id.grade);
        TextView2 = (TextView) findViewById(R.id.erd);

        spinner = (Spinner) findViewById(R.id.spinner_convert_from);


        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, grades);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(onItemSelectedListener1);

        spin2 = (Spinner) findViewById(R.id.spinner_convert);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, credits);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);
        spin2.setOnItemSelectedListener(this);



        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,cities);

        spinnerDropDown.setAdapter(adapter);

        spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
                int sid=spinnerDropDown.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), "You have selected City : " + cities[sid],
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        firebase = new Firebase(Firebase_Server_URL);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        SubmitButton = (Button)findViewById(R.id.submit);

        NameEditText = (EditText)findViewById(R.id.name);

        PhoneNumberEditText = (EditText)findViewById(R.id.phone_number);

        DisplayButton = (Button)findViewById(R.id.DisplayButton);

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StudentDetails studentDetails = new StudentDetails();

                GetDataFromEditText();

                // Adding name into class function object.
                studentDetails.setStudentName(NameHolder);

                // Adding phone number into class function object.
                studentDetails.setStudentPhoneNumber(NumberHolder);

                // Getting the ID from firebase database.
                String StudentRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(StudentRecordIDFromServer).setValue(studentDetails);

                // Showing Toast message after successfully data submit.
                Toast.makeText(MainActivity.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();

            }
        });

        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ShowStudentDetailsActivity.class);
                startActivity(intent);

            }
        });


    }
    AdapterView.OnItemSelectedListener onItemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            point = points1[position];
            TextView1.setText(String.valueOf(point));

            if ( value != -1.0 ) {
                double res = (double) point * value;
                TextView2.setText(String.valueOf(res));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id ){

        Toast.makeText(this, "Your choice :" + credits[position],Toast.LENGTH_SHORT).show();

        String valueStr = credits[position];
        value = Double.parseDouble(valueStr);

        if ( point != -1.0 ) {
            double res = (double) point * value;
            TextView2.setText(String.valueOf(res));
        }
    }

    public void onNothingSelected(AdapterView<?> parent){
        Toast.makeText(this, "Choose grades :", Toast.LENGTH_SHORT).show();
    }


    public void GetDataFromEditText(){

        NameHolder = NameEditText.getText().toString().trim();

        NumberHolder = PhoneNumberEditText.getText().toString().trim();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}