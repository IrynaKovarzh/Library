package com.lexicon.libraryservice.data;

import java.util.List;
import com.lexicon.libraryservice.model.Book;

public interface BookDAOInterface {	
	void persistBook(Book book);
	
	List<Book> getAllBooks();
	Book getBookById(long id);	
	boolean contains(long id);
	
	void loanCopy(long id);
	
	Book findBookByISBN(String isbn);
	List<Book> findBookByTitle(String title);
	List<Book> findBookByAuthor(String author);
	List<Book> findBookByGenre(String genre);
}
