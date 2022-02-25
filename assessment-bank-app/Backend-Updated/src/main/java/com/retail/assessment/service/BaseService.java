/*
 * =============================================================================
 * For internal use of XXX Bank only.(C) 2022  XXX BANK.All Rights Reserved.
 * Information in this file is the intellectual property of XXX BANK
 * =============================================================================
 */
package com.retail.assessment.service;

import com.retail.assessment.repository.AccountRepository;

/**
 * Base Interface for
 * @author GuganVaithilingam
 */
public interface BaseService {

	public static final AccountRepository accountRepo = AccountRepository.getInstance();
}
