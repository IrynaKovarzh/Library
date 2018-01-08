package com.lexicon.libraryservice.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Loan.class)
public abstract class Loan_ {

	public static volatile SingularAttribute<Loan, LocalDate> dateToReturn;
	public static volatile SingularAttribute<Loan, Long> id;
	public static volatile SingularAttribute<Loan, Long> bookId;
	public static volatile SingularAttribute<Loan, Long> memberId;

}

