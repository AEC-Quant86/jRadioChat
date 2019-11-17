package com.aec.jradiochat;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;


public class SerialPortActivity implements SerialPortDataListener {

    private SerialPort port;
    private List<UpdateChat> listeners = new ArrayList<UpdateChat>();
    
    public void addListener(UpdateChat toAdd) {
        listeners.add(toAdd);
    }
    
    private void updateChat (String msg) {
        for (UpdateChat hl : listeners)
            hl.messegeReceive(msg);
    }
    
    
    public int sendMessege(String msg) { //Изменить метод для возможности отправки зашифрованного сообщения
        //msg += '\n';
        msg = " " + msg;
        
        try {
            byte[] out = msg.getBytes("UTF-8");
            return port.writeBytes(out, out.length);
        } 
        catch (Exception e) {
            return 0;
        }
    }

    public boolean openPort(String portName, String baud) {

        try {
            port = SerialPort.getCommPort(portName);
         //   port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1000, 100);
            port.setParity(SerialPort.EVEN_PARITY);
            port.setNumStopBits(SerialPort.ONE_STOP_BIT);
            port.setNumDataBits(8);
            port.addDataListener(this);
            port.setBaudRate(Integer.parseInt(baud));
            return port.openPort();

        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closePort() {
        port.closePort();
    }

    public String[] scanPorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] result = new String[ports.length];
        
        for (int i = 0; i < ports.length; i++) {
            result[i] = ports[i].getSystemPortName();
        }
        
        return result;
    }

    @Override
    public String toString() {
        if (port.isOpen())
            return "Чат запушен на Serial - " + port.getSystemPortName() + '\n';
        else
            return "Ошибка открытия Serial\n";
    }

    @Override
    public int getListeningEvents() {
        // TODO Auto-generated method stub
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;

    }
    
    @Override
    public void serialEvent(SerialPortEvent event) { //Изменить метод для возможности приема зашифрованного сообщения

        try {
            Thread.sleep(100);
            byte[] buffer = new byte[port.bytesAvailable()];
            port.readBytes(buffer, buffer.length);
            String output = new String(buffer, "UTF-8");
            System.out.println(output);
            updateChat(output);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка приема данных");
            updateChat("Ошибка приема данных\n");
        }

    }

}
