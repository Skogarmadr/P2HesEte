package com.stuffle.Menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Index.IndexScreen;
import com.stuffle.Input.mouseInput;
import com.stuffle.Parameter.GameParameter;
import com.stuffle.Parameter.LoadingState;
import com.stuffle.Parameter.MenuState;
import com.stuffle.Utils.Button;
import com.stuffle.game.StufflesGame;

public class MainMenuScreen implements Screen
{
    final StufflesGame game;
    
    
    public BitmapFont font;
    OrthographicCamera camera;
    
    private Button[] buttons;
    private String[] textButton = {"Continue", "NewGame","Load","Exit"};
    private LoadingPanel loadingPanel;
    private MenuState menuState = MenuState.DEFAULT;
    
    /*Screen of the main menu*/
    public MainMenuScreen(StufflesGame _game)
    {
        this.game = _game;
        camera = this.game.camera;
        int height = 120;
        int width = 450;
        int vSeparator = 20;
        int layoutVertical = height * 4 + vSeparator * 3;
        buttons = new Button[4];
        for(int i = 0; i < 4; i++)
        	buttons[i] = new Button(15,GameParameter.SCREEN_HEIGHT / 2 + layoutVertical / 2 - i * (height+vSeparator),
        			width,height,textButton[i],"Left",22);
        	
        
    }
    
    private void Update()
    {
    	switch(menuState)
    	{
    		case DEFAULT :
	    		mouseInput.update();
		    	btn_Clicked(mouseInput.buttons_Listener(buttons, 0));
		    	btn_Hover(mouseInput.buttons_Listener(buttons, 1));
		    	btn_Was_Hover(mouseInput.buttons_Listener(buttons, 2));
	    	break;
    		case LOADING :
	    		if(loadingPanel.getLoadingState() == LoadingState.CHOOSE)
	    		{
	    			menuState = MenuState.DEFAULT;
	    			IndexScreen index = new IndexScreen(game,loadingPanel.strNameChoose);
	    			game.setScreen(index);
	    			loadingPanel = null;
	                this.dispose();
	    		}
	    		else if (loadingPanel.getLoadingState() == LoadingState.EXIT)
	    		{
	    			menuState = MenuState.DEFAULT;
	    			loadingPanel = null;
	    		}
	    	break;
    		case CONTINUE :
	    		if(loadingPanel.getLoadingState() == LoadingState.CHOOSE)
	    		{
	    			menuState = MenuState.DEFAULT;
	    			IndexScreen index = new IndexScreen(game,loadingPanel.strNameChoose);
	    			game.setScreen(index);
	    			loadingPanel = null;
	                this.dispose();
	    		}
	    		else
	    			menuState = MenuState.DEFAULT;
	    	break;
    	}
    }
    
    @Override
    public void render(float delta)
    {
    	Update();
    	
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        
        switch(menuState)
        {
        	case DEFAULT :
        		for(Button button : buttons)
                	button.render(game.batch);
        		break;
        	case LOADING:
        		loadingPanel.render(game.batch);
        		break;
        }
        
        
        game.batch.end();
        
    }
    
    
	public void btn_Clicked(int index) {
    	switch(index)
    	{
    		case 0:
    			 loadingPanel = new LoadingPanel(new Rectangle((int) (GameParameter.SCREEN_WIDTH * 0.2),
     		    		(int) (GameParameter.SCREEN_HEIGHT * 0.1), (int) (GameParameter.SCREEN_WIDTH * 0.6), 
     		    		(int) (GameParameter.SCREEN_HEIGHT * 0.8)));
    			 loadingPanel.chooseFirstItem();
    			 menuState = MenuState.CONTINUE;
    			break;
    		case 1:
    			game.setScreen(new IndexScreen(game));
                dispose();
    			break;
    		case 2:
    		    loadingPanel = new LoadingPanel(new Rectangle((int) (GameParameter.SCREEN_WIDTH * 0.2),
    		    		(int) (GameParameter.SCREEN_HEIGHT * 0.1), (int) (GameParameter.SCREEN_WIDTH * 0.6), 
    		    		(int) (GameParameter.SCREEN_HEIGHT * 0.8)));
    		    menuState = MenuState.LOADING;
    			break;
    		case 3:
    			Gdx.app.exit();
    			break;
    	}
	}

	
	public void btn_Hover(int index) {
		if(index >= 0)
		{
	    	buttons[index].mouseHover = true;
	    	buttons[index].changeBackColor(Color.BROWN);	
		}
	}

	
	public void btn_Was_Hover(int index) {
		if(index >= 0)
		{
	    	buttons[index].mouseHover = false;
	    	buttons[index].changeBackColor(Color.WHITE);
		}
	}
    
    
	@Override
    public void show()
    {
        
        
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
    
        
    }

	



}
