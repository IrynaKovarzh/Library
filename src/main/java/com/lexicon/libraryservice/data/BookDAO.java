package com.lexicon.libraryservice.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lexicon.libraryservice.model.Book;

@Stateless
public class BookDAO implements BookDAOInterface {
	
	@Inject
	EntityManager em;

	@Override
	public void persistBook(Book book) {		
		book.setCopiesAvailableToLoan(book.getCopies());
		em.persist(book);		
	}

	@Override
	public List<Book> getAllBooks() {		
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
		return query.getResultList();
	}

	@Override
	public Book getBookById(long id) {
		return em.find(Book.class, id);
	}
	
	@Override
    public boolean contains(long id) {
		Book book = em.find(Book.class, id);
		return book != null;
	};
	
	@Override
	public Book findBookByISBN(String isbn) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        criteria.select(book).where(cb.equal(book.get("strISBN"), isbn));
        return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<Book> findBookByTitle(String title) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        criteria.select(book).where(cb.equal(book.get("title"), title));
        return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<Book> findBookByAuthor(String author) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        criteria.select(book).where(cb.equal(book.get("author"), author));
        return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<Book> findBookByGenre(String genre) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        Root<Book> book = criteria.from(Book.class);
        criteria.select(book).where(cb.equal(book.get("genre"), genre));
        return em.createQuery(criteria).getResultList();
	}
	
	@Override
	public void loanCopy(long id) {
		em.find(Book.class, id).loanCopy();
	}
}
