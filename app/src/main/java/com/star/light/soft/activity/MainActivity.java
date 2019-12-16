package com.star.light.soft.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.star.light.soft.R;
import com.star.light.soft.service.KeyEventReceiver;
import com.star.light.soft.service.ScreenLockService;
import com.star.light.soft.utils.SharedPref;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;
import static com.star.light.soft.Constants.BROADCAST_SCREEN_ACTION;
import static com.star.light.soft.Constants.PREFS_BUTTON_STATUS;

public class MainActivity extends AppCompatActivity {

    private static final int PERMIT_DRAWING_OVER_OTHER_APPS = 857;
    private static final int DOUBLE_CLICK_DELAY_IN_MILLISECONDS = 150;
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_status_btn)
    Button statusBtn;

    @BindString(R.string.warning_draw_permission)
    String permissionWarning;

    @BindString(R.string.main_status_btn_on)
    String on;

    @BindString(R.string.main_status_btn_off)
    String off;

    private long volUpEventTime = 0;
    private long volDownEventTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setStatus(SharedPref.getBoolean(PREFS_BUTTON_STATUS));

        Intent service = new Intent(this, ScreenLockService.class);
        service.setFlags(FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));

            startActivityForResult(intent, PERMIT_DRAWING_OVER_OTHER_APPS);
        }

        IntentFilter filter = new IntentFilter(BROADCAST_SCREEN_ACTION);
        registerReceiver(new KeyEventReceiver(), filter);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMIT_DRAWING_OVER_OTHER_APPS) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this,
                        permissionWarning,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Timber.tag(TAG).i("KeyCode = %s \n KeyEvent = %s", keyCode, event);
        if (SharedPref.getBoolean(PREFS_BUTTON_STATUS)) {
            switch (event.getKeyCode()) {
                case KEYCODE_VOLUME_UP:
                    volUpEventTime = event.getEventTime();
                    checkDoubleClick();
                    return true;
                case KEYCODE_VOLUME_DOWN:
                    volDownEventTime = event.getEventTime();
                    checkDoubleClick();
                    return true;
                default:
                    return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick(R.id.main_status_btn)
    void setOnStatusClickL() {
        SharedPref.save(PREFS_BUTTON_STATUS, !SharedPref.getBoolean(PREFS_BUTTON_STATUS));
        setStatus(SharedPref.getBoolean(PREFS_BUTTON_STATUS));
    }

    private void setStatus(boolean status) {
        statusBtn.setText(status ? off : on);
        if (status) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void checkDoubleClick() {
        if (Math.abs(volUpEventTime - volDownEventTime) <= DOUBLE_CLICK_DELAY_IN_MILLISECONDS) {
            Intent intent = new Intent(BROADCAST_SCREEN_ACTION);
            sendBroadcast(intent);
        }
    }
}