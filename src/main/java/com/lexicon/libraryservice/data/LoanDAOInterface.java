package com.lexicon.libraryservice.data;

import java.time.LocalDate;
import java.util.List;

import com.lexicon.libraryservice.model.Book;
import com.lexicon.libraryservice.model.Loan;
import com.lexicon.libraryservice.model.Member;

public interface LoanDAOInterface {
	
	void persistLoan(Loan loan);	
	
	void deleteLoan(Loan loan);
	void deleteLoan(Member member, Book book);
	
	void prolongLoan(Loan loan, LocalDate date);
	
	List<Loan> getAllLoans();	
	List<Loan> getAllLoansExpiredByDate(LocalDate date);
	List<Loan> getAllLoansOfMember(Member member);
	List<Loan> getAllLoansBook(Long bookId);
	List<Loan> getLoanOfMemberWithBook(Member member, Book book);
	//get all overdue in 2 days, grouped/sorted by member	
}
