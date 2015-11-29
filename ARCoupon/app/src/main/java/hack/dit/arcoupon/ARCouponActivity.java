package hack.dit.arcoupon;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonyericsson.extras.liveware.aef.registration.Registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by kiyomaru on 15/11/29.
 */
public class ARCouponActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    public static String shop_name = null;
    public static String detail = null;
    public static Double lat = null;
    public static Double lng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gps);

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
        //TextView tv_provider = (TextView) findViewById(R.id.Provider);
        //tv_provider.setText("Provider: " + provider);

        // LocationListenerを登録
        mLocationManager.requestLocationUpdates(provider, 0, 0, this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 緯度経度の登録
        LatLng location = new LatLng(34.694524, 135.195996);
        // カメラポジション設定
        CameraPosition cameraPos = new CameraPosition.Builder().target(location).zoom(18.0f).bearing(0).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
        // マーカーを作成
        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title("お店の名前").snippet("クーポンの内容"));
        // インフォウィンドウ表示
        marker.showInfoWindow();
    }

    /**
     *  Start the app with the message "Hello SmartEyeglass"
     */
    public void startExtension() {
        // Check ExtensionService is ready and referenced
        if (ARCouponExtensionService.Object != null) {
            ARCouponExtensionService.Object
                    .sendMessageToExtension("Hello SmartEyeglass");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // 緯度の表示
        //TextView tv_lat = (TextView) findViewById(R.id.Latitude);
        //tv_lat.setText("Latitude:" + location.getLatitude());

        // 経度の表示
        //TextView tv_lng = (TextView) findViewById(R.id.Longitude);
        //tv_lng.setText("Latitude:" + location.getLongitude());

        String result = null;
        lat = location.getLatitude();
        lng = location.getLongitude();

        Request request = new Request.Builder()
                .url("http://www.geocoding.jp/?q=lat&lng")
                .get()
                .build();

        // クライアントオブジェクトを作って
        OkHttpClient client = new OkHttpClient();
        /*
        // リクエストして結果を受け取って
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String result = response.body().string();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        //TextView tv_provider = (TextView) findViewById(R.id.Result);
                        //tv_provider.setText(result);

                        shop_name = null;
                        detail = null;
                        lat = null;
                        lng = null;
                        String jsonData = "{\"shop_name\" : \"お店の名前\",\"detail\" : \"クーポンの内容\",\"lat\" : 48.858205,\"lng\" : 2.294359}";

                        try {
                            JSONObject json = new JSONObject(jsonData);
                            shop_name = json.getString("shop_name");
                            detail = json.getString("detail");
                            lat = json.getDouble("lat");
                            lng = json.getDouble("lng");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //tv_provider.setText(shop_name + detail + lat + lng);

                    }
                });
            }
        });


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

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Gps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://hack.dit.arcoupon/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Gps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://hack.dit.arcoupon/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
