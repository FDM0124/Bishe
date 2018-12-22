package fdm.com.b;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * 地理编码与逆地理编码功能介绍
 */
public class GeocoderActivity extends Activity implements
        OnGeocodeSearchListener, OnClickListener {
	//private ProgressDialog progDialog = null;
	private GeocodeSearch geocoderSearch;
	private String addressName;
	private AMap aMap;
	private MapView mapView;
	private Marker geoMarker;
	EditText station;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geocoder_activity);

	
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
	    //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
		// MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写


		init();



	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)//anchor点标记的锚点
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		 station =(EditText)findViewById(R.id.station);
		Button geoButton = (Button) findViewById(R.id.geoButton);
		geoButton.setText("位置查询");
		geoButton.setOnClickListener(this);

		Button dingwei = (Button) findViewById(R.id.dingwei);
		dingwei.setText("定位");
		dingwei.setOnClickListener(this);
		geocoderSearch = new GeocodeSearch(this);//构造 GeocodeSearch 对象
		geocoderSearch.setOnGeocodeSearchListener(this);//设置监听。
		//progDialog = new ProgressDialog(this);
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

	/**
	 * 显示进度条对话框
	 */
	/*public void showDialog() {
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在获取地址");
		progDialog.show();
	}

	/**
	 * 隐藏进度条对话框
	 */
	/*public void dismissDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * 响应地理编码
	 */
	public void getLatlon(final String name) {
		//showDialog();


		GeocodeQuery query = new GeocodeQuery(name, "");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
		geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
	}

	/**
	 * 地理编码查询回调
	 */
	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode) {//通过回调接口 onGeocodeSearched 解析返回的结果。
		//dismissDialog();//解析result获取坐标信息  ,返回结果成功或者失败的响应码。1000为成功，其他为失败
		if (rCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getGeocodeAddressList() != null
					&& result.getGeocodeAddressList().size() > 0) {
				GeocodeAddress address = result.getGeocodeAddressList().get(0);

				if(address != null) {

					//描述地图状态将要发生的变化。 调用参考AMap.animateCamera(CameraUpdate) or AMap.moveCamera(CameraUpdate) 使用CameraUpdateFactory 类里面的一系列方法可以构造CameraUpdate 对象。如CameraUpdateFactory.zoomIn()
					aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(//aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(纬度,经度),缩放级别))
							AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
								//convertToLatLng :把LatLonPoint对象转化为LatLon对象

					geoMarker.setPosition(AMapUtil.convertToLatLng(address
							.getLatLonPoint()));
					addressName = address.getLatLonPoint() + "\n位置描述:"
							+ address.getFormatAddress();
					LatLonPoint latLonPoint1=address.getLatLonPoint();
					ToastUtil.show(GeocoderActivity.this, addressName);
				}
			} else {
				ToastUtil.show(GeocoderActivity.this, R.string.app_name);
			}
		} else {
			ToastUtil.showerror(this, rCode);
		}
	}

	/**
	 * 逆地理编码回调
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {


	}

	public void show(){
		Intent intent =getIntent();

		String str = intent.getStringExtra("t");

		getLatlon(str);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 响应地理编码按钮
		 */


		case R.id.geoButton:
			//Intent intent =getIntent();

			//String str = intent.getStringExtra("t");
			aMap.setMyLocationEnabled(false);//隐藏定位蓝点并不进行定位，
			String str =station.getText().toString();

					getLatlon(str);

			break;


			case R.id.dingwei:
				//Intent intent =getIntent();

				//String str = intent.getStringExtra("t");
				Toast.makeText(this,"定位",Toast.LENGTH_SHORT).show();

				MyLocationStyle myLocationStyle;
				myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
				//myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
				aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
				aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。



				break;

		}
	}


}