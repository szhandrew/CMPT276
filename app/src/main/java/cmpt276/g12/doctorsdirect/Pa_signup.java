package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Pa_signup extends Activity {
    DatabaseHelper myDb;
    EditText editF_name, editL_name, editUsername, editPassword, editPassword2;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pa_signup);
        //------------------------------------------------------------
        myDb = new DatabaseHelper(this);

        editF_name=(EditText)findViewById(R.id.pa_FirstName);
        editL_name=(EditText)findViewById(R.id.pa_LastName);
        editUsername=(EditText)findViewById(R.id.pa_Username);
        editPassword=(EditText)findViewById(R.id.pa_Password);
        editPassword2 = (EditText) findViewById(R.id.pa_Password2);


        btnSignup=(Button)findViewById(R.id.btn_PASignUp);
        AddData();

    }


    public void AddData(){
        btnSignup.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        if(editF_name.getText().toString().equalsIgnoreCase(""))
                        {
                            Toast.makeText(Pa_signup.this, "Please input a valid first name.",Toast.LENGTH_LONG).show();
                        }
                        else if (editL_name.getText().toString().equalsIgnoreCase("")){
                            Toast.makeText(Pa_signup.this, "Please input a last name.", Toast.LENGTH_LONG) .show();
                        }
                        else if (editUsername.getText().toString().equalsIgnoreCase("")){
                            Toast.makeText(Pa_signup.this, "Please input a valid username.", Toast.LENGTH_LONG) .show();
                        }
                        else if (editPassword.getText().toString().equalsIgnoreCase("")){
                            Toast.makeText(Pa_signup.this, "Please input a valid password.", Toast.LENGTH_LONG) .show();
                        }
                        else if (editPassword2.getText().toString().compareTo(editPassword.getText().toString()) != 0){
                            Toast.makeText(Pa_signup.this, "Make sure the passwords are the same.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            boolean isInserted = myDb.PA_insertData(editF_name.getText().toString(),
                                    editL_name.getText().toString(),
                                    editUsername.getText().toString(),
                                    editPassword.getText().toString());

                            if (isInserted == true) {
                                //Intent register = new Intent(Pa_signup.this, Pa_login.class);
                                //startActivity(register);
                                finish();
                                Toast.makeText(Pa_signup.this, "Registered successfully!!", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(Pa_signup.this, "NOT REGISTERED!! TRY AGAIN", Toast.LENGTH_LONG).show();
                        }}
                }
        );
    }





    //-----------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pa_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}