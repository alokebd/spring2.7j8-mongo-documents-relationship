package com.ait.vis.mongo.embeded.api.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException() {
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
