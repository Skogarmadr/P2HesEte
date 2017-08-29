package com.stuffle.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.stuffle.Index.PartStats;
import com.stuffle.Parameter.CollisionFilter;
import com.stuffle.creator.BodyCreator;

public class Player extends People
{
    final static float MAX_VELOCITY = 4f;
    
    
    enum State
    {
        JUMPING, FALLING, STOP_STATE;
    }
    

	protected static ArrayList<Ennemi> ennemis;

    public Player(String _nameFileTexture, float _PPM, float f,
            int initPosY, World _world)
    {
        super(_nameFileTexture, _PPM, f, initPosY, _world);
    	autoAttaque = new AutoAttaque();
    	autoAttaque.PPM = PPM;
    	
    	bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(f / _PPM, initPosY / _PPM);

        // Create a circle shape and set its radius to 6
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(spritePeople.getWidth() / 2,
                spritePeople.getHeight() / 2);

        body = BodyCreator.createBody(world,bodyDef, CollisionFilter.BIT_PLAYER, CollisionFilter.BIT_WALL, (short)0, playerShape, 12 / (9.79f * 9.79f), 0.2f);
        body.setFixedRotation(true);
       
        playerShape.dispose();
        
    }

    @Override
    public void update()
    {
    	super.update();
    	Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();
        boolean keyA = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean keyD = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean mouseL = Gdx.input.isButtonPressed(0);
        
        if(!keyA && !keyD)
        {
            state = 1;
        }

        if (keyD && vel.x < MAX_VELOCITY)
            body.applyForce(new Vector2(10, 0), new Vector2(pos.x, pos.y),
                    true);
        
        if (keyA && vel.x > -MAX_VELOCITY)
            body.applyForceToCenter(new Vector2(-10, 0), true);
        
        if (keyD)
        	state = 2;
        if (keyA)
        	state = 3;
        
        updateInput(0);
        
        if(mouseL)
        {
        	autoAttaque.start(state);
        }
        
    }    
    @Override
    public void render(SpriteBatch batch)
    {
        update(); 
        super.setPositionPeople();
        super.render(batch);
        autoAttaque.render(batch, spritePosition, sizeTexture);
    }
    
    public void touchByEnnemi()
    {
    	for(Ennemi ennemi : ennemis)
    	{
    		boolean isIntersect = false;
    		if(isIntersect)
    		{
    			PartStats.iLife--;
    			if(PartStats.iLife <= 0)
    				PartStats.isDead = true;
    		}
    	}
    }
    

    public void updateInput(float dt)
    {
        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                || Gdx.input.isKeyJustPressed(
                        Input.Keys.W)) /* || hud.jumpButtonPressed == true */)
        {
            if (this.getState() != State.JUMPING)
            {
                this.body.setLinearVelocity(body.getLinearVelocity().x, 0);
                body.applyForceToCenter(0, 70f, true);
            }
        }
    }

    public State getState()
    {
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0))
            return State.JUMPING;
        else if (body.getLinearVelocity().y < 0)
            return State.FALLING;
        else
            return State.STOP_STATE;
    }
    
    public void addEnnemis(ArrayList<Ennemi> _ennemis) {
		ennemis = _ennemis;
	}
    
    public void fall()
    {
    	if(body.getPosition().y < -15)
    		PartStats.isDead=true;
    }
}
