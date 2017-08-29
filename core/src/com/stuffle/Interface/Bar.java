package com.stuffle.Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuffle.Parameter.GameParameter;

public class Bar {
	private Sprite rectFull;
	private Sprite rectContent;
	private float x, y, wFull, hFull, wContent, hContent, partWFull, partWContent, partHFull, partHContent;
	private Color colorFull, colorContent;
	private boolean blInGame = false; 
	
	public Bar(float _x, float _y, float _wFull, float _hFull, float _partWMax, float _partWContent, float _partHMax,
			float _partHContent, Color _colorFull, Color _colorContent,boolean _blInGame) {
		blInGame = _blInGame;
		x = _x;
		y = _y;
		if(blInGame)
		{
			wFull = _wFull / GameParameter.PPM;
			hFull = _hFull / GameParameter.PPM;
		}
		else
		{
			wFull = _wFull;
			hFull = _hFull - _hFull/2;
		}
		partWFull = _partWMax;
		partWContent = _partWContent;
		partHFull = _partHMax;
		partHContent = _partHContent;
		colorFull = _colorFull;
		colorContent = _colorContent;
		rectFull = new Sprite(new Texture(Gdx.files.internal("pixel.png")));
		rectContent = new Sprite(new Texture(Gdx.files.internal("pixel.png")));
		rectFull.setSize(wFull, hFull);
		rectFull.setPosition(x, y);
		if(colorFull != null)
			rectFull.setColor(colorFull);
		blInGame = _blInGame;
		computeContent();

	}


	
	public void render(SpriteBatch batch) {
		rectFull.draw(batch);
		rectContent.draw(batch);
	}
	
	public void updatePos(float x, float y)
	{
		rectFull.setPosition(x, y);
		rectContent.setPosition(x, y);
	}

	public void setFullW(int width) {
		partWFull = width;
	}

	public void setContentW(int width) {
		partWContent = width;
	}

	public void computeContent() {
		wContent = (wFull / partWFull) * partWContent;
		hContent = (hFull / partHFull) * partHContent;
		rectContent.setSize(wContent, hContent);
		rectContent.setPosition(x, y);
		rectContent.setColor(colorContent);
	}

}
