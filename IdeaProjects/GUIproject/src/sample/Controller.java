package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class Controller {

    public TextField textGetCost;
    public TextField textGetQuantity;
    public TextField textGetName;
    public ListView<Product> productList;
    public Label lblName;
    public Label lblQuant;
    public Label lblCost;
    public Button btnPurchase;

    public void addProduct(ActionEvent actionEvent) {
        Product temp = new Product(textGetName.getText(), Integer.parseInt(textGetQuantity.getText()), Double.parseDouble(textGetCost.getText()));
        productList.getItems().add(temp);
        textGetName.clear();
        textGetCost.clear();
        textGetQuantity.clear();
        btnPurchase.setDisable(true);
    }

    public void displayProduct(MouseEvent mouseEvent) {
        Product temp;
        temp = productList.getSelectionModel().getSelectedItem();
        lblCost.setText("$" + Double.toString(temp.getCost()));
        lblQuant.setText(Integer.toString(temp.getQuantity()));
        lblName.setText(temp.name);
        btnPurchase.setDisable(false);
    }

    public void purchaseProduct(ActionEvent actionEvent) {
        Product temp;
        temp = productList.getSelectionModel().getSelectedItem();
        temp.purchase();
        lblQuant.setText(Integer.toString(temp.getQuantity()));
    }
}
