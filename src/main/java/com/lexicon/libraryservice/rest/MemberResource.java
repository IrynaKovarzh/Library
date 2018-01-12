package com.lexicon.libraryservice.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lexicon.libraryservice.data.LoanDAOInterface;
import com.lexicon.libraryservice.data.MembersDAOInterface;
import com.lexicon.libraryservice.model.*;

@Path("/member")
public class MemberResource {
		
	@Inject
	MembersDAOInterface dao;
	
	@Inject
	LoanDAOInterface loanDAO;
			
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMembers() {
		return Response.ok(dao.getAllMembers()).build();
	}
	
	@GET
	@Path("/{id}")	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMemberById(@PathParam("id")Long id) {
		Member member = dao.getMemberById(id);
		
		Response.ResponseBuilder builder = null;
		if (member == null) 
		{
			builder = Response.status(Response.Status.NOT_FOUND);
		} else {
			builder = Response.ok(dao.getMemberById(id));
		}
		return builder.build();
	}

	@GET
   	@Path("/{id}/loans")	   	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLoansOfMember(@PathParam("id") Long id) {
		Member member = dao.getMemberById(id);
		List<Loan> loans = loanDAO.getAllLoansOfMember(member);
		
		Response.ResponseBuilder builder = null;
		if (loans == null || loans.isEmpty()) 
		{
			builder = Response.status(Response.Status.NOT_FOUND);
		} else {
			builder = Response.ok(loans);
		}
		return builder.build();
	}
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(Member member) {

        Response.ResponseBuilder builder = null;

        try {  
        	
            validateMember(member);
        	
            dao.persistMember(member);
            builder = Response.created(new URI("localhost:8080/Library/rest/members/"  + member.getId()));
            
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }
        
        private void validateMember(Member member) throws ValidationException {
            // Check the uniqueness of the email address
            if (emailAlreadyExists(member.getEmail())) {
                throw new ValidationException("Unique Email Violation");
            }
        }

        public boolean emailAlreadyExists(String email) {
            Member member = null;
            try {
            	 member = dao.findByEmail(email);
            } catch (Exception e) {
            //} catch (NoResultException e) {
                // ignore
            }
            return member != null;
        }
        
        @DELETE    
        @Path("/{id}")	  
       	public Response returnBookByMember(@PathParam("id") Long id) throws URISyntaxException{           	
        	Response.ResponseBuilder builder = Response.ok(); 
        						
    		Member member = dao.getMemberById(id);
    		List<Loan> loans = loanDAO.getAllLoansOfMember(member);
    		
    		if ( member == null) {
    		 builder = Response.status(Response.Status.NOT_FOUND);
    		} else if ( !loans.isEmpty() ) {
    			builder = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("the loan list is not empty");
    		} else
    		{	try {	
    			dao.delete(member);
    		} catch (Exception e) {
    			
    		}
    		}    		
    	      return builder.build();
       	}       	
        
        @PUT
       	@Path("/{id}/phnum/{number}")	
       	@Produces(MediaType.APPLICATION_JSON)
       	public Response setNewDate(@PathParam("id") Long id, @PathParam("number") String number) throws URISyntaxException{
        	Response.ResponseBuilder builder;
    	       		
    		Member member  = dao.getMemberById(id);
    		
    		//formatter ...    		        	    			
    		if (member == null) {
    		 builder = Response.status(Response.Status.NOT_FOUND);
    		} 
    		else
    		{	
    			 dao.setPhoneNumber(member, number);
    			 builder = Response.ok(new URI("localhost:8080/Library/rest/loan/member/" + id + "/phnum/" + number));    			    			     									
    		}
    	      return builder.build();
       	}
}
