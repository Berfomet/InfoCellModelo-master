package com.example.marcoycaza.info_cell_modelo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int PERMISSION_READ_STATE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);

        try {
            int permissionCheck =
                    ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    );

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_READ_STATE);
            }
        }catch (Exception e){
            Log.i("Error1",e.toString());
            Toast.makeText(this, "error"+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public String MyTelephonyManager(TelephonyManager telephonyManager) {

        try {

            List<CellInfo> cellList = telephonyManager.getAllCellInfo();

                CellInfoWcdma cellinfoWcdma =
                        (CellInfoWcdma)telephonyManager.getAllCellInfo().get(0);

                Integer cellSignalStrengthWcdma =
                        cellinfoWcdma.getCellSignalStrength().getDbm();

                return cellSignalStrengthWcdma.toString();

        } catch (Exception e) {

            Log.d("FILTRO",e.toString());
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
            return null;

        }
    }






































































    public void pulsa(View view) {

        TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            TextView textView = findViewById(R.id.textView);
            textView.setText(MyTelephonyManager(telephonyManager));
        }
    }
}
