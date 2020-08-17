import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import java.util.Locale;

public class Tests {

    private Customer testCustomer;
    private String testString;
    private GregorianCalendar gWith;
    private GregorianCalendar gDeps;
    private Date dateWithdrawal;
    private Date dateDeposit;
    private Deposit testDeposit;
    private Withdraw testWithdraw;

    @Before
    public void setup () {
        String testString = "";
        //Withdrawal of: $400.0 Date: Sun Nov 04 00:00:00 PDT 2018 into account: Checking
        //Deposit of: $500.0 Date: Thu Aug 16 10:52:17 PDT 2018 into account: Saving
        gWith = new GregorianCalendar(2018, Calendar.NOVEMBER, 4, 00, 00, 00);
        gDeps = new GregorianCalendar(2018, Calendar.AUGUST, 16, 10, 52, 17);
        gWith.setTimeZone(TimeZone.getTimeZone("PDT"));
        gDeps.setTimeZone(TimeZone.getTimeZone("PDT"));
        gWith.add(gWith.HOUR_OF_DAY, 7);
        gDeps.add(gWith.HOUR_OF_DAY, 7);
        dateDeposit = gWith.getTime();
        dateWithdrawal = gDeps.getTime();
        testCustomer = new Customer("Bob", 0, 20.0, 200.0);
        testDeposit = new Deposit(400.0, dateDeposit, Customer.CHECKING);
        testWithdraw = new Withdraw(500.0, dateWithdrawal, Customer.SAVING);
    }

    @Test
    public void CheckDeposit () {
        testCustomer.displayDeposits();
        assertEquals(testDeposit.toString(), "Deposit of: $400.0 Date: Sun Nov 04 00:00:00 PDT 2018 into account: Checking");
        assertEquals(testCustomer.deposit(400.0, dateDeposit, Customer.CHECKING), 420, 0.1);
        testCustomer.displayDeposits();
    }

    @Test
    public void CheckWithdrawal() {
        testCustomer.displayWithdraws();
        assertEquals(testWithdraw.toString(), "Withdrawal of: $500.0 Date: Thu Aug 16 10:52:17 PDT 2018 into account: Saving");
        assertEquals(testCustomer.withdraw(500.0, dateWithdrawal, Customer.SAVING), 200, 0.1);
        testCustomer.deposit(500.0, dateWithdrawal, Customer.SAVING);
        assertEquals(testCustomer.withdraw(300.0, dateWithdrawal, Customer.SAVING), 400, 0.1);
        testCustomer.displayWithdraws();
    }
}
