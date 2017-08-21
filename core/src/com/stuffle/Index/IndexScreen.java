package com.stuffle.Index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.stuffle.Input.mouseInput;
import com.stuffle.Parameter.GameParameter;
import com.stuffle.Utils.Label;
import com.stuffle.game.GameScreen;
import com.stuffle.game.StufflesGame;
import com.stuffle.uid.PlayerUid;

public class IndexScreen implements Screen {

	
	private TrainingCamp camp;
	private Market market;
	private MissionPanel mission;
	
	//Array of element like the market, the training camp, etc...
	private IndexElement[] elements;
	private boolean isLocked = false;
	
	private SpriteBatch batch;

	public int[] iSpec;
	private Portrait[] portraits;
	private Label title;
	private String strIndexState = "";
	private Sprite backGround;
	private BossCastle bossCastle;
	final StufflesGame game;
	OrthographicCamera camera;
	boolean partAlreadyLoad;
	PlayerUid pUid;

	
	/*Screen of the index, where you can upgrade youre perso and choose youre missions*/
	public IndexScreen(StufflesGame _game) {
		this.game = _game;
		strIndexState = "NewGame";
		initialisation();
	}

	public IndexScreen(StufflesGame _game, String _strNamePart) {
		game = _game;
		PartStats.saveSource = _strNamePart;
		strIndexState = "Game";
		initialisation();
	}
	public IndexScreen(StufflesGame _game, String _strNamePart, boolean alreadyLoad) {
		game = _game;
		PartStats.saveSource = _strNamePart;
		strIndexState = "Game";
		partAlreadyLoad = alreadyLoad;
		initialisation();
	}

	private void update() {
		mouseInput.update();
		if (strIndexState == "NewGame") {
			for (int i = 0; i < 3; i++) {
				if (mouseInput.mouseClickOnRectangle(portraits[i].rectangle, 0))
					portraits_Clicked(i);
				else {
					if (mouseInput.mouseOverRectangle(portraits[i].rectangle)) {
						if (!portraits[i].mouseHover)
							portraits_Hover(i);
					} else if (portraits[i].mouseHover)
						portraits_Was_Hover(i);
				}
			}
		} else if (strIndexState == "Game") {
			isLocked = elements[0].isShown | elements[1].isShown | elements[2].isShown;
			if (!isLocked) {
				for (int i = 0; i < 3; i++) {
					if (mouseInput.mouseClickOnRectangle(elements[i].rectElement, 0))
						elements_Clicked(i);
					else {
						if (mouseInput.mouseOverRectangle(elements[i].rectElement)) {
							if (!elements[i].mouseHover)
								elements_Hover(i);
						} else if (elements[i].mouseHover)
							elements_Was_Hover(i);
					}
				}

			} else {
				for (int i = 0; i < 2; i++) {
					if (elements[i].isShown || elements[i].isShown) {
						if (elements[i].saveNeeded) {
							save();
							elements[i].saveNeeded = false;
						}
					}
				}
				if (elements[2].isShown) {
					MissionPanel mp = (MissionPanel) elements[2];
					if (mp.blMissionChoose == true) {
						game.setScreen(new GameScreen(game, mp.missionChoose));
					}
				}
			}
		}
	}

