package BlackJack;

import java.awt.Color;
import BlackJackBase.PCard;

public class BJCard extends PCard {
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
	public static final int KNIGHT = 12;
	public static final int QUEEN = 13;
	public static final int KING = 14;
	public static final int SPADE = 1;
	public static final int HEART = 2;
	public static final int DIAMOND = 3;
	public static final int CLUB = 4;
	public BJCard(int rank, int suit) {
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
	}
	@Override
	public void showCard() {
		hidden = false;
	}
	@Override
	public boolean isHidden() {
		return hidden;
	}
	@Override
	public String getText() {
		String result = "";
		if (rank != 1 && rank <= 10) {
			result += rank + "";
		}
		else {
			switch(rank) {
				case ACE:
					result += "A";
					break;
				case JACK:
					result += "J";
					break;
				case KNIGHT:
					result += "KN";
					break;
				case QUEEN:
					result += "Q";
					break;
				case KING: 
					result += "K";
					break;
			}
		}
		if (suit == SPADE) {
			result += "♠";
		} else if (suit == CLUB) {
			result += "♣";
		} else if (suit == HEART) {
			result += "♥";
		} else {
			result += "♦";
		}
		return result;
	}
	@Override 
	public Color getBorderColor() {
		if (hidden) return Color.WHITE;
		if (suit == HEART || suit == DIAMOND) return Color.RED;
		return Color.BLACK;
	}
	@Override 
	public Color getFontColor() {
		if (suit == HEART || suit == DIAMOND) return Color.RED;
		return Color.BLACK;
	}/*
	@Override
	public Color getBackColor() {
		if (hidden) return Color.BLUE;
		else return Color.WHITE;
	}*/
	@Override
	public Color getStripeColor() {
		if (hidden) return new Color(0, 0, .5f);
		else return Color.WHITE;
	}
	@Override
	public Color getFaceColor() {
		if (hidden) return Color.BLUE;
		else return Color.WHITE;
	}
}
