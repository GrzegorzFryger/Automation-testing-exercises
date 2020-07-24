package edu.pjwstk.tau.controller;

import edu.pjwstk.tau.domain.Card;
import edu.pjwstk.tau.service.CardServiceDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/card")
public class ToDoController {

	@Autowired
	CardServiceDataBase cardServiceDataBase;



	@GetMapping(value = "/{id}")
	public ResponseEntity<Card> readCardById(@PathVariable("id") Integer id) {


	  Optional<Card> card = cardServiceDataBase.read(id);

		return card.map(card1 -> new ResponseEntity<>(card1, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

	}

	@PostMapping
	public ResponseEntity<Card> create(@RequestBody Card card ) {
		Card createdCard = this.cardServiceDataBase.create(card);

		if(createdCard != null){
			return new ResponseEntity<>(createdCard, HttpStatus.OK);
		}else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	public ResponseEntity<Card> update(@RequestBody Card card ) {

		Card createdCard = cardServiceDataBase.update(card);

		if(createdCard != null){
			return new ResponseEntity<>(createdCard, HttpStatus.OK);
		}else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Integer id) {
		boolean delete =  cardServiceDataBase.delete(id);

		if(delete){
			return new ResponseEntity<>(HttpStatus.OK);
		}else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	@GetMapping("/all")
	public ResponseEntity<List<Card>> readAll() {

		List<Card> cardList = cardServiceDataBase.readAll();

		if(cardList.size() > 0){
			return new ResponseEntity<>(cardList, HttpStatus.OK);
		}else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
