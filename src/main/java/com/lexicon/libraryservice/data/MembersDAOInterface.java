package com.lexicon.libraryservice.data;

import java.util.List;

import com.lexicon.libraryservice.model.Loan;
import com.lexicon.libraryservice.model.Member;

public interface MembersDAOInterface {
   Member getMemberById(Long id);        
   Member findByEmail(String email);
   
   List<Member> findAllOrderedByName();
    
	void persistMember(Member member);
	void delete(Member member);
	
	void setPhoneNumber(Member member, String number);
		
	List<Member> getAllMembers();
    boolean contains(long id);
    
    List<Loan> getAllLoansOfMember(Long id);
    void addLoan(Long id, Loan loan);	
    void deleteLoan(Long id, Loan loan);
    	
}
