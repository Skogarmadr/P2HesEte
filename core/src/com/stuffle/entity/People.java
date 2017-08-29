package com.stuffle.entity;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public abstract class People
{
    // protected Texture texturePeople;
	protected Rectangle rectHitBox;
    protected Sprite spritePeople;
    protected Vector2 sizeTexture = new Vector2(75, 110);
    protected float PPM;
    protected BodyDef bodyDef;
    protected Body body;
    protected int state = 1;
    public AutoAttaque autoAttaque;
    
    
    protected Vector2 walkRightTexturePosition1 = Vector2.Zero;
    protected Vector2 walkRightTexturePosition2 = Vector2.Zero;
    protected Vector2 walkLeftTexturePosition1 = Vector2.Zero;
    protected Vector2 walkLeftTexturePosition2 = Vector2.Zero;
    protected Vector2 waitTexturePosition1 = Vector2.Zero;
    protected Vector2 waitTexturePosition2 = Vector2.Zero;

    private int walkRightNumberTexture = 0;
    private int walkLeftNumberTexture = 0;
    private int waitNumberTexture = 0;

    protected String strMovementType;
    
    public boolean isDead = false;
    // private int heightTexture = 110, widthTexture = 75;

    TextureRegion currentFrame = null;
    int verticalMirror = 1;


    protected Vector2 spritePosition;
    protected World world;
    
    
    // Objects used
    protected Animation<TextureRegion> walkRightAnimation; // Must declare frame type
    protected Animation<TextureRegion> walkLeftAnimation;
    protected Animation<TextureRegion> waitAnimation;
    // SpriteBatch spriteBatch;

    Texture peopleTexture;
    String sourceFolder;
    // A variable for tracking elapsed time for the animation
    float stateTime;

    public People(String _sourceFolder, float _PPM, float f,
            int _initPosY, World _world)
    {
    	world = _world;
        sourceFolder = _sourceFolder;
        initialisation();

        peopleTexture = new Texture(
                Gdx.files.internal(_sourceFolder + "/Sprite.png"));

        TextureRegion[][] tmp = TextureRegion.split(peopleTexture,
                (int) sizeTexture.x, (int) sizeTexture.y);

        // Place the regions into a 1D array in the correct order, starting from
        // the top
        // left, going across first. The Animation constructor requires a 1D
        // array.
        TextureRegion[] walkRightFrames = new TextureRegion[walkRightNumberTexture];
        TextureRegion[] walkLeftFrames = new TextureRegion[walkLeftNumberTexture];
        TextureRegion[] waitFrames = new TextureRegion[waitNumberTexture];
        int index = 0;

        for (int j = (int) walkRightTexturePosition1.y; j <= (int) walkRightTexturePosition2.y; j++)
        {
            int i = 0;
            if (index == 0)
                i = (int) walkRightTexturePosition1.x;
            while (i <= (int) walkRightTexturePosition2.x)
            {
                walkRightFrames[index++] = tmp[j][i];
                i++;
            }
        }
        
        index = 0;
        for (int j = (int) walkLeftTexturePosition1.y; j <= (int) walkLeftTexturePosition2.y; j++)
        {
            int i = 0;
            if(index == 0)
            {
                i = (int) walkLeftTexturePosition1.x;
            }
            while(i <= (int) walkLeftTexturePosition2.x)
            {
                walkLeftFrames[index++] = tmp[j][i];
                i++;
            }     
        }

        index = 0;
        for (int j = (int) waitTexturePosition1.y; j <= (int) waitTexturePosition2.y; j++)
        {
            int i = 0;
            if (index == 0)
                i = (int) waitTexturePosition1.x;
            while (i <= (int) waitTexturePosition2.x)
            {
                waitFrames[index++] = tmp[j][i];
                i++;
            }
        }
        // Initialize the Animation with the frame interval and array of frames
        walkRightAnimation = new Animation<TextureRegion>(1 / 7f,
                walkRightFrames);
        walkLeftAnimation = new Animation<TextureRegion>(1 / 7f,
                walkLeftFrames);
        //walkLeftAnimation = new Animation<TextureRegion>(1 / 7f, walkLeftFrames);
        waitAnimation = new Animation<TextureRegion>(1 / 7f, waitFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        // spriteBatch = new SpriteBatch();
        stateTime = 0f;

        spritePosition = new Vector2();

        // texturePeople = new Texture(_nameFileTexture);
        spritePeople = new Sprite(walkRightFrames[0]);
        spritePeople.setSize(walkRightFrames[0].getRegionWidth() / (_PPM),
                (walkRightFrames[0].getRegionHeight()) / (_PPM));

        PPM = _PPM;

        

    }
    

    public Texture getTexturePeople()
    {
        return peopleTexture;
    }

    public void setTexturePeople(Texture texturePeople)
    {
        this.peopleTexture = texturePeople;
    }

    public Sprite getSpritePeople()
    {
        return spritePeople;
    }

    public void setSpritePeople(Sprite texturePlayer)
    {
        this.spritePeople = texturePlayer;
    }

    public BodyDef getBodyDef()
    {
        return bodyDef;
    }

    public void setBodyDef(BodyDef bodyDef)
    {
        this.bodyDef = bodyDef;
    }

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
    {
        this.body = body;
    }

    public void setPositionPeople()
    {
        spritePosition.set(
                this.body.getPosition().x - spritePeople.getWidth() / 2f,
                this.body.getPosition().y - spritePeople.getHeight() / 2f);
    }

    public void initialisation()
    {
        try
        {
            Element root = new XmlReader()
                    .parse(Gdx.files.internal(sourceFolder + "/spec.xml"));
            Element move = root.getChildByName("Movement");
            Element sprite = root.getChildByName("Sprite");

            Element typeMove = move.getChild(0);
            strMovementType = typeMove.getName();
            Element wait = typeMove.getChildByName("Wait");
            Element walkRight = typeMove.getChildByName("WalkRight");
            Element walkLeft = typeMove.getChildByName("WalkLeft");
            waitTexturePosition1 = new Vector2(wait.getFloat("x1"),
                    wait.getFloat("y1"));
            waitTexturePosition2 = new Vector2(wait.getFloat("x2"),
                    wait.getFloat("y2"));
            walkRightTexturePosition1 = new Vector2(walkRight.getFloat("x1"),
            		walkRight.getFloat("y1"));
            walkRightTexturePosition2 = new Vector2(walkRight.getFloat("x2"),
            		walkRight.getFloat("y2"));
            walkLeftTexturePosition1 = new Vector2(walkLeft.getFloat("x1"), walkLeft.getFloat("y1"));
            walkLeftTexturePosition2 = new Vector2(walkLeft.getFloat("x2"), walkLeft.getFloat("y2"));

            // Autre variables
            int nbCols = typeMove.getInt("NbCols");
            int nbRows = typeMove.getInt("NbRows");

            walkRightNumberTexture = (int) ((walkRightTexturePosition2.y
                    - walkRightTexturePosition1.y + 1) * nbCols
                    - walkRightTexturePosition1.x
                    - (nbCols - (walkRightTexturePosition2.x + 1)));
            
            walkLeftNumberTexture = (int) ((walkLeftTexturePosition2.y
                    - walkLeftTexturePosition1.y + 1) * nbCols
                    - walkLeftTexturePosition1.x
                    - (nbCols - (walkLeftTexturePosition2.x + 1)));

            waitNumberTexture = (int) ((waitTexturePosition2.y
                    - waitTexturePosition1.y + 1) * nbCols
                    - waitTexturePosition1.x
                    - (nbCols - (waitTexturePosition2.x + 1)));

            sizeTexture = new Vector2(sprite.getFloat("Width"),
                    sprite.getFloat("Height"));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void update()
    {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed
        // animation time

        // Get current frame of animation for the current stateTime
        if (state == 1)
        {
            currentFrame = waitAnimation.getKeyFrame(stateTime, true);
        } 
        else if(state == 2)
        {
            currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
        }
        else  if (state == 3)
        {
            currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
        }
        
        rectHitBox = new Rectangle(body.getPosition().x - 5, body.getPosition().y - 5, spritePeople.getWidth() + 10, spritePeople.getHeight() +10);
    	fall();
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(currentFrame, spritePosition.x, spritePosition.y,
                (currentFrame.getRegionWidth()) / PPM,
                (currentFrame.getRegionHeight()) / PPM);
    }
    

    protected void fall()
    {
    	
    }
    
    public Vector2 getPosition()
    {
        return body.getPosition();

    }

}
