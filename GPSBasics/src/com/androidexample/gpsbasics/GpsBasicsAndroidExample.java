package com.androidexample.gpsbasics;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;




public class GpsBasicsAndroidExample extends Activity implements LocationListener {

	Button btnShowLocation;
	Button btnResetCounter;
	Button btnEmail;
	Button btnClear;
	TextView txtLocation;
	EditText etxtLocation;
	Integer locCount;
	
	// GPSTracker class
    GPSTracker gps;
    
	private LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps_basics_android_example);
		
		locCount = 0;
		
		btnShowLocation = (Button) findViewById(R.id.button1);
		btnResetCounter = (Button) findViewById(R.id.buttonReset);
		btnEmail = (Button) findViewById(R.id.buttonEmail);
		btnClear = (Button) findViewById(R.id.buttonClear);
        txtLocation = (TextView) findViewById(R.id.textView1);
        etxtLocation = (EditText) findViewById(R.id.editText1);
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
            	
            	// create class object
                gps = new GPSTracker(GpsBasicsAndroidExample.this);
 
                // check if GPS enabled     
                if(gps.canGetLocation()){
                	locCount++;
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    //txtLocation.append("\n" + latitude + "," + longitude);
                    etxtLocation.append("\n" + locCount + "," + latitude + "," + longitude);
                    // \n is for new line
                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                 
            }
        });
        
        // show Reset button click event
        btnResetCounter.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
            	locCount = 0;
            	Toast.makeText(getApplicationContext(), "Counter Reset", Toast.LENGTH_LONG).show();
            }
        });
        
        // show Email button click event
        btnEmail.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
            	Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();
            }
        });
        
     // show Clear button click event
        btnClear.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
            	etxtLocation.setText("");
            	Toast.makeText(getApplicationContext(), "Clear Contents", Toast.LENGTH_LONG).show();
            }
        });
        
        
		/********** get Gps location service LocationManager object ***********/
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		/*
		  Parameters :
		     First(provider)    :  the name of the provider with which to register 
		     Second(minTime)    :  the minimum time interval for notifications, in milliseconds. This field is only used as a hint to conserve power, and actual time between location updates may be greater or lesser than this value. 
		     Third(minDistance) :  the minimum distance interval for notifications, in meters 
		     Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location) method will be called for each location update 
        */
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000,   // 3 sec
				10, this);
		
		/********* After registration onLocationChanged method called periodically after each 3 sec ***********/
	}
	
	/************* Called after each 3 sec **********/
	@Override
	public void onLocationChanged(Location location) {
		   
		String str = "Latitude: "+location.getLatitude()+" \nLongitude: "+location.getLongitude();
		Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		
		/******** Called when User off Gps *********/
		
		Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		
		/******** Called when User on Gps  *********/
		
		Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}