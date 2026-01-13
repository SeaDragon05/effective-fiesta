package com.own.master.cardgame.objects;

import com.own.master.cardgame.base.PCard;

public class Card extends PCard {
	private int rank = 0;
	private int suit = 0;
	private boolean hidden;
	public static final int ACE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int SPADE = 1;
	public static final int HEART = 2;
	public static final int DIAMOND = 3;
	public static final int CLUB = 4;
	public Card(int rank, int suit) {
		this.rank = rank;
		this.suit = suit;
		this.hidden = true;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getSuit() {
		return suit;
	}
	public void setSuit(int suit) {
		this.suit = suit;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	@Override
	public void hideCard() {
		hidden = true;
		this.setHidden(true);
	}
	@Override
	public void showCard() {
		hidden = false;
		this.setHidden(false);
	}
	@Override
	public boolean isHidden() {
		return hidden;
	}
	@Override
	public String getURL() {
		String result = "";
		if (rank != 1 && rank <= 10) {
			result += rank + "";
		}
		else {
			switch(rank) {
				case ACE:
					result += "a";
					break;
				case JACK:
					result += "11";
					break;
				case QUEEN:
					result += "12";
					break;
				case KING:
					result += "13";
					break;
			}
		}
		if (suit == SPADE) {
			result += "s";
		} else if (suit == CLUB) {
			result += "c";
		} else if (suit == HEART) {
			result += "h";
		} else {
			result += "d";
		}
		return result;
	}
}