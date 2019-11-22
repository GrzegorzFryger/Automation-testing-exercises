package edu.pjwstk.tau.domain;

import lombok.*;

import javax.persistence.*;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Entity
public class Card implements Cloneable, DeepClone<Card> {


	@Id
	private int id;
	private String title;
	private String description;
	@Enumerated(EnumType.STRING)
	private CardStatus cardStatus;
	@ManyToOne()
	@JoinColumn(name = "fk_owner")
	private User owner;
	@OneToOne(
			mappedBy = "card",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
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
		if (cardHistory == null) {
			if (this.cardHistory != null) {
				this.cardHistory.setCard(null);
			}
		}
		else {
			cardHistory.setCard(this);
		}
		this.cardHistory = cardHistory;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Card)) return false;

		Card card = (Card) o;

		if (id != card.id) return false;
		if (title != null ? !title.equals(card.title) : card.title != null) return false;
		if (description != null ? !description.equals(card.description) : card.description != null) return false;
		if (cardStatus != card.cardStatus) return false;
		if (owner != null ? !owner.equals(card.owner) : card.owner != null) return false;
		return cardHistory != null ? cardHistory.equals(card.cardHistory) : card.cardHistory == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (cardStatus != null ? cardStatus.hashCode() : 0);
		result = 31 * result + (owner != null ? owner.hashCode() : 0);
		result = 31 * result + (cardHistory != null ? cardHistory.hashCode() : 0);
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
