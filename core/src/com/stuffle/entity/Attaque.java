package com.stuffle.entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Attaque {

	protected boolean asProjectile = false;
	protected ArrayList<Projectile> projectiles = null;
	protected Rectangle recHitbox = null;
	protected Sprite spriteAttaque = null;
	protected boolean attaqueStarted = false;
	protected static ArrayList<Ennemi> ennemis;
	protected int stateNumber = 0;
	protected boolean hasAttaqueAnnimation;
	protected Texture attaqueTexture;
	protected TextureRegion currentFrame;
	protected float PPM;
	protected int attaqueDmg = 0;
	protected float timeAnimation = 1 / 20f;
	Animation<TextureRegion> attaqueAnimation;

	protected int iWidthFrame;
	protected int iHeightFrame;
	protected int iRowFrame;
	protected int iColsFrame;
	private Vector2 spritePosition;
	protected int currentState = 0;
	
	float stateTime;
	
	protected void initialisation() {
		if (hasAttaqueAnnimation) {
			TextureRegion[][] tmp = TextureRegion.split(attaqueTexture, iWidthFrame, iHeightFrame);

			TextureRegion[] attaqueFrames = new TextureRegion[iColsFrame * iRowFrame];
			int index = 0;
			for (int i = 0; i < iRowFrame; i++) {
				for (int j = 0; j < iColsFrame; j++) {
					attaqueFrames[index++] = tmp[i][j];
				}
			}
			attaqueAnimation = new Animation<TextureRegion>(timeAnimation, attaqueFrames);
		}
		spritePosition = new Vector2();
	}

	protected void update(Vector2 playerPos, Vector2 sizePlayer) {
		if (stateTime / timeAnimation >= stateNumber) {
			stop();
		} else {
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = attaqueAnimation.getKeyFrame(stateTime, true);
			
			
			spritePosition.x = playerPos.x + sizePlayer.x / (2 * PPM);
			spritePosition.y = playerPos.y - sizePlayer.y / (2 * PPM);
			if(currentState == 3)
				spritePosition.x -= currentFrame.getRegionWidth() / PPM;
			recHitbox.set(new Rectangle(spritePosition.x,spritePosition.y,iWidthFrame / PPM, iHeightFrame/PPM));
		}
		touchEnnemi();

	}

	protected void render(SpriteBatch batch, Vector2 playerPos, Vector2 sizePlayer) {
		if (attaqueStarted) {
			if(currentState == 3)
				currentFrame.flip(true,false);			
			batch.draw(currentFrame, spritePosition.x, spritePosition.y,
	                (currentFrame.getRegionWidth()) / PPM,
	                (currentFrame.getRegionHeight()) / PPM);
			if(currentState == 3)
				currentFrame.flip(true,false);			
		}
	}
	
	
	protected void touchEnnemi() {
		if(attaqueStarted)
		{	
			for(Iterator<Ennemi> iterator = ennemis.iterator(); iterator.hasNext();)
			{
				Ennemi ennemi = iterator.next();
				Rectangle r = new Rectangle(ennemi.getPosition().x,ennemi.getPosition().y,
						ennemi.getSpritePeople().getWidth(), ennemi.getSpritePeople().getHeight());
				Rectangle intersection = new Rectangle();                  
				boolean isInersect = Intersector.intersectRectangles(r, recHitbox, intersection);
				if(isInersect)
				{
					if(!ennemi.touched)
					{
						ennemi.looseLife(attaqueDmg);
						ennemi.touched = true;
					}
				}
				
			}
		}
	}
	
	protected void start(int state) {
		if (!attaqueStarted) {
			attaqueStarted = true;
			currentState = state;
			stateTime = 0;
			recHitbox = new Rectangle();
		}
	}

	protected void stop() {
		attaqueStarted = false;
		recHitbox = null;
		ennemiTouchReset();
	}
	
	private void ennemiTouchReset()
	{
		for(Iterator<Ennemi> iterator = ennemis.iterator(); iterator.hasNext();)
		{
			Ennemi ennemi = iterator.next();
			ennemi.touched = false;
		
		}
	}

	protected void addEnnemis(ArrayList<Ennemi> _ennemis) {
		ennemis = _ennemis;
	}
}
