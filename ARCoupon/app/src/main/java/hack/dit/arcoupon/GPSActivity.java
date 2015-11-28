package hack.dit.arcoupon;

import android.app.Activity;
import android.app.LoaderManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.content.Context;
import android.util.Log;
import com.sony.smarteyeglass.SmartEyeglassControl;
import com.sony.smarteyeglass.extension.util.SmartEyeglassControlUtils;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;

import android.location.Location;
import android.location.LocationListener;
import android.location.Location.LocationManager;

public class GPSActivity extends Activity implements LocationListener{
    private LoaderManager mLocationManager;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.activity_test);

        // GPS
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsFlg = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("GPS Enabled", gpsFlg?"OK":"NG");
    }

    // GPSボタン
    public void onBtnGpsClicked(View view) {
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, //LocationManager.NETWORK_PROVIDER,
                3000, // 通知のための最小時間間隔（ミリ秒）
                10, // 通知のための最小距離間隔（メートル）
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        String msg = "Lat=" + location.getLatitude()
                                + "\nLng=" + location.getLongitude();
                        Log.d("GPS", msg);
                        mLocationManager.removeUpdates(this);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                }
        );
    }

}