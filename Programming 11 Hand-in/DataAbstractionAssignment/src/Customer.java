import javax.security.sasl.SaslClient;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Customer {
    private int accountNumber;
    private ArrayList<Deposit> deposits;
    private ArrayList<Withdraw> withdraws;
    private double checkBalance;
    private double savingBalance;
    private double savingRate;
    private String name;
    public static final String CHECKING = "Checking";
    public static final String SAVING = "Saving";
    private final int OVERDRAFT = -100;

    Customer(){
        //create default constructor
        this.accountNumber = 0;
        this.deposits = new ArrayList<>();
        this.withdraws = new ArrayList<>();
        this.checkBalance = 0;
        this.savingBalance = 0;
        this.savingRate = 0;
        this.name = "";
    }
    /*
    Requires: name, accountNumber, checkDeposit >= 0, savingDeposit >= 0
    Modifies: this
    Effects: creates a new Customer, filling the fields with the matching arguments.
     */
    Customer(String name, int accountNumber, double checkDeposit, double savingDeposit){
        //constructor code here
        this.accountNumber = accountNumber;
        this.deposits = new ArrayList<>();
        this.withdraws = new ArrayList<>();
        this.savingRate = 0;
        this.name = name;
        this.checkBalance = checkDeposit;
        this.savingBalance = savingDeposit;
    }

    /*
    Requires: amt >= 0, date, account = this.CHECKING or this.SAVING
    Modifies: this
    Effects: Deposits an amount specified by amt into the account specified by account, and adds a new deposit object to the internal list of deposits
     */
    public double deposit(double amt, Date date, String account){
        //your code here
        this.deposits.add(new Deposit(amt, date, account));
        if(account.equals(this.CHECKING)) {
            this.checkBalance += amt;
            return this.checkBalance;
        } else if(account.equals(this.SAVING)) {
            this.savingBalance += amt;
            return this.savingBalance;
        }
        return 0;
    }

    /*
    Requires: amt >= 0, date, account = this.CHECKING or this.SAVING
    Modifies: this
    Effects: withdraws an amount specified by amt from the account specified by account if checkOverdraft returns false (there is no overdraft), otherwise does nothing. Also adds a new withdraw object to the internal list of withdraws if no overdraft is detected.
     */
    public double withdraw(double amt, Date date, String account){
        //your code here
        if(account.equals(this.CHECKING)) {
            if(this.checkOverdraft(amt, this.CHECKING) == false) {
                this.checkBalance -= amt;
                this.withdraws.add(new Withdraw(amt, date, account));
            } else {
                System.out.println("overdraft detected!");
            }
            return this.checkBalance;
        } else if(account.equals(this.SAVING)) {
            if(checkOverdraft(amt, this.SAVING) == false) {
                this.savingBalance -= amt;
                this.withdraws.add(new Withdraw(amt, date, account));
            } else {
                System.out.println("overdraft detected!");
            }
            return this.savingBalance;
        }
        return 0;
    }

    /*
    Requires: amt >= 0, account = this.CHECKING or this.SAVING
    Modifies: this
    Effects: returns true if an overdraft is detected (the balance of the account becomes negative if amt is subtracted from it). Otherwise, returns false.
     */
    private boolean checkOverdraft(double amt, String account){
        //your code here
        double accountValue;
        boolean error = false;
        if(account.equals(this.CHECKING)) {
            accountValue = checkBalance;
        }
        if(account.equals(this.SAVING)) {
            accountValue = savingBalance;
        } else {
            accountValue = 0;
            error = true;
        }
        if(accountValue - amt <= 0 && error == false) {
            return true;
        } else {
            return false;
        }
    }
    //do not modify
    public void displayDeposits(){
        for(Deposit d : deposits){
            System.out.println(d);
        }
    }
    //do not modify
    public void displayWithdraws(){
        for(Withdraw w : withdraws){
            System.out.println(w);
        }
    }

}
