package com.stuffle.fx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteAnimation{
	private int iFrameCols, iFrameRows;
	public TextureRegion currentFrame;
	Animation<TextureRegion> animation;
	Texture tSheet;
	float stateTime, fPosX, fPosY, fSpeed;
	public AnimationState eState;
	
	public SpriteAnimation(int FrameCols, int FrameRows, String link, float posX, float posY, float speed, AnimationState state)
	{
		iFrameCols = FrameCols;
		iFrameRows = FrameRows;
		eState = state;
		tSheet = new Texture(Gdx.files.internal(link));
		fPosX = posX;
		fPosY = posY;
		fSpeed = speed;
		initialisation();
	}

	public void initialisation() {
		TextureRegion[][] tmp = TextureRegion.split(tSheet, 
				tSheet.getWidth() / iFrameCols,
				tSheet.getHeight() / iFrameRows);
		
		TextureRegion[] frames = new TextureRegion[iFrameCols * iFrameRows];
		int index = 0;
		for (int i = 0; i < iFrameRows; i++) {
			for (int j = 0; j < iFrameCols; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		animation = new Animation<TextureRegion>(fSpeed, frames);
				
	}
	
	public void start()
	{
		eState = AnimationState.STARTED;
		stateTime = 0f;
	}
	
	public void end()
	{
		eState = AnimationState.WAITING;
	}
	
	public void update(float x, float y)
	{
		fPosX = x;
		fPosY = y;
	}

	public void render(SpriteBatch spriteBatch) {
		stateTime += Gdx.graphics.getDeltaTime();		
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
		spriteBatch.draw(currentFrame, fPosX, fPosY);
		
		if(animation.isAnimationFinished(stateTime))
			end();
		
		
	}
	
	public void dispose() {
		tSheet.dispose();
	}
	
}
