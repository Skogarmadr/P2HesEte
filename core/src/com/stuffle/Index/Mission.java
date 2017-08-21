package com.stuffle.Index;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Utils.Label;

public class Mission {

	private String strDifficulty;
	public int iGold, iXp;
	private float flX, flY, flW, flH;
	private ArrayList<Label> lblInformations;

	public int iLevel;
	public Sprite spritePanel;
	public Rectangle rectPanel;
	public boolean mouseHover = false;

	public Mission(int _level, String _difficulty, float _x, float _y, float _w, float _h) {
		iLevel = _level;
		strDifficulty = _difficulty;
		flX = _x;
		flY = _y;
		flW = _w;
		flH = _h;
		inisalisation();
	}

	private void inisalisation() {
		computeMission();
		rectPanel = new Rectangle();
		rectPanel.setSize(flW, flH);
		rectPanel.setPosition(flX, flY);
		lblInformations = new ArrayList<Label>();

		spritePanel = new Sprite(new Texture(Gdx.files.internal("index/indexElementBack.png")));
		spritePanel.setSize(flW, flH);
		spritePanel.setPosition(flX, flY);
		Label l;
		int h = 30;

		h = 20;
		l = new Label(flX + 10, flY + flH - 40 - h, "Difficulty : " + strDifficulty, Color.BLACK, h);
		lblInformations.add(l);
		l = new Label(flX + 10, flY + flH - 70 - h, "Gold : " + iGold, Color.BLACK, h);
		lblInformations.add(l);
		l = new Label(flX + 200, flY + flH - 70 - h, "Xp : " + iXp, Color.BLACK, h);
		lblInformations.add(l);

	}

	private void computeMission() {
		if (iLevel > 0) {
			iGold = (int) (100 * Math.log(iLevel) + 15 * iLevel);
			iXp = (int) (50 * Math.log(iLevel) + 10 * iLevel);
		}
		iGold += 50;
		iXp += 50;
	}

	public void render(SpriteBatch batch) {
		spritePanel.draw(batch);
		for (Label l : lblInformations)
			l.render(batch);
	}
}
