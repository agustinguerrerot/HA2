package com.example.ha2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements SensorEventListener,
		OnClickListener {
	/** Called when the activity is first created. */
	CustomDrawableView mCustomDrawableView = null;
	ShapeDrawable mDrawable = new ShapeDrawable();
	public static Ball ball;

	private SensorManager sensorManager = null;

	int width;
	int height;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		ball = new Ball(200, height - 2 * 200, width, height, new Rectangle(
				width / 2 - 200, width / 2 + 200, height - 500, height + 500));

		// Get a reference to a SensorManager
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mCustomDrawableView = new CustomDrawableView(this);
		setContentView(mCustomDrawableView);
		// setContentView(R.layout.main);
		mCustomDrawableView.setOnClickListener(this);

	}

	// This method will update the UI on new sensor events
	public void onSensorChanged(SensorEvent sensorEvent) {
		{
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				// the values you were calculating originally here were over
				// 10000!
				ball.ax = sensorEvent.values[1] / 10;
				// x = (int) Math.pow(sensorEvent.values[1], 3.3);
				// y = (int) Math.pow(sensorEvent.values[2], 2);

			}

			if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {

			}
		}
	}

	// I've chosen to not implement this method
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		super.onResume();
		// Register this class as a listener for the accelerometer sensor
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		// ...and the orientation sensor
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		// Unregister the listener
		sensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		if (ball.isOnBottom())
			ball.jump();

	}

	public class CustomDrawableView extends View {

		Paint p = new Paint();

		public CustomDrawableView(Context context) {
			super(context);

			mDrawable = new ShapeDrawable(new OvalShape());
			mDrawable.getPaint().setColor(0xff74AC23);
			mDrawable.setBounds(ball.x, ball.y, ball.x + ball.width, ball.y
					+ ball.height);

			p.setColor(Color.BLACK);
		}

		protected void onDraw(Canvas canvas) {
			ball.draw(canvas, p);
			ball.box.draw(canvas, p);
			invalidate();
		}
	}
}

/*
 * public class MainActivity extends Activity implements SensorEventListener {
 * 
 * public static int horizontal; public static int vertical; private
 * SensorManager mySensorManager; ShapeDrawable myShapeDrawable = new
 * ShapeDrawable(); CustomDrawableView myCustomDrawableView = null;
 * 
 * Sensor accelerometer; SensorManager sm; TextView acceleration; TextView
 * screenDimensions;
 * 
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * 
 * mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
 * myCustomDrawableView = new CustomDrawableView(this);
 * setContentView(myCustomDrawableView);
 * 
 * setContentView(R.layout.activity_main); sm = (SensorManager)
 * getSystemService(SENSOR_SERVICE); accelerometer =
 * sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); sm.registerListener(this,
 * accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
 * 
 * acceleration = (TextView) findViewById(R.id.textView1); screenDimensions =
 * (TextView) findViewById(R.id.textView2);
 * 
 * Display display = getWindowManager().getDefaultDisplay(); Point size = new
 * Point(); display.getSize(size); int width = size.x; int height = size.y;
 * 
 * screenDimensions.setText("Screen width: " + getString(width) +
 * "Screen height: " + getString(height));
 * 
 * }
 * 
 * @Override public void onAccuracyChanged(Sensor arg0, int arg1) { // TODO
 * Auto-generated method stub }
 * 
 * @Override public void onSensorChanged(SensorEvent event) {
 * acceleration.setText("X: " + event.values[0] + "\nY: " + event.values[1] +
 * "\nZ: " + event.values[2]); { if (event.sensor.getType() ==
 * Sensor.TYPE_ACCELEROMETER){ horizontal = (int) Math.pow(event.values[2],1);
 * vertical = (int) Math.pow(event.values[2],2); }
 * 
 * if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
 * 
 * }
 * 
 * } }
 * 
 * public class CustomDrawableView extends View {
 * 
 * static final int width = 50; static final int height = 50;
 * 
 * public CustomDrawableView(Context context) { super(context);
 * 
 * myShapeDrawable = new ShapeDrawable(new OvalShape());
 * myShapeDrawable.getPaint().setColor(0xff74AC23);
 * myShapeDrawable.setBounds(horizontal, vertical, horizontal + width, vertical
 * + height); }
 * 
 * protected void onDraw(Canvas canvas) { RectF oval = new
 * RectF(MainActivity.horizontal, MainActivity.vertical, MainActivity.horizontal
 * + width, MainActivity.vertical + height); // set bounds of rectangle Paint
 * paint = new Paint(); paint.setColor(Color.BLUE); canvas.drawOval(oval,
 * paint); invalidate(); } } }
 */