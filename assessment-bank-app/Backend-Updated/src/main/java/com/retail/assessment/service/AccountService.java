/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.retail.assessment.common.Message;
import com.retail.assessment.common.Utils;
import com.retail.assessment.controller.RetailBankController;
import com.retail.assessment.model.Account;
import com.retail.assessment.model.Debt;

/**
 * Service class to service the account level details
 * @author GuganVaithilingam
 */

@Service
public class AccountService implements BaseService {

	private AccountService() {
	};

	public static AccountService getInstance() {
		AccountService as = new AccountService();
		return as;
	}

	/**
	 * Method used for Save Account or update Account Details
	 * @param account
	 * @return boolean : Status of update
	 */
	public boolean saveOrUpdateAccount(Account account) {
		return accountRepo.saveOrUpdateAccount(account);

	}

	/**
	 * Method used to search Account Details
	 * 
	 * @param accountName
	 *            :Input param is Account Name
	 * @return account : Details of account information
	 * 
	 *
	 */
	public Account searchAccount(String accountName) {
		Account acc = accountRepo.searchAccount(accountName);
		if (null != acc) {
			acc.setDebts(retrieveDebtByAccountName(accountName));
			acc.setDebtsByOthers(retrieveDebtByOweToAccountName(accountName));
		}
		return acc;

	}

	/**
	 * Method used to Create Account Details
	 * @param accountName
	 * :Input param is Account Name
	 * @return boolean : Details of account information
	 */
	public Account createAccount(String accountName) {
		Account account = new Account.AccountBuilder(accountName).withCreatedDateTime(LocalDateTime.now())
				.withAccoutBalance(BigDecimal.ZERO).build();
		this.saveOrUpdateAccount(account);
		return account;

	}

	/**
	 * Method used to Retrieve Debt by Account Name
	 * @param accountName
	 * :Input param is Account Name
	 * @return List : Details of debt information
	 */
	public List<Debt> retrieveDebtByAccountName(String accountName) {
		return accountRepo.retrieveDebtByAccountName(accountName);
	}

	/**
	 * Method used to Retrieve Debt by Account Name
	 * @param OweToAccountName
	 * :Input param owe to other account
	 * @return account : Details of debt information owe to other account

	 */

	public List<Debt> retrieveDebtByOweToAccountName(String OweToAccountName) {
		return accountRepo.retrieveDebtByOweToAccountName(OweToAccountName);
	}

	/**
	 * Method used to getBalanceDetails
	 * @param account
	 * :Input param is Account details
	 * @return account : Details with Balance account information
	 */
	public Account getBalanceDetails(Account account) {
		if (!Utils.isEmpty(account.getDebts())) {
			account.getDebts().stream().forEach((debt) -> {
				account.setMessageDetails(account.getMessageDetails()
						+ Message.owingToStatement(debt.getDebtAmount(), debt.getOweToAccountName()));
			});
		}

		if (!Utils.isEmpty(account.getDebtsByOthers())) {
			account.getDebtsByOthers().stream().forEach((debt) -> {
				account.setMessageDetails(account.getMessageDetails()
						+ (Message.owingFromStatement(debt.getDebtAmount(), debt.getAccountName())));
			});
		}
		account.setMessageDetails(Message.printBalance(account.getAccoutBalance()));
		return account;
	}

}
