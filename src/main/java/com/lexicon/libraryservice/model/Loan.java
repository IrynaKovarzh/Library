package com.lexicon.libraryservice.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne  
	private Book book;
    
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	 
	private LocalDate dateToReturn;

	public Loan() {
    	//	super();	
    	}
  	
	 public Loan(Member member, Book book) {				
			//super();		
			this.book = book;
			this.member = member;
			
			setDateToReturn();
		}
		
	public Long getId() {
		return id;
	}
   	
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

	//DATE TO RETURN:	
	public void setDateToReturn(LocalDate dateToReturn) {
		this.dateToReturn = dateToReturn;
    }
	
	public void setDateToReturn() {
		setDateToReturn(termToReturn());
    }
		
	 public LocalDate getDateToReturn() {
			return dateToReturn;
		}
		
	private LocalDate termToReturn() {
		return LocalDate.now().plusDays(30); //May 'depends' on a book 
}	
}
