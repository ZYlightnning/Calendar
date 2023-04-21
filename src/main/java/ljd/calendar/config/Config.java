package ljd.calendar.config;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.List;

@Slf4j
public class Config {


     public Config(){ }

     public void init() throws IOException {
         File file=new File("./config.xml");

         if(file.createNewFile()){
             log.info("无配置 以创建");
             Document document=DocumentHelper.createDocument();
             document.addElement("config");
             XMLWriter xmlWriter=new XMLWriter(new FileWriter(file));
             xmlWriter.write(document);
             xmlWriter.close();
             log.info("写入规范");

         }else {
             log.info("有配置");

         }
     }

     public void setConfig() throws DocumentException, IOException {
         SAXReader saxReader=new SAXReader();
         Document document =saxReader.read("./config.xml");
         Element root=document.getRootElement();
         List<Element> elements = root.elements();
         for (Element element:elements){
             System.out.println( element.element("time").getText());
         }

//         Document document= DocumentHelper.createDocument();
//
//         Element config=DocumentHelper.createElement("config");
//
//         document.setRootElement(config);
//
//         Element event=config.addElement("event");
//
//         OutputFormat format= OutputFormat.createPrettyPrint();
//
//         format.setEncoding("utf-8");
//
//         FileOutputStream outFile=new FileOutputStream("test.xml");
//
//         XMLWriter  xmlWriter=new XMLWriter(outFile,format);
//
//         xmlWriter.write(document);
//
//         outFile.close();
//         xmlWriter.close();





     }
}
