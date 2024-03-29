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
			msg = msg.substring(0, msg.length() - 1);
			this.messegeReceive(msg);
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
       this.messegeReceive(msg);
       
       
       if (portActivity.sendMessege(msg) > 0) {
           messegeTextField.clear();
       }
       else
           chatTextField.appendText("Ошибка отправки сообщения" + '\n');
    }
    
    private void scanPorts() {
    	portChoiceBox.setItems(FXCollections.observableArrayList( 
    			portActivity.scanPorts()
    			)
    			);
    }
    
    @Override
    public void messegeReceive(String msg) {
       
        chatTextField.appendText(msg + "\n");
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        portActivity.closePort();
    }
}
