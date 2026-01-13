package com.own.master.cardgame.BJ;

import java.util.ArrayList;
import java.util.Random;

import com.own.master.cardgame.base.TableInterface;
import com.own.master.cardgame.objects.Card;
import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.io.AudioPlayer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.utils.FileUtils;

import Main.Main;

public class Table implements TableInterface {
	public static final Vector3f p_pos1 = new Vector3f(1.0779f, 1.9f, 1.8f);
	public static final Vector3f p_pos2 = new Vector3f(0.44585f, 1.9f, 1.8f);
	public static final Vector3f p_pos3 = new Vector3f(-0.2088f, 1.9f, 1.8f);
	public static final Vector3f p_pos4 = new Vector3f(-0.86155f, 1.9f, 1.6613f);
	public static final Vector3f p_pos5 = new Vector3f(-1.5496f, 1.9f, 1.6613f);
	static float b = -0.67493f;
	public static final Vector3f d_pos1 = new Vector3f(1.0779f, 1.9f, b);
	public static final Vector3f d_pos2 = new Vector3f(0.44585f, 1.9f, b);
	public static final Vector3f d_pos3 = new Vector3f(-0.2088f, 1.9f, b);
	public static final Vector3f d_pos4 = new Vector3f(-0.86155f, 1.9f, b);
	public static final Vector3f d_pos5 = new Vector3f(-1.5496f, 1.9f, b);// lazy
	public static final Vector3f ds = new Vector3f(0, 0, 0);
	private ArrayList<GameObject> cards = new ArrayList<>();
	private ArrayList<GameObject> playerHand = new ArrayList<>();
	private ArrayList<GameObject> dealerHand = new ArrayList<>();
	public static boolean endGame = false;
	public static boolean pressedH = false;
	public static boolean settingup = false;
	public static int sets = 0;
	public static boolean playerWin = false;
	public Table() {}
	@Override
	public void setup() {
		if (GameThread.isExecuting) {
			while (!GameThread.isExecuting) {}
			settingup = true;
		}
		sets += 1;
		settingup = true;
		endGame = false;
		destroyHands();
		cards = new ArrayList<>();
		playerHand = new ArrayList<>();
		dealerHand = new ArrayList<>();
		System.out.println("Setting things up");
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 13; j++) {
				Card card = new Card(j, i);
				int[] values = new int[3];
				values[0] = j;
				values[1] = i;
				values[2] = 0;
				cards.add(new GameObject(ds, ds, new Vector3f(1, 1, 1), FileUtils.loadOBJ("./models/tableMap/Card2.obj",
						"/textures/common/cards/" + card.getURL() + ".jpg", values, ds, ds, 5, 0)));
				cards.get(((i - 1) * 13) + j - 1).getMesh().create();// sheeesh
			}
		}
		// shuffle the cards
		shuffle();
		// deal the cards
		// deal two to the player:
		playerHand.add(dealCard());
		playerHand.add(dealCard());
		// make them appear on the table:
		playerHand.get(0).setPosition(p_pos1);
		playerHand.get(1).setPosition(p_pos2);
		// reveal them:
		playerHand.get(0).setRotation(new Vector3f(180, 180, 0));
		playerHand.get(1).setRotation(new Vector3f(180, 180, 0));
		// deal cards to the dealer:
		dealerHand.add(dealCard());
		dealerHand.add(dealCard());
		// make them appear on the table:
		dealerHand.get(0).setPosition(d_pos1);
		dealerHand.get(1).setPosition(d_pos2);
		// reveal the second one only:
		dealerHand.get(0).setRotation(new Vector3f(0, 180, 0));
		dealerHand.get(1).setRotation(new Vector3f(180, 180, 0));
		// get the current count for both players to see if someone won or lost without
		// doing anything (which is balls annoying if true)
		// checkTable(true); // true since this is the first round check so we can
		// appoligize to the player?
		if (getValue(playerHand) >= 21 || getValue(dealerHand) >= 21) {
			setup();
		}
		sets -= 1;
		if (sets == 0) {
			settingup = false;
		}
	}

	@Override
	public void renderCards(Renderer rnd) {
		for (GameObject element : playerHand) {
			rnd.renderMesh(element, 1);
		}
		for (GameObject element : dealerHand) {
			rnd.renderMesh(element, 1);
		}
	}


	@Override
	public void destroy() {
		for (GameObject card : cards) {
			card.getMesh().destroy();
		}
	}
	@Override
	public void destroyHands() {
		for (GameObject card : cards) {
			card.getMesh().destroy();
		}
		for (GameObject element : playerHand) {
			element.getMesh().destroy();
		}
		for (GameObject element : dealerHand) {
			element.getMesh().destroy();
		}
	}
	@Override
	public void shuffle() {
		Random rnd = new Random();
		ArrayList<GameObject> newDeck = new ArrayList<>();
		while (cards.size() > 0) {
			int index = rnd.nextInt(cards.size());
			newDeck.add(cards.get(index));
			cards.remove(index);
		}
		cards = newDeck;
	}

	@Override
	public GameObject dealCard() {
		AudioPlayer.playaudio(Main.csh);
		GameObject result = new GameObject();
		try {
			result = cards.get(0);
		} catch (Exception e) {
			System.err.println("ERROR: Deck index out of range!");
			System.exit(0);
		}
		cards.remove(0);
		return result;
	}

	@Override
	public int getValue(ArrayList<GameObject> hand) {
		int result = 0;
		boolean hasAce = false;
		int aceCount = 0;
		if (hand.isEmpty()) {
			return 0;
		}
		for (GameObject element : hand) {
			if (element.getMesh().getMaterial().getValue(0) == Card.ACE) {
				result += 11;
				aceCount += 1;
				hasAce = true;
			} else {
				result += element.getMesh().getMaterial().getValue(0);
			}
		}
		if (result > 21 && hasAce) {
			for (int i = 0; i < aceCount; i++) {
				if (result <= 21) {
					return result;
				} else {
					result -= 10;
				}
			}
		}
		return result;
	}
	public static float i = 0;
	public static float br, rb = 0;
	@Override
	public void checkRenderUpdates() {
		if (Main.updateCards) {
			Main.updateCards = false;
			Main.isSettingUp = true;
			this.setup();// this is here to prevent an error
			Main.isSettingUp = false;
			/*
			 * For what ever reason, (I've got no idea why) when ever a thread outside the
			 * main thread calls a function that writes to the gpu memory, it causes an
			 * error. This function here is constantly called by the main thread that CAN
			 * interact with the gpu memory to check to see if the outside thread needs a
			 * change. Black jack needs to refresh the main deck which interacts with the
			 * gpu memory. It changes the updateCards variable to true so that the main
			 * process knows what to call.
			 *
			 * Why? Probably not doing something right.
			 */
		}

	}
	@Override
	public ArrayList<GameObject> getHand(int player) {
		if (player == 0) return playerHand;
		else if (player == 1) return dealerHand;
		else return null;
	}
}
