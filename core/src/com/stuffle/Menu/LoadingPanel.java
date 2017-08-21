package com.stuffle.Menu;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Utils.Label;
import com.stuffle.Utils.ListBox;
import com.stuffle.Input.mouseInput;
import com.stuffle.Parameter.LoadingState;
import com.stuffle.Utils.Button;

public class LoadingPanel {
	private Rectangle panel;
	//private Rectangle virtualPanel;
	private ListBox listBox;
	private Sprite sprite;
	private ArrayList<Button> buttons;
	private LoadingState loadingState;
	public String strNameChoose = "";

	public LoadingState getLoadingState() {
		return loadingState;
	}

	public LoadingPanel(Rectangle _panel) {
		panel = _panel;
		sprite = new Sprite(new Texture(Gdx.files.internal("./utils/Button.png")));
		sprite.setBounds(panel.x, panel.y, panel.width, panel.height);
		buttons = new ArrayList<>();
		int iWidth = 300;
		int iHeight = 100;
		buttons.add(new Button(panel.x + panel.width / 2 - iWidth - 20, panel.y + 100, iWidth, iHeight, "Select", 17));
		buttons.add(new Button(panel.x + panel.width / 2 + 20, panel.y + 100, iWidth, iHeight, "Quit", 17));
		LoadItems();
	}

	private void update() {
		mouseInput.update();
		btn_Clicked(mouseInput.buttons_Listener(buttons, 0));
		btn_Hover(mouseInput.buttons_Listener(buttons, 1));
		btn_Was_Hover(mouseInput.buttons_Listener(buttons, 2));
		
		for(int i = 0; i < listBox.getItems().size(); i++)
		{
			LoadingItem item = (LoadingItem)(listBox.getItems().get(i));
			if(mouseInput.mouseClickOnRectangle((Rectangle)(item),0))
			{
				listBox.setItemSelected(i);
				listBox.setSelected(true);
				item.setColor(Color.BROWN);
				item.setSelected(true);
				listBox.isSelected();
				listBox.setItemSelected(i);
			}
			else if(listBox.getItemSelected() != i)
			{
				if(item.isSelected())
				{
					item.setSelected(false);
					item.setColor(Color.WHITE);
				}
			}
				
		}
	}

	public void render(SpriteBatch spriteBatch) {
		update();
		sprite.draw(spriteBatch);
		listBox.render(spriteBatch);
		for (Button b : buttons)
			b.render(spriteBatch);
	}

	private void LoadItems() {
		File folder = new File("./Save");
		Path dir = folder.toPath();
		List<Path> files = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path p : stream) {
				files.add(p);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Collections.sort(files, new Comparator<Path>() {
			public int compare(Path o1, Path o2) {
				try {
					return Files.getLastModifiedTime(o2).compareTo(Files.getLastModifiedTime(o1));
				} catch (IOException e) {
				}
				return 0;
			}
		});

		ArrayList<File> listOfFiles = new ArrayList<>();
		for (Path f : files)
			listOfFiles.add(new File(f.toString()));

		int iCpt = 0;
		int iWidth = (int) (0.6 * panel.width);
		int iHeight = (int) (0.6 * panel.height);
		int iX = (int) (0.2 * panel.width);
		int iY = (int) (0.3 * panel.height);

		listBox = new ListBox(new Rectangle(panel.x + iX, panel.y + iY, iWidth, iHeight));
		iHeight = 100;

		for (File f : listOfFiles)
			if (f.isDirectory()) {
				String name = f.getName();
				String[] values = name.split("_");
				if (values.length == 3) {
					Rectangle r = new Rectangle(listBox.panelListBox.x,
							listBox.panelListBox.y + listBox.panelListBox.height - iHeight - (iHeight + 1) * iCpt,
							iWidth, iHeight);
					LoadingItem item = new LoadingItem(r, iCpt, f.getPath(), iWidth, iHeight);

					Label l1 = new Label(r.x + 40, r.y + r.height - 35, values[0], Color.BLACK, 20);
					Label l2 = new Label(r.x + 40, r.y + 30, "Level : " + values[1], Color.BLACK, 16);
					Label l3 = new Label(r.x + r.width - 50, r.y + 30, values[2], "Right", Color.BLACK, 16);
					item.addLabel(l1);
					item.addLabel(l2);
					item.addLabel(l3);
					listBox.addElement(item);
					iCpt++;

				}
			}
	}
	
	public void chooseFirstItem()
	{
		if(listBox.getItems().size() > 0)
		{
			strNameChoose = ((LoadingItem)(listBox.getItems().get(0))).getSrcItem();
			loadingState = LoadingState.CHOOSE;
		}
	}
	
	public void btn_Clicked(int index) {
		switch (index) {
		case 0:
			if (listBox.isSelected()) {
				LoadingItem item = (LoadingItem) (listBox.getItems().get(listBox.getItemSelected()));
				strNameChoose = item.getSrcItem();
				loadingState = LoadingState.CHOOSE;
			}
			break;
		case 1:
			loadingState = LoadingState.EXIT;
			break;
		}
	}

	public void btn_Hover(int index) {
		if (index >= 0) {
			buttons.get(index).mouseHover = true;
			buttons.get(index).changeBackColor(Color.BROWN);
		}
	}

	public void btn_Was_Hover(int index) {
		if (index >= 0) {
			buttons.get(index).mouseHover = false;
			buttons.get(index).changeBackColor(Color.WHITE);
		}
	}

}
