import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;

public class Withdraw {
    private double amount;
    private Date date;
    private String account;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        //My computer runs in japanese because I'm learning the language, but it causes the default locale to be japanese. oops! Need to manually set the locale to be US
    //Withdrawal of: $400.0 Date: Sun Nov 04 00:00:00 PDT 2018 into account: Checking

    /*
    Requires: amount >= 0, date, account = Customer.CHECKING or Customer.SAVING
    Modifies: this
    Effects: creates a new Withdraw object, filling in the fields with the appropriate arguments.
     */
    Withdraw(double amount, Date date, String account){
        this.amount = amount;
        this.date = date;
        this.account = account;
    }
    /*
    Requires: nothing
    Modifies: this
    Effects: returns a string representation of the Withdraw Object, formatted as specified by the instructions.
     */
    public String toString(){
        //your code here
        return "Withdrawal of: $" + this.amount + " Date: " +  simpleDateFormat.format(this.date) + " into account: " + this.account;
    }
}
