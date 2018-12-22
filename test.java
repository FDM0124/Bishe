package fdm.com.b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class test extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView=(TextView)findViewById(R.id.a);

        Intent intent =getIntent();

      textView.setText(intent.getStringExtra("t"));





    }
}
