/**
 *
 */
package no.hvl.dat152.rest.ws.controller;


import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 *
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors/{authorId}")
    public ResponseEntity<Object> getAuthor(@PathVariable("authorId") long authorId) throws AuthorNotFoundException {
        Author author;
        try {
            author = authorService.findById(authorId);
        } catch (AuthorNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);

    }

    @GetMapping("/authors")
    public ResponseEntity<Object> getAuthors() {
        List<Author> authors = authorService.findAll();
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }


}
