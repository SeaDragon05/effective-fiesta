package BlackJack;

import java.util.ArrayList;
import java.util.Random;

import BlackJackBase.PCard;
import BlackJackBase.PDeck;

public class BJDeck implements PDeck {
	@SuppressWarnings("serial")
	private ArrayList<BJCard> cards = new ArrayList<BJCard>() {
		@Override
		public String toString() {
			String result = "";
			for (int i = 0; i < this.size(); i ++) {
				result += this.get(i).getText() + ",";
			}
			return result;
		}
	};
	public BJDeck() {
		for (int i = 1; i <= 14; i ++) {
			for (int j = 1; j <= 4; j++) {
				cards.add(new BJCard(i, j));
			}
		}
	}
	@Override
	public void shuffle() {
		Random rnd = new Random();
		ArrayList<BJCard> newDeck = new ArrayList<BJCard>();
		while (cards.size() > 0) {
			int index = rnd.nextInt(cards.size());
			newDeck.add(cards.get(index));
			cards.remove(index);
		} 
		cards = newDeck;
	}
	@Override
	public void addCard(PCard card) {
		cards.add((BJCard) card);
	}
	@Override
	public PCard dealCard() {
		BJCard result = cards.get(0);
		result.setHidden(false);
		cards.remove(0);
		return result;
	}
	@Override
	public PCard dealHiddenCard() {
		PCard hiddencard;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).isHidden()) {
				hiddencard = cards.get(i);
				cards.remove(i);
				return hiddencard;
			}
		}
		return null;//this was painful to debug!
	}
	@Override
	public int cardCount() {
		return cards.size();
	}
	//these are added in for fun:
	@Override
	public String getCardType(int index) {
		return cards.get(index).getText();
	}
	public void removeCard(int index) {
		cards.remove(index);
	}
}
