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

		if (id != that.id) return false;
		if (addedTime != null ? !addedTime.equals(that.addedTime) : that.addedTime != null) return false;
		if (modificationTime != null ? !modificationTime.equals(that.modificationTime) : that.modificationTime != null)
			return false;
		return readingTime != null ? readingTime.equals(that.readingTime) : that.readingTime == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (addedTime != null ? addedTime.hashCode() : 0);
		result = 31 * result + (modificationTime != null ? modificationTime.hashCode() : 0);
		result = 31 * result + (readingTime != null ? readingTime.hashCode() : 0);
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
