package com.stuffle.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Map
{
    private AssetManager assetManager;
    private TiledMap map;
    
    private final static int ppt=16;
    private Array<Body> bodies;
    private BodyDef bodyDef;

    public Map(String _nameMap)
    {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(_nameMap, TiledMap.class);
        assetManager.finishLoading();
        map = assetManager.get(_nameMap);
    }

    public void mapBloc(World _world)
    {
        MapObjects objects = map.getLayers().get("C1").getObjects();

        bodies = new Array<Body>();

        for (MapObject object : objects)
        {

            if (object instanceof TextureMapObject)
            {
                continue;
            }

            Shape shape = null;

            if (object instanceof RectangleMapObject)
            {

                Rectangle rectangle = ((RectangleMapObject) object)
                        .getRectangle();
                PolygonShape polygon = new PolygonShape();
                Vector2 size = new Vector2(
                        (rectangle.x + rectangle.width * 0.5f) / ppt,
                        (rectangle.y + rectangle.height * 0.5f) / ppt);
                polygon.setAsBox(rectangle.width * 0.5f / ppt,
                        rectangle.height * 0.5f / ppt, size, 0.0f);
                shape = polygon;
            }

            bodyDef = new BodyDef();
            bodyDef.type = BodyType.StaticBody;
            Body bodyG = _world.createBody(bodyDef);
            FixtureDef fixture = new FixtureDef();
            fixture.friction = 1.0f;
            fixture.shape = shape;
            bodyG.createFixture(fixture);

            bodies.add(bodyG);

            shape.dispose();
        }
    }

    public TiledMap getMap()
    {
        return map;
    }

    public void setMap(TiledMap map)
    {
        this.map = map;
    }

}
