/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.retail.assessment.Exception.AccountNotFoundException;
import com.retail.assessment.Exception.RetailBankException;
import com.retail.assessment.common.Message;
import com.retail.assessment.common.Utils;
import com.retail.assessment.common.Constants.TRANSACTION_TYPE;
import com.retail.assessment.model.Account;
import com.retail.assessment.model.Debt;
import com.retail.assessment.model.Transaction;

/**
 * Transaction Service used for handling transaction request
 * 
 * @author DINESHKANNAN_R
 * @version 1.0
 * @since 04Feb2022
 *
 */
@Service
public class TransactionService implements BaseService {

	private AccountService accountService = AccountService.getInstance();

	private TransactionService() {
	};

	/**
	 * Method to get transaction service instance
	 * 
	 * @return Transaction instance
	 */
	public static TransactionService getInstance() {
		TransactionService ts = new TransactionService();
		ts.accountService = AccountService.getInstance();
		return ts;
	}

	/**
	 * Method used to make transfer of payment
	 * 
	 * @param account
	 *            :account from which transfer is to be made
	 * @param transferAmount:amount
	 *            to be transferred
	 * @param transferTo:
	 *            Account to be transferred to
	 * @return Boolean : status of transaction
	 */
	public boolean makeTransfer(Account account, BigDecimal transferAmount, String transferTo) {
		if (null == account || transferAmount == null || transferTo == null) {
			return false;
		} else if (transferTo.equalsIgnoreCase(account.getAccountName())) {
			throw new RetailBankException("Could not transfer money to same account");
		}

		// Fetch destination account details
		Account recipAccount = this.accountService.searchAccount(transferTo);
		if (recipAccount == null) {
			throw new AccountNotFoundException("Unable to Transfer amount. Account, " + transferTo + " does not exist");
		}

		BigDecimal balance = null;

		// Check if sufficient amount of balance available, if not, add the
		// missing
		// money to debt
		if (account.getAccoutBalance().compareTo(transferAmount) >= 0) {
			balance = account.getAccoutBalance().subtract(transferAmount);
		} else {
			this.addDebt(account, transferAmount.subtract(account.getAccoutBalance()), recipAccount.getAccountName());

			transferAmount = account.getAccoutBalance();
			balance = BigDecimal.ZERO;
		}

		// Set remaining balance
		account.setAccoutBalance(null != balance ? balance : BigDecimal.ZERO);

		// Transfer amount if not zero
		if (transferAmount.compareTo(BigDecimal.ZERO) > 0) {
			account.setMessageDetails(Message.printTransfer(transferAmount, transferTo));
			this.addBalance(recipAccount, transferAmount);
		}

		// Capture transaction entry
		Transaction transaction = new Transaction.TransactionBuilder().withDestinationAccountName(transferTo)
				.withTransferAmount(transferAmount).withSourceAccountName(account.getAccountName())
				.withTransactionType(TRANSACTION_TYPE.TOPUP).withTransactionDateTime(LocalDateTime.now()).build();
		transaction.setTransactionType(TRANSACTION_TYPE.TRANSFER);
		this.recordTransaction(account, transaction);
		this.recordTransaction(recipAccount, transaction);
		account.setDebtsByOthers(accountService.retrieveDebtByOweToAccountName(account.getAccountName()));
		return true;

	}

	/**
	 * Method used to topup account
	 * 
	 * @param account
	 *            :account details
	 * @param amount
	 *            to be topup
	 * @return Boolean : status of transaction
	 */
	public boolean topUpAccount(Account account, BigDecimal amount) {
		if (null == account || amount == null) {
			return false;
		}
		this.addBalance(account, amount);
		Transaction transaction = new Transaction.TransactionBuilder()
				.withDestinationAccountName(account.getAccountName()).withTransferAmount(amount)
				.withTransactionType(TRANSACTION_TYPE.TOPUP).withTransactionDateTime(LocalDateTime.now()).build();
		this.recordTransaction(account, transaction);
		account.setDebtsByOthers(accountService.retrieveDebtByOweToAccountName(account.getAccountName()));
		return true;
	}

