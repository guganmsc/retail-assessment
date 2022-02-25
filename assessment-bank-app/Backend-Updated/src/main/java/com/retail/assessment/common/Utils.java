/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.common;

/**
 * Util class for all the validation
 * 
 * @author GuganVaithilingam

 */
import java.util.List;

public class Utils {

	
	//Empty check  
	public static boolean isEmpty(String val) {
		return (null == val || val.trim().isEmpty());
	}

	//List Empty Check
	public static <T> boolean isEmpty(List<T> val) {
		return (null == val || val.isEmpty());
	}

	public static String cleanString(String val) {
		if (isEmpty(val)) {
			return null;
		}
		return val.trim();
	}
}
