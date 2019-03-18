package com.example.android.wifisignalstrength;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class display extends AppCompatActivity {

    String fileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int rssi = wifiManager.getConnectionInfo().getRssi();
        int calc=wifiManager.calculateSignalLevel(rssi,5);

        int linkSpeed=wifiManager.getConnectionInfo().getLinkSpeed();
        String ssid=wifiManager.getConnectionInfo().getSSID();

        TextView textView=findViewById(R.id.ssid);
        textView.setText(ssid);

        TextView textView1=findViewById(R.id.linkspeed);
        textView1.setText(String.valueOf(linkSpeed)+"Mbps");

        TextView textView2=findViewById(R.id.rssi);
        textView2.setText(String.valueOf(rssi)+"dBm");

        TextView textView3=findViewById(R.id.strength);
        textView3.setText(String.valueOf(calc));

        fileContents="SSID:"+ssid+"\n"+
                "Link Speed: "+String.valueOf(linkSpeed)+"Mbps"+"\n"
                +"RSSI: "+String.valueOf(rssi)+"\n"
                +"Strength(Out of 5): "+String.valueOf(calc);

        //createFile(this);
        Button button=findViewById(R.id.info);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile();
                Toast.makeText(display.this,"File created",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void writeToFile(){
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/MyApp");
        dir.mkdirs();
        File file = new File(dir, "info.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.print(fileContents);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
