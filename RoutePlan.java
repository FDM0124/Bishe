package fdm.com.b;
//RoutePlan
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

public class RoutePlan extends Activity implements AMap.OnMapClickListener,
        AMap.OnMarkerClickListener,GeocodeSearch.OnGeocodeSearchListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener {
    private AMap aMap;
    private MapView mapView;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private BusRouteResult mBusRouteResult;
    private GeocodeSearch geocoderSearch,geocoderSearch1;
    EditText editText,editText1,editText2;
    Double a=null;
    Double b=null;
    Button button;
    Button button1;
    Button button2;

    int A=0;



    private String mCurrentCityName = "广州";
    private final int ROUTE_TYPE_BUS = 1;
  //  private String addressName;

    private LinearLayout mBusResultLayout;
    private RelativeLayout mHeadLayout,mHeadLayout1;
    private ListView mBusResultList;



    LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，116.335891,39.942295
    LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，116.481288,39.995576
  //  private ProgressDialog progDialog = null;// 搜索时进度条
    @Override
    protected void onCreate(Bundle bundle) {

        setContentView(R.layout.activity_route_plan);
        super.onCreate(bundle);

        mContext = this.getApplicationContext();
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(bundle);// 此方法必须重写
        init();



        mapView.setVisibility(View.GONE);
        mBusResultLayout.setVisibility(View.VISIBLE);


        ButtonOnclick();





    }



    private void setfromandtoMarker() {

      //  Intent intent = getIntent();
       // String str = intent.getStringExtra("t");

      //  Double d = 39.942295;


       // Intent intent =getIntent();

      //  String str = intent.getStringExtra("t");
      //  Double d = Double.parseDouble(str);






        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background)));
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background)));
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        registerListener();
        editText=(EditText)findViewById(R.id.route_drive);
        editText1=(EditText)findViewById(R.id.route_bus);

        //test
        //editText2=(EditText)findViewById(R.id.route_test2);
        //editText2.setEnabled(true);


        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);



        mBusResultLayout = (LinearLayout) findViewById(R.id.bus_result);
        mBusResultList = (ListView) findViewById(R.id.bus_result_list);



        mHeadLayout = (RelativeLayout) findViewById(R.id.routemap_header);
        mHeadLayout.setVisibility(View.VISIBLE);


        //Test

        mHeadLayout1 = (RelativeLayout) findViewById(R.id.test);
        mHeadLayout1.setVisibility(View.VISIBLE);




        geocoderSearch = new GeocodeSearch(this);//构造 GeocodeSearch 对象
        geocoderSearch.setOnGeocodeSearchListener(this);//设置监听。
        geocoderSearch1 = new GeocodeSearch(this);//构造 GeocodeSearch 对象
        geocoderSearch1.setOnGeocodeSearchListener(this);//设置监听。


        ///

    }


    private void ButtonOnclick(){
        button =(Button)findViewById(R.id.btn);
        button1 =(Button)findViewById(R.id.btn1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

                A=1;
                String str =editText.getText().toString();
                getLatlon(str);

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A=2;

                String str1 =editText1.getText().toString();
                getLatlon1(str1);



            }
        });

        button2=(Button)findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setfromandtoMarker();
                searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BusDefault);




            }
        });



    }

    /**
     * 注册监听
     */
    private void registerListener() {
        aMap.setOnMapClickListener(RoutePlan.this);
        aMap.setOnMarkerClickListener(RoutePlan.this);
        aMap.setOnInfoWindowClickListener(RoutePlan.this);
        aMap.setInfoWindowAdapter(RoutePlan.this);

    }

    @Override
    public View getInfoContents(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onMarkerClick(Marker arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onMapClick(LatLng arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);

                if(address != null) {

                    //描述地图状态将要发生的变化。 调用参考AMap.animateCamera(CameraUpdate) or AMap.moveCamera(CameraUpdate) 使用CameraUpdateFactory 类里面的一系列方法可以构造CameraUpdate 对象。如CameraUpdateFactory.zoomIn()
                    //   aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(//aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(纬度,经度),缩放级别))
                    //         AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
                    //convertToLatLng :把LatLonPoint对象转化为LatLon对象


                  //  addressName = "" + address.getLatLonPoint();
                    LatLonPoint latLonPoint=address.getLatLonPoint();
                  if(A==1){
                      mStartPoint=address.getLatLonPoint();//获得起点坐标

                  }
                  else if (A==2)
                  {
                      mEndPoint=address.getLatLonPoint();//获得终点位置

                  }

                }
            } else {
                ToastUtil.show(RoutePlan.this, R.string.app_name);
            }
        } else {
            ToastUtil.showerror(this, rCode);
        }

    }


    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {

       // Intent intent = getIntent();
        //tring str = intent.getStringExtra("t");

      //  Toast.makeText(this,str,Toast.LENGTH_SHORT).show();

        //Double d = Double.parseDouble(str);
       // Double d = 39.942295;

        //int num = Integer.parseInt(str);


       // Intent intent =getIntent();

       // String str = intent.getStringExtra("t");
       // Double d = Double.parseDouble(str);





      /*  if (mStartPoint == null) {
            ToastUtil.show(mContext, "定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            ToastUtil.show(mContext, "终点未设置");
        }*/
        //showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_BUS) {// 公交路径规划
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
                    mCurrentCityName, 1);// "mCurrentCityName"第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        }
    }

    /**
     * 规划路线结果回调方法
     */
    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {
      //  dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mBusRouteResult = result;
                    BusResultListAdapter mBusResultListAdapter = new BusResultListAdapter(mContext, mBusRouteResult);
                    mBusResultList.setAdapter(mBusResultListAdapter);
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(mContext, R.string.app_name);
                }
            } else {
                ToastUtil.show(mContext, R.string.app_name);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {

    }


    /**
     * 显示进度框
     */
    /*private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
   /* private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onRideRouteSearched(RideRouteResult arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    public void getLatlon(final String name) {
        //showDialog();


        GeocodeQuery query = new GeocodeQuery(name, "");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求



    }
    public void getLatlon1(final String name) {
        //showDialog();


        GeocodeQuery query1 = new GeocodeQuery(name, "");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch1.getFromLocationNameAsyn(query1);// 设置同步地理编码请求



    }


}