	@Override
	public void render(float delta) {

		update();
		Gdx.gl.glClearColor(255 / 255f, 255 / 255f, 255 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		if (strIndexState == "NewGame") {
			title.render(batch);
			for (int i = 0; i < 3; i++)
				portraits[i].render(batch);
		} else if (strIndexState == "Game") {
			backGround.draw(batch);
			bossCastle.render(batch);
			for(int i = 0; i< elements.length;i++)
				elements[i].render(batch);
			pUid.render(batch);
		}
		batch.end();

	}

	private void initialisation() {
		backGround = new Sprite(new Texture(Gdx.files.internal("index/BackGroundIndex.png")));
		batch = new SpriteBatch();

		PartStats.lUpgrades = new ArrayList<Integer>();
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("DefaultPart/upgrade.xml"));
			PartStats.lUpgrades.add(root.getInt("Attaque"));
			PartStats.lUpgrades.add(root.getInt("Defense"));
			PartStats.lUpgrades.add(root.getInt("AttaqueSpe"));
			PartStats.lUpgrades.add(root.getInt("Power"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (strIndexState == "NewGame") {
			display_Reborn();
			create_Save();
		} else if (strIndexState == "Game") {
			if(partAlreadyLoad)
			{
				while(PartStats.iExp >= PartStats.iExpMax)
				{
					PartStats.iExp -= PartStats.iExpMax;
					PartStats.iLevel++;
					PartStats.iNewLevel++;
				}
				display_Index();
				save();
			}
			else
			{
				loadPart();
				display_Index();
				load();
			}
		}
	}

	/******* Method ********/

	private void reborn(int index) {
		PartStats.iType = portraits[index].iType;
		PartStats.strType = portraits[index].strType;
		PartStats.flSpeedAtt = portraits[index].flAtts;
		PartStats.iAttaque = portraits[index].iAttD;
		PartStats.iLife = PartStats.iLifeMax = portraits[index].iLife;
		PartStats.iPower = PartStats.iPowerMax = portraits[index].iPower;
		PartStats.iLevel = 0;
		PartStats.iExp = 0;
		PartStats.iExpMax = PartStats.iLevel * 150 + 100;

		PartStats.iNewLevel = 0;
		PartStats.isDead = false;

	}

	private void display_Reborn() {
		title = new Label(GameParameter.SCREEN_WIDTH / 2, GameParameter.SCREEN_HEIGHT - 100, "Choose youre new Body",
				"Center", Color.BLACK, 64, "font/OLDENGL.ttf");

		portraits = new Portrait[3];
		for (int i = 0; i < 3; i++) {

			int[] iSpec = new int[4];
			String strSprite = "", strType = "";
			try {
				Element root = new XmlReader().parse(Gdx.files.internal("classePerso.xml"));
				Element classe = root.getChild(i);
				strType = classe.getName();
				iSpec[0] = classe.getInt("life");
				iSpec[1] = classe.getInt("power");
				iSpec[2] = classe.getInt("attD");
				iSpec[3] = classe.getInt("attS");
				strSprite = classe.get("sprite");
			} catch (IOException e) {
				e.printStackTrace();
			}
			portraits[i] = new Portrait(new Sprite(new Texture(Gdx.files.internal(strSprite))), strType, iSpec[0],
					iSpec[1], iSpec[2], iSpec[3],
					(GameParameter.SCREEN_WIDTH / 3) * (i + 1) - (GameParameter.SCREEN_WIDTH / 6),
					GameParameter.SCREEN_HEIGHT / 2, i);
		}
	}

	private void display_Index() {
		strIndexState = "Game";
		bossCastle = new BossCastle();
		pUid = new PlayerUid(new Rectangle(20, 900, 375, 125));
		elements = new IndexElement[3];
		elements[0] = new TrainingCamp(0, 0, false, "pixel.png");
		elements[1] = new Market(100, 30, false, "pixel.png", pUid);
		elements[2] = new MissionPanel(200, 40, false, "pixel.png");

	}

	private void create_Save() {
		String dest = "./Save/newGame";
		File theDir = new File(dest);
		PartStats.saveSource = dest;
		try {
			theDir.mkdir();
		} catch (SecurityException se) {

		}
		File folder = new File("./DefaultPart");
		File[] listOfFiles = folder.listFiles();

		try {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					File saveFile = new File(PartStats.saveSource + "/" + listOfFiles[i].getName());
					copySave(listOfFiles[i], saveFile);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("resource")
	private static void copySave(File source, File dest) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}
	}

	private void save() {
		DateFormat dateFormat = new SimpleDateFormat("dd-HH-mm");
		Date date = new Date();
		File newName = new File("./Save/" + PartStats.strType + "_" + PartStats.iLevel + "_" + dateFormat.format(date));
		File folder = new File(PartStats.saveSource);
		folder.renameTo(newName);
		PartStats.saveSource = "./Save/" + newName.getName();

		Save save = new Save();
		save.partWriter();
		Market m = (Market) elements[1];
		save.articleWriter(m.articles);
		save.upgradeWriter();

	}

	private void loadPart() {
		Load load = new Load();
		load.loadPart();
	}

	private void load() {
		Load load = new Load();
		load.loadPart();

		Market m = (Market) (elements[1]);
		m.articles = load.loadArticle(m.articles);

		load.loadUpgrade();
		TrainingCamp t = (TrainingCamp) (elements[0]);
		t.updateValues();
		save();
	}

	/******* EVENT ********/
	private void portraits_Clicked(int index) {
		reborn(index);
		display_Index();
		save();
	}

	private void portraits_Hover(int index) {
		portraits[index].mouseHover = true;
		portraits[index].changeBackColor(80 / 255f, 80 / 255f, 80 / 255f, 1);
	}

	private void portraits_Was_Hover(int index) {
		portraits[index].mouseHover = false;
		portraits[index].changeBackColor(20 / 255f, 20 / 255f, 20 / 255f, 1);
	}

	private void elements_Clicked(int index) {
		elements[index].isShown = true;
	}

	private void elements_Hover(int index) {
		elements[index].mouseHover = true;
		elements[index].changeColor(20 / 255f, 20 / 255f, 20 / 255f, 1, elements[index].spriteElement);
	}

	private void elements_Was_Hover(int index) {
		elements[index].mouseHover = false;
		elements[index].changeColor(80 / 255f, 80 / 255f, 80 / 255f, 1, elements[index].spriteElement);
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
