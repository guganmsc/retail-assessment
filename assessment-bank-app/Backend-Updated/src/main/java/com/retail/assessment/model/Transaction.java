/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.retail.assessment.common.Constants.TRANSACTION_TYPE;

/**
 * Transaction Details
 * @author GuganVaithilingam
 *
 */
public class Transaction {
	private String sourceAccountName;
	private String destinationAccountName;
	private BigDecimal transferAmount;
	private LocalDateTime transactionDateTime;
	private TRANSACTION_TYPE transactionType;

	public Transaction(TransactionBuilder builder) {
		super();
		this.sourceAccountName = builder.sourceAccountName;
		this.destinationAccountName = builder.destinationAccountName;
		this.transferAmount = builder.transferAmount;
		this.transactionDateTime = builder.transactionDateTime;
		this.transactionType = builder.transactionType;
	}

	public String getSourceAccountName() {
		return sourceAccountName;
	}

	public void setSourceAccountName(String sourceAccountName) {
		this.sourceAccountName = sourceAccountName;
	}

	public String getDestinationAccountName() {
		return destinationAccountName;
	}

	public void setDestinationAccountName(String destinationAccountName) {
		this.destinationAccountName = destinationAccountName;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public TRANSACTION_TYPE getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TRANSACTION_TYPE transactionType) {
		this.transactionType = transactionType;
	}

	public static class TransactionBuilder {
		private String sourceAccountName;
		private String destinationAccountName;
		private BigDecimal transferAmount;
		private LocalDateTime transactionDateTime;
		private TRANSACTION_TYPE transactionType;

		public TransactionBuilder withSourceAccountName(String sourceAccountName) {
			this.sourceAccountName = sourceAccountName;
			return this;
		}

		public TransactionBuilder withDestinationAccountName(String destinationAccountName) {
			this.destinationAccountName = destinationAccountName;
			return this;

		}

		public TransactionBuilder withTransferAmount(BigDecimal transferAmount) {
			this.transferAmount = transferAmount;
			return this;

		}

		public TransactionBuilder withTransactionDateTime(LocalDateTime transactionDateTime) {
			this.transactionDateTime = transactionDateTime;
			return this;

		}

		public TransactionBuilder withTransactionType(TRANSACTION_TYPE transactionType) {
			this.transactionType = transactionType;
			return this;
		}

		public Transaction build() {
			return new Transaction(this);
		}

	}

}