	/**
	 * Method used to add balance to an account
	 * 
	 * @param account
	 *            :account details
	 * @param amount
	 *            to be added
	 * @return Boolean : status of transaction
	 */
	public boolean addBalance(Account account, BigDecimal amount) {
		if (null == account || amount == null) {
			return false;
		}
		// add balance first and then Clear debts !
		account.setAccoutBalance(account.getAccoutBalance().add(amount));
		amount = clearCurrentDebt(account, account.getAccoutBalance());
		return true;

	}

	/**
	 * Method used to Clear debts of the account with given amount
	 * 
	 * @param account
	 *            :account details
	 * @param amount
	 *            to be cleared
	 * @return amount : remaining amount
	 */
	private BigDecimal clearCurrentDebt(Account account, BigDecimal amount) {
		if (null == account || Utils.isEmpty(account.getDebts())) {
			return amount;
		}
		List<Debt> debtOrdered = account.getDebts().stream()
				.sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime())).collect(Collectors.toList());
		for (Debt debt : debtOrdered) {
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
				amount = this.processDebt(account, amount, debt);
			}
		}
		return amount;

	}

	/**
	 * Method used to Subtract amount from debt, transfer the subtracted debt
	 * amount and return
	 * 
	 * @param acc
	 *            :account details
	 * @param amount:amount
	 *            to be processed for Debt
	 * @param debt:
	 *            debt Details
	 * @return amount : remaining amount
	 */
	//
	// remaining amount
	private BigDecimal processDebt(Account acc, BigDecimal amount, Debt debt) {
		if (null != debt && debt.getDebtAmount() != null && amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal transferAmount = BigDecimal.ZERO;
			if (amount.compareTo(debt.getDebtAmount()) >= 0) {
				transferAmount = debt.getDebtAmount();
				amount = amount.subtract(transferAmount);
				this.deleteDebt(acc, debt);
			} else {
				transferAmount = amount;
				amount = BigDecimal.ZERO;
				debt.setDebtAmount(debt.getDebtAmount().subtract(transferAmount));
			}
			this.makeTransfer(acc, transferAmount, debt.getOweToAccountName());
		}
		return amount;

	}

	/**
	 * Method used to Record Transaction
	 * 
	 * @param acc
	 * @param transaction:transaction
	 * @return Null
	 */
	public void recordTransaction(Account account, Transaction transaction) {
		List<Transaction> transactions = account.getTransactions();
		transaction.setTransactionDateTime(LocalDateTime.now());
		if (transactions == null) {
			transactions = new ArrayList<Transaction>();
			account.setTransactions(transactions);
		}
		transactions.add(transaction);
	}

	/**
	 * Method used to add Debt details
	 * 
	 * @param acc
	 * @param debtAmount:
	 * @param oweTo:
	 * @return Null
	 */
	private void addDebt(Account account, BigDecimal debtAmount, String oweTo) {

		List<Debt> debts = account.getDebts();
		if (null == debts) {
			debts = new ArrayList<Debt>();
			account.setDebts(debts);
		}

		// Create new debt entry if not exist already, or add to existing debt
		Debt debt = this.getDebt(account, oweTo);
		if (debt == null) {
			debt = new Debt();
			debt.setAccountName(account.getAccountName());
			debt.setOweToAccountName(oweTo);
			debts.add(debt);
		} else {
			debtAmount = debtAmount.add(debt.getDebtAmount());
		}
		debt.setDebtAmount(debtAmount);
		debt.setDateTime(LocalDateTime.now());
		accountRepo.addDebt(debt);
	}

	/**
	 * Method used to get Debt details
	 * @param acc
	 * @param oweTo:
	 * @return Debt Details
	 */
	private Debt getDebt(Account account, String oweTo) {
		if (account == null || Utils.isEmpty(account.getDebts()) || Utils.isEmpty(oweTo)) {
			return null;
		}
		return account.getDebts().stream().filter(debt -> debt.getOweToAccountName().equalsIgnoreCase(oweTo))
				.findFirst().orElse(null);

	}

	/**
	 * Method used to get delete Debt details
	 * 
	 * @param account
	 * @param debt:
	 * @return boolean : Status of transaction
	 */
	public boolean deleteDebt(Account account, Debt debt) {
		if (!Utils.isEmpty(account.getDebts())) {
			account.getDebts().remove(debt);
		}
		return accountRepo.deleteDebt(debt);
	}

}
