package sample;

import com.sun.codemodel.internal.JArray;
import com.sun.tools.internal.xjc.model.CPropertyInfo;
import com.sun.tools.javac.comp.Lower;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ValueFactory;

import java.util.ArrayList;

public class Controller {
    public Label FirstNum;
    public Label SecondNum;
    public Label ThirdNum;
    public TableView<Bet> HistoryTable;
    public TableColumn RoundColumn;
    public TableColumn Number1Column;
    public TableColumn Number2Column;
    public TableColumn Number3Column;
    public TableColumn WinColumn;
    public TableColumn AmountColumn;
    public Label messageLabel;
    public TextField BetTextField;
    public Label Wins;
    public Label Losses;
    public Label AmountOfMoney;
    public Button AddButton;
    public Button SubtractButton;
    public TextField amountToAddField;

    public Circle C5;
    public Circle C7;
    public Circle C3;
    public Circle C9;
    public Circle C1;
    public Circle C6;
    public Circle C4;
    public Button HigherBtn;
    public Button LowerBtn;
    public TableView<DiceRound> DiceHistoryTable;
    public TableColumn RoundColumnDice;
    public TableColumn RollColumn;
    public TableColumn PrevColumn;
    public TableColumn BetColumn;
    public TableColumn WinColumnDice;
    public TableColumn AmountColumnDice;
    public Label WinsDice;
    public Label LossesDice;
    public Label AmountOfMoneyDice;
    public TextField amountToAddFieldDice;
    public TextField BetTextFieldDice;
    public Button SubtractButton1;
    public Button AddButton1;
    public Label diceMesssageLabel;
    public Tab diceGameTab;
    public Tab slotGameTab;
    public Button RollBtn;
    public Button gambleBtn;

    private Bet tempBet;
    private int roundNumber = 0;
    private double bet;
    private double totalAmount = 100;
    private int losses;
    private int wins;

    private int currentDiceNumber = (int) (6*Math.random()) + 1;
    private int previousDiceNumber;
    private int betHighOrLow = 2;
    private DiceRound tempRound;
    private boolean winThisRound;
    private int diceWins;
    private int diceLosses;
    private int diceRoundNumber = 0;


    public void initialize() {
        drawCircles(currentDiceNumber);
    }

    public void drawCircles(int number) {
        Boolean[] dotList = {false, false, false, false, false, false, false};
        if(number == 1) {
            dotList[3] = true;
        } else if (number == 2) {
            dotList[1] = true;
            dotList[5] = true;
        } else if (number == 3) {
            dotList[0] = true;
            dotList[3] = true;
            dotList[6] = true;
        } else if (number == 4) {
            dotList[0] = true;
            dotList[1] = true;
            dotList[5] = true;
            dotList[6] = true;
        } else if (number == 5) {
            dotList[0] = true;
            dotList[1] = true;
            dotList[3] = true;
            dotList[5] = true;
            dotList[6] = true;
        } else if (number == 6) {
            dotList[0] = true;
            dotList[1] = true;
            dotList[2] = true;
            dotList[4] = true;
            dotList[5] = true;
            dotList[6] = true;
        } else {
            System.out.println("number outside of range: " + currentDiceNumber);
        }
        C1.setVisible(dotList[0]);
        C3.setVisible(dotList[1]);
        C4.setVisible(dotList[2]);
        C5.setVisible(dotList[3]);
        C6.setVisible(dotList[4]);
        C7.setVisible(dotList[5]);
        C9.setVisible(dotList[6]);
    }

    public void Gamble(ActionEvent actionEvent) {
        if(totalAmount > 0) {
            BetTextField.setDisable(true);
            AddButton.setDisable(true);
            SubtractButton.setDisable(true);
            roundNumber++;
            tempBet = new Bet(totalAmount, roundNumber);

            FirstNum.setText(Integer.toString(tempBet.getFirstNumber()));
            SecondNum.setText(Integer.toString(tempBet.getSecondNumber()));
            ThirdNum.setText(Integer.toString(tempBet.getThirdNumber()));

            if (tempBet.getWin()) {
                messageLabel.setText("You win!");
                wins++;
                Wins.setText(Integer.toString(wins));
                totalAmount += bet;
                AmountOfMoney.setText("$" + totalAmount);
                AmountOfMoneyDice.setText("$" + totalAmount);
            } else {
                messageLabel.setText("You lost. Try again?");
                losses++;
                Losses.setText(Integer.toString(losses));
                totalAmount -= bet;
                AmountOfMoney.setText("$" + totalAmount);
                AmountOfMoneyDice.setText("$" + totalAmount);
            }

            RoundColumn.setCellValueFactory(new PropertyValueFactory("roundNumber"));
            Number1Column.setCellValueFactory(new PropertyValueFactory("FirstNumber"));
            Number2Column.setCellValueFactory(new PropertyValueFactory("SecondNumber"));
            Number3Column.setCellValueFactory(new PropertyValueFactory("ThirdNumber"));
            WinColumn.setCellValueFactory(new PropertyValueFactory("winString"));
            AmountColumn.setCellValueFactory(new PropertyValueFactory("amountString"));

            HistoryTable.getItems().addAll(tempBet);

            BetTextField.setDisable(false);
            AddButton.setDisable(false);
            SubtractButton.setDisable(false);
        } else {
            gambleBtn.setDisable(true);
            messageLabel.setText("You're broke! Add some money!");
        }
        if(bet > totalAmount) {
            bet = totalAmount;
            BetTextFieldDice.setText(Double.toString(bet));
            BetTextField.setText(Double.toString(bet));
        }
    }

