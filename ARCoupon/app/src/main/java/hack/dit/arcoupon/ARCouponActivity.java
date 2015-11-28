package hack.dit.arcoupon;

import com.sonyericsson.extras.liveware.aef.registration.Registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by kiyomaru on 15/11/29.
 */
public class ARCouponActivity extends Activity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // When button is clicked, run the SmartEyeglass app
        //Button btnGlass = (Button) findViewById(R.id.btnglass);
        /*
        btnGlass.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                startExtension();
            }
        });
        */
        startExtension();
        /*
         * Check if activity was started with a message in the intent
         * If there is a message, show it as a Toast message
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String message = extras.getString("Message");
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
                    .show();
        }

        /*
         * Make sure ExtensionService of your SmartEyeglass app has already
         * started.
         * This is normally started automatically when user enters your app
         * on SmartEyeglass, although you can initialize it early using
         * request intent.
         */
        if (ARCouponExtensionService.Object == null) {
            Intent intent = new Intent(Registration.Intents
                    .EXTENSION_REGISTER_REQUEST_INTENT);
            Context context = getApplicationContext();
            intent.setClass(context, ARCouponExtensionService.class);
            context.startService(intent);
        }
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
}
