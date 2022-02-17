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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SUROVI
 */
public class LoginViewController implements Initializable {

    @FXML
    private TabPane tabpaneLogin;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Tab tabAdmin;
    @FXML
    private TextField adminuName;
    @FXML
    private PasswordField adminPassword;
    @FXML
    private Button loginBtn;
    @FXML
    private Tab tabUser;
    @FXML
    private TextField adminuName1;
    @FXML
    private PasswordField adminPassword1;
    @FXML
    private Label lbCreateAccount;
    @FXML
    private Pane slidingPane;
    @FXML
    private Label lbAdmin;
    @FXML
    private Label lbUser;
    @FXML
    private Label lb1;
     @FXML
    private StackPane rootPane;
    @FXML
    private PasswordField adminPassword12;
    
    Boolean authentication=false;
    Connection connection = null;
    Statement st = null;
    PreparedStatement pst = null;
    ResultSet resultset = null;
    int row = -1;
    @FXML
    private Button changeBtn;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     @FXML
    void changeAction(ActionEvent event) {
    
         String query = "SELECT password FROM table1 WHERE username = ? ";
        //String query="SELECT * FROM 'table1' WHERE username=? AND password=?";
        String uName = adminuName1.getText();
        String uPassword = adminPassword1.getText();
            
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_page_info", "root", "root");
            pst = connection.prepareStatement(query);
            pst.setString(1, uName);
            
            resultset = pst.executeQuery();
            
            if (!resultset.isBeforeFirst()) {
                System.out.println("User not found in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided informations are incorrect");
                alert.show();
            } else {
                while (resultset.next()) {
                    String retrievedPassword = resultset.getString("password");
                   
                    if (retrievedPassword.equals(uPassword)) {
                        System.out.println("Successfull!");
                        
                        authentication = true;
                        
                    } else {
                        System.out.println("Password didn't match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided informations are incorrect");
                        alert.show();
                    }
                    //System.out.println("Successfull");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*finally
        {
            if(resultset != null)
            {
                try
                {
                    resultset.close();
                }catch(SQLException e)
                {    
                   e.printStackTrace();
                 }
            }
            if( pst != null)
            {
                try
                {
                    pst.close();
                }catch(SQLException e)
                {    
                   e.printStackTrace();
                 }
            }
           if(connection != null)
            {
                try
                {
                    connection.close();
                }catch(SQLException e)
                {    
                   e.printStackTrace();
                 }
            }
 

             
        }*/
       String newPass= adminPassword12.getText();
      String sql="SELECT password FROM table1 WHERE username = '?'";
      String sql1="UPDATE table1 SET password= '"+newPass+"' WHERE username ='"+uName+"'  ";
       
        if(authentication == true)
        {
            try {   
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_page_info", "root", "root");
                st = connection.createStatement();
                st.executeUpdate(sql1);
                JOptionPane.showMessageDialog(null, "Password Updated!");
                adminuName1.setText("");
                adminPassword1.setText("");
                adminPassword12.setText("");
                
            } catch (SQLException ex) {
                System.out.println("row value"+row);
                JOptionPane.showMessageDialog(null, "HEHE");
            }
        }

        
    }

    
    @FXML
    private void openAdminTab(MouseEvent event) {
        
     long duration=500;
       try{
  TranslateTransition  toLeftAnimation = new TranslateTransition(javafx.util.Duration.millis(duration),lb1);
  toLeftAnimation.setToX(slidingPane.getTranslateX());
  toLeftAnimation.play();
   toLeftAnimation.setOnFinished((ActionEvent event2)->{
   lb1.setText("USER");
  });
    tabpaneLogin.getSelectionModel().select(tabAdmin);
   
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
        
        
        
    }
      @FXML
    void openCreateAccountScene(MouseEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("CreateAccountView.fxml"));
        Scene loginScene =lbAdmin.getScene();
        root.translateYProperty().set(loginScene.getHeight());
        rootPane.getChildren().add(root);
        Timeline timeline = new Timeline ();
        KeyValue keyValue = new KeyValue(root.translateYProperty(),0,Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        timeline.setOnFinished((ActionEvent event2)->{
            rootPane.getChildren().remove(anchorPane);
        });
        
        
        
    }

    @FXML
    private void openUserTab(MouseEvent event) {
        System.out.println("Mouse Event1 Working");
        long duration1=500;
       try{
           
  TranslateTransition  toRightAnimation = new TranslateTransition(javafx.util.Duration.millis(duration1),lb1);
  toRightAnimation.setToX(slidingPane.getTranslateX()+(slidingPane.getPrefWidth()-lb1.getPrefWidth()));
  toRightAnimation.play();
  toRightAnimation.setOnFinished((ActionEvent event1)->{
   lb1.setText("RESET PASSWORD");
  });
  tabpaneLogin.getSelectionModel().select(tabUser);
  
          
       }catch(Exception e)
       {
           e.printStackTrace();
       }
    }

    @FXML
    private void loginAction(ActionEvent event) {
        
       String query = "SELECT password FROM table1 WHERE username = ? ";
        //String query="SELECT * FROM 'table1' WHERE username=? AND password=?";
        String uname = adminuName.getText();
        String password = adminPassword.getText();
            
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_page_info", "root", "root");
            pst = connection.prepareStatement(query);
            pst.setString(1, uname);
            
            resultset = pst.executeQuery();
            
            if (!resultset.isBeforeFirst()) {
                System.out.println("User not found in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided informations are incorrect");
                alert.show();
            } else {
                while (resultset.next()) {
                    String retrievedPassword = resultset.getString("password");
                   
                    if (retrievedPassword.equals(password)) {
                        System.out.println("Successfull!");
                        JOptionPane.showMessageDialog(null, "Log in Successfull");
                        adminuName.setText("");
                        adminPassword.setText("");
                        authentication = true;
                        
                    } else {
                        System.out.println("Password didn't match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided informations are incorrect");
                        alert.show();
                    }
                    //System.out.println("Successfull");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*finally
        {
            if(resultset != null)
            {
                try
                {
                    resultset.close();
                }catch(SQLException e)
                {    
                   e.printStackTrace();
                 }
            }
            if( pst != null)
            {
                try
                {
                    pst.close();
                }catch(SQLException e)
                {    
                   e.printStackTrace();
                 }
            }
           if(connection != null)
            {
                try
                {
                    connection.close();
                }catch(SQLException e)
                {    
                   e.printStackTrace();
                 }
            }
 

             
        }*/
        

        
        
        
    }
    
}
