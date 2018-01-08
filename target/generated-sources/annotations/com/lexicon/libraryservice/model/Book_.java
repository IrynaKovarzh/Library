package com.lexicon.libraryservice.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SingularAttribute<Book, String> strISBN;
	public static volatile SingularAttribute<Book, String> shelfColumn;
	public static volatile SingularAttribute<Book, Integer> copies;
	public static volatile SingularAttribute<Book, String> author;
	public static volatile SingularAttribute<Book, String> genre;
	public static volatile SingularAttribute<Book, String> shelfRow;
	public static volatile SingularAttribute<Book, Long> id;
	public static volatile SingularAttribute<Book, String> title;
	public static volatile SingularAttribute<Book, String> shelf;

}

