package cmpt276.g12.doctorsdirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pa_Main extends Activity {
	TextView welcome_mes;
	String pa_username;
	Button advising;
	Button review;
	Button findClinic;
	Button message;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pa_main);
		setBtn();

		Intent pa_login = getIntent();
		pa_username = pa_login.getStringExtra("etPa_uname");
		welcome_mes = (TextView) findViewById(R.id.Pa_welMes);
		welcome_mes.setText("Welcome " + pa_username);

	}

	public void setBtn() {
		advising = (Button) findViewById(R.id.advising);
		review = (Button) findViewById(R.id.review);
		findClinic = (Button) findViewById(R.id.findClinic);

		advising.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent pa_appManage = new Intent(Pa_Main.this, Pa_apManage.class);
				pa_appManage.putExtra("Pa_uname", pa_username);
				startActivity(pa_appManage);
			}
		});

		review.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent startReview = new Intent(Pa_Main.this, ReadReview.class);
				startActivity(startReview);

			}
		});

		findClinic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent clinic_loc= new Intent(Pa_Main.this, clinic_locator.class);
				startActivity(clinic_loc);
			}
		});


	}
}

