package edu.pjwstk.tau.domain;

import lombok.Builder;

@Builder
public class Card implements Cloneable, DeepClone<Card> {
	private int id;
	private String title;
	private String description;
	private CardStatus cardStatus;
	private User owner;
	private CardHistory cardHistory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public CardHistory getCardHistory() {
		return cardHistory;
	}

	public void setCardHistory(CardHistory cardHistory) {
		this.cardHistory = cardHistory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Card)) return false;

		Card card = (Card) o;

		if (getId() != card.getId()) return false;
		if (getTitle() != null ? !getTitle().equals(card.getTitle()) : card.getTitle() != null) return false;
		if (getDescription() != null ? !getDescription().equals(card.getDescription()) : card.getDescription() != null)
			return false;
		if (getCardStatus() != card.getCardStatus()) return false;
		if (getOwner() != null ? !getOwner().equals(card.getOwner()) : card.getOwner() != null) return false;
		return getCardHistory() != null ? getCardHistory().equals(card.getCardHistory()) : card.getCardHistory() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
		result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
		result = 31 * result + (getCardStatus() != null ? getCardStatus().hashCode() : 0);
		result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
		result = 31 * result + (getCardHistory() != null ? getCardHistory().hashCode() : 0);
		return result;
	}

	@Override
	protected Card clone() throws CloneNotSupportedException {
		return (Card) super.clone();
	}

	@Override
	public Card deepClone() {
		Card card;

		try {
			card = this.clone();
			card.setOwner(this.owner.deepClone());
			card.setCardHistory(this.cardHistory.deepClone());
		}catch (CloneNotSupportedException e) {
			return null;
		}

		return card;
	}

}
