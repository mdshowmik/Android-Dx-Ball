package com.showmik.dxball;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;

public class GameCanvas extends View{
	DxBallActivity dxballactivity;
	ArrayList<String> objList=new ArrayList<String>();
	ArrayList<Ball> ballList=new ArrayList<Ball>();
	ArrayList<Bar> barList=new ArrayList<Bar>();
	public static ArrayList<Brick> brickList=new ArrayList<Brick>();
	public static ArrayList<Brick2> brick2List=new ArrayList<Brick2>();
	ArrayList<Integer> background=new ArrayList<Integer>();
	String textList="";
	BufferedReader level = null;
	Handler RedrawHandler = new Handler(); 
	public boolean isFirstTime=true;
	public static int score=0;
	public static int life=2;
	public boolean gameWin=false;
	public boolean gameLoss=false;

	public GameCanvas(Context context) {
		super(context);
		dxballactivity=(DxBallActivity) context;
		LoadStage("level.txt");

	}
	
	protected void onDraw(Canvas canvas) 
	{
		Paint paint = new Paint();	

		canvas.drawRGB(background.get(0), background.get(1), background.get(2));      //draw the drawables from the arraylists
		
		paint.setTextSize(30);
        paint.setFakeBoldText(true);
        paint.setARGB(100,0,0,0);
		canvas.drawText("Score:"+String.valueOf(score), canvas.getWidth()/2 , 60, paint);
		canvas.drawText("Life:"+String.valueOf(life), canvas.getWidth()/2 , 30, paint);

		if(!textList.equals(null))
		{
			canvas.drawText(textList, canvas.getWidth()/2 ,canvas.getHeight()/2 , paint);
		}
		if(!ballList.isEmpty())
		{
			for(int i=0;i<ballList.size();i++)
			{
				Ball b=ballList.get(i);
				b.Draw(canvas, paint);
			}
		}
		if(!barList.isEmpty())
		{
			for(int i=0;i<barList.size();i++)
			{
				Bar b=barList.get(i);
				b.Draw(canvas, paint);
			}
		}
		if(!brickList.isEmpty())
		{
			for(int i=0;i<brickList.size();i++)
			{
				Brick b=brickList.get(i);
				b.Draw(canvas, paint);
			}
		}
		if(!brick2List.isEmpty())
		{
			for(int i=0;i<brick2List.size();i++)
			{
				Brick2 b=brick2List.get(i);
				b.Draw(canvas, paint);
			}
		}
		if(life>0 && brickList.isEmpty() && brick2List.isEmpty())
		{
			background.clear();
			ballList.clear();
			barList.clear();
			paint.setTextSize(30);
	        paint.setFakeBoldText(true);
	        paint.setARGB(255,0,0,0);
			Ball.onTouch=false;
			Ball.firstTime=true;
			Bar.firstTime=true;
			Bar.changePosition=false;
			LoadStage("GameCompleted.txt");
			gameWin=true;
		}
		invalidate();
	}
	
	protected void LoadStage(String levelName)
	{
		try 
		{
			level = new BufferedReader(new InputStreamReader(dxballactivity.getStage(levelName)));  //get the level file name
			String line;
			
			while((line = level.readLine()) != null )	//read and store the info of drawables into arraylists
			{
				if(line.equals("Text:"))
				{
					textList=level.readLine();
				}
				if(line.equals("Background:"))
				{
					line=level.readLine();
					String[] thisBackground;
					thisBackground = line.split(",");
					String test=null;
					for(int i = 0; i < thisBackground.length; i++)
					{
						 test = thisBackground[i];
						 if(test != null) 
							 background.add(Integer.parseInt(test)); 		
					}
				}
				else if(line.equals("Ball:"))
				{
					line=level.readLine();
					String[] thisBall;
					thisBall = line.split(",");
					String test=null;
					for(int i = 0; i < thisBall.length; i++)
					{
						 test = thisBall[i];
						 if(test != null) 
							 objList.add(test); 		
					}
					ballList.add(new Ball(this,Float.parseFloat(objList.get(0)),Float.parseFloat(objList.get(1)),Float.parseFloat(objList.get(2)),objList.get(3)));
					objList.clear();
				}
				else if(line.equals("Bar:"))
				{
					line=level.readLine();
					String[] thisBox;
					thisBox = line.split(",");
					String test=null;
					for(int i = 0; i < thisBox.length; i++)
					{
						 test = thisBox[i];
						 if(test != null) 
							 objList.add(test); 		
					}
					barList.add(new Bar(Float.parseFloat(objList.get(0)),Float.parseFloat(objList.get(1)),objList.get(2)));
					objList.clear();
				}
				else if(line.equals("Brick:"))
				{
					line=level.readLine();
					String[] thisBrick;
					thisBrick = line.split(",");
					String test=null;
					for(int i = 0; i < thisBrick.length; i++)
					{
						 test = thisBrick[i];
						 if(test != null) 
							 objList.add(test); 		
					}
					brickList.add(new Brick(Float.parseFloat(objList.get(0)),Float.parseFloat(objList.get(1)),Float.parseFloat(objList.get(2)),Float.parseFloat(objList.get(3)),objList.get(4)));
					objList.clear();
				}
				else if(line.equals("Brick2:"))
				{
					line=level.readLine();
					String[] thisBrick2;
					thisBrick2 = line.split(",");
					String test=null;
					for(int i = 0; i < thisBrick2.length; i++)
					{
						 test = thisBrick2[i];
						 if(test != null) 
							 objList.add(test); 		
					}
					brick2List.add(new Brick2(Float.parseFloat(objList.get(0)),Float.parseFloat(objList.get(1)),Float.parseFloat(objList.get(2)),Float.parseFloat(objList.get(3)),objList.get(4)));
					objList.clear();
				}
								
			}
			level.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void gameStart(float x)
	{
		barList.get(0).changeBarPos(x); //calling changepos method to change the position of the bar
		Ball.onTouch=true;
	}
	
	public void Life()
	{
		life--;
		Ball.onTouch=false;
		Ball.firstTime=true;
		Bar.firstTime=true;
		Bar.changePosition=false;
		if(GameCanvas.life==0)
		{
			background.clear();
			ballList.clear();
			barList.clear();
			brickList.clear();
			brick2List.clear();
			LoadStage("GameOver.txt");
			gameLoss=true;
		}
	}

}
