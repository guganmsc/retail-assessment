/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.assessment.common.Message;
import com.retail.assessment.model.Account;

/**
 * Account service process to account related information
 * 
 * @author GuganVaithilingam
 *
 */
 
@Service
public class LoginService implements BaseService{
	@Autowired
	private AccountService accountService;
 
	/**
	 * Method used perform login based on user input
	 * @param accountName
	 * @return Account details
	 */
	public Account login(String accountName) {
		Account acc = accountService.searchAccount(accountName.trim());
		if (acc == null) {
			acc = accountService.createAccount(accountName);
		}
		acc.setMessageDetails(Message.getGreetingInfo(accountName));
		return acc;
	}
}