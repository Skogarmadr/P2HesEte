package com.stuffle.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.stuffle.Index.IndexScreen;
import com.stuffle.Index.Mission;
import com.stuffle.Index.PartStats;
import com.stuffle.uid.PlayerUid;

public class GameScreen implements Screen
{
    final static float PPM = 100;

    final StufflesGame game;

    private OrthographicCamera camera;
    private int level;

    private World world;

    private TiledMapRenderer renderer;
    
    private SpriteBatch batch;
    
    public static ArrayList<Ennemi> ennemis;
    
    public PlayerUid pUid;
    
    /*********************\ 
     * After Refractoring *
     \********************/

    People player;
    Map map;
    String strObjectif = "Ennemis";
    Mission actualMissions;
    public GameScreen(StufflesGame _game,Mission _mission)
    {
    	this.actualMissions = _mission;
        this.game = _game;
        this.camera = this.game.camera;

        this.camera.setToOrtho(false, Gdx.graphics.getWidth() / PPM,
                Gdx.graphics.getHeight() / PPM);
        this.level = this.actualMissions.iLevel;
        batch = new SpriteBatch();

        map = new Map("map1.tmx");
        System.out.println(map.getMap().getProperties());

        renderer = new OrthogonalTiledMapRenderer(map.getMap(), 1 / 16f);

        world = new World(new Vector2(0, -10), true);
        player = new Player("./"+ PartStats.strType, PPM, 27*PPM, 300, world);
        
        ennemis = new ArrayList<Ennemi>();
        ennemis.add(new Ennemi("./Ennemis/Blob", PPM, 34*PPM, 270, world,100));
        ennemis.add(new Ennemi("./Ennemis/Blob", PPM, 35*PPM, 270, world,100));
        ennemis.add(new Ennemi("./Ennemis/Blob", PPM, 37*PPM, 270, world,100));
        ennemis.add(new Ennemi("./Ennemis/Blob", PPM, 38*PPM, 270, world,100));
        ennemis.add(new Ennemi("./Ennemis/Blob", PPM, 28*PPM, 270, world,100));
        ennemis.add(new Ennemi("./Ennemis/Blob", PPM, 30*PPM, 270, world,100));
        
        
        player.autoAttaque.addEnnemis(ennemis);
        
        map.mapBloc(world);
    }
    
    

    @Override
    public void show()
    {
        // TODO Auto-generated method stub

    }
    
    public void objectif_end()
    {
    	boolean objectifComplete = false;
    	switch(strObjectif)
    	{
			case "Ennemis":
				if(ennemis.size() <= 0)
					objectifComplete = true;
				break;
    	}
    	if(objectifComplete)
    	{
    		PartStats.iExp += actualMissions.iXp;
    		PartStats.iGold += actualMissions.iGold;
    		close();
    	}
    }
    
    public void update()
    {
    	for(Iterator<Ennemi> iterator = ennemis.iterator(); iterator.hasNext();)
		{
			Ennemi ennemi = iterator.next();
			if(ennemi.isDead)
			{
				ennemi.dispose();
				iterator.remove();
			}			
		}
    	
    	if(PartStats.isDead)
    		close();
    	
    	objectif_end();
    }
    
    @Override
    public void render(float delta)
    {
    	update();
        world.step(1 / 60f, 6, 2);
        Gdx.gl.glClearColor(0.5f, 0.4f, 0.4f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float shiftHeight = (Gdx.graphics.getWidth() / (4 * PPM)) + 0.6f;
        Vector2 cameraPosition = new Vector2();
        if (player.getPosition().y*100 <= Gdx.graphics.getHeight() / 2)
        {
            cameraPosition.set(player.getPosition().x, shiftHeight);

        } else
        {
            cameraPosition.set(player.getPosition().x,player.getPosition().y);

        }

        camera.position.set(cameraPosition.x, cameraPosition.y, 0);
        camera.viewportWidth = Gdx.graphics.getWidth() / PPM;
        camera.viewportHeight = Gdx.graphics.getHeight() / PPM;
        camera.update();
        
        if (Gdx.input.isKeyPressed(Input.Keys.T))
        {
            this.pause();
        }

        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        player.render(batch);
        
        for(Ennemi e : ennemis)
        	e.render(batch);
        batch.end();
        }
    
    public void close()
    {
    	IndexScreen index = null;
    	if(PartStats.isDead)
    		index = new IndexScreen(game);
    	else
    		index = new IndexScreen(game, PartStats.saveSource, true);
    	game.setScreen(index);
    	dispose();
    }
    
    @Override
    public void resize(int width, int height)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause()
    {
        // TODO Auto-generated method stub
        System.out.println("Le jeu est en pause");

    }

    @Override
    public void resume()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose()
    {
        // TODO Auto-generated method stub

    }

}
