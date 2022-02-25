/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.retail.assessment.common.Utils;
import com.retail.assessment.model.Account;
import com.retail.assessment.model.Debt;

/**
 * Repository Class 
 * @author GuganVaithilingam

 *
 */
public class AccountRepository {
	private static List<Account> accountList = new ArrayList<Account>();
	private static List<Debt> debtList = new ArrayList<Debt>();

	private static AccountRepository accountRepo = new AccountRepository();

	private AccountRepository() {
	};

	/**
	 * Method used for get the instance  of Account Repo
	 * 
	 * @return Instance of Account Repo
	 * 
	 *
	 */
	public static AccountRepository getInstance() {
		return accountRepo;
	}

	/**
	 * Method used for  get  the account details based on account name
	 * 
	 * @param accountName
	 *       
	 * @return Account Details
	 * 
	 *
	 */
	public Account searchAccount(String accountName) {
		if (Utils.isEmpty(accountName)) {
			return null;
		}
		return accountList.stream().filter(acc -> acc.getAccountName().equalsIgnoreCase(accountName.trim())).findFirst()
				.orElse(null);
	}

	/**
	 * Method used for  Save Account or update Account Details
	 * 
	 * @param account
	 *            :Input param is Account details
	 * @return boolean : Status of update
	 * 
	 *
	 */
	public boolean saveOrUpdateAccount(Account account) {
		if (null == account) {
			return false;
		}
		accountList.add(account);
		return true;
	}

	/**
	 * Method used to Retrieve Debt by Account Name
	 * 
	 * @param accountName
	 *          
	 * @return List : Details of debt information
	 * 
	 *
	 */
	public List<Debt> retrieveDebtByAccountName(String accountName) {
		if (Utils.isEmpty(accountName)) {
			return null;
		}
		return debtList.stream().filter(debt -> debt.getAccountName().equalsIgnoreCase(accountName.trim()))
				.collect(Collectors.toList());
	}

	/**
	 * Method used for  Retrieve Debt by Account Name
	 * 
	 * @param OweToAccountName
	 * @return account : Details of debt information owe to other account
	 * 
	 *
	 */
	public List<Debt> retrieveDebtByOweToAccountName(String OweToAccountName) {
		if (Utils.isEmpty(OweToAccountName)) {
			return null;
		}
		return debtList.stream().filter(debt -> debt.getOweToAccountName().equalsIgnoreCase(OweToAccountName.trim()))
				.collect(Collectors.toList());
	}

	/**
	 * Method used to get delete Debt 
	 * 
	 * @param debt
	 *        
	 * @return Boolean : status of the transaction
	 * 
	 *
	 */
	public boolean deleteDebt(Debt debt) {
		if (debtList.contains(debt)) {
			debtList.remove(debt);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method used for  get add Debt 
	 * 
	 * @param debt
	 * @return Boolean : status of the transaction
	 * 
	 *
	 */
	public void addDebt(Debt debt) {
		if (!debtList.contains(debt)) {
			debtList.add(debt);
		}
	}

}
