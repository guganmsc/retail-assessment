package com.retail.assessment.common;
import java.math.BigDecimal;

/**
 * user action messages 
 * @author GuganVaithilingam
 */
public class Message {
	public static String getGreetingInfo(String userId) {
		return "Hello, " + userId + "!";
	}

	
	public static <T> String printTransfer(BigDecimal transferAmount, String transferTo) {
		return "Transferred " + transferAmount + " to " + transferTo;
	}


	public static <T> String owingToStatement(BigDecimal debtAmount, String oweToAccountName) {
		return "Owing " + debtAmount + " to " + oweToAccountName;
	}

	/**
	 * Owing From message information
	 *
	 */
	public static <T> String owingFromStatement(BigDecimal debtAmount, String accountName) {
		return "Owing " + debtAmount + " from " + accountName;
	}

	/**
	 * Method used for getting  for  Balance Account  message

	 *
	 */
	public static <T> String printBalance(BigDecimal accoutBalance) {
		return "Your balance is " + accoutBalance;
	}

}
