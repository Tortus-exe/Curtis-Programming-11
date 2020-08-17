import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;

public class Deposit {
    private double amount;
    private Date date;
    private String account;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        //My computer runs in japanese because I'm learning the language, but it causes the default locale to be japanese. oops! Need to manually set the locale to be US
    /*
    Requires: amount >= 0, date, account = Customer.SAVING or Customer.CHECKING
    Modifies: this
    Effects: creates a new deposit object, filling in the fields with the appropriate arguments.
     */
    Deposit(double amount, Date date, String account){
        this.amount = amount;
        this.date = date;
        this.account = account;
    }

    /*
    Requires: nothing
    Modifies: this
    Effects: returns a string representation of the Deposit object, formatted as specified by the instructions.
     */
    public String toString(){
        //your code here
        return "Deposit of: $" + this.amount + " Date: " + simpleDateFormat.format(this.date) + " into account: " + this.account;
    }
}
