package com.stuffle.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stuffle.Interface.Bar;
import com.stuffle.Parameter.CollisionFilter;
import com.stuffle.Parameter.GameParameter;
import com.stuffle.creator.BodyCreator;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ennemi extends People {

	int iStateNumber = 0;
	int iLife = 0;
	float fOldPosY;
	State currentState = State.PREPARING;
	Direction direction = Direction.RIGHT;
	
	public boolean touched = false;
	
	float w;
	Bar lifeBar;
	
	public enum State {
		JUMPING, PREPARING;
	}
	
	public enum Direction {
		RIGHT, LEFT, UP, DOWN, FINDER;
	}

	public Ennemi(String _nameFileTexture, float _PPM, float f, int _initPosY, World _world, int _life) {
		super(_nameFileTexture, _PPM, f, _initPosY, _world);
		iLife = _life;
		
		
		bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(f / _PPM, _initPosY / _PPM);

        // Create a circle shape and set its radius to 6
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(spritePeople.getWidth() / 2,
                spritePeople.getHeight() / 2);

        
        body = BodyCreator.createBody(world,bodyDef, CollisionFilter.BIT_ENNEMIS, CollisionFilter.BIT_WALL, (short)0, shape, 60 / (9.79f * 9.79f), 0f);
        body.setFixedRotation(true);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();
        
        w = 50;
		lifeBar = new Bar(body.getPosition().x + spritePeople.getWidth() / (2*GameParameter.PPM) - w / (2*GameParameter.PPM) , body.getPosition().y + 0.2f
				,w,2,iLife,iLife,2,2,null, Color.RED,true);
		
	}

	@Override
	protected void update() {
		super.update();
		switch(strMovementType)
		{
			case "Jump" :
								
					if(currentState != State.JUMPING)
					{
						currentState = State.JUMPING;
						fOldPosY = body.getPosition().y;
						if(direction == Direction.RIGHT)
						{
							
							this.body.setLinearVelocity(3, 0);
							this.body.applyForceToCenter(0, 20f, true);
							direction = Direction.LEFT;
						}
						else
						{
							this.body.setLinearVelocity(-3, 0);
							this.body.applyForceToCenter(0, 20f, true);
							direction = Direction.RIGHT;
						}
					}
					else
					{
						 if (body.getPosition().y <= fOldPosY)
						 {
					          currentState = State.PREPARING;
						 }
					}
			break;
		}
		
		super.setPositionPeople();
		lifeBar.updatePos(body.getPosition().x + spritePeople.getWidth() / (2*GameParameter.PPM) - w / (2*GameParameter.PPM) , body.getPosition().y + 0.2f);
	}

	@Override
	public void render(SpriteBatch batch) {
		update();
		super.render(batch);
		lifeBar.render(batch);
	}
	

	public void looseLife(int attaqueDmg) {
		iLife -= attaqueDmg;
		if(iLife <= 0)
			isDead = true;
		lifeBar.setContentW(iLife);
		lifeBar.computeContent();
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
