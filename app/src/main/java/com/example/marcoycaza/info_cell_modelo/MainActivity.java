package com.example.marcoycaza.info_cell_modelo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
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
            TextView tvTecnologia = findViewById(R.id.tvTecnologia);

            switch (getNetworkClass(telephonyManager.getNetworkType())) {

                case "2G":

                    CellInfoGsm cellInfoGsm =
                            (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);

                    tvTecnologia.setText(tvTecnologia.getText().toString()+"Tecnología: GSM");

                    return Integer.toString(cellInfoGsm.getCellSignalStrength().getDbm());

                case "3G":

                    CellInfoWcdma cellinfoWcdma =
                            (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                    tvTecnologia.setText(tvTecnologia.getText().toString()+"Tecnología: UMTS");

                    return Integer.toString(cellinfoWcdma.getCellSignalStrength().getDbm());

                case "4G":

                    CellInfoLte cellInfoLte =
                            (CellInfoLte) telephonyManager.getAllCellInfo().get(0);

                    tvTecnologia.setText("Tecnología: LTE");

                    return Integer.toString(cellInfoLte.getCellSignalStrength().getDbm());

                default:

                    tvTecnologia.setText(tvTecnologia.getText().toString()+": No encontro compatibilidad");

                    return null;

            }

        } catch (Exception e) {

            Log.d("FILTRO",e.toString());
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
            return null;

        }
    }

    public void pulsa(View view) {

        TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.getNetworkType();

        if (telephonyManager != null) {
            TextView tvPotencia = findViewById(R.id.tvPotencia);
            tvPotencia.setText(tvPotencia.getText().toString()+": "+MyTelephonyManager(telephonyManager));
        }
    }

    private String getNetworkClass(int networkType){

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }

    }

}
