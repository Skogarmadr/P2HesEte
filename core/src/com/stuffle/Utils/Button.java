package com.stuffle.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	Label label;
	String strText;
	String strAlign;
	public float flPosX;
	public float flPosY;
	public float flWidth;
	public float flHeight;
	public boolean mouseHover = false;
	public Sprite sprite;
	public Rectangle rectButton;
	int fontSize = 18;

	
	public Button(float x,float y, float w, float h, String text, int fSize)
	{
		flPosX = x;
		flPosY = y;
		flWidth = w;
		flHeight = h;
		strText = text;
		fontSize = fSize;
		initialisation();
	}
	
	public Button(float x,float y, float w, float h, String text, String align, int fSize)
	{
		flPosX = x;
		flPosY = y;
		flWidth = w;
		flHeight = h;
		strText = text;
		strAlign = align;
		fontSize = fSize;
		initialisation();
		
	}
	
	public void render(SpriteBatch batch)
	{
		sprite.draw(batch);
		label.render(batch);
	}
	
	private void initialisation()
	{
		flPosY = flPosY - flHeight /2;
		if(strAlign == "Center")
			flPosX -= flWidth/2;
		else if(strAlign == "Right")
			flPosX -= flWidth;
		rectButton = new Rectangle(flPosX,flPosY,flWidth,flHeight);
		sprite = new Sprite(new Texture(Gdx.files.internal("utils/Button.png")));
		reposition();
		sprite.setColor(Color.WHITE);
		label = new Label(sprite.getX() + sprite.getWidth() / 2,sprite.getY() + sprite.getHeight() / 2,strText, "Center", Color.BLACK, fontSize);
	}
	
	private void reposition()
	{
		sprite.setSize(flWidth,flHeight);
		sprite.setPosition(flPosX, flPosY);
	}
	
	public void changeBackColor(Color color)
	{
		sprite.setColor(color);
	}
	
	
}
