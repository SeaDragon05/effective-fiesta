package BlackJack;

import java.awt.Color;

import BlackJackBase.PGame;

public class PlayBlackJack {
	public static void main(String[] args) {
		BJDeck deck = new BJDeck();
		BJHand PlayerHand = new BJHand();
		BJHand DealerHand = new BJHand();
		PGame.setBackgroundColor(new Color(0.1f, 1f, 0.3f));
		PGame.setFont("Comic Sans");
		PGame.setBannerColor(new Color(1f,0f,0f));
		PGame.setBannerTextColor(Color.BLACK);
		PGame.setButtonColor(new Color(.5f,.5f,.5f));
		PGame.setStatusTextColor(new Color(.1f,.1f,.1f));
		PGame.run(deck, DealerHand, PlayerHand);
	}
}
