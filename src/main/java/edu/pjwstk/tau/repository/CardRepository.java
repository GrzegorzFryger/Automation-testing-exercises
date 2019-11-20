package edu.pjwstk.tau.repository;

import edu.pjwstk.tau.domain.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {
}
