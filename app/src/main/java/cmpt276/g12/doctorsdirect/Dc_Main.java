package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Dc_Main extends Activity{
TextView welcome_mes;
String username;
	@Override



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dc_main);
		setupbtn();

		Intent dc_login=getIntent();
		username = dc_login.getStringExtra("etDc_uname");
		welcome_mes=(TextView)findViewById(R.id.dc_welText);
		welcome_mes.setText("Welcome "+ username);

	}

	private void setupbtn() {
		// TODO Auto-generated method stub
		Button update = (Button) findViewById(R.id.update);
		Button record = (Button) findViewById(R.id.record);
		Button feedback=(Button) findViewById(R.id.feedback);

		update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent update = new Intent(Dc_Main.this,Db_select_time.class);
				update.putExtra("username",username);
				startActivity(update);
			}
		});
		record.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent record = new Intent(Dc_Main.this,PatientRecord.class);
				record.putExtra("username",username);
				startActivity(record);
			}
		});
		feedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent feedback = new Intent(Dc_Main.this,doctorFeedback.class);
				feedback.putExtra("username",username);
				startActivity(feedback);
			}
		});

	}
	
}
