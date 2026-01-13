package com.own.master.cardgame.base;

import com.own.master.engine.math.Vector3f;

public interface PDeck {

	public void shuffle();

	public void addCard(PCard card);

	public PCard dealCard();

	public PCard dealCard(Vector3f loc);

	public PCard dealHiddenCard();

	public int cardCount();

	public String getCardType(int index);
}