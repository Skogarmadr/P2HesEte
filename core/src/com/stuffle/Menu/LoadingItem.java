package com.stuffle.Menu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Utils.ItemList;
import com.stuffle.Utils.Label;

public class LoadingItem extends ItemList {
	private static final long serialVersionUID = 1L;
	private Rectangle recPanel;
	private boolean isSelected;

	private Color color = Color.WHITE;
	private ArrayList<Label> labels;
	private String srcItem;
	private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("./utils/Button.png")));

	public void setColor(Color color) {
		this.color = color;
		sprite.setColor(color);
	}

	public String getSrcItem() {
		return srcItem;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public LoadingItem(Rectangle _panel, int _index, String _item, int width, int height) {
		this.width = width;
		this.height = height;

		recPanel = _panel;
		srcItem = _item;
		labels = new ArrayList<>();
	}

	public void addLabel(Label l) {
		labels.add(l);
	}

	public void update() {
		this.setPosition(recPanel.x, recPanel.y);
		sprite.setBounds(this.x, this.y, this.width, this.height);
		sprite.setColor(color);
	}

	public void render(SpriteBatch spriteBatch) {
		update();
		sprite.draw(spriteBatch);
		for (Label l : labels)
			l.render(spriteBatch);
	}
}
