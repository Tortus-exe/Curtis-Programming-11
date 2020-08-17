package sample;

public class DiceRound {
    private int roundNumber;
    private int currentRoll;
    private int lastRoll;
    private String bet;
    private String winString;
    private String totalAmountString;

    DiceRound(int roundNum, int currentRoll, int lastRoll, int betInt, Double betAmount) {
        roundNumber = roundNum;
        this.currentRoll = currentRoll;
        this.lastRoll = lastRoll;
        this.totalAmountString = "$" + betAmount;
        if(betInt == 0) {
            this.bet = "H";
            if(currentRoll > lastRoll) {
                this.winString = "W";
            } else {
                this.winString = "L";
            }
        } else if (betInt == 1) {
            this.bet = "L";
            if(currentRoll < lastRoll) {
                this.winString = "W";
            } else {
                this.winString = "L";
            }
        }
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getWinString() {
        return winString;
    }

    public String getTotalAmountString() {
        return totalAmountString;
    }

    public int getCurrentRoll() {
        return currentRoll;
    }

    public int getLastRoll() {
        return lastRoll;
    }

    public String getBet() {
        return bet;
    }

    @Override
    public String toString() {
        return "DiceRound{" +
                "roundNumber=" + roundNumber +
                ", currentRoll=" + currentRoll +
                ", lastRoll=" + lastRoll +
                ", bet='" + bet + '\'' +
                ", winString='" + winString + '\'' +
                ", totalAmountString='" + totalAmountString + '\'' +
                '}';
    }
}
