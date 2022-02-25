/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Debt Attributes
 * 
 * @author GuganVaithilingam
 * @version 1.0
 * @since 04Feb2022
 *
 */
public class Debt {

	private String accountName;
	private String oweToAccountName;
	private BigDecimal debtAmount;
	private LocalDateTime dateTime;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getOweToAccountName() {
		return oweToAccountName;
	}

	public void setOweToAccountName(String oweToAccountName) {
		this.oweToAccountName = oweToAccountName;
	}

	public BigDecimal getDebtAmount() {
		return debtAmount;
	}

	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((debtAmount == null) ? 0 : debtAmount.hashCode());
		result = prime * result + ((oweToAccountName == null) ? 0 : oweToAccountName.hashCode());
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
		Debt other = (Debt) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (debtAmount == null) {
			if (other.debtAmount != null)
				return false;
		} else if (!debtAmount.equals(other.debtAmount))
			return false;
		if (oweToAccountName == null) {
			if (other.oweToAccountName != null)
				return false;
		} else if (!oweToAccountName.equals(other.oweToAccountName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Debt [accountName=" + accountName + ", oweToAccountName=" + oweToAccountName + ", debtAmount="
				+ debtAmount + "]";
	}

}
