package com.stuffle.uid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Index.PartStats;
import com.stuffle.Interface.Bar;
import com.stuffle.Utils.Label;

public class PlayerUid {
	public Rectangle rectPanel;
	private Sprite spriteTexture, spriteGold;
	private Bar barLife, barPower, barExp;
	private Label lblGold, lblLevel;
	
	
	public PlayerUid(Rectangle _r)
	{
		rectPanel = _r;
		updatePosition();
	}
	
	public void updatePosition()
	{
		spriteTexture = new Sprite(new Texture(Gdx.files.internal("UidBack.png")));
		spriteTexture.setBounds(rectPanel.x, rectPanel.y, rectPanel.width, rectPanel.height);
		initialisation();
	}
	
	public void render(SpriteBatch batch)
	{
		spriteTexture.draw(batch);
		barLife.render(batch);
		barPower.render(batch);
		barExp.render(batch);
		lblGold.render(batch);
		spriteGold.draw(batch);
		lblLevel.render(batch);
	}
	
	private void initialisation()
	{
		barLife = new Bar(rectPanel.x + rectPanel.width * 14/40, rectPanel.y + rectPanel.height - 30, rectPanel.width * 23/40, 20, PartStats.iLifeMax,PartStats.iLife,1,1,Color.WHITE, Color.RED);
		barPower = new Bar(rectPanel.x + rectPanel.width * 14/40,  rectPanel.y + rectPanel.height - 45, rectPanel.width * 23/40, 20, PartStats.iPowerMax,PartStats.iPower,1,1,Color.WHITE, Color.BLUE);
		barExp = new Bar(rectPanel.x + rectPanel.width * 14/40,  rectPanel.y + 15, rectPanel.width * 23/40, 5, PartStats.iExpMax,PartStats.iExp,1,1,Color.WHITE, Color.VIOLET);
		int w = 20, h = 20;
		spriteGold = new Sprite(new Texture(Gdx.files.internal("Coin.png")));
		spriteGold.setSize(w, h);
		spriteGold.setPosition(rectPanel.x + rectPanel.width * 14/40, rectPanel.y + 40 - h/2);
		lblGold = new Label(rectPanel.x + rectPanel.width * 14/40 + w + 5,  rectPanel.y + 40, ""+PartStats.iGold, Color.BLACK, 17);
		lblLevel = new Label(rectPanel.x + rectPanel.width * 11/40, rectPanel.y + 20, ""+PartStats.iLevel, Color.BLACK, 22);
	}
	
	public void updateAllInformations()
	{
		barLife.setFullW(PartStats.iLifeMax);
		barPower.setFullW(PartStats.iPowerMax);
		barExp.setFullW(PartStats.iExpMax);
		updateInformations();
	}
	
	public void updateInformations()
	{
		barLife.setContentW(PartStats.iLife);
		barPower.setContentW(PartStats.iPower);
		barExp.setContentW(PartStats.iExp);
		
		lblGold.setText("" + PartStats.iGold);	
		lblLevel.setText("" + PartStats.iLevel);
		
	}
}
