package ljd.calendar;


import javafx.collections.transformation.FilteredList;
import ljd.calendar.config.Config;
import ljd.calendar.entity.Calendar;
import ljd.calendar.util.Player;
import ljd.calendar.util.PlayerThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */

@Slf4j
public class App {

    public static void main(String[] args) throws InterruptedException, ParseException, IOException, DocumentException {


        Config config=new Config();
        config.init();

        Scanner scanner=new Scanner(System.in);
        Calendar calendar;


        while (true){
            calendar=new Calendar();

            int a=scanner.nextInt();
            switch (a){
                case 1:
                    calendar.showCalendar();
                    break;
                case 2:
                    String t=scanner.next();
                    String tx=scanner.next();
                    calendar.addEvent(t,tx);
                    break;
                case 3:
                    calendar.start();
                    break;
                case 4:
            }

        }





    }



}
