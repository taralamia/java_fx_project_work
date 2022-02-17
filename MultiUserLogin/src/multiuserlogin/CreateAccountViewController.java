/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiuserlogin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SUROVI
 */
public class CreateAccountViewController implements Initializable {

    @FXML
    private Label againLoginLb;
    @FXML
    private TextField adminuName;
    @FXML
    private PasswordField adminPassword;
    @FXML
    private Button createBtn;
    @FXML
    private AnchorPane anchorpane;
    Connection connection = null;
    Statement st = null;
    PreparedStatement pst = null;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   

    @FXML
    private void openLoginScene(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene AccountScene =againLoginLb.getScene();
        root.translateYProperty().set(AccountScene.getHeight());
        
        StackPane rootPane = (StackPane) AccountScene.getRoot();
        
        rootPane.getChildren().add(root);
        Timeline timeline = new Timeline ();
        KeyValue keyValue = new KeyValue(root.translateYProperty(),0,Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        timeline.setOnFinished((ActionEvent event2)->{
            rootPane.getChildren().remove(anchorpane);
        });
    }

    @FXML
    private void createAction(ActionEvent event) {
        
        System.out.println("Initial");
        String insertUname = adminuName.getText();
        String insertPassword = adminPassword.getText();
       
     String insertSQL = "INSERT into table1 (username,password) Values ('"+insertUname+"','"+insertPassword+"')";
                int confirmMessage = 0;
               
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_page_info", "root", "root");
            st = connection.createStatement();
            st.executeUpdate(insertSQL);
            System.out.println("working");
            confirmMessage++;
            //connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CreateAccountViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
            if(confirmMessage >= 1)
            {
                JOptionPane.showMessageDialog(null, "Successfull");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Failed");
            }

        
    }


 
}