package hack.dit.arcoupon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by kiyomaru on 15/11/29.
 */
public class HelloWorldExtensionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(Constants.LOG_TAG, "onReceive: " + intent.getAction());
        intent.setClass(context, ARCouponExtensionService.class);
        context.startService(intent);
    }
}
