package br.com.drivin.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.hardware.Camera;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Method;

import br.com.drivin.R;

public final class Util {

    public static final class View{

        public static float pxToDp(float px) {
            float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
            return px / (densityDpi / 160f);
        }

        public static int dpToPx(int dp) {
            float density = Resources.getSystem().getDisplayMetrics().density;
            return Math.round(dp * density);
        }

        public static void hideKeyboard(Activity activity) {
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }


    }


    public static final class DialogFactory {

        public static android.app.Dialog createSimpleOkDialog(Context context, String title, String message) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setNeutralButton(R.string.dialog_action_ok, null);
            return alertDialog.create();
        }

        public static android.app.Dialog createSimpleOkDialog(Context context,
                                                              @StringRes int titleResource,
                                                              @StringRes int messageResource) {

            return createSimpleOkDialog(context,
                    context.getString(titleResource),
                    context.getString(messageResource));
        }

        public static android.app.Dialog createGenericErrorDialog(Context context, String message) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.dialog_error_title))
                    .setMessage(message)
                    .setNeutralButton(R.string.dialog_action_ok, null);
            return alertDialog.create();
        }

        public static android.app.Dialog createGenericErrorDialog(Context context, @StringRes int messageResource) {
            return createGenericErrorDialog(context, context.getString(messageResource));
        }

        public static android.app.Dialog createGenericErrorDialog(Context context, Throwable throwable) {
            return createGenericErrorDialog(context, throwable.getLocalizedMessage());
        }

        public static ProgressDialog createProgressDialog(Context context, String message) {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);
            return progressDialog;
        }

        public static ProgressDialog createProgressDialog(Context context,
                                                          @StringRes int messageResource) {
            return createProgressDialog(context, context.getString(messageResource));
        }

        public static android.app.Dialog createSimpleConfirmationDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
            return createSimpleConfirmationDialog(context, title, message, listener, true);
        }

        public static android.app.Dialog createSimpleConfirmationDialog(Context context, String title, String message, DialogInterface.OnClickListener listener, boolean cancelButton) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(R.string.dialog_action_ok, listener);

            if (cancelButton){
                alertDialog.setNegativeButton(R.string.dialog_action_cancel, null);
            }

            return alertDialog.create();
        }

        public static android.app.Dialog createSimpleConfirmationDialog(Context context, @StringRes int titleResource,
                                                                        @StringRes int messageResource, DialogInterface.OnClickListener listener) {
            return createSimpleConfirmationDialog(context,
                    context.getString(titleResource),
                    context.getString(messageResource),
                    listener, true);
        }

        public static android.app.Dialog createSimpleConfirmationDialog(Context context, @StringRes int titleResource,
                                                                        @StringRes int messageResource, DialogInterface.OnClickListener listener, boolean cancelButton) {
            return createSimpleConfirmationDialog(context,
                    context.getString(titleResource),
                    context.getString(messageResource),
                    listener, cancelButton);
        }



    }
}
