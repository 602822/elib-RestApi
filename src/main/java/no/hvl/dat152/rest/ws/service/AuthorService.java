/**
 *
 */
package no.hvl.dat152.rest.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.AuthorRepository;

/**
 * @author tdoy
 */
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    public Author saveAuthor(Author author) {

        author = authorRepository.save(author);
        // TODO
        return author;
    }

    public Author findById(long id) throws AuthorNotFoundException {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with the id: " + id + "not found!"));

        return author;
    }

    public List<Author> findAll() {
        return (List<Author>) authorRepository.findAll();
    }


    public Author createAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }

    public Author updateAuthor(Author author, int authorId) throws AuthorNotFoundException {

        Optional<Author> authorFromRepo = authorRepository.findById((long) authorId);

        if (authorFromRepo.isEmpty()) {
            throw new AuthorNotFoundException("Author with id: " + authorId + " could not be found");
        }
        int id = authorFromRepo.get().getAuthorId();
        author.setAuthorId(id);
        authorRepository.save(author);

        return author;
    }

    public Set<Book> getAuthorBooksOfAuthor( int authorId) throws AuthorNotFoundException {
        Optional<Author> authorFromRepo = authorRepository.findById((long) authorId);

        if (authorFromRepo.isEmpty()) {
            throw new AuthorNotFoundException("Author with id: " + authorId + " could not be found");
        }

        Set<Book> books = authorFromRepo.get().getBooks();
      return books;
    }

}
