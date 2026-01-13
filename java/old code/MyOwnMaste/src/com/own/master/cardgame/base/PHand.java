package com.own.master.cardgame.base;

import java.util.ArrayList;

import com.own.master.cardgame.objects.Card;

public interface PHand {

	public int getSize();

	public void addCard(PCard card);

	public PCard getCard(int index);

	public PCard removeCard(int index);
	public int getValue();

	public ArrayList<Card> getList();
}