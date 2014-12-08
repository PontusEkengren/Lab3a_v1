package com.example.lab3a_v1;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity implements SensorEventListener{

	CustomDrawableView mCustomDrawableView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static int x;
    public static int y;

	 private SensorManager sensorManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
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
	                x = (int) Math.pow(sensorEvent.values[1], 2); 
	                y = (int) Math.pow(sensorEvent.values[2], 2);

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
		   
			
		        public CustomDrawableView(Context context)
		        {
		            super(context);

		            mDrawable = new ShapeDrawable(new OvalShape());
		            mDrawable.getPaint().setColor(0xff74AC23);
		            mDrawable.setBounds(x, y, x + width, y + height);
		        }

		        protected void onDraw(Canvas canvas)
		        {
		            RectF oval = new RectF(MainActivity.x, MainActivity.y, MainActivity.x + width, MainActivity.y
		                    + height); // set bounds of rectangle
		            Paint p = new Paint(); // set some paint options
		            p.setColor(Color.BLUE);
		            canvas.drawOval(oval, p);
		            invalidate();
		        }
			

		}
	 
}

