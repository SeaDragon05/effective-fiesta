package BlackJackTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import BlackJack.BJCard;

public class BJCardTest {
	@Test
	public void testGetText() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		assertEquals(card.getText(), "A♠");
	}
	@Test
	public void testGetSuit() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		assertEquals(card.getSuit(), BJCard.SPADE);
	}
	@Test
	public void testGetRank() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		assertEquals(card.getRank(), BJCard.ACE);
	}
	@Test
	public void testSetSuit() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		card.setSuit(BJCard.CLUB);
		assertEquals(card.getSuit(), BJCard.CLUB);
	}
	@Test
	public void testSetRank() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		card.setRank(BJCard.KING);
		assertEquals(card.getRank(), BJCard.KING);
	}
	@Test
	public void testIsHidden() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		assertEquals(card.isHidden(), false);
	}
	@Test
	public void testSetHidden() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		card.setHidden(true);
		assertEquals(card.isHidden(), true);
	}
	@Test
	public void testShowCard() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		card.showCard();
		assertEquals(card.isHidden(), false);
	}
	@Test
	public void testHideCard() {
		BJCard card = new BJCard(BJCard.ACE, BJCard.SPADE);
		card.hideCard();;
		assertEquals(card.isHidden(), true);
	}
}
