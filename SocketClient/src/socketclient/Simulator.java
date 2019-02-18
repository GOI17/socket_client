package socketclient;
import java.text.DecimalFormat;
import java.util.Random;

public class Simulator {
    
    public static String[] value(){
        Random r = new Random();
        DecimalFormat df = new DecimalFormat("#.00");
//        double minTemp = 0;
        String qualityWind = "";
        String ipAddress = "";
        String descStation = "";
        double humidity = 0;
        int max = 2;
        int min = 1;
        int maxTemp = 27;
        int minTemp = 26;
        int maxHum = 33;
        int minHum = 30;
        int stationActive = r.nextInt(max-min + 1) + min;
        int temp = r.nextInt(maxTemp-minTemp + 1) + minTemp;
        int humi = r.nextInt(maxHum-minHum + 1) + minHum;
        String[] values = new String[6];
        
        descStation = "North station";
        qualityWind = "Normal wind";
        ipAddress = "192.168.18.1";
        
        values[0] = String.valueOf(stationActive);
        values[1] = descStation;
        values[2] = String.valueOf(temp);
        values[3] = qualityWind;
        values[4] = String.valueOf(humi);
        values[5] = ipAddress;
        
        return values;
    }
}
