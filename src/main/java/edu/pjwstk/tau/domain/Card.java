package edu.pjwstk.tau.domain;

public class Card {
	private int id;
	private String title;
	private String description;
	private CardStatus cardStatus;
	private User owner;

	public Card(int id, String title, String description, CardStatus cardStatus, User owner) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.cardStatus = cardStatus;
		this.owner = owner;
	}

	private Card(Builder builder) {
		setId(builder.id);
		setTitle(builder.title);
		setDescription(builder.description);
		setCardStatus(builder.cardStatus);
		setOwner(builder.owner);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public User getOwner() {
		return owner;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Card{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", cardStatus=" + cardStatus +
				", owner=" + owner +
				'}';
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
		return getOwner() != null ? getOwner().equals(card.getOwner()) : card.getOwner() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
		result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
		result = 31 * result + (getCardStatus() != null ? getCardStatus().hashCode() : 0);
		result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
		return result;
	}

	public static final class Builder {
		private int id;
		private String title;
		private String description;
		private CardStatus cardStatus;
		private User owner;

		public Builder() {
		}

		public Builder id(int val) {
			id = val;
			return this;
		}

		public Builder title(String val) {
			title = val;
			return this;
		}

		public Builder description(String val) {
			description = val;
			return this;
		}

		public Builder cardStatus(CardStatus val) {
			cardStatus = val;
			return this;
		}

		public Builder owner(User val) {
			owner = val;
			return this;
		}

		public Card build() {
			return new Card(this);
		}
	}
}
