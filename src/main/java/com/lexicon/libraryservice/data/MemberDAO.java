package com.lexicon.libraryservice.data;

	import javax.ejb.Stateless;
	import javax.inject.Inject;
	
	import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
	import javax.persistence.criteria.CriteriaQuery;
	import javax.persistence.criteria.Root;

import com.lexicon.libraryservice.model.Book;
import com.lexicon.libraryservice.model.Loan;
import com.lexicon.libraryservice.model.Member;

import java.util.List;

      @Stateless
      public class MemberDAO implements MembersDAOInterface {

	    @Inject
	    private EntityManager em;
	 
	    public Member getMemberById(Long id) {
	        return em.find(Member.class, id);
	    }
	    
	    public Member findByEmail(String email) {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
	        Root<Member> member = criteria.from(Member.class);
	        criteria.select(member).where(cb.equal(member.get("email"), email));
	        return em.createQuery(criteria).getSingleResult();
	    }

	    public List<Member> findAllOrderedByName() {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
	        Root<Member> member = criteria.from(Member.class);
	        criteria.select(member).orderBy(cb.asc(member.get("name")));
	        return em.createQuery(criteria).getResultList();
	    }
	
		@Override
		public void persistMember(Member member) {
			em.persist(member);
		}
		
		@Override
		public void delete(Member member){
			em.remove(member);
		}
		
		@Override
		public void setPhoneNumber(Member member, String number){
			member.setPhoneNumber(number);
			em.merge(member);
		}
		
		@Override
		public List<Member> getAllMembers() {		
			TypedQuery<Member> query = em.createQuery("SELECT M FROM Member M", Member.class);
			return query.getResultList();
		}
	    
	    @Override
	    public boolean contains(long id) {
	    	Member member = em.find(Member.class, id);
			return member != null;	    	
		};
		
		@Override
		 public List<Loan> getAllLoansOfMember(Long id) {
			Member member = em.find(Member.class, id);
			return member.getLoans();
		 }
		
		 @Override
		 public void addLoan(Long id, Loan loan) {
			Member member = em.find(Member.class, id);
			member.addLoan(loan);
		 }
		 
		 @Override
		 public void deleteLoan(Long id, Loan loan) {
			Member member = em.find(Member.class, id);
			member.deleteLoan(loan);
		 }
	}
	
