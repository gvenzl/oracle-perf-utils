/*
* author:  gvenzl
* created: 30 Nov 2016
*/

package com.gvenzl.awr;

/**
 * Thrown to indicate that the sequence of begin and end snapshot is out of order.
 * 
 * @author gvenzl
 *
 */
public class OutOfSequenceException extends Exception {
	
	public OutOfSequenceException() {
		super();
	}
	
	public OutOfSequenceException(String message) {
		super(message);
	}
	
	public OutOfSequenceException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = -604380623276955122L;

}
