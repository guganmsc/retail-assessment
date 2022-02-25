package com.retail.assessment;

import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.retail.assessment.model.Account;
import com.retail.assessment.service.AccountService;
import com.retail.assessment.service.LoginService;
import com.retail.assessment.service.TransactionService;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class RetailbankApplicationTests {

    @Autowired
    TransactionService transactionService ;
    @Autowired
    AccountService accountService ;
    @Autowired
    LoginService loginService;


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */


    /**
     * Functional Test
     */
    @Test
    public void testLogin() {

        Account account = loginService.login("Dinesh");
        assertTrue(account != null);
        assertTrue(account.getAccountName().equals("Dinesh"));
    }

@Test
    public void testTopup() {
        Account account = loginService.login("Dinesh");
        transactionService.topUpAccount(account, new BigDecimal("500"));
        assertTrue(account.getAccoutBalance().compareTo(new BigDecimal("500")) == 0);

    }
@Test
    public void testPay() {
        Account account1 = loginService.login("Dinesh");
        Account account2 = loginService.login("Kannan");
        transactionService.topUpAccount(account2, new BigDecimal("500"));
        transactionService.makeTransfer(account2, new BigDecimal("100"), "Dinesh");
        assertTrue(account2.getAccoutBalance().compareTo(new BigDecimal("400")) == 0);
        account1 = loginService.login("Dinesh");
        assertTrue(account1.getAccoutBalance().compareTo(new BigDecimal("100")) == 0);

    }

}
