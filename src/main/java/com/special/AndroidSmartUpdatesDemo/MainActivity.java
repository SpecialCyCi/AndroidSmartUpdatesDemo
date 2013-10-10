package com.special.AndroidSmartUpdatesDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.special.AndroidSmartUpdates.SmartUpdates;
import com.special.AndroidSmartUpdates.helper.SystemHelper;
import com.special.AndroidSmartUpdates.impl.UpdateListener;
import com.special.AndroidSmartUpdates.model.PatchInformation;


public class MainActivity extends Activity {

    private static final String UPDATE_SERVER_ADDRESS = "http://192.168.1.171:3000/";
    private static final int    APPLICATION_ID = 4;
    private SmartUpdates smartUpdates;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        setUpView();

        smartUpdates = new SmartUpdates(this);
        smartUpdates.setServerAddress(UPDATE_SERVER_ADDRESS);
        smartUpdates.setApplicationId(APPLICATION_ID);
        smartUpdates.checkForUpdate();
    }

    private void setUpView(){
        TextView tv_version_code = (TextView) findViewById(R.id.tv_version_code);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        tv_version_code.setText(String.valueOf(SystemHelper.getVersionCode(this)));
        btn_update.setOnClickListener(checkUpdateOnClickListener);
    }

    private View.OnClickListener checkUpdateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            smartUpdates.setShowUpdateDialog(false);
            smartUpdates.setUpdateListener(updateListener);
            smartUpdates.checkForUpdate();
        }
    };

    private UpdateListener updateListener = new UpdateListener() {

        @Override
        public void hasUpdate(PatchInformation information) {
            Toast.makeText(context, "has update, version:" + information.getVersionName(),
                    Toast.LENGTH_LONG).show();
            smartUpdates.startUpdate();
        }

        @Override
        public void hasNoUpdate() {
            Toast.makeText(context, "no update", Toast.LENGTH_LONG).show();
        }
    };
}
