package com.stuffle.creator;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BodyCreator {
	public static Body createBody(World _world,BodyDef _bodyDef, short _category, short _mask, short _index, Shape _shape, float _density, float _frixion)
	{
		Body bodyG = _world.createBody(_bodyDef);
		FixtureDef fixture = new FixtureDef();
		fixture.friction = 1.0f;
		fixture.shape = _shape;
		fixture.density = _density;
		fixture.friction = _frixion;
		fixture.filter.categoryBits = _category;
		fixture.filter.maskBits = _mask;
		fixture.filter.groupIndex = _index;
		bodyG.createFixture(fixture);
		
		return bodyG;
	}
}
