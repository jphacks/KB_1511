package hack.dit.arcoupon;

import android.content.Context;
import android.util.Log;
import com.sony.smarteyeglass.SmartEyeglassControl;
import com.sony.smarteyeglass.extension.util.SmartEyeglassControlUtils;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;

/**
 * Created by kiyomaru on 15/11/29.
 */
public class ARCouponControl extends ControlExtension{
    private final SmartEyeglassControlUtils utils;
    private static final int SMARTEYEGLASS_API_VERSION = 1;

    /**
     * Shows a simple layout on the SmartEyeglass display and sets
     * the text content dynamically at startup.
     * Tap on the device controller touch pad to start the Android activity
     * for this app on the phone.
     * Tap the Android activity button to run the SmartEyeglass app.
     *
     * @param context            The context.
     * @param hostAppPackageName Package name of SmartEyeglass host application.
     */
    public ARCouponControl(final Context context,
                             final String hostAppPackageName, final String message) {
        super(context, hostAppPackageName);
        utils = new SmartEyeglassControlUtils(hostAppPackageName, null);
        utils.setRequiredApiVersion(SMARTEYEGLASS_API_VERSION);
        utils.activate(context);

        /*
         * Set reference back to this Control object
         * in ExtensionService class to allow access to SmartEyeglass Control
         */
        ARCouponExtensionService.Object.SmartEyeglassControl = this;

        /*
         * Show the message that was set Iif any) when this Control started
         */
        if (message != null) {
            showToast(message);
        } else {
            updateLayout();
        }
    }

    /**
     * Provides a public method for ExtensionService and Activity to call in
     * order to request start.
     */
    public void requestExtensionStart() {
        startRequest();
    }

    // Update the SmartEyeglass display when app becomes visible
    @Override
    public void onResume() {
        updateLayout();
        super.onResume();
    }

    // Clean up data structures on termination.
    @Override
    public void onDestroy() {
        Log.d(Constants.LOG_TAG, "onDestroy: ARCouponWorldControl");
        utils.deactivate();
    };

    /**
     * Process Touch events.
     * This starts the Android Activity for the app, passing a startup message.
     */
    @Override
    public void onTouch(final ControlTouchEvent event) {
        super.onTouch(event);
        ARCouponExtensionService.Object
                .sendMessageToActivity("ARCoupon Activity");
    }

    /**
     *  Update the display with the dynamic message text.
     */
    private void updateLayout() {
        showLayout(R.layout.layout, null);
        sendText(R.id.btn_update_this1, "ミント神戸");
        sendText(R.id.btn_update_this2, "【タイムセール！！】全品10%OFF!!");
    }

    /**
     * Timeout dialog messages are similar to Toast messages on
     * Android Activities
     * This shows a timeout dialog with the specified message.
     */
    public void showToast(final String message) {
        Log.d(Constants.LOG_TAG, "Timeout Dialog : HelloWorldControl");
        utils.showDialogMessage(message,
                SmartEyeglassControl.Intents.DIALOG_MODE_TIMEOUT);
    }

}
