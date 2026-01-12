package BlackJack;

import java.util.ArrayList;

import BlackJackBase.PCard;
import BlackJackBase.PHand;

public class BJHand implements PHand {
	private ArrayList<BJCard> cards = new ArrayList<BJCard>();
	public BJHand() {}
	@Override
	public int getSize() {
		return cards.size();
	}
	@Override
	public void addCard(PCard card) {
		cards.add((BJCard) card);
	}

	@Override
	public PCard getCard(int index) {
		return cards.get(index);
	}

	@Override
	public PCard removeCard(int index) {
		BJCard result = cards.get(index);
		cards.remove(index);
		return result;
	}

	@Override
	public int getValue() {
		int result = 0;
		boolean hasAce = false;
		int aceCount = 0;
		if (cards.isEmpty()) {
			return 0;
		}
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getRank() == BJCard.ACE) {
				result += 11;
				aceCount += 1;
				hasAce = true;
			} else {
				result += cards.get(i).getRank();
			}
		}
		if (result > 21 && hasAce == true) {
			for (int i = 0; i < aceCount; i ++) {
				if (result <= 21) {
					return result;
				} else {
					result -= 10;
				}
			}
		}
		return result;
	}
	//items that are added in after assignment submitting
	@Override
	public ArrayList<BJCard> getList() {
		return cards;
	}
}
