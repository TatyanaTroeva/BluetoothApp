package com.bluetoothapp.main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView statusTextView_ = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		setListener();
		initData();
	}
	public void initView() {
		statusTextView_ = (TextView) findViewById(R.id.statusTextView);
	}
	public void setListener() {
		
	}
	public void initData() {
		registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
		BluetoothAdapter btAdapter = ((Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1)?
				                     ((BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter()
				                     :(BluetoothAdapter.getDefaultAdapter()));

        if(btAdapter==null){
        	return;
        }
        if(btAdapter.getState()==BluetoothAdapter.STATE_ON){
        	statusTextView_.setText(getResources().getString(R.string.bluetooth_on));
        } else {
        	statusTextView_.setText(getResources().getString(R.string.bluetooth_off));
        }
	}
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive (Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_OFF) {
                	statusTextView_.setText(getResources().getString(R.string.bluetooth_off));
            	} else if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_ON){
            		statusTextView_.setText(getResources().getString(R.string.bluetooth_on));
            	}

        	}
        }
    };
}

