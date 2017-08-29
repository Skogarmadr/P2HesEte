package com.stuffle.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class MapBloc
{
    private final static int ppt=16;
    private Array<Body> bodies;
    private BodyDef bodyDef;
    
    public MapBloc(World _world, Map _map)
    {
        
        MapObjects objects = _map.getMap().getLayers().get("C1").getObjects();

        bodies = new Array<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape = null;

            if (object instanceof RectangleMapObject) {
                
                Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
                PolygonShape polygon = new PolygonShape();
                Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                                           (rectangle.y + rectangle.height * 0.5f ) / ppt);
                polygon.setAsBox(rectangle.width * 0.5f / ppt,
                                 rectangle.height * 0.5f / ppt,
                                 size,
                                 0.0f);
                shape = polygon;
            }
            
            bodyDef = new BodyDef(); 
            bodyDef.type = BodyType.StaticBody;
            Body bodyG = _world.createBody(bodyDef);
            bodyG.createFixture(shape, 1);
            
            bodies.add(bodyG);

            shape.dispose();
        }
       
        
    }

}
