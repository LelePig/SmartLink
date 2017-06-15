package com.example.user.smartlink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.mediatek.elian.ElianNative;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    TextView wifiName,tx_result;
    Button bt_send,bt_stop;
    EditText et_pwd;
    ElianNative elianNative;
    private boolean isRegFilter=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        wifiName=(TextView)findViewById(R.id.wifi_name);
        tx_result=(TextView)findViewById(R.id.tx_result);
        bt_send=(Button)findViewById(R.id.bt_send);
        bt_stop=(Button)findViewById(R.id.bt_stop);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
        bt_send.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        currenWifi();
        elianNative=new ElianNative();
        boolean isLoad=elianNative.LoadLib();
        regFilter();
        Log.e("leleTest","lele like");

    }
    public void regFilter(){
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(br,filter);
        isRegFilter=true;
    }
    BroadcastReceiver br=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
                currenWifi();
            }
        }
    };

    private byte mAuthMode;
    private byte AuthModeOpen = 0;
    private byte AuthModeWPA = 3;
    private byte AuthModeWPA1PSKWPA2PSK = 9;
    private byte AuthModeWPA1WPA2 = 8;
    private byte AuthModeWPA2 = 6;
    private byte AuthModeWPA2PSK = 7;
    private byte AuthModeWPAPSK = 4;
    String ssid;
    private void currenWifi(){
        WifiInfo wifiInfo =getConnectWifiInfo();
        if (wifiInfo == null) {
            return;
        }
        ssid = wifiInfo.getSSID();
        if (ssid == null) {
            return;
        }
        if (ssid.equals("")) {
            return;
        }
        if (ssid.length() <= 0) {
            return;
        }
        int a = ssid.charAt(0);
        if (a == 34) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        if (!ssid.equals("<unknown ssid>") && !ssid.equals("0x")) {
            wifiName.setText(ssid);
        }
        List<ScanResult> wifiList = getLists();
        if (wifiList == null) {
            return;
        }
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult result = wifiList.get(i);
//            tx_result.append(result.SSID+"\n");
            if (!result.SSID.equals(ssid)) {
                continue;
            }
            boolean bool1 = result.capabilities.contains("WPA-PSK");
            boolean bool2 = result.capabilities.contains("WPA2-PSK");
            boolean bool3 = result.capabilities.contains("WPA-EAP");
            boolean bool4 = result.capabilities.contains("WPA2-EAP");
            if (result.capabilities.contains("WEP")) {
                this.mAuthMode = this.AuthModeOpen;
            }
            if ((bool1) && (bool2)) {
                mAuthMode = AuthModeWPA1PSKWPA2PSK;
            } else if (bool2) {
                this.mAuthMode = this.AuthModeWPA2PSK;
            } else if (bool1) {
                this.mAuthMode = this.AuthModeWPAPSK;
            } else if ((bool3) && (bool4)) {
                this.mAuthMode = this.AuthModeWPA1WPA2;
            } else if (bool4) {
                this.mAuthMode = this.AuthModeWPA2;
            } else {
                if (!bool3)
                    break;
                this.mAuthMode = this.AuthModeWPA;
            }
        }
//        WPA/WPA2 PSK  9
//        WPA2 PSK      7
//        WPA PSK       4
//        WPA/WPA2 EAP  8
//        WPA2 EAP      6
//        WPA EAP       3
//        其它          0

    }
    //获取当前连接wifi
    public WifiInfo getConnectWifiInfo() {
        if (!isWifiConnected(context)) {
            return null;
        }
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return null;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo;
    }

    /**
     * 判断是否连接上wifi
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }

    /**
     * 获取wifi列表
     * @return
     */
    public List<ScanResult> getLists() {
        WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> lists = wifiManager.getScanResults();
        return lists;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_send:
                String pwd=et_pwd.getText().toString().trim();
                Log.e("leleTest","ssid="+ssid+"pwd="+pwd+"mAuthMode="+mAuthMode);
                elianNative.InitSmartConnection(null,1,1);
                elianNative.StartSmartConnection(ssid,pwd,"",mAuthMode);
                break;
            case R.id.bt_stop:
                elianNative.StopSmartConnection();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        elianNative.StopSmartConnection();
        if(isRegFilter){
            unregisterReceiver(br);
            isRegFilter=false;
        }
    }
}
