package ljd.calendar.entity;

import ljd.calendar.util.Player;
import ljd.calendar.util.PlayerThread;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Calendar {

    private SAXReader saxReader;
    private Document document;
    private Element configelement;
    private List<Element> event;
    private List<String> time = new ArrayList<String>();
    private List<String> text = new ArrayList<String>();
    ScheduledExecutorService scheduledExecutorService;
    public Calendar() throws DocumentException {
        this.scheduledExecutorService=new ScheduledThreadPoolExecutor(5);
        this.saxReader = new SAXReader();
        this.document = saxReader.read("./config.xml");
        this.configelement = this.document.getRootElement();
        this.event = configelement.elements();

        for (Element e : event) {
            Element time = e.element("time");
            Element text = e.element("text");
            this.time.add(time.getText());
            this.text.add(text.getText());

        }
    }

    public void showCalendar() {
        /* for (Map.Entry<String,String> e: event.entrySet()){
            System.out.println("time:"+e.getKey()+"  text:"+e.getValue());
         }  */
        for (int i = 0; i < time.size(); i++) {
            System.out.println("time:" + time.get(i) + "  text:" + text.get(i));
        }
    }

    public void addEvent(String time, String text) throws IOException {
        String regex = "^(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
        if (time.matches(regex)) {

            Time t = Time.valueOf(time);
            Element event = DocumentHelper.createElement("event");

            if(t.getTime()> Time.valueOf(this.time.get( this.time.size()-1)).getTime()){

                Element tt = DocumentHelper.createElement("time");
                Element tx = DocumentHelper.createElement("text");
                tt.setText(time);
                tx.setText(text);
                event.add(tt);
                event.add(tx);

                this.event.add( event);
                XMLWriter xmlWriter = new XMLWriter(new FileWriter("./config.xml"));
                xmlWriter.write(document);
                xmlWriter.close();

            }else {
                for (int i = 0; i < this.time.size(); i++) {
                    Time last = Time.valueOf("00:00:00");
                    Time next = Time.valueOf(this.time.get(i));

                    if (t.getTime() > last.getTime() && t.getTime() < next.getTime()) {
//                        System.out.println(last);
//                        System.out.println(next);
//                        System.out.println(1111);

                        Element tt = DocumentHelper.createElement("time");
                        Element tx = DocumentHelper.createElement("text");
                        tt.setText(time);
                        tx.setText(text);
                        event.add(tt);
                        event.add(tx);

                        this.event.add(i, event);
                        XMLWriter xmlWriter = new XMLWriter(new FileWriter("./config.xml"));
                        xmlWriter.write(document);
                        xmlWriter.close();

                        break;

                    }else if(t.getTime()==next.getTime()){
//                        System.out.println(i);
                        log.info("当前时间以存在事件是否替换Y/N");
                        Scanner scanner=new Scanner(System.in);
                        String s=scanner.next();
                        if(s.equals("y")){
                            Element text1 = this.event.get(i).element("text");
                            text1.setText(text);
                            XMLWriter xmlWriter = new XMLWriter(new FileWriter("./config.xml"));
                            xmlWriter.write(document);
                            xmlWriter.close();
                            break;
                        }
                        log.info("返回");
                        break;
                    }else {
                        last=next;
                    }

                }
            }

        } else {
            log.info("输入事件不规范");
        }
    }

    public void start(){
        scheduledExecutorService.shutdown();
        File file=new File("C:\\Users\\Administrator\\Music\\圣 - 献给爱丽丝（上课铃版）.wav");
        for (int i = 0; i < time.size(); i++) {
             long now=System.currentTimeMillis();
             Time  target =Time.valueOf(time.get(i));
             scheduledExecutorService.schedule(new PlayerThread(new Player(file),text.get(i),1),target.getTime()-now, TimeUnit.MILLISECONDS);
        }



    }
}
