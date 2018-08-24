package in.techfantasy.hope;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class MainActivity extends AppCompatActivity {

    EditText etxtName,etxtPhone,etxtLocation;
    Button btnJoin;
    Boolean permissionsGranted=false;
    String latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        compinit();


        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please grant the following permissions to continue";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this,"Granted",Toast.LENGTH_SHORT).show();
                permissionsGranted=true;

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this,"Sorry, You must grant permissions to use this app.",Toast.LENGTH_LONG).show();
                finish();
                permissionsGranted=false;
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
            if(Connectivity.isConnected(MainActivity.this)&&permissionsGranted){
                if(checkGPS()) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Problem with GPS Connection",Toast.LENGTH_SHORT).show();
                    showGPSDisabledAlertToUser();
                }
            }
            else{
                Toast.makeText(MainActivity.this,"Not Connected",Toast.LENGTH_SHORT).show();
            }
            }
        });





    }

    private void compinit(){
        etxtName = findViewById(R.id.etxtName);
        etxtPhone = findViewById(R.id.etxtPhone);
        etxtLocation = findViewById(R.id.etxtLocation);
        btnJoin = findViewById(R.id.btnJoin);

        SmartLocation.with(MainActivity.this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                etxtLocation.setText("Loc:"+location.getLatitude()+","+location.getLongitude());
            }
        });
    }

    private boolean checkGPS(){
        if(etxtLocation.getText().toString().equals("")) {
            return false;
        }
        else{
            return true;
        }
    }
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Toast.makeText(MainActivity.this, "Please turn on GPS to use this App", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        finish();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
