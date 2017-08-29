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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.stuffle.Index.IndexScreen;
import com.stuffle.Index.Mission;
import com.stuffle.Index.PartStats;
import com.stuffle.Parameter.GameParameter;
import com.stuffle.entity.Ennemi;
import com.stuffle.entity.People;
import com.stuffle.entity.Player;
import com.stuffle.entity.Spawner;
import com.stuffle.uid.PlayerUid;

public class GameScreen implements Screen
{
    final StufflesGame game;

    private OrthographicCamera camera;
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

        this.camera.setToOrtho(false, Gdx.graphics.getWidth() / GameParameter.PPM,
                Gdx.graphics.getHeight() / GameParameter.PPM);
        batch = new SpriteBatch();

        map = new Map("Maps/Level_0.tmx");

        renderer = new OrthogonalTiledMapRenderer(map.getMap(), 1 / 64f);

        world = new World(new Vector2(0, -20), true);

        map.mapBloc(world);
        player = new Player("./"+ PartStats.strType, GameParameter.PPM, 350*GameParameter.RATIO, (int)(7*64 *GameParameter.RATIO), world);
        
        ennemis = new ArrayList<Ennemi>();
        for(Spawner spawn : map.Spawns)
        	ennemis.add(new Ennemi("./Ennemis/" + spawn.strType,GameParameter.PPM, spawn.fPosX*GameParameter.RATIO, (int)(spawn.fPosY*GameParameter.RATIO), world,50));
        
        
        player.autoAttaque.addEnnemis(ennemis);
        
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

        float shiftHeight = (Gdx.graphics.getWidth() / (4 * GameParameter.PPM)) + 0.6f;
        Vector2 cameraPosition = new Vector2();
        if (player.getPosition().y*100 <= Gdx.graphics.getHeight() / 2)
        {
            cameraPosition.set(player.getPosition().x, shiftHeight);

        } else
        {
            cameraPosition.set(player.getPosition().x,player.getPosition().y);

        }

        camera.position.set(cameraPosition.x, cameraPosition.y, 0);
        camera.viewportWidth = Gdx.graphics.getWidth() / GameParameter.PPM;
        camera.viewportHeight = Gdx.graphics.getHeight() / GameParameter.PPM;
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
