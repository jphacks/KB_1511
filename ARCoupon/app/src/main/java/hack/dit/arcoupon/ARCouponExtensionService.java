package hack.dit.arcoupon;

import java.util.List;

import android.content.Intent;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.registration.DeviceInfo;
import com.sonyericsson.extras.liveware.extension.util.registration.DisplayInfo;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationAdapter;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

/**
 * Created by kiyomaru on 15/11/29.
 */
public class ARCouponExtensionService extends ExtensionService {
    /** */
    public static ARCouponControl SmartEyeglassControl;
    /** */
    public static ARCouponExtensionService Object;

    /** */
    private static String Message = null;

    /** Creates a new instance. */
    public ARCouponExtensionService() {
        super(Constants.EXTENSION_KEY);
        Object = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.LOG_TAG, "onCreate: ARCouponExtensionService");
    }

    @Override
    protected RegistrationInformation getRegistrationInformation() {
        return new ARCouponRegistrationInformation(this);
    }

    @Override
    protected boolean keepRunningWhenConnected() {
        return false;
    }

    /**
     * Sets message to be shown when the SmartEyeglass app is ready.
     * Starts the app if it has not already started.
     */
    public void sendMessageToExtension(final String message) {
        Message = message;
        if (SmartEyeglassControl == null) {
            startSmartEyeglassExtension();
        } else {
            SmartEyeglassControl.requestExtensionStart();
        }
    }

    /**
     * You can use this method to kickstart your extension on SmartEyeglass
     * Host App.
     * This is useful if the user has not started your extension
     * since the SmartEyeglass was turned on.
     */
    public void startSmartEyeglassExtension() {
        Intent intent = new Intent(Control.Intents
                .CONTROL_START_REQUEST_INTENT);
        ExtensionUtils.sendToHostApp(getApplicationContext(),
                "com.sony.smarteyeglass", intent);
    }

    /**
     * Sends a  message to be shown in Android activity
     */
    public void sendMessageToActivity(final String message) {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), ARCouponActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Message", message);
        startActivity(intent);
    }

    /**
     * Creates ControlExtension object for the accessory.
     * This creates the HelloWorldControl object after verifying
     * that the connected accessory is a SmartEyeglass.
     */
    @Override
    public ControlExtension createControlExtension(
            final String hostAppPackageName) {
        ScreenSize size = new ScreenSize(this);
        final int width = size.getWidth();
        final int height = size.getHeight();
        List<DeviceInfo> list = RegistrationAdapter.getHostApplication(
                this, hostAppPackageName).getDevices();
        for (DeviceInfo device : list) {
            for (DisplayInfo display : device.getDisplays()) {
                if (display.sizeEquals(width, height)) {
                    return new ARCouponControl(this,
                            hostAppPackageName, Message);
                }
            }
        }
        throw new IllegalArgumentException("No control for: "
                + hostAppPackageName);
    }
}
