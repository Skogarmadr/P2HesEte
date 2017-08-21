package com.stuffle.Index;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.stuffle.Input.mouseInput;
import com.stuffle.Utils.Label;
import com.stuffle.Utils.TextArea;
import com.stuffle.uid.PlayerUid;

public class Market extends IndexElement {

	public ArrayList<ArrayList<Article>> articles;
	private TextArea textArea;
	private Label articleTitle;
	private PlayerUid pUid;

	public Market(int _x, int _y, boolean _show, String sprite, PlayerUid _pUid) {
		super(_x, _y, _show, sprite);
		pUid = _pUid;
		articles = new ArrayList<ArrayList<Article>>();
		int w = 300, h = 300;
		articleTitle = new Label(rectPanel.x + rectPanel.width - w, rectPanel.y + rectPanel.height * 3 / 4, "",
				Color.BLACK, 22);
		textArea = new TextArea(rectPanel.x + rectPanel.width - w, rectPanel.y + rectPanel.height * 2 / 3, "",
				Color.BLACK, 18, w, h);
		createArticles();
	}

	public void update() {
		super.update();

		for (int i = 0; i < articles.size(); i++) {
			for (int j = 0; j < articles.get(i).size(); j++) {
				if (mouseInput.mouseClickOnRectangle(articles.get(i).get(j).rectItem, 0))
					articles_Clicked(i, j);
				else {
					if (mouseInput.mouseOverRectangle(articles.get(i).get(j).rectItem)) {
						if (!articles.get(i).get(j).mouseHover)
							articles_Hover(i, j);
					} else if (articles.get(i).get(j).mouseHover)
						articles_Was_Hover(i, j);
				}
			}
		}
	}

	public void render(SpriteBatch batch) {
		if (isShown) {
			update();
			spritePanel.draw(batch);
			for (ArrayList<Article> list : articles)
				for (Article a : list)
					a.render(batch);
			articleTitle.render(batch);
			textArea.render(batch);
		}
		super.render(batch);
	}

	private void createArticles() {
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("./" + PartStats.strType + "/article.xml"));

			String n, type, link, status, descr;
			int p;
			float upgrade;
			for (int i = 0; i < root.getChildCount(); i++) {
				Element r1 = root.getChild(i);
				articles.add(new ArrayList<Article>());
				for (int j = 0; j < r1.getChildCount(); j++) {
					Element r2 = r1.getChild(j);
					n = r2.get("name");
					link = r2.get("sprite");
					status = r2.get("status");
					p = r2.getInt("price");
					upgrade = r2.getFloat("upgrade");
					type = r2.get("type");
					descr = r2.get("description");
					Sprite s = new Sprite(new Texture(Gdx.files.internal(link)));
					int w = 64, h = 64, paddingV = 50, paddingH = 80;
					int vLayout = (int) (rectPanel.y + rectPanel.height / 2 + (h + paddingV) * 2 / 2);
					int hLayout = (int) (rectPanel.x + rectPanel.width / 2 - (w + paddingH) * (r1.getChildCount()) / 2);
					articles.get(articles.size() - 1).add(new Article(hLayout + j * (w + paddingH) + w / 2,
							vLayout - i * (paddingV + h), w, h, s, p, upgrade, type, n, status, descr));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buyArticle(int indexList, int index) {
		if (articles.get(indexList).get(index).strStatus.equalsIgnoreCase("open")) {
			if (articles.get(indexList).get(index).iPrice <= PartStats.iGold) {
				PartStats.iGold -= articles.get(indexList).get(index).iPrice;
				articles.get(indexList).get(index).strStatus = "bought";

				if (articles.get(indexList).get(index).strType.equals("attaque"))
					PartStats.iAttaque += articles.get(indexList).get(index).flUpgrade;
				if (articles.get(indexList).get(index).strType.equals("defense"))
					PartStats.iLife += articles.get(indexList).get(index).flUpgrade;

				if (index < articles.get(indexList).size() - 1) {
					articles.get(indexList).get(index + 1).strStatus = "open";
					articles.get(indexList).get(index + 1).colorChange(Color.WHITE);
				}
				pUid.updateAllInformations();
				saveNeeded = true;
			}
		}
	}

	private void articles_Clicked(int indexList, int index) {
		if (index >= 0) {
			buyArticle(indexList, index);
		}
	}

	private void articles_Hover(int indexList, int index) {
		articles.get(indexList).get(index).mouseHover = true;
		articles.get(indexList).get(index).colorChange(Color.BROWN);
		articleTitle.strText = articles.get(indexList).get(index).strName;
		textArea.strText = articles.get(indexList).get(index).strDescription;
	}

	private void articles_Was_Hover(int indexList, int index) {
		articles.get(indexList).get(index).mouseHover = false;
		articles.get(indexList).get(index).colorChange(Color.WHITE);
		articleTitle.strText = "";
		textArea.strText = "";
	}

}
