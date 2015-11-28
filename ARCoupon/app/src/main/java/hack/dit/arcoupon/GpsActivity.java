package hack.dit.arcoupon;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GpsActivity extends AppCompatActivity implements LocationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        // LocationManagerを取得
        LocationManager mLocationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Criteriaオブジェクトを生成
        Criteria criteria = new Criteria();

        // Accuracyを指定(低精度)
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        // PowerRequirementを指定(低消費電力)
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        // ロケーションプロバイダの取得
        String provider = mLocationManager.getBestProvider(criteria, true);

        // 取得したロケーションプロバイダを表示
        TextView tv_provider = (TextView) findViewById(R.id.Provider);
        tv_provider.setText("Provider: " + provider);

        // LocationListenerを登録
        mLocationManager.requestLocationUpdates(provider, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // 緯度の表示
        TextView tv_lat = (TextView) findViewById(R.id.Latitude);
        tv_lat.setText("Latitude:"+location.getLatitude());

        // 経度の表示
        TextView tv_lng = (TextView) findViewById(R.id.Longitude);
        tv_lng.setText("Latitude:"+location.getLongitude());

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}
