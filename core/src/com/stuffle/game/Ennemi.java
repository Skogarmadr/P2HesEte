package com.stuffle.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stuffle.game.Player.State;

public class Ennemi extends People {

	int iStateNumber = 0;
	int iLife = 0;
	
	State currentState = State.PREPARING;

	enum State {
		JUMPING, PREPARING;
	}

	public Ennemi(String _nameFileTexture, float _PPM, float f, int _initPosY, World _world, int _life) {
		super(_nameFileTexture, _PPM, f, _initPosY, _world);
		iLife = _life;
	}

	@Override
	protected void update() {
		super.update();
		switch(strMovementType)
		{
			case "Jump" :
								
					if(getState() != State.JUMPING)
					{
						if(walkRightAnimation.getKeyFrameIndex(stateTime) == 4)
						{	
							state = 1;
							this.body.setLinearVelocity(3, 0);
			                body.applyForceToCenter(0, 9f, true);
						}
					}
					else
					{
						state = 2;
					}
			break;
		}
		
		super.setPositionPeople();
	}

	@Override
	public void render(SpriteBatch batch) {
		update();
		super.render(batch);
	}
	
	 public State getState()
	    {
	        if (body.getLinearVelocity().y != 0)
	            return State.JUMPING;
	        else
	            return State.PREPARING;
	    }

	public void looseLife(int attaqueDmg) {
		iLife -= attaqueDmg;
		if(iLife <= 0)
			isDead = true;
	}
	
	public void dispose(){
		world.destroyBody(body);
		body.setUserData(null);
		body = null;
		
	}
	
	public void fall()
    {
    	if(body.getPosition().y < -15)
    		isDead=true;
    }
}
