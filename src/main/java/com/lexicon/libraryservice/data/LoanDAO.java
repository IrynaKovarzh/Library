package com.lexicon.libraryservice.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

//import com.lexicon.libraryservice.model.Book;
import com.lexicon.libraryservice.model.Loan;

@Stateless
public class LoanDAO implements LoanDAOInterface{

	@Inject
	EntityManager em;
	
	@Override
	public void persistLoan(Loan loan)
	{
		em.persist(loan);		
	}
	
	@Override
	public List<Loan> getAllLoans()
	{
		TypedQuery<Loan> query = em.createQuery("SELECT l FROM Loan l", Loan.class);
		return query.getResultList();
	}
	
	@Override
	public void deleteLoan(Loan loan)
	{
		
	}
	
	@Override
	public void prolongLoan(Loan loan)
	{
		loan.setNewDateToReturn();
	}
	
/*
	//persistLoan(memberid, bookid)
	@Override
	public void persistLoan(Long memberid, Book book) {
		Loan loan = new Loan(memberid, book);
		em.persist(loan);
	}
	*/
}
