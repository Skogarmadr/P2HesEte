package com.stuffle.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Label {
	public String strText;
	String strTextAlign = "Left";
	float flPosX;
	float flPosY;
	BitmapFont font;
	String fontName = "font/pixelmix.ttf";
	Color fontColor;
	int fontSize;
	
	
	public Label(float x,float y,String text, Color c,int fSize)
	{
		strText = text;
		flPosX = x;
		flPosY = y;
		fontColor = c;
		fontSize = fSize;
		initialisation();
	}
	
	public Label(float x,float y,String text, String textAlign, Color c,int fSize)
	{
		strText = text;		
		strTextAlign = textAlign;		
		flPosX = x;
		flPosY = y;
		fontColor = c;
		fontSize = fSize;		
		initialisation();
	}
	public Label(float x,float y,String text, String textAlign, Color c,int fSize, String fName)
	{
		strText = text;		
		strTextAlign = textAlign;		
		flPosX = x;
		flPosY = y;
		fontColor = c;
		fontSize = fSize;	
		fontName = fName;
		initialisation();
	}
	
	private void initialisation()
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = fontSize;
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;

		font = generator.generateFont(parameter);
		font.setColor(fontColor);
		
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, strText);
		
		flPosY = flPosY + layout.height / 2 ;
		if(strTextAlign == "Right")
			flPosX = flPosX - layout.width;
		else if(strTextAlign == "Center")
			flPosX = flPosX - layout.width/2;
	}
	
	public void render(SpriteBatch batch)
	{
		font.draw(batch, strText, flPosX,flPosY);
	}
	
	public void setText(String text)
	{
		strText = text;
	}
}
