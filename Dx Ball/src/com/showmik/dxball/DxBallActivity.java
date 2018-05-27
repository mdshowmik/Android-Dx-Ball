package com.showmik.dxball;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public class DxBallActivity extends Activity implements OnTouchListener{
	EditText edittext;
	GameCanvas g;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        g= new GameCanvas(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        g.setFocusableInTouchMode(true);
        g.setOnTouchListener(this);
        g.requestFocus();
        setContentView(g);
        
    }
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
    
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	protected InputStream getStage(String name)    //get the level file from assets folder
	{
		try 
		{
			return getAssets().open(name);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(g.gameWin==true)
		{
			g.background.clear();
			g.ballList.clear();
			g.barList.clear();
			g.gameWin=true;
			g.LoadStage("GameCompleted.txt");
		}
		else if(g.gameLoss==true)
		{
			g.background.clear();
			g.ballList.clear();
			g.barList.clear();
			g.gameLoss=true;
			g.LoadStage("GameOver.txt");
		}
		else
		{
			g.gameStart(event.getX());
		}
		return true;
	}
}