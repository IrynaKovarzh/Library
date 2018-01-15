package com.lexicon.libraryservice.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
		
		dao.persistLoan(loan);
		
		Book book = loan.getBook();
		booksRepository.loanCopy(book.getId());
		book.loanCopy();
		
		Member member = loan.getMember();
		membersRepository.addLoan(member.getId(), loan);		
				
      return builder.build();    
	}
	
	@POST	
	@Path("/member/{memberId}/book/{bookId}")	
	public Response saveLoan(@PathParam("memberId") Long memberId, @PathParam("bookId") Long bookId) throws URISyntaxException{
		
		Response.ResponseBuilder builder = Response.created(new URI("localhost:8080/Library/rest/loan/" + memberId + "/book/" + bookId));
				
		Book book = booksRepository.getBookById(bookId);			
		Member member  = membersRepository.getMemberById(memberId);		
		
		if (book == null || member == null) {
		 builder = Response.status(Response.Status.CONFLICT).entity("data error");
		} 
		else
		{
		if (book.getCopiesAvailableToLoan() > 0) {	
			
		booksRepository.loanCopy(bookId);	
		
		Loan loan = new Loan(member, book);		
		membersRepository.addLoan(member.getId(), loan);
		
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
        
    @GET
   	@Path("/book/{bookid}")	
   	@Produces(MediaType.APPLICATION_JSON)
   	public Response getAllLoansBook(@PathParam("bookid") Long bookId) throws URISyntaxException{
       	Response.ResponseBuilder builder; 
       	
		Book book = booksRepository.getBookById(bookId);						
		if (book == null) {
			builder = Response.status(Response.Status.NOT_FOUND);
   		} 
   		else
   		{		
    	builder = Response.ok(dao.getAllLoansBook(bookId));
    	}    	       	
       	 return builder.build();
   	}
    
    @GET
	@Path("/member/{memberid}")	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLoansOfMember(@PathParam("memberid") Long memberId) throws URISyntaxException{
    	Response.ResponseBuilder builder; 
    	    
    	Member member  = membersRepository.getMemberById(memberId);
    	if (member == null) {   		 
    		builder = Response.status(Response.Status.NOT_FOUND);
   		} 
   		else
   		{		
    	builder = Response.ok(dao.getAllLoansOfMember(member));
    	}    	
    	return builder.build();
	}
    
    @GET
	@Path("/date/{datestr}")	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLoansExpiredByDate(@PathParam("datestr") String datestr) throws URISyntaxException{
    	Response.ResponseBuilder builder; 
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");    	    	
    	LocalDate date = LocalDate.parse(datestr, formatter);
    	    	    	
    	if (date == null) {
   		 builder = Response.status(Response.Status.NOT_FOUND).entity("data error");
   		} 
   		else
   		{
		return Response.ok(dao.getAllLoansExpiredByDate(date)).build();
    	}    	
    	 return builder.build();
	}
    
    @DELETE    
    @Path("/member/{memberId}/book/{bookId}")	  
   	public Response returnBookByMember(@PathParam("memberId") Long memberId, @PathParam("bookId") Long bookId) throws URISyntaxException{
       	Response.ResponseBuilder builder = Response.ok(); 
       	       
       	Book book = booksRepository.getBookById(bookId);			
		Member member  = membersRepository.getMemberById(memberId);
		
		if (book == null || member == null) {
		 builder = Response.status(Response.Status.NOT_FOUND);
		} 
		else
		{	
			try {
				
			List<Loan> loans = dao.getLoanOfMemberWithBook(member, book);					
			Loan loan = null; 
			if (!loans.isEmpty()) {loan = loans.get(0);}
			
			membersRepository.deleteLoan(memberId, loan);
			
			booksRepository.returnCopy(bookId);
			
			dao.deleteLoan(member, book);
		//	builder = Response.ok();
			} catch (Exception e) {}
		}
	      return builder.build();
   	}
    
    @PUT
   	@Path("/member/{memberId}/book/{bookId}/date/{newdatestr}")	
   	@Produces(MediaType.APPLICATION_JSON)
   	public Response setNewDate(@PathParam("memberId") Long memberId, @PathParam("bookId") Long bookId, @PathParam("newdatestr") String newdatestr) throws URISyntaxException{
    	Response.ResponseBuilder builder = Response.ok();
	       
       	Book book = booksRepository.getBookById(bookId);			
		Member member  = membersRepository.getMemberById(memberId);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    	LocalDate date = LocalDate.parse(newdatestr, formatter);
    	    			
		if (book == null || member == null || date == null) {
		 builder = Response.status(Response.Status.NOT_FOUND).entity("data error");
		} 
		else
		{		
			List<Loan> loans  = dao.getLoanOfMemberWithBook(member, book);	
			Loan loan = null; 
			if (!loans.isEmpty()) {loan = loans.get(0);}
			
			if (loan != null) {
				dao.prolongLoan(loan, date);
			}						
		}
	      return builder.build();
   	}
    
}
