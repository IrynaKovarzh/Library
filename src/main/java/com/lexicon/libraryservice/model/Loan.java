package com.lexicon.libraryservice.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
//@Table(name = "loans")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne  
	private Book book;
	//private Long bookId;
    
	@ManyToOne
	private Member member;
	//private Long memberId;  
 
	private LocalDate dateToReturn;

	public Loan() {
    	//	super();	
    	}
  
	/*
   public Loan(Long memberId, Long bookId) {				
		//super();		
		this.bookId = bookId;
		this.memberId = memberId;
		
		setNewDateToReturn();
		//setDateToReturn(termToReturn()); 
		//this.dateToReturn = LocalDate.now().plusDays(30);  
	}
	*/
	
	 public Loan(Member member, Book book) {				
			//super();		
			this.book = book;
			this.member = member;
			
			setNewDateToReturn();
			//setDateToReturn(termToReturn()); 
			//this.dateToReturn = LocalDate.now().plusDays(30);  
		}
	
	
	public Long getId() {
		return id;
	}
   
	/*
	public Long getBookId() {
		return bookId;
	} */
	
	public Book getBook() {
		return book;
	}
	
	
	public Member getMember() {
		return member;
	}
	/*
	public long getMemberId() {
		return memberId;
	} */

	public void setDateToReturn(LocalDate dateToReturn) {
		this.dateToReturn = dateToReturn;
    }	
	
//DATE TO RETURN:
	
	 public LocalDate getDateToReturn() {
			return dateToReturn;
		}
	
	public void setNewDateToReturn() {
		setDateToReturn(termToReturn()); 		
    }	
	
	private LocalDate termToReturn() {
		return LocalDate.now().plusDays(30); //May 'depends' on a book 
}	
}
