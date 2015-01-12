package org.oregonask.exceptions;

public class DataAccessLayerException extends RuntimeException {

	private static final long serialVersionUID = -5993198611087078308L;

	public DataAccessLayerException() {
	}
	
	public DataAccessLayerException(String message) {
		super(message);
	}
	
	public DataAccessLayerException(Throwable cause) {
		super(cause);
	}
	
	public DataAccessLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
