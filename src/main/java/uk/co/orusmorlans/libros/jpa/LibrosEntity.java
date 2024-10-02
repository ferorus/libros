package uk.co.orusmorlans.libros.jpa;

import java.io.Serializable;

public interface LibrosEntity<I> extends Serializable {
	
	public I getId();

}
