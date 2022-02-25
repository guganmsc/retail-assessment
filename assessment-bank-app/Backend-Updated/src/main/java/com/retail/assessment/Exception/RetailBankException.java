package com.retail.assessment.Exception;

/**
 * Exception Handling method for Retail Banking application
 * @author GuganVaithiligam

 *
 */
public class RetailBankException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RetailBankException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
	public RetailBankException(String errorMessage) {
        super(errorMessage);
    }
}
