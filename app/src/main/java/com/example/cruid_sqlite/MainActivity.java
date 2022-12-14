package com.example.cruid_sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper myDb;
    EditText name, surname, marks, ID;
    Button add, view, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        name=(EditText)findViewById(R.id.name);
        surname=(EditText)findViewById(R.id.surname);
        marks=(EditText)findViewById(R.id.marks);
        ID=(EditText)findViewById(R.id.id);
        add=(Button)findViewById(R.id.add);
        view =(Button)findViewById(R.id.view);
        update=(Button)findViewById(R.id.update);
        delete=(Button)findViewById(R.id.delete);
        addData();
        viewData();
        UpdateData();
        deleteData();
    }
    public void UpdateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated=myDb.update(ID.getText().toString(),name.getText().toString(), surname.getText().toString(), marks.getText().toString() );
                if(isUpdated == true){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error Data not Updated", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void deleteData(){
delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Integer deletedRows=myDb.deleteData(delete.getText().toString());
        if(deletedRows > 0){
            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "Error Data not Deleted", Toast.LENGTH_LONG).show();
        }

    }
});
    }
    public void addData(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isInserted= myDb.insertData(name.getText().toString(), surname.getText().toString(), marks.getText().toString());
               if(isInserted){
                   Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
               }else{
                   Toast.makeText(MainActivity.this, "Error Data not inserted", Toast.LENGTH_LONG).show();
               }
            }
        });
    }
    public void viewData(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Cursor res=  myDb.getAllData();
              if(res.getCount()==0){
                  //show some message
                  showMessage("Error", "No data found");

                  return;
              }else{
                  StringBuffer buffer=new StringBuffer();
                  while (res.moveToNext()){
                      buffer.append("ID :"+res.getString(0)+"\n");
                      buffer.append("Name :"+res.getString(1)+"\n");
                      buffer.append("Surname :"+res.getString(2)+"\n");
                      buffer.append("Marks :"+res.getString(3)+"\n\n");
                  }
                  //show all data
                 showMessage("Data", buffer.toString());
              }
            }
        });
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
