package com.lexicon.libraryservice.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lexicon.libraryservice.data.*;
import com.lexicon.libraryservice.model.Book;
import com.lexicon.libraryservice.model.Loan;
import com.lexicon.libraryservice.model.Member;

@Path("/loan")
public class LoanResource {

	@Inject
	LoanDAOInterface dao;
	
	@Inject
    BookDAOInterface booksRepository;

	@Inject
	MembersDAOInterface membersRepository; 
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveLoan(Loan loan) throws URISyntaxException {
		
		Response.ResponseBuilder builder = Response.created(new URI("localhost:8080/Library/rest/loan"));		
		
		Book book = loan.getBook();
		book.loanCopy();
		
		dao.persistLoan(loan);		
      return builder.build();    
	}
	
	@POST	
	@Path("/{memberId}/book/{bookId}")	
	public Response saveLoan(@PathParam("memberId") Long memberId, @PathParam("bookId") Long bookId) throws URISyntaxException{
		
		Response.ResponseBuilder builder = Response.created(new URI("localhost:8080/Library/rest/loan/" + memberId + "/book/" + bookId));
		
		//boolean bookExist  = booksRepository.contains(bookId);
		Book book = booksRepository.getBookById(bookId);			
		Member member  = membersRepository.getMemberById(memberId);
		//boolean memberExist  = membersRepository.contains(memberId);
		//boolean memberExist = true;
		
		if (book == null || member == null) {
		 builder = Response.status(Response.Status.NOT_FOUND).entity("data error");
		} 
		else
		{
	//	Loan loan = new Loan(memberId, bookId);
		
			//transaction 
		if (book.getCopiesAvailableToLoan() > 0) {
			//Book book = loan.getBook();
		
		//book.loanCopy();
		booksRepository.loanCopy(bookId);
			
		Loan loan = new Loan(member, book);
				
	    dao.persistLoan(loan);
	    loan.getBook().loanCopy();
		}
		} 
	      return builder.build();
	} 
	
    @GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLoans(){
		return Response.ok(dao.getAllLoans()).build();
	}
}
