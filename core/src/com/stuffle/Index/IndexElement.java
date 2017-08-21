package com.stuffle.Index;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Input.mouseInput;
import com.stuffle.Parameter.GameParameter;
import com.stuffle.Utils.Button;

public abstract class IndexElement {
	protected int iX, iY;
	protected boolean isShown;
	protected Rectangle rectElement;
	protected Sprite spriteElement;
	protected boolean mouseHover = false;
	protected boolean saveNeeded = false;

	protected Rectangle rectPanel;
	protected Sprite spritePanel;
	protected Color colorPanel;
	private Button[] buttons;

	public IndexElement(int _x, int _y, boolean _show, String sprite) {
		iX = _x;
		iY = _y;
		isShown = _show;

		rectElement = new Rectangle(_x, 60, 60, 60);

		spriteElement = new Sprite(new Texture(Gdx.files.internal(sprite)));
		initialisation();
	}

	protected void update() {
		if (isShown) {
			if (mouseInput.buttons_Listener(buttons, 0) != -1) {
				btn_Clicked(mouseInput.buttons_Listener(buttons, 0));
			}
			btn_Hover(mouseInput.buttons_Listener(buttons, 1));
			btn_Was_Hover(mouseInput.buttons_Listener(buttons, 2));
		}
	}

	protected void render(SpriteBatch batch) {
		spriteElement.draw(batch);
		if (isShown)
			buttons[0].render(batch);
	}

	protected void initialisation() {

		spriteElement.setSize(rectElement.width, rectElement.height);
		spriteElement.setPosition(rectElement.x, rectElement.y);
		changeColor(80 / 255f, 80 / 255f, 80 / 255f, 1, spriteElement);

		int w = 1200, h = 850;
		rectPanel = new Rectangle(GameParameter.SCREEN_WIDTH / 2 - w / 2, GameParameter.SCREEN_HEIGHT / 2 - h / 2, w,
				h);

		spritePanel = new Sprite(new Texture(Gdx.files.internal("Index/IndexElementBack.png")));
		spritePanel.setBounds(rectPanel.x, rectPanel.y, rectPanel.width, rectPanel.height);
		changeColor(255 / 255f, 255 / 255f, 255 / 255f, 92 / 100f, spritePanel);
		buttons = new Button[1];
		w = 300;
		h = 75;
		Button btnExit = new Button(rectPanel.x + rectPanel.width / 2 - w / 2, rectPanel.y + 100, w, h, "Close", 30);
		buttons[0] = btnExit;
	}

	protected void changeColor(float r, float g, float b, float a, Sprite sprite) {
		sprite.setColor(r, g, b, a);
	}

	/******** EVENTS ***********/

	private void btn_Clicked(int index) {
		if (index == 0) {
			isShown = false;
		}
	}

	private void btn_Hover(int index) {
		if (index >= 0) {
			buttons[index].changeBackColor(Color.BROWN);
		}
	}

	private void btn_Was_Hover(int index) {
		if (index >= 0) {
			buttons[index].changeBackColor(Color.WHITE);
		}
	}

}
