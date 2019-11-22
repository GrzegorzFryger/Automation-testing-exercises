package edu.pjwstk.tau.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Entity
public class CardHistory implements Cloneable, DeepClone<CardHistory> {

	@Id
	private int id;
	private LocalDateTime addedTime;
	private LocalDateTime modificationTime;
	private LocalDateTime readingTime;
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JsonIgnore
	private Card card;

	public LocalDateTime getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(LocalDateTime addedTime) {
		this.addedTime = addedTime;
	}

	public LocalDateTime getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(LocalDateTime modificationTime) {
		this.modificationTime = modificationTime;
	}

	public LocalDateTime getReadingTime() {
		return readingTime;
	}

	public void setReadingTime(LocalDateTime readingTime) {
		this.readingTime = readingTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CardHistory)) return false;

		CardHistory that = (CardHistory) o;

		if (getId() != that.getId()) return false;
		if (getAddedTime() != null ? !getAddedTime().equals(that.getAddedTime()) : that.getAddedTime() != null)
			return false;
		if (getModificationTime() != null ? !getModificationTime().equals(that.getModificationTime()) : that.getModificationTime() != null)
			return false;
		if (getReadingTime() != null ? !getReadingTime().equals(that.getReadingTime()) : that.getReadingTime() != null)
			return false;
		return getCard() != null ? getCard().equals(that.getCard()) : that.getCard() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getAddedTime() != null ? getAddedTime().hashCode() : 0);
		result = 31 * result + (getModificationTime() != null ? getModificationTime().hashCode() : 0);
		result = 31 * result + (getReadingTime() != null ? getReadingTime().hashCode() : 0);
		result = 31 * result + (getCard() != null ? getCard().hashCode() : 0);
		return result;
	}

	@Override
	protected CardHistory clone() throws CloneNotSupportedException {
		return (CardHistory) super.clone();
	}

	@Override
	public CardHistory deepClone() {
		CardHistory cardHistory;
		try {
			cardHistory =	this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
		return cardHistory;
	}

}
