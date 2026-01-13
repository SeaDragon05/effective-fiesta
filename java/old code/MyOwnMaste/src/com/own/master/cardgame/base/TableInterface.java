package com.own.master.cardgame.base;

import java.util.ArrayList;

import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.objects.GameObject;

public interface TableInterface {
	abstract void setup();
	abstract void renderCards(Renderer rnd);
	abstract void destroy();
	abstract void shuffle();
	abstract void checkRenderUpdates();
	abstract void destroyHands();
	abstract GameObject dealCard();
	abstract int getValue(ArrayList<GameObject> hand);
	abstract ArrayList<GameObject> getHand(int player);
}
