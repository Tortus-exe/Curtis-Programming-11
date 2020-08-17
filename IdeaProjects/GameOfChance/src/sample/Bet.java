package sample;

public class Bet {
    private int FirstNumber;
    private int SecondNumber;
    private int ThirdNumber;
    private Boolean Win;
    private double amount;
    private int roundNumber;
    private String winString;
    private String amountString;

    Bet (double amount, int roundNumber) {
        this.FirstNumber = (int) (10*Math.random());
        this.SecondNumber = (int) (10*Math.random());
        this.ThirdNumber = (int) (10*Math.random());
        if(this.FirstNumber == this.SecondNumber || this.FirstNumber == this.ThirdNumber || this.SecondNumber == this.ThirdNumber) {
            this.Win = true;
            this.winString = "Win";
        } else {
            this.Win = false;
            this.winString = "Loss";
        }
        this.amount = amount;
        this.amountString = "$" + this.amount;
        this.roundNumber = roundNumber;
    }

    Bet(Bet original) {
        this.FirstNumber = original.getFirstNumber();
        this.SecondNumber = original.getSecondNumber();
        this.ThirdNumber = original.getThirdNumber();
        this.Win = original.getWin();
        this.amount = original.getAmount();
        this.roundNumber = original.getRoundNumber();
        this.winString = original.getWinString();
        this.amountString = original.getAmountString();
    }

    public int getFirstNumber() {
        return FirstNumber;
    }

    public int getSecondNumber() {
        return SecondNumber;
    }

    public int getThirdNumber() {
        return ThirdNumber;
    }

    public Boolean getWin() {
        return Win;
    }

    public double getAmount() {
        return amount;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getAmountString() {
        return amountString;
    }

    public String getWinString() {
        return winString;
    }
}
