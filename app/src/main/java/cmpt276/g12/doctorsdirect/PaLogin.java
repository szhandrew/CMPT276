package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PaLogin extends Activity {
	Button btn_PaSignIn;
	Button btn_PaSignUp;
	EditText etPa_uname, etPa_pword;
	SQLiteDatabase sqLiteDatabase;
	DatabaseHelper myDb;
	Cursor res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pa_login);
		//---------------------------------------------------
		etPa_uname=(EditText)findViewById(R.id.pa_uname);
		etPa_pword=(EditText)findViewById(R.id.pa_pword);
		btn_PaSignIn=(Button)findViewById(R.id.btn_PALogin);
		btn_PaSignUp=(Button) findViewById(R.id.register_pa);
		viewData();
		Button_PaSignUp();
	}

	public void Button_PaSignUp()
	{
		btn_PaSignUp.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent pa_reg = new Intent(PaLogin.this, Pa_signup.class);
						startActivity(pa_reg);
					}
				}
		);
	}

	public void viewData()
	{
		btn_PaSignIn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(Check_database()==1) {
							Intent pa_login = new Intent(PaLogin.this, Pa_Main.class);
							pa_login.putExtra("etPa_uname",etPa_uname.getText().toString());
							startActivity(pa_login);
							Toast.makeText(PaLogin.this, "Login successfully!!", Toast.LENGTH_LONG).show();
						}
						else
							Toast.makeText(PaLogin.this, "Login failed:(", Toast.LENGTH_LONG).show();
					}
				}

		);
	}

	int Check_database()
	{
		myDb=new DatabaseHelper(getApplicationContext());
		sqLiteDatabase=myDb.getReadableDatabase();
		res=myDb.getPa_info(sqLiteDatabase);
		while(res.moveToNext()){
			if(etPa_uname.getText().toString().equals(res.getString(2))&&
					etPa_pword.getText().toString().equals((res.getString(3)))) {
				return 1;
			}
		}
		return 0;
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pa_login, menu);
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
