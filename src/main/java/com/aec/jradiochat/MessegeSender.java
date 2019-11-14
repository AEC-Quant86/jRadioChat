package com.aec.jradiochat;

public class MessegeSender {
    

    public static boolean send(String msg, SerialPortActivity portActivity) {
        if (portActivity.sendMessege(msg) > 0)
            return true;
        else 
            return false;
    }
    
    public static boolean send(String msg, SerialPortActivity portActivity, Encryptor encryptor) {
        if (portActivity.sendMessege(msg) > 0)
            return true;
        else 
            return false;
    }


}
