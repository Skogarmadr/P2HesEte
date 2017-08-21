package com.stuffle.Utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextArea extends Label{
	
	int widthMax,heightMax;
	
	public TextArea(float x, float y, String text, Color c, int fSize, int wMax, int hMax) {
		super(x, y, text, c, fSize);
		widthMax = wMax;
		heightMax = hMax;
		updateText();
	}
	
	public TextArea(float x,float y,String text, String textAlign, Color c,int fSize, int wMax, int hMax)
	{
		super(x, y, text,textAlign, c, fSize);
		widthMax = wMax;
		heightMax = hMax;
		updateText();
	}
	public TextArea(float x,float y,String text, String textAlign, Color c,int fSize, String fName, int wMax, int hMax)
	{
		super(x, y, text, textAlign, c, fSize,fName);
		widthMax = wMax;
		heightMax = hMax;
		updateText();
	}
	
	public void render(SpriteBatch batch)
	{
		font.draw(batch, strText,flPosX,flPosY,widthMax,heightMax,true);
	}
	
	public void setText(String text)
	{
		strText = text;
		updateText();
	}
	
	private void updateText()
	{
		String[] words = strText.split(" ");
		strText = "";
		String testText = "";
		for(String w : words)
		{
			testText += w + " ";
			GlyphLayout layout = new GlyphLayout();
			layout.setText(font, testText);
			if(layout.width > widthMax)
			{
				strText += "\n";
			}
			strText += w + " ";
			testText = strText;
		}
	}

}
