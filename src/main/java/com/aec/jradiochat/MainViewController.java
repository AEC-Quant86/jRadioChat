package com.aec.jradiochat;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainViewController  implements UpdateChat {

	private SerialPortActivity portActivity = new SerialPortActivity();
	private boolean portIsOpen = false;
	private Encryptor encryptor;
	
	
    @FXML
    private Tab startTab;

    @FXML
    private ChoiceBox<String> portChoiceBox;

    @FXML
    private ChoiceBox<String> baudChoiceBox;

    @FXML
    private Button findPortsButton;

    @FXML
    private Button toglePortButton;

    @FXML
    private TextField nicknameField;

    @FXML
    private PasswordField cryptoPassField;

    @FXML
    private CheckBox cryptoCheckBox;

    @FXML
    private Tab mainTab;

    @FXML
    private TextArea chatTextField;

    @FXML
    private TextArea messegeTextField;

    @FXML
    private Button sendMessegeButton;


    @FXML
	void enterSend(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String msg = nicknameField.getText() + ": " + messegeTextField.getText();
			chatTextField.appendText(msg);
			if (portActivity.sendMessege(msg) > 0) {
		           messegeTextField.clear();
		       }
		       else
		           chatTextField.appendText("Ошибка отправки сообщения" + '\n');
		    }
		}
		
	
    
    @FXML
    void findPortsButtonClick(ActionEvent event) {
    	scanPorts();
    }

    @FXML
    void openPortButtonClick(ActionEvent event) {
        if (portIsOpen) {
            portIsOpen = false;
            toglePortButton.setText("Открыть порт");
            portActivity.closePort();
        } 
        else if (portActivity.openPort(portChoiceBox.getValue(), baudChoiceBox.getValue())) {
            mainTab.setDisable(false);
            toglePortButton.setText("Закрыть порт");
            portIsOpen = true;
        }
        chatTextField.appendText(portActivity.toString());

    }

    @FXML
    void sendMessegeButtonClick(ActionEvent event) {
       String msg = nicknameField.getText() + ": " + messegeTextField.getText();
       chatTextField.appendText(msg + '\n');
       
    //   if (cryptoCheckBox.isSelected()) 
      //     msg = encryptor.encrypt_msg(msg, cryptoPassField.getText());
       
       
       if (portActivity.sendMessege(msg) > 0) {
           messegeTextField.clear();
       }
       else
           chatTextField.appendText("Ошибка отправки сообщения" + '\n');
    }
    

    @FXML
    void cryptoCheckBoxStateChange(ActionEvent event) {
      // if (cryptoCheckBox.isSelected());
        //   encryptor = new Encryptor(cryptoPassField.getText());
    }
    
    private void scanPorts() {
    	portChoiceBox.setItems(FXCollections.observableArrayList( 
    			portActivity.scanPorts()
    			)
    			);
    }
    
    @Override
    public void messegeReceive(String msg) {
     //   if (cryptoCheckBox.isSelected())
      //      msg = encryptor.decrypt_msg(msg, cryptoPassField.getText());
       
        chatTextField.appendText(msg);
    }
    
    @FXML
    public void initialize() {
    	mainTab.setDisable(true);
    	scanPorts();
    	portActivity.addListener(this);
    	
        baudChoiceBox.setItems(FXCollections.observableArrayList(
        		"9600", "115200"
        		)
        		);
        
        
    }



}
