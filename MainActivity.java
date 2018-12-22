package fdm.com.b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.view.View.*;

public class MainActivity extends Activity {
    MapView mMapView = null;
   // EditText start;
  //  EditText stop;
    Button button,button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // start=(EditText)findViewById(R.id.start);
       //stop=(EditText)findViewById(R.id.stop);

        button=(Button)findViewById(R.id.select);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,GeocoderActivity.class);
//                intent.putExtra("t",start.getText().toString());
                startActivity(intent);
            }
        });


        button1=(Button)findViewById(R.id.select1);

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RouteSelect.class);
                startActivity(intent);
            }
        });


        button2=(Button)findViewById(R.id.select2);

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoutePlan.class);
               // int id;
              //  id = Integer.valueOf(start.getText().toString());
//                intent.putExtra("t",start.getText().toString());
                //intent.setClass();
                startActivity(intent);
                //Toast.makeText(this,start.getText().toString(),Toast.LENGTH_SHORT).show();

            }
        });


        //获取地图控件引用
       // mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        //mMapView.onCreate(savedInstanceState);
        //mMapView.setVisibility(GONE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
      //  mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
       // mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
      //  mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
      //  mMapView.onSaveInstanceState(outState);
    }
}