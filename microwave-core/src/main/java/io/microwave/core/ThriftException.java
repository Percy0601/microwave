package io.microwave.core;

public class ThriftException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ThriftException(){
		super();
	}
	
	public ThriftException(String message){
		super(message);
	}
	
	public ThriftException(Throwable e){
		super(e);
	}
	
	public ThriftException(String message, Throwable e){
		super(message, e);
	}
}
