package com.retail.assessment.Exception;

/**
 * Exception Handling method for Account Details
 * @author GuganVaithilingam
 *
 */
public class AccountNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
    public AccountNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public AccountNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
