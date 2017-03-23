package org.example.canvasdemo;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
public class MainActivity extends Activity {

	MyView myView;
	ArrayList<GoldCoin> goldCoins = new ArrayList<GoldCoin>();
	int point = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button rightButton = (Button) findViewById(R.id.moveRight);
		Button leftButton = (Button) findViewById(R.id.moveLeft);
		Button upButton = (Button) findViewById(R.id.moveUp);
		Button downButton = (Button) findViewById(R.id.moveDown);
		Button resetBtn = (Button) findViewById(R.id.resetBtn);

		myView = (MyView) findViewById(R.id.gameView);
		//listener of our pacman

		leftButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				point = myView.movePac(1,-20);
				setPointTextView();
			}
		});
		rightButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				point = myView.movePac(1,20);
				setPointTextView();
			}
		});
		upButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				point = myView.movePac(0,-20);
				setPointTextView();
			}
		});
		downButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				point = myView.movePac(0,20);
				setPointTextView();

			}
		});
		for (int i = 0; i < 10; i++ ){
			GoldCoin g = new GoldCoin();
			goldCoins.add(g);
			System.out.print("Adding gold coin nr: " + i);
		}

		myView.setGoldCoint(goldCoins);
	}

	public void setPointTextView(){
		TextView tw = (TextView) findViewById(R.id.pointsTextView);
		tw.setText("Points: " + point);
	}

	public void resetGame(View view){
		point = 0;
		setPointTextView();

		myView.resetGame();



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


}
