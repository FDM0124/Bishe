package fdm.com.b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RouteSelect extends AppCompatActivity {

    Button button,button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_select);
        btnonclick();


    }

    public  void btnonclick(){

        button=(Button)findViewById(R.id.Busline);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RouteSelect.this,BuslineActivity.class);
                startActivity(intent);
            }
        });


        button1=(Button)findViewById(R.id.Busstation);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RouteSelect.this,BusStationActivity.class);
                startActivity(intent);
            }
        });



    }
}
