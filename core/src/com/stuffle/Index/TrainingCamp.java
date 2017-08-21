package com.stuffle.Index;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuffle.Input.mouseInput;
import com.stuffle.Interface.Bar;
import com.stuffle.Utils.Button;
import com.stuffle.Utils.Label;

public class TrainingCamp extends IndexElement {

	Sprite spriteTrainingCamp;
	Button[] buttons;
	Label[] labels;
	Label numberLevel;
	String[] texts = { "Attaque : ", "Defense : ", "Attaque speciale :", "Pouvoir :" };
	ArrayList<Bar> bars;
	int nb = 4;

	public TrainingCamp(int _x, int _y, boolean _show, String sprite) {
		super(_x, _y, _show, sprite);
		int w = 45, h = 45, padding = 50;
		float VLayout = rectPanel.y + rectPanel.height / 2 + (padding) * nb / 2;
		buttons = new Button[nb];
		labels = new Label[nb];
		numberLevel = new Label(rectPanel.x + 30, rectPanel.y + rectPanel.height - 150,
				"Level dispo. : " + PartStats.iNewLevel, Color.WHITE, 65);

		bars = new ArrayList<Bar>();
		for (int i = 0; i < nb; i++) {
			h = 30;
			labels[i] = new Label(rectPanel.x + 40, VLayout - i * (padding), texts[i], Color.WHITE, h);
			w = h = 45;
			buttons[i] = new Button(rectPanel.x + rectPanel.width - 80, VLayout - i * (padding), w, h, "", 10);
			buttons[i].sprite.setTexture(new Texture(Gdx.files.internal("utils/Button_Upgrade.png")));

			w = 650;
			h = 30;
			bars.add(new Bar(rectPanel.x + 450, VLayout - i * (padding), w, h, 5, PartStats.lUpgrades.get(i), 1, 1,
					Color.WHITE, Color.RED));
		}
	}
	
	public void update() {
		super.update();
		btn_Clicked(mouseInput.buttons_Listener(buttons, 0));
		btn_Hover(mouseInput.buttons_Listener(buttons, 1));
		btn_Was_Hover(mouseInput.buttons_Listener(buttons, 2));
	}
	@Override
	public void render(SpriteBatch batch) {
		if (isShown) {
			update();
			spritePanel.draw(batch);
			for (Button button : buttons)
				button.render(batch);
			for (Label label : labels) {
				label.render(batch);
			}
			for (Bar b : bars)
				b.render(batch);
			numberLevel.render(batch);
		}
		super.render(batch);
	}
	
	public void updateValues() {
		for (int i = 0; i < bars.size(); i++) {
			bars.get(i).setContentW(PartStats.lUpgrades.get(i));
			bars.get(i).computeContent();
		}
	}

	private void update_Level() {
		numberLevel.strText = "Level dispo. : " + PartStats.iNewLevel;
	}

	private void btn_Clicked(int index) {
		if (index >= 0) {
			if (PartStats.iNewLevel > 0) {
				if (PartStats.lUpgrades.get(index) < 5) {
					int val = PartStats.lUpgrades.get(index) + 1;
					PartStats.lUpgrades.set(index, val);
					bars.get(index).setContentW(val);
					bars.get(index).computeContent();
					PartStats.iNewLevel--;
					update_Level();
					saveNeeded = true;
				}
			}
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
