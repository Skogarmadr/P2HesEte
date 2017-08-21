package com.stuffle.Input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.stuffle.Parameter.GameParameter;
import com.stuffle.Utils.Button;

public class mouseInput {

	public static boolean precPressed = false, currentPressed = false;

	public static void update() {
		precPressed = currentPressed;
		if (Gdx.input.isButtonPressed(0))
			currentPressed = true;
		else
			currentPressed = false;
	}

	public static boolean mouseClickOnRectangle(Rectangle _rectangle, int _button) {
		if (precPressed) {
			if (!currentPressed)
				return mouseOverRectangle(_rectangle);
		}
		return false;
	}

	public static boolean mouseOverRectangle(Rectangle _rectangle) {
		Rectangle rMouse = new Rectangle(Gdx.input.getX(), GameParameter.SCREEN_HEIGHT - Gdx.input.getY(), 1, 1);
		if (rMouse.overlaps(_rectangle))
			return true;
		return false;
	}

	public static boolean mouseClickOnPos(float flPosX, float flPosY, float flWidth, float flHeight, int _button) {
		if (Gdx.input.isButtonPressed(_button)) {
			Rectangle rRec = new Rectangle(flPosX, flPosY, flWidth, flHeight);
			return mouseOverRectangle(rRec);
		}
		return false;
	}

	public static int buttons_Listener(Button[] buttons, int func) {

		for (int i = 0; i < buttons.length; i++) {
			switch (func) {
			case 0:
				if (mouseClickOnRectangle(buttons[i].rectButton, 0))
					return i;
				break;
			case 1:
				if (mouseOverRectangle(buttons[i].rectButton)) {
					if (!buttons[i].mouseHover) {
						buttons[i].mouseHover = true;
						return i;
					}
				}
				break;
			case 2:
				if (!mouseOverRectangle(buttons[i].rectButton))
					if (buttons[i].mouseHover) {
						buttons[i].mouseHover = false;
						return i;
					}
				break;
			}
		}
		return -1;
	}

	public static int buttons_Listener(ArrayList<Button> buttons, int func) {

		for (int i = 0; i < buttons.size(); i++) {
			switch (func) {
			case 0:
				if (mouseClickOnRectangle(buttons.get(i).rectButton, 0))
					return i;
				break;
			case 1:
				if (mouseOverRectangle(buttons.get(i).rectButton)) {
					if (!buttons.get(i).mouseHover) {
						buttons.get(i).mouseHover = true;
						return i;
					}
				}
				break;
			case 2:
				if (!mouseOverRectangle(buttons.get(i).rectButton))
					if (buttons.get(i).mouseHover) {
						buttons.get(i).mouseHover = false;
						return i;
					}
				break;
			}
		}
		return -1;
	}
}
