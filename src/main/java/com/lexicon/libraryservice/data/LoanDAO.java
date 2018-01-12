package com.lexicon.libraryservice.data;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lexicon.libraryservice.model.Book;
import com.lexicon.libraryservice.model.Loan;
import com.lexicon.libraryservice.model.Member;

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
	public List<Loan> getAllLoansOfMember(Member member)
	{		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Loan> criteria = cb.createQuery(Loan.class);
		Root<Loan> loan = criteria.from(Loan.class);
		criteria.select(loan).where(cb.equal(loan.get("member"), member));
		return em.createQuery(criteria).getResultList();
	}
	
	@Override
	public List<Loan> getLoanOfMemberWithBook(Member member, Book book)
	{		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Loan> criteria = cb.createQuery(Loan.class);
		Root<Loan> loan = criteria.from(Loan.class);
		criteria.select(loan).where(cb.equal(loan.get("member"), member)).where(cb.equal(loan.get("book"), book));
		return em.createQuery(criteria).getResultList();	
	}
	
	@Override
	public List<Loan> getAllLoansBook(Long bookId)
	{		
		Query query = em.createNativeQuery("SELECT * from Loan L JOIN Book B ON B.ID = L.book_ID WHERE b.ID = :param", Loan.class);		
		
		 query.setParameter("param", bookId);
		 return query.getResultList();
	}
	
	@Override
	public List<Loan> getAllLoansExpiredByDate(LocalDate date)
	{		
		TypedQuery<Loan> query = em.createQuery("SELECT L FROM Loan L WHERE L.dateToReturn < :param", Loan.class);
		query.setParameter("param", date);
		
		return query.getResultList();
	}
	
	@Override
	public void deleteLoan(Member member, Book book)
	{
		List<Loan> loans = getLoanOfMemberWithBook(member, book);
		Loan loan = null; 
		if (!loans.isEmpty()) {loan = loans.get(0);}
		
		em.remove(loan);
	}
		
	@Override
	public void deleteLoan(Loan loan)
	{
		em.remove(loan);
	}
	
	@Override
	public void prolongLoan(Loan loan, LocalDate date)
	{
		Loan updatedLoan = em.find(Loan.class, loan.getId());
		updatedLoan.setDateToReturn(date);
		em.merge(updatedLoan);
	}
}
