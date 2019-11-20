package edu.pjwstk.tau.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Entity
public class CardHistory implements Cloneable, DeepClone<CardHistory> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private LocalDateTime addedTime;
	private LocalDateTime modificationTime;
	private LocalDateTime readingTime;


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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CardHistory)) return false;

		CardHistory that = (CardHistory) o;

		if (getAddedTime() != null ? !getAddedTime().equals(that.getAddedTime()) : that.getAddedTime() != null)
			return false;
		if (getModificationTime() != null ? !getModificationTime().equals(that.getModificationTime()) : that.getModificationTime() != null)
			return false;
		return getReadingTime() != null ? getReadingTime().equals(that.getReadingTime()) : that.getReadingTime() == null;
	}

	@Override
	public int hashCode() {
		int result = getAddedTime() != null ? getAddedTime().hashCode() : 0;
		result = 31 * result + (getModificationTime() != null ? getModificationTime().hashCode() : 0);
		result = 31 * result + (getReadingTime() != null ? getReadingTime().hashCode() : 0);
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
