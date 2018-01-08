package com.lexicon.libraryservice.model;

    import java.io.Serializable;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
	import javax.persistence.Table;
	import javax.persistence.UniqueConstraint;
	import javax.validation.constraints.Digits;
	import javax.validation.constraints.Pattern;
	import javax.validation.constraints.Size;


	import org.hibernate.validator.constraints.Email;

//	@SuppressWarnings("serial")
	//@Entity
	//@XmlRootElement
	
	@Entity
	//@Table(name = "lmembers", uniqueConstraints = @UniqueConstraint(columnNames = "email"))	
	public class Member implements Serializable{
	//public class Member implements Serializable {

		private static final long serialVersionUID = 2907771672497519248L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Size(min = 1, max = 25)
	    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	    private String name;

	    @Email
	    private String email;

	    @Size(min = 10, max = 12)
	    @Digits(fraction = 0, integer = 12)
	    @Column(name = "phone_number")
	    private String phoneNumber;

	    public Member() {
			//super();
		}

		public Long getId() {
	        return id;
	    }

		/*
	    public void setId(Long id) {
	        this.id = id;
	    }
*/
		
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    /*
	    public void setEmail(String email) {
	        this.email = email;
	    }
*/
	    
	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }
	}
