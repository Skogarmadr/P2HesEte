package com.stuffle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuffle.Menu.MainMenuScreen;

public class StufflesGame extends Game
{

    public SpriteBatch batch;
    public BitmapFont font;
    
    public OrthographicCamera camera;
    
    @Override
    public void create()
    {
        batch = new SpriteBatch();

        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.setScreen(new MainMenuScreen(this));
        
    }
    
    @Override
    public void render()
    {
        super.render(); // important!   
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

}
