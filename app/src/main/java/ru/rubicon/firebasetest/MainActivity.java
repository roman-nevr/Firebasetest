package ru.rubicon.firebasetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import ru.rubicon.firebasetest.entity.Person;
import ru.rubicon.firebasetest.utils.Config;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAddress;
    private TextView textViewPersons;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);

        textViewPersons = (TextView) findViewById(R.id.textViewPersons);
        Firebase.setAndroidContext(this);
        //Click Listener for button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object
                Firebase ref = new Firebase(Config.FIREBASE_URL);

                //Getting values to store
                String name = editTextName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();

                //Creating Person object
                Person person = new Person();

                //Adding values
                person.setName(name);
                person.setAddress(address);

                //Storing values to firebase
                ref.child("Person").setValue(person);

                //Value event listener for realtime data update
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //Getting the data from snapshot
                            Person person = postSnapshot.getValue(Person.class);

                            //Adding it to a string
                            String string = "Name: "+person.getName()+"\nAddress: "+person.getAddress()+"\n\n";

                            //Displaying it on textview
                            textViewPersons.setText(string);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }
        });
    }

    /*
            //Value event listener for realtime data update
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //Getting the data from snapshot
                            Person person = postSnapshot.getValue(Person.class);

                            //Adding it to a string
                            String string = "Name: "+person.getName()+"\nAddress: "+person.getAddress()+"\n\n";

                            //Displaying it on textview
                            textViewPersons.setText(string);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
     */


}
