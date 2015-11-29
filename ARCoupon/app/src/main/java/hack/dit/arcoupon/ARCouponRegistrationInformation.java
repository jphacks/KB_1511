package hack.dit.arcoupon;

import android.content.ContentValues;
import android.content.Context;

import com.sonyericsson.extras.liveware.aef.registration.Registration.ExtensionColumns;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

/**
 * Created by kiyomaru on 15/11/29.
 */
public class ARCouponRegistrationInformation extends RegistrationInformation{
    /** The application context. */
    private final Context context;

    /** The control API version in use. */
    private static final int CONTROL_API_VERSION = 4;

    /**
     * Creates a control registration object.
     *
     * @param context The context.
     */
    public ARCouponRegistrationInformation(final Context context) {
        this.context = context;
    }

    @Override
    public int getRequiredControlApiVersion() {
        // This extension supports all accessories from Control API level 1 and
        // up.
        return CONTROL_API_VERSION;
    }

    @Override
    public int getTargetControlApiVersion() {
        return CONTROL_API_VERSION;
    }

    @Override
    public int getRequiredSensorApiVersion() {
        return API_NOT_REQUIRED;
    }

    @Override
    public int getRequiredNotificationApiVersion() {
        return API_NOT_REQUIRED;
    }

    @Override
    public int getRequiredWidgetApiVersion() {
        return API_NOT_REQUIRED;
    }

    @Override
    public ContentValues getExtensionRegistrationConfiguration() {
        String iconHostapp = getUriString(R.mipmap.ic_launcher);
        String iconExtension = getUriString(R.mipmap.ic_launcher);
        String iconExtension48 = getUriString(R.mipmap.ic_launcher);

        ContentValues values = new ContentValues();
        values.put(ExtensionColumns.CONFIGURATION_ACTIVITY,
                ARCouponActivity.class.getName());
        values.put(ExtensionColumns.CONFIGURATION_TEXT,
                context.getString(R.string.configuration_text));
        values.put(ExtensionColumns.NAME,
                context.getString(R.string.extension_name));
        values.put(ExtensionColumns.EXTENSION_KEY, Constants.EXTENSION_KEY);
        values.put(ExtensionColumns.HOST_APP_ICON_URI, iconHostapp);
        values.put(ExtensionColumns.EXTENSION_ICON_URI, iconExtension);
        values.put(ExtensionColumns.EXTENSION_48PX_ICON_URI, iconExtension48);
        values.put(ExtensionColumns.NOTIFICATION_API_VERSION,
                getRequiredNotificationApiVersion());
        values.put(ExtensionColumns.PACKAGE_NAME, context.getPackageName());
        return values;
    }

    @Override
    public boolean isDisplaySizeSupported(final int width, final int height) {
        ScreenSize size = new ScreenSize(context);
        return size.equals(width, height);
    }

    /**
     * Returns the URI string corresponding to the specified resource ID.
     *
     * @param id
     *            The resource ID.
     * @return The URI string.
     */
    private String getUriString(final int id) {
        return ExtensionUtils.getUriString(context, id);
    }


}
