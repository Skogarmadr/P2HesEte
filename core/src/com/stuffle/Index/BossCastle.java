package com.stuffle.Index;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BossCastle {
	private Sprite spriteCastle;

	public BossCastle() {
		spriteCastle = new Sprite(new Texture(Gdx.files.internal("index/Castle_01.png")));
		spriteCastle.setPosition(151, 470);
	}

	public void render(SpriteBatch batch) {
		spriteCastle.draw(batch);
	}
}
