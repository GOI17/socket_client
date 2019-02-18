package socketclient;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketClient {
    
    public static void main(String[] args) throws ArduinoException, SerialPortException {
        PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
        while(true) {
            SerialPortEventListener listener = new SerialPortEventListener() {
                Socket s;
                PrintStream ps;
                InputStreamReader isr;
                BufferedReader br;
                Random r = new Random();
                String message = "";
                String response = "";
                String stationId = "1";
                double value = 0;
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
                String strDate = dateFormat.format(date);
                @Override
                public void serialEvent(SerialPortEvent spe) {
                    try{
                        if(Arduino.isMessageAvailable() == true) {
//                            System.out.println(Arduino.printMessage());
                            message = Arduino.printMessage();
                            System.out.println(message);
                            s = new Socket("Localhost",2800);
                            ps = new PrintStream(s.getOutputStream());
                            isr = new InputStreamReader(s.getInputStream());
                            br = new BufferedReader(isr);
                            ps.println(message);
                            response = br.readLine();
                            System.out.println(response);
                            s.close();//close socket
                            try {
                                Thread.sleep(6000);
                            } catch(InterruptedException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } catch(SerialPortException ex) {
                        System.out.println("Serial port Exception : "+ex.getMessage());
                    } catch(ArduinoException ex) {
                        System.out.println("Arduino port Exception : "+ex.getMessage());
                    } catch (IOException ex) {
                        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            Arduino.arduinoRXTX("COM11", 9600,listener);
//              String[] val = Simulator.value();
//        for(int x = 0; x < val.length; x++) {
//            System.out.println(val[x]);
//            String idStation = val[0];
//            String descStation = val[1];
//            String valTempStation = val[2];
//            String valWindStation = val[3]; 
//            String valHumiStation = val[4];
//            String result = valTempStation + "°C " + valWindStation + " " + valHumiStation + "%";
//            String ipStation = val[5];
//        json message
//
//          message = arduinoListener(Arduino);
//              message = "{" +
//                            " \"id\": \"" + 1 +"\"," +
//                            " \"ts\": \"" + strDate +"\"," + 
//                            " \"station\":" +
//                            "{" +
//                                " \"id\": \"" + idStation +"\"," +
//                                " \"description\": \"" + descStation +"\"," +
//                                " \"temperature\": \"" + valTempStation +"\"," +
//                                " \"humidity\": \"" + valHumiStation +"\"," +
//                                " \"windQuality\": \"" + valWindStation +"\"," +
//                                " \"ipAddress\": \"" + ipStation +"\"" +
//                        "}"+
//                      "}";
//        }
        }
  }
    
    public static String arduinoListener(PanamaHitek_Arduino Arduino) throws SerialPortException, ArduinoException {
        SerialPortEventListener listener = new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent spe) {
                try{
                    if(Arduino.isMessageAvailable() == true) {
                        System.out.println(Arduino.printMessage());
                    }
                } catch(SerialPortException ex) {
                    System.out.println("Serial port Exception : "+ex.getMessage());
                } catch(ArduinoException ex) {
                    System.out.println("Arduino port Exception : "+ex.getMessage());
                }
            }
        };
        Arduino.arduinoRXTX("COM11", 9600,listener);
        return Arduino.printMessage();
    }
    
}
