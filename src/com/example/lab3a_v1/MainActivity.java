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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity implements SensorEventListener{

	CustomDrawableView mCustomDrawableView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static int x;
    public static int y;
    ImageView image = null;

    private SensorManager sensorManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
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
	                // the values you were calculating originally here were over 10000!
	                x = (int)sensorEvent.values[0]*10; 
	                //y = 3*((int) Math.pow(sensorEvent.values[2], 2))+100;
	                //x= x*-1+70;
	                y=2*100;
	                //System.out.println("X value: "+x);
	                //Log.d("Value", "X value: "+x);
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
	 
	 public class CustomDrawableView extends View {
			static final int width = 50;
		    static final int height = 50;
		     
		    private Drawable flowerCenter,flowerRight1,flowerRight2, flowerRight3, flowerRight4, flowerRight5, flowerRight6, flowerRight7;
			
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
		            
		        }

		        protected void onDraw(Canvas canvas){
		        	int x = 150, y = 200;
		        	
		        	if(MainActivity.x >=0 && MainActivity.x < 10){
		        		int iw = flowerCenter.getIntrinsicWidth();
		        		int ih = flowerCenter.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerCenter.setBounds(bounds);
		        		flowerCenter.draw(canvas);
		        	}else if(MainActivity.x >=10 && MainActivity.x < 20){
		        		int iw = flowerRight1.getIntrinsicWidth();
		        		int ih = flowerRight1.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight1.setBounds(bounds);
		        		flowerRight1.draw(canvas);
		        	}else if(MainActivity.x >=20 && MainActivity.x < 30){
		        		int iw = flowerRight2.getIntrinsicWidth();
		        		int ih = flowerRight2.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight2.setBounds(bounds);
		        		flowerRight2.draw(canvas);
		        	}else if(MainActivity.x >=30 && MainActivity.x < 40){
		        		int iw = flowerRight3.getIntrinsicWidth();
		        		int ih = flowerRight3.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight3.setBounds(bounds);
		        		flowerRight3.draw(canvas);
		        	}else if(MainActivity.x >=40 && MainActivity.x < 50){
		        		int iw = flowerRight4.getIntrinsicWidth();
		        		int ih = flowerRight4.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight4.setBounds(bounds);
		        		flowerRight4.draw(canvas);
		        	}else if(MainActivity.x >=50 && MainActivity.x < 60){
		        		int iw = flowerRight5.getIntrinsicWidth();
		        		int ih = flowerRight5.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight5.setBounds(bounds);
		        		flowerRight5.draw(canvas);
		        	}else if(MainActivity.x >=60 && MainActivity.x < 70){
		        		int iw = flowerRight6.getIntrinsicWidth();
		        		int ih = flowerRight6.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight6.setBounds(bounds);
		        		flowerRight6.draw(canvas);
		        	}else if(MainActivity.x >=60 && MainActivity.x < 70){
		        		int iw = flowerRight7.getIntrinsicWidth();
		        		int ih = flowerRight7.getIntrinsicHeight();
		        		Rect bounds = new Rect(x,y,x+iw, y+ih);
		        		flowerRight7.setBounds(bounds);
		        		flowerRight7.draw(canvas);
		        	}
		        	
		        	
		            
		            invalidate();
		            
		        }
			

		}
	 
}

