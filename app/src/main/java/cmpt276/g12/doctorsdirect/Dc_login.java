package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dc_login extends Activity {
	Button btn_DcSignIn;
	Button btn_DcSignUp;
	EditText etDc_uname, etDc_pword;
	SQLiteDatabase sqLiteDatabase;
	DatabaseHelper myDb;
	Cursor res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dc_login);
		//-----------------------------------------------------------
		etDc_uname=(EditText)findViewById(R.id.dc_Uname);
		etDc_pword=(EditText)findViewById(R.id.dc_Pword);
		btn_DcSignIn=(Button) findViewById(R.id.btn_DCLogin);
		btn_DcSignUp=(Button)findViewById(R.id.register_dc);
		viewData();
		Button_DcSignUp();
	}

	public void Button_DcSignUp()
	{
		btn_DcSignUp.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent dc_reg = new Intent(Dc_login.this, Dc_signup.class);
						startActivity(dc_reg);
					}
				}
		);
	}

	public void viewData()
	{
		btn_DcSignIn.setOnClickListener(
				new View.OnClickListener() {
					@Override


					public void onClick(View v) {
						if(Check_database()==1) {
							Intent dc_login = new Intent(Dc_login.this, Dc_Main.class);
							dc_login.putExtra("etDc_uname",etDc_uname.getText().toString());
							startActivity(dc_login);

							Toast.makeText(Dc_login.this, "Login successfully!!", Toast.LENGTH_LONG).show();
						}
						else
							Toast.makeText(Dc_login.this, "Login failed:(", Toast.LENGTH_LONG).show();
					}
				}

		);
	}

	int Check_database()
	{
		myDb=new DatabaseHelper(getApplicationContext());
		sqLiteDatabase=myDb.getReadableDatabase();
		res=myDb.getDc_info(sqLiteDatabase);
		while(res.moveToNext()){
			if(etDc_uname.getText().toString().equals(res.getString(2))&&
					etDc_pword.getText().toString().equals((res.getString(3)))) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dc_login, menu);
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
