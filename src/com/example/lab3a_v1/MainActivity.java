package com.example.lab3a_v1;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	CustomDrawableView mCustomDrawableView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
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
    

    private SensorManager sensorManager = null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 Log.d("Lab3", "Test1"); 
		
		shakeIndex=0;
		shakeArrayX= new int[10];
		shakeArrayY= new int[10];
		shakeArrayZ= new int[10];
		
		//image.setImageResource(R.drawable.android1);
		//
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mCustomDrawableView = new CustomDrawableView(this);
		setContentView(mCustomDrawableView);
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
			setContentView(mCustomDrawableView);
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
	                		animationLeaf();
	                	}else if((medelY/10)>sens){
	                		isShake = true;
	                		animationLeaf();
	                	}else if((medelZ/10)>sens){
	                		isShake = true;
	                		animationLeaf();
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
	
	public void animationLeaf(){
		setContentView(R.layout.activity_main);
		ImageView leafImageView = (ImageView) findViewById(R.id.imgAnimation);
		leafImageView.setBackgroundResource(R.drawable.leaffall);
		AnimationDrawable leafAnimation = (AnimationDrawable) leafImageView.getBackground();
		
		leafAnimation.start();
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
	 
	 public class CustomDrawableView extends View {
			static final int width = 50;
		    static final int height = 50;
		    int x, y;
		    private Drawable flowerCenter,flowerRight1,flowerRight2, flowerRight3, flowerRight4, flowerRight5, flowerRight6, flowerRight7;
		    private Drawable flowerLeft1,flowerLeft2, flowerLeft3, flowerLeft4, flowerLeft5, flowerLeft6, flowerLeft7, flowerdead;
			
		        public CustomDrawableView(Context context){
		            super(context);
		            Resources resource = context.getResources();
		            flowerCenter = (Drawable) resource.getDrawable(R.drawable.flowercenter);
		            flowerRight1 = (Drawable) resource.getDrawable(R.drawable.flowerright1);
		            flowerRight2 = (Drawable) resource.getDrawable(R.drawable.flowerright2);
		            flowerRight3 = (Drawable) resource.getDrawable(R.drawable.flowerright3);
		            flowerRight4 = (Drawable) resource.getDrawable(R.drawable.flowerright4);
		            flowerRight5 = (Drawable) resource.getDrawable(R.drawable.flowerright5);
		            flowerRight6 = (Drawable) resource.getDrawable(R.drawable.flowerright6);
		            flowerRight7 = (Drawable) resource.getDrawable(R.drawable.flowerright7);
		            flowerdead = (Drawable) resource.getDrawable(R.drawable.flowerdead);
		            flowerLeft1 = (Drawable) resource.getDrawable(R.drawable.flowerleft1);
		            flowerLeft2 = (Drawable) resource.getDrawable(R.drawable.flowerleft2);
		            flowerLeft3 = (Drawable) resource.getDrawable(R.drawable.flowerleft3);
		            flowerLeft4 = (Drawable) resource.getDrawable(R.drawable.flowerleft4);
		            flowerLeft5 = (Drawable) resource.getDrawable(R.drawable.flowerleft5);
		            flowerLeft6 = (Drawable) resource.getDrawable(R.drawable.flowerleft6);
		            flowerLeft7 = (Drawable) resource.getDrawable(R.drawable.flowerleft7);
		            
		        }

		        protected void onDraw(Canvas canvas){
		        	
		        	x = 30;
		            y = 30;
		        	
		        	/*if(shakeIndex>9)
		        		shakeIndex=0;
		        	
		        	shakeArrayX[shakeIndex] = (int) accel[0];
		        	shakeArrayY[shakeIndex] = (int) accel[1];
		        	shakeArrayZ[shakeIndex] = (int) accel[2];
		        	
		        	
		        		
		        	
		        	if(shakeArrayX[shakeIndex]-shakeArrayX[(shakeIndex+1)%10]>95){  
		        		shaker=true;
		        	}else if(shakeArrayY[shakeIndex]-shakeArrayY[(shakeIndex+1)%10]>95){
		        		shaker=true;
		        	}else if(shakeArrayZ[shakeIndex]-shakeArrayZ[(shakeIndex+1)%10]>95){
		        		shaker=true;
		        	}
		        	
		        	
		        	
		        	else if(shaker){
		        		int iw = flowerDead.getIntrinsicWidth();
		        		int ih = flowerDead.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerDead.setBounds(bounds);
		        		flowerDead.draw(canvas);
		        	}*/
		        	
		        	if(isShake){
		        		/*int iw = flowerdead.getIntrinsicWidth();
		        		int ih = flowerdead.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerdead.setBounds(bounds);
		        		flowerdead.draw(canvas);*/
		        		
		        	}else if(MainActivity.x >=0 && MainActivity.x < 12){
		        		int iw = flowerCenter.getIntrinsicWidth();
		        		int ih = flowerCenter.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerCenter.setBounds(bounds);
		        		flowerCenter.draw(canvas);
		        	}else if(MainActivity.x >=12 && MainActivity.x < 24){
		        		int iw = flowerRight1.getIntrinsicWidth();
		        		int ih = flowerRight1.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight1.setBounds(bounds);
		        		flowerRight1.draw(canvas);
		        	}else if(MainActivity.x >=24 && MainActivity.x < 36){
		        		int iw = flowerRight2.getIntrinsicWidth();
		        		int ih = flowerRight2.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight2.setBounds(bounds);
		        		flowerRight2.draw(canvas);
		        	}else if(MainActivity.x >=36 && MainActivity.x < 48){
		        		int iw = flowerRight3.getIntrinsicWidth();
		        		int ih = flowerRight3.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight3.setBounds(bounds);
		        		flowerRight3.draw(canvas);
		        	}else if(MainActivity.x >=48 && MainActivity.x < 60){
		        		int iw = flowerRight4.getIntrinsicWidth();
		        		int ih = flowerRight4.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight4.setBounds(bounds);
		        		flowerRight4.draw(canvas);
		        	}else if(MainActivity.x >=60 && MainActivity.x < 72){
		        		int iw = flowerRight5.getIntrinsicWidth();
		        		int ih = flowerRight5.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight5.setBounds(bounds);
		        		flowerRight5.draw(canvas);
		        	}else if(MainActivity.x >=72 && MainActivity.x < 84){
		        		int iw = flowerRight6.getIntrinsicWidth();
		        		int ih = flowerRight6.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight6.setBounds(bounds);
		        		flowerRight6.draw(canvas);
		        	}else if(MainActivity.x >=84){
		        		int iw = flowerRight7.getIntrinsicWidth();
		        		int ih = flowerRight7.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight7.setBounds(bounds);
		        		flowerRight7.draw(canvas);
		        	}else if(MainActivity.x <=0 && MainActivity.x > -12){
		        		int iw = flowerCenter.getIntrinsicWidth();
		        		int ih = flowerCenter.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerCenter.setBounds(bounds);
		        		flowerCenter.draw(canvas);
		        	}else if(MainActivity.x <=-12 && MainActivity.x > -24){
		        		int iw = flowerLeft1.getIntrinsicWidth();
		        		int ih = flowerLeft1.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerLeft1.setBounds(bounds);
		        		flowerLeft1.draw(canvas);
		        	}else if(MainActivity.x <=-24 && MainActivity.x > -36){
		        		int iw = flowerLeft2.getIntrinsicWidth();
		        		int ih = flowerLeft2.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerLeft2.setBounds(bounds);
		        		flowerLeft2.draw(canvas);
		        	}else if(MainActivity.x <=-36 && MainActivity.x > -48){
		        		int iw = flowerLeft3.getIntrinsicWidth();
		        		int ih = flowerLeft3.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerLeft3.setBounds(bounds);
		        		flowerLeft3.draw(canvas);
		        	}else if(MainActivity.x <=-48 && MainActivity.x > -60){
		        		int iw = flowerLeft4.getIntrinsicWidth();
		        		int ih = flowerLeft4.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerLeft4.setBounds(bounds);
		        		flowerLeft4.draw(canvas);
		        	}else if(MainActivity.x <=-60 && MainActivity.x > -72){
		        		int iw = flowerLeft5.getIntrinsicWidth();
		        		int ih = flowerLeft5.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerLeft5.setBounds(bounds);
		        		flowerLeft5.draw(canvas);
		        	}else if(MainActivity.x <=-72 && MainActivity.x > -84){
		        		int iw = flowerLeft6.getIntrinsicWidth();
		        		int ih = flowerLeft6.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerLeft6.setBounds(bounds);
		        		flowerLeft6.draw(canvas);
		        	}else if(MainActivity.x <=-84){
		        		int iw = flowerLeft7.getIntrinsicWidth();
		        		int ih = flowerLeft7.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerLeft7.setBounds(bounds);
		        		flowerLeft7.draw(canvas);
		        	}
		        	
		        	 
		        	shakeIndex++;
		            invalidate();
		            
		        }
			

		}
	 
}

