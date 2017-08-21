package com.stuffle.Utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ListBox {
	private int itemSelected;

	private boolean isSelected = false;

	private ArrayList<ItemList> items;
	public Rectangle panelListBox;
	private Sprite sprite;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ArrayList<ItemList> getItems() {
		return items;
	}

	public int getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(int itemSelected) {
		this.itemSelected = itemSelected;
	}

	public ListBox(Rectangle r) {
		panelListBox = r;
		items = new ArrayList<>();
		sprite = new Sprite(new Texture(Gdx.files.internal("./pixel.png")));
		sprite.setBounds(panelListBox.x, panelListBox.y, panelListBox.width, panelListBox.height);
		sprite.setColor(Color.BLACK);
	}


	public void render(SpriteBatch spriteBatch) {
		sprite.draw(spriteBatch);
		for (ItemList i : items)
			i.render(spriteBatch);
	}
	
	public void addElement(ItemList r) {
		items.add(r);
	}


}
