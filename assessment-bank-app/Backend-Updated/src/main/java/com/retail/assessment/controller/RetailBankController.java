package com.retail.assessment.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.retail.assessment.Exception.AccountNotFoundException;
import com.retail.assessment.Exception.RetailBankException;
import com.retail.assessment.common.Utils;
import com.retail.assessment.model.Account;
import com.retail.assessment.service.AccountService;
import com.retail.assessment.service.LoginService;
import com.retail.assessment.service.TransactionService;

/**
 * @author GuganVaithilingam
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RetailBankController {

	@Autowired
	LoginService loginService;

	@Autowired
	TransactionService transacationService;

	@Autowired
	AccountService accountService;

	public static Account loggedInAccount;

	/**
	 * Method used  to get the login user details and remaining balance amount
	 * 
	 * @param account
	 *         
	 * @return  Account status  with account details
	 * 
	 *
	 */

	@PostMapping("/login")
	public ResponseEntity<Account> login(@RequestBody Account account) {
		try {
			loggedInAccount = loginService.login(Utils.cleanString(account.getAccountName()));
			accountService.getBalanceDetails(loggedInAccount);
		} catch (RetailBankException e) {
			logOutput(e.getMessage());
			return ResponseEntity.ok(loggedInAccount);
		} catch (Exception e) {
			logOutput(e.getMessage());
		}

		return ResponseEntity.ok(loggedInAccount);
	}

	/**
	 * Method used for topup to an account 
	 * @param account
	 * @return Account details
	 */

	@PostMapping("/topup/{amountStr}")
	public ResponseEntity<Account> topup(@PathVariable String amountStr) {
		BigDecimal amount = null;
		try {
			if (loggedInAccount == null) {
				loggedInAccount.setMessageDetails("Please login first");
				return ResponseEntity.ok(loggedInAccount);
			}
			amount = new BigDecimal(amountStr);
			transacationService.topUpAccount(loggedInAccount, amount);
			loggedInAccount = accountService.getBalanceDetails(loggedInAccount);
		} catch (NumberFormatException e) {
			logOutput("Please enter valid amount");
		} catch (RetailBankException e) {
			logOutput(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logOutput(e.getMessage());
		}
		return ResponseEntity.ok(loggedInAccount);
	}

	/**
	 * Method used to pay to an account from another account 
	 * Return type is the account info containing Account
	 * balance and transfer details
	 * @param amount
	 * @param accountName
	 *   
	 */

	@PostMapping("/pay/")
	public ResponseEntity<Account> pay(BigDecimal amount, String accountName) {
		try {
			if (loggedInAccount == null) {
				loggedInAccount.setMessageDetails("Please login first");
				return ResponseEntity.ok(loggedInAccount);
			}
			transacationService.makeTransfer(loggedInAccount, amount, Utils.cleanString(accountName));
			loggedInAccount = accountService.getBalanceDetails(loggedInAccount);
		} catch (NumberFormatException e) {
			logOutput("Please enter valid amount");
		} catch (AccountNotFoundException e) {
			logOutput(e.getMessage());
		} catch (RetailBankException e) {
			logOutput(e.getMessage());
		} catch (Exception e) {
			logOutput(e.getMessage());
		}
		return ResponseEntity.ok(loggedInAccount);
	}

	/**
	 * Method used to process the  Logout action 
	 * @param amount
	 */
	@PostMapping("/logOut/")
	public ResponseEntity<Account> logOut(Account account) {
		try {
			loggedInAccount = null;
		} catch (RetailBankException e) {
			logOutput(e.getMessage());
			account.setMessageDetails("Exception in processing the request");
			return ResponseEntity.ok(account);
		} catch (Exception e) {
			logOutput(e.getMessage());
		}

		return ResponseEntity.ok(account);
	}

	public static void logOutput(String text) {
		System.out.println(text);

	}
}
