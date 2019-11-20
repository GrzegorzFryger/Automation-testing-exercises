package edu.pjwstk.tau.repository;

import edu.pjwstk.tau.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
