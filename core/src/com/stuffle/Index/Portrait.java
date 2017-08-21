package com.stuffle.Index;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Utils.Label;

public class Portrait {

	private Label lblType, lblLife, lblPower, lblAttD, lblAttS;

	private Sprite sprite;
	private Sprite spritePanel;

	public Rectangle rectangle;
	public boolean mouseHover = false;
	public int iLife, iPower, iAttD;
	public float flAtts;
	public String strType;
	public int iType;

	public Portrait(Sprite _sprite, String type, int life, int power, int attD, float attS, int posX, int posY,
			int _type) {
		iLife = life;
		iPower = power;
		iAttD = attD;
		flAtts = attS;

		int width = 200;
		int height = 250;
		rectangle = new Rectangle(posX - width / 2, posY - height / 2, width, height);
		sprite = _sprite;
		sprite.setPosition(rectangle.x + rectangle.width / 2 - sprite.getWidth() / 2,
				rectangle.y + rectangle.height - sprite.getHeight() - 40);
		lblType = new Label(rectangle.x + rectangle.width / 2, sprite.getY() + sprite.getHeight() + 20, type, "Center",
				Color.WHITE, 20);
		lblLife = new Label(rectangle.x + rectangle.width / 2, sprite.getY() - 25, "Life : " + life, "Center",
				Color.WHITE, 17);
		lblPower = new Label(rectangle.x + rectangle.width / 2, sprite.getY() - 45, "Power : " + power, "Center",
				Color.WHITE, 17);
		lblAttD = new Label(rectangle.x + rectangle.width / 2, sprite.getY() - 65, "A. Damage : " + attD, "Center",
				Color.WHITE, 17);
		lblAttS = new Label(rectangle.x + rectangle.width / 2, sprite.getY() - 85, "A. Speed : " + attS, "Center",
				Color.WHITE, 17);

		spritePanel = new Sprite(new Texture(Gdx.files.internal("pixel.png")));
		spritePanel.setColor(20 / 255f, 20 / 255f, 20 / 255f, 1);
		spritePanel.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

		strType = type;
		iType = _type;
	}

	public void changeBackColor(float r, float g, float b, float alpha) {
		spritePanel.setColor(r, g, b, alpha);
	}

	public void render(SpriteBatch batch) {
		spritePanel.draw(batch);
		sprite.draw(batch);
		lblType.render(batch);
		lblLife.render(batch);
		lblPower.render(batch);
		lblAttD.render(batch);
		lblAttS.render(batch);
	}
}