    public void SubtractFromBet(ActionEvent actionEvent) {
        if(bet - 1 >= 0) {
            bet--;
            BetTextField.setText(Double.toString(bet));
            BetTextFieldDice.setText(Double.toString(bet));
        } else {
            messageLabel.setText("You cannot bet a negative number!");
            diceMesssageLabel.setText("You cannot bet a negative number!");
        }
    }

    public void AddToBet(ActionEvent actionEvent) {
        if(bet+1 <= totalAmount) {
            bet++;
            BetTextField.setText(Double.toString(bet));
            BetTextFieldDice.setText(Double.toString(bet));
        } else {
            if(slotGameTab.isSelected()) {
                messageLabel.setText("You have reached your betting limit! Add money to increase your bet!");
            } else {
                diceMesssageLabel.setText("You have reached your betting limit! Add money to increase your bet!");
            }
        }
    }

    public void AddToAmount(ActionEvent actionEvent) {
        if(slotGameTab.isSelected()) {
            totalAmount += Double.parseDouble(amountToAddField.getText());
        } else {
            totalAmount += Double.parseDouble(amountToAddFieldDice.getText());
        }
        AmountOfMoney.setText("$" + Double.toString(totalAmount));
        AmountOfMoneyDice.setText("$" + Double.toString(totalAmount));
        amountToAddFieldDice.clear();
        amountToAddField.clear();
        RollBtn.setDisable(false);
        gambleBtn.setDisable(false);
    }

    public void RollDice(ActionEvent actionEvent) {
        if(totalAmount > 0) {
            HigherBtn.setDisable(true);
            LowerBtn.setDisable(true);
            RollBtn.setDisable(true);

            if (betHighOrLow == 1 || betHighOrLow == 0) {
                diceRoundNumber++;
                previousDiceNumber = currentDiceNumber;
                currentDiceNumber = (int) (6 * Math.random()) + 1;
                drawCircles(currentDiceNumber);
                tempRound = new DiceRound(diceRoundNumber, currentDiceNumber, previousDiceNumber, betHighOrLow, totalAmount);
                if (tempRound.getWinString().equals("W")) {
                    diceWins++;
                    totalAmount += bet;
                    AmountOfMoneyDice.setText("$" + totalAmount);
                    AmountOfMoney.setText("$" + totalAmount);
                    diceMesssageLabel.setText("You win!");
                } else if (tempRound.getWinString().equals("L")) {
                    diceLosses++;
                    totalAmount -= bet;
                    AmountOfMoneyDice.setText("$" + totalAmount);
                    AmountOfMoney.setText("$" + totalAmount);
                    diceMesssageLabel.setText("You lost. Try again?");
                }

                RoundColumnDice.setCellValueFactory(new PropertyValueFactory("roundNumber"));
                RollColumn.setCellValueFactory(new PropertyValueFactory("currentRoll"));
                PrevColumn.setCellValueFactory(new PropertyValueFactory("lastRoll"));
                BetColumn.setCellValueFactory(new PropertyValueFactory("bet"));
                WinColumnDice.setCellValueFactory(new PropertyValueFactory("winString"));
                AmountColumnDice.setCellValueFactory(new PropertyValueFactory("totalAmountString"));

                DiceHistoryTable.getItems().addAll(tempRound);
            } else {
                diceMesssageLabel.setText("You need to bet either higher or lower first!");
            }

            WinsDice.setText(Integer.toString(diceWins));
            LossesDice.setText(Integer.toString(diceLosses));

            RollBtn.setDisable(false);
            HigherBtn.setDisable(false);
            LowerBtn.setDisable(false);
            if (betHighOrLow == 0) {
                HigherBtn.setDisable(true);
            } else if (betHighOrLow == 1) {
                LowerBtn.setDisable(true);
            }
        } else {
            diceMesssageLabel.setText("You're broke! add some money!");
            RollBtn.setDisable(true);
        }
        if(bet > totalAmount) {
            bet = totalAmount;
            BetTextFieldDice.setText(Double.toString(bet));
            BetTextField.setText(Double.toString(bet));
        }
    }

    public void SetBetHigher(ActionEvent actionEvent) {
        HigherBtn.setDisable(true);
        LowerBtn.setDisable(false);
        betHighOrLow = 0;
    }

    public void SetBetLower(ActionEvent actionEvent) {
        HigherBtn.setDisable(false);
        LowerBtn.setDisable(true);
        betHighOrLow = 1;
    }

    public void UpdateBet(KeyEvent keyEvent) {
        Double tempDouble;
        if(slotGameTab.isSelected()) {
            tempDouble = Double.parseDouble(BetTextField.getText());
        } else {
            tempDouble = Double.parseDouble(BetTextFieldDice.getText());
        }
        if(tempDouble <= totalAmount) {
            bet = tempDouble;
            if(slotGameTab.isSelected()) {
                gambleBtn.setDisable(false);
                BetTextFieldDice.setText(Double.toString(bet));
                messageLabel.setText("");
            } else {
                RollBtn.setDisable(false);
                BetTextField.setText(Double.toString(bet));
                diceMesssageLabel.setText("");
            }
        } else {
            if(slotGameTab.isSelected()) {
                messageLabel.setText("You cannot bet more than the amount of money you have! Add some money!");
                gambleBtn.setDisable(true);
            } else {
                diceMesssageLabel.setText("You cannot bet more than the amount of money you have! Add some money!");
                RollBtn.setDisable(true);
            }
        }
    }
}
