package com.stuffle.Index;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Article {
	Rectangle rectItem;
	Sprite spriteTexture;
	int iPrice, iPosX, iPosY, iW, iH;
	float flUpgrade;
	public boolean mouseHover = false;
	public String strType, strStatus, strName;
	String strDescription;

	public Article(int _posX, int _posY, int _w, int _h, Sprite _s, int _p, float _u, String _t, String _n,
			String _status, String _description) {
		rectItem = new Rectangle(_posX, _posY + _h / 2, _w, _h);
		iPrice = _p;
		flUpgrade = _u;
		strName = _n;
		strType = _t;
		strStatus = _status;
		spriteTexture = _s;
		spriteTexture.setSize(_w, _h);
		spriteTexture.setPosition(_posX, _posY + _h / 2);
		changeStatus(_status);
		strDescription = _description;
	}

	public void changeStatus(String _strStatus) {
		strStatus = _strStatus;
		if (strStatus.equals("blocked"))
			spriteTexture.setColor(new Color(30 / 255f, 30 / 255f, 30 / 255f, 1));
		else if (strStatus.equals("bought"))
			spriteTexture.setColor(Color.BROWN);
		else
			spriteTexture.setColor(Color.WHITE);
	}

	public void colorChange(Color color) {
		if (strStatus.equals("open"))
			spriteTexture.setColor(color);
	}

	public void render(SpriteBatch batch) {
		spriteTexture.draw(batch);
	}
}
