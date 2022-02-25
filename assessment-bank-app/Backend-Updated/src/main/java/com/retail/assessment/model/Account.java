/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Account Detail 
 * 
 * @author GuganVaithilingam
 *
 */
public class Account {

	private String accountName;
	private List<Transaction> transactions;
	private BigDecimal accoutBalance;
	private LocalDateTime createdDateTime;
	private List<Debt> debts;
	private List<Debt> debtsByOthers;
	private String messageDetails;
	private BigDecimal amountStr;

	public Account() {

	}

	public Account(AccountBuilder builder) {
		super();
		this.accountName = builder.accountName;
		this.accoutBalance = builder.accoutBalance;
		this.createdDateTime = builder.createdDateTime;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(String messageDetails) {
		this.messageDetails = messageDetails;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(BigDecimal amountStr) {
		this.amountStr = amountStr;
	}

	public BigDecimal getAccoutBalance() {
		return accoutBalance;
	}

	public void setAccoutBalance(BigDecimal accoutBalance) {
		this.accoutBalance = accoutBalance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Debt> getDebts() {
		return debts;
	}

	public void setDebts(List<Debt> debts) {
		this.debts = debts;
	}

	public static class AccountBuilder {

		private String accountName;
		private BigDecimal accoutBalance;
		private LocalDateTime createdDateTime;

		public AccountBuilder(String accountName) {
			this.accountName = accountName;
		}

		public AccountBuilder withAccountName(String accountName) {
			this.accountName = accountName;
			return this;
		}

		public AccountBuilder withAccoutBalance(BigDecimal accoutBalance) {
			this.accoutBalance = accoutBalance;
			return this;
		}

		public AccountBuilder withCreatedDateTime(LocalDateTime createdDateTime) {
			this.createdDateTime = createdDateTime;
			return this;
		}

		public Account build() {
			return new Account(this);

		}

	}

	public List<Debt> getDebtsByOthers() {
		return debtsByOthers;
	}

	public void setDebtsByOthers(List<Debt> debtsByOthers) {
		this.debtsByOthers = debtsByOthers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accoutBalance == null) ? 0 : accoutBalance.hashCode());
		result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accoutBalance == null) {
			if (other.accoutBalance != null)
				return false;
		} else if (!accoutBalance.equals(other.accoutBalance))
			return false;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accountName=" + accountName + ", accoutBalance=" + accoutBalance + "]";
	}

}
