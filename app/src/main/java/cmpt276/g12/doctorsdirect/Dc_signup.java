package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dc_signup extends Activity {
	DatabaseHelper myDb;
	EditText editF_name, editL_name, editUsername, editPassword, editPassword2;
	Button btnSignup_dc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dc_signup);
		//-----------------------------------------------------------
		myDb = new DatabaseHelper(this);
		editF_name=(EditText)findViewById(R.id.dc_FirstName);
		editL_name=(EditText)findViewById(R.id.dc_LastName);
		editUsername=(EditText)findViewById(R.id.dc_UserName);
		editPassword=(EditText)findViewById(R.id.dc_Password);
		editPassword2 = (EditText) findViewById(R.id.dc_Password2);

		btnSignup_dc=(Button)findViewById(R.id.btn_DCSignUp);
		AddData();
	}

public void AddData()
{
	btnSignup_dc.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(editF_name.getText().toString().equalsIgnoreCase(""))
					{
						Toast.makeText(Dc_signup.this, "Please input a valid first name.",Toast.LENGTH_LONG).show();}
					else if (editL_name.getText().toString().equalsIgnoreCase("")) {
						Toast.makeText(Dc_signup.this, "Please input a valid last name.", Toast.LENGTH_LONG).show();
					}
					else if (editUsername.getText().toString().equalsIgnoreCase("")) {
						Toast.makeText(Dc_signup.this, "Please input a valid username.", Toast.LENGTH_LONG).show();
					}
					else if (editPassword.getText().toString().equalsIgnoreCase("")) {
						Toast.makeText(Dc_signup.this, "Please input a valid password.", Toast.LENGTH_LONG).show();
					}
					else if (editPassword.getText().toString().compareTo(editPassword2.getText().toString()) != 0) {
						Toast.makeText(Dc_signup.this, "Make sure the passwords are the same.", Toast.LENGTH_LONG) .show();
					}
					else {
						boolean isInserted = myDb.DC_insertData(editF_name.getText().toString(),
								editL_name.getText().toString(),
								editUsername.getText().toString(),
								editPassword.getText().toString());

						if (isInserted == true) {
							//Intent register = new Intent(Dc_signup.this, Dc_login.class);
							//startActivity(register);
							finish();
							Toast.makeText(Dc_signup.this, "Registered successfully!!", Toast.LENGTH_LONG).show();
						} else
							Toast.makeText(Dc_signup.this, "NOT REGISTERED!! TRY AGAIN", Toast.LENGTH_LONG).show();
					}
				}
			}
	);
}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dc_signup, menu);
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
