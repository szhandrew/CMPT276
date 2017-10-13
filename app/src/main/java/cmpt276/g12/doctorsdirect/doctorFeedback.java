package cmpt276.g12.doctorsdirect;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;



public class doctorFeedback extends Activity {

    String username;
    DatabaseHelper db;
    TextView textview;
    Button clear_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_feedback);
      clear_btn =(Button) findViewById(R.id.clearHistory);
       textview = (TextView) findViewById(R.id.feedback);
        db = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");
        setClearBtn();
        getFeedback();
    }

    public void getFeedback(){
        String review = db.getReview(username);
        textview.setText(review);

    }

    public void setClearBtn(){
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked", Toast.LENGTH_SHORT).show();
               clearHistory();
            }
        });
    }

    public void clearHistory(){
        boolean clear = db.clearFeedback(username);
        if (clear == true){
            Toast.makeText(getApplicationContext(),"Clear Successful", Toast.LENGTH_SHORT).show();
            getFeedback();
            return;
        }
        else{
            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
        }

    }

}

