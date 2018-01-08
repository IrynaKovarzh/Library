package com.lexicon.libraryservice.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lexicon.libraryservice.data.BookDAOInterface;
import com.lexicon.libraryservice.model.Book;

@Path("/book")
public class BookResource {
	
	@Inject
	BookDAOInterface dao;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveBook(Book book) throws URISyntaxException {
		dao.persistBook(book);
		return Response.created(new URI("localhost:8080/Library/rest/book")).build();
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBooks() {
		return Response.ok(dao.getAllBooks()).build();
	}

}
