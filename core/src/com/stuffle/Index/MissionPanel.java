package com.stuffle.Index;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuffle.Input.mouseInput;
import com.stuffle.Utils.Label;

public class MissionPanel extends IndexElement {

	Mission[] missions;
	String[] difficulties = { "Easy", "Hard", "Just don't" };
	Label lblTitle;
	boolean blMissionChoose = false;
	Mission missionChoose;
	
	public MissionPanel(int _x, int _y, boolean _show, String sprite) {
		super(_x, _y, _show, sprite);
		lblTitle = new Label(rectPanel.x + rectPanel.width / 2, rectPanel.y + rectPanel.height - 40, "Quest", "Center",
				Color.BLACK, 40);
		createMission();

	}

	protected void update() {
		if (isShown) {
			super.update();
			for (int i = 0; i < 3; i++) {
				if (mouseInput.mouseClickOnRectangle(missions[i].rectPanel, 0))
					mission_Clicked(i);
				else {
					if (mouseInput.mouseOverRectangle(missions[i].rectPanel)) {
						if (!missions[i].mouseHover)
							mission_Hover(i);
					} else if (missions[i].mouseHover)
						mission_Was_Hover(i);
				}
			}
		}
	}

	public void render(SpriteBatch batch) {
		if (isShown) {
			update();
			lblTitle.render(batch);
			spritePanel.draw(batch);
			for (Mission m : missions)
				m.render(batch);
		}
		super.render(batch);
	}

	private void createMission() {
		missions = new Mission[3];
		int w = 350, h = 150, vPadding = 60;
		int vLayout = (int) (rectPanel.y + rectPanel.height / 2 + 1.5 * (h + vPadding) - h);
		for (int i = 0; i < missions.length; i++)
			missions[i] = new Mission(PartStats.iLevel + i, difficulties[i], rectPanel.x + rectPanel.width / 2 - w / 2,
					vLayout - i * (vPadding + h), w, h);

	}

	private void mission_Clicked(int index) {
		if (index >= 0) {
			missionChoose = missions[index];
			blMissionChoose = true;
		}
	}

	private void mission_Hover(int index) {
		missions[index].mouseHover = true;
		missions[index].spritePanel.setColor(new Color(193 / 255f, 158 / 255f, 77 / 255f, 1));
	}

	private void mission_Was_Hover(int index) {
		missions[index].mouseHover = false;
		missions[index].spritePanel.setColor(Color.WHITE);
	}
}
