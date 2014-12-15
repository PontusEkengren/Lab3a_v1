package com.example.lab3a_v1;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity implements SensorEventListener{

    public static int x;
    public static int y;
    public static int z;
    public boolean shaker;
    public int shakeIndex;
    public int j=0;
    public int[] shakeArrayX,shakeArrayY,shakeArrayZ;  
    public int[] accel = new int[3];
    public float[] gravity = new float[3];
    public boolean isShake = false;
    long lastUpdate = 0;
    float lastX = 0;
    float lastY = 0;
    float lastZ = 0;
    private ImageView plantImage = null;
    

    private SensorManager sensorManager = null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("Lab3", "Test1"); 
		
		plantImage = (ImageView) findViewById(R.id.imgAnimation);
		 
		shakeIndex=0;
		shakeArrayX= new int[10];
		shakeArrayY= new int[10];
		shakeArrayZ= new int[10];
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			isShake=false;
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		// TODO Auto-generated method stub
		 {
	            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	            	double ff = 0.1;//filter factor
	            	
	            	float alpha = (float) 0.8; 
	            	
	                x = (int)sensorEvent.values[0]*10; 
	                
	                //y = 3*((int) Math.pow(sensorEvent.values[2], 2))+100;
	                //x= x*-1+70;
	               // y=2*100;
	                y = (int)sensorEvent.values[1]*5; 
	                z = (int) sensorEvent.values[2]*5;
	                changePicture(x);
	                Log.d("Lab3", "X value: "+x); 
	                
	                gravity[0] = alpha * gravity[0] +(1-alpha) * (sensorEvent.values[0]);
	                gravity[1] = alpha * gravity[1] +(1-alpha) * (sensorEvent.values[1]);//y
	                gravity[2] = alpha * gravity[2] +(1-alpha) * (sensorEvent.values[2]);//z
	                
	                //High pass filter
	                accel[0] = (int) (sensorEvent.values[0] - gravity[0]);
	                accel[1] = (int) (sensorEvent.values[1] - gravity[1]);
	                accel[2] = (int) (sensorEvent.values[2] - gravity[2]);
	                long currTime = System.currentTimeMillis();
	                
	                
	                shakeArrayX[j]=accel[0];
	                shakeArrayY[j]=accel[1];
	                shakeArrayZ[j]=accel[2];
	                
	                j++;
	                if(j>9)
	                	j=0;
	                
	                if((currTime - lastUpdate) > 1000){
	                	//long diffTime = (currTime - lastUpdate);
	                	lastUpdate = currTime;
	                	
	                	//float speed = Math.abs(sensorEvent.values[0] + sensorEvent.values[1] + sensorEvent.values[2] - lastX - lastY - lastZ)/diffTime * 10000;
	                	
	                	int medelX=0; int medelY=0; int medelZ=0; 
	                	for (int xaxis : shakeArrayX) { medelX+=Math.pow(xaxis, 2); }
	                	for (int yaxis : shakeArrayY) { medelY+=Math.pow(yaxis, 2); }
	                	for (int zaxis : shakeArrayZ) { medelZ+=Math.pow(zaxis, 2); }
	                	
	                	//Change this for making it more difficult to kill plant!
	                	final int sens=80;//Acceleration sensitivity
	                	
	                	if((medelX/10)>sens){
	                		isShake = true;
	                	}else if((medelY/10)>sens){
	                		isShake = true;
	                	}else if((medelZ/10)>sens){
	                		isShake = true;
	                	}
	                		/*
	                	if(speed > 250){
	                		isShake = true;
	                	}*/
	                	
	                	lastX = sensorEvent.values[0];
	                	lastY = sensorEvent.values[1];
	                	lastZ = sensorEvent.values[2];
	                }
	                
	                 
	                //System.out.println("X value: "+x);
	                Log.d("Lab3", "accel[2] (Z) value: "+accel[2]); 
	            }

	            if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {

	            }
	        }
	}
	
	@Override
    protected void onResume()
    {
        super.onResume();
        // Register this class as a listener for the accelerometer sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        // ...and the orientation sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
	
	 @Override
	    protected void onStop()
	    {
	        // Unregister the listener
	        sensorManager.unregisterListener(this);
	        super.onStop();
	    }
	 
	 public void changePicture(int xPos){
		 if(isShake){
			 plantImage.setBackgroundResource(R.drawable.leaffall);
			 AnimationDrawable leafAnimation = (AnimationDrawable) plantImage.getBackground();
			 leafAnimation.start();
		 }else{
			 if(xPos >=0 && xPos< 12){
				 plantImage.setBackgroundResource(R.drawable.flowercenter);
			 }else if(xPos >=12 && xPos < 24){
				 plantImage.setBackgroundResource(R.drawable.flowerright1);
			 }else if(xPos >=24 && xPos < 36){
				 plantImage.setBackgroundResource(R.drawable.flowerright2);
			 }else if(xPos >=36 && xPos< 48){
				 plantImage.setBackgroundResource(R.drawable.flowerright3);
			 }else if(xPos >=48 && xPos < 60){
				 plantImage.setBackgroundResource(R.drawable.flowerright4);
			 }else if(xPos >=60 && xPos < 72){
				 plantImage.setBackgroundResource(R.drawable.flowerright5);
			 }else if(xPos >=72 && xPos < 84){
				 plantImage.setBackgroundResource(R.drawable.flowerright6);
			 }else if(xPos >=84){
				 plantImage.setBackgroundResource(R.drawable.flowerright7);
			 }else if(xPos <=0 && xPos > -12){
				 plantImage.setBackgroundResource(R.drawable.flowercenter);
			 }else if(xPos <=-12 && xPos > -24){
				 plantImage.setBackgroundResource(R.drawable.flowerleft1);
			 }else if(xPos <=-24 && xPos > -36){
				 plantImage.setBackgroundResource(R.drawable.flowerleft2);
			 }else if(xPos <=-36 && xPos > -48){
				 plantImage.setBackgroundResource(R.drawable.flowerleft3);
			 }else if(xPos <=-48 && xPos > -60){
				 plantImage.setBackgroundResource(R.drawable.flowerleft4);
			 }else if(xPos <=-60 && xPos > -72){
				 plantImage.setBackgroundResource(R.drawable.flowerleft5);
			 }else if(xPos <=-72 && xPos > -84){
				 plantImage.setBackgroundResource(R.drawable.flowerleft6);
			 }else if(xPos <=-84){
				 plantImage.setBackgroundResource(R.drawable.flowerleft7);
			 }
		 }
		 
	 }
}

