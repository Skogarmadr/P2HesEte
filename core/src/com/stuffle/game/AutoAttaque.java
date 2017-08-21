package com.stuffle.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stuffle.Index.PartStats;

public class AutoAttaque extends Attaque{	
	
	
	public AutoAttaque() {
		initialisation();
	}

	@Override
	public void initialisation()
	{
		hasAttaqueAnnimation = true;
		switch(PartStats.strType)
		{
			case "Warrior" :
				asProjectile = false;
				iWidthFrame = 100;
				iHeightFrame = 128;
				iRowFrame = 1;
				iColsFrame = 5;
				stateNumber = 5;
				break;
			case "Mage" :
				asProjectile = true;
				projectiles = new ArrayList<>();
				break;
			case "Hunter" :
				asProjectile = true;
				projectiles = new ArrayList<>();
				break;
		}
		attaqueTexture = new Texture(Gdx.files.internal("./"+PartStats.strType+"/autoAttaque.png"));
		attaqueDmg = PartStats.iAttaque;
		super.initialisation();
	}
	
	@Override
	protected void update(Vector2 playerPos, Vector2 sizePlayer) {
		super.update(playerPos, sizePlayer);
	}
	
	@Override
	protected void render(SpriteBatch batch, Vector2 playerPos, Vector2 sizePlayer) {
		if (attaqueStarted) {
		update(playerPos, sizePlayer);
		super.render(batch, playerPos, sizePlayer);
		}
	}
	
	@Override
	public void touchEnnemi() {
		super.touchEnnemi();
	}


	@Override
	public void start(int state) {
		super.start(state);
	}


	@Override
	public void stop() {
		super.stop();
	}
	
	@Override
	protected void addEnnemis(ArrayList<Ennemi> _ennemis) {
		super.addEnnemis(_ennemis);
	}

}
