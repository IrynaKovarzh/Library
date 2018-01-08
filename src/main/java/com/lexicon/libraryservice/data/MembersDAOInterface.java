package com.lexicon.libraryservice.data;

import java.util.List;

import com.lexicon.libraryservice.model.Member;

public interface MembersDAOInterface {
   Member getMemberById(Long id);
        
   Member findByEmail(String email);

   List<Member> findAllOrderedByName();
    
	void persistMember(Member member);
	List<Member> getAllMembers();
    boolean contains(long id);
}
