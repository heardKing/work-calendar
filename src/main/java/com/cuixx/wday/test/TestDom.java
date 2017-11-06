package com.cuixx.wday.test;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TestDom {

    @Test
    public void test() throws Exception, IOException {

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDocumentById () throws Exception{
        File f = new File("F:/WS_YU/wday/WebContent/FullCanlendar/note.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(f);

        Element root = document.getRootElement();
        Element e1 = root.elementByID("S2016-02-01");
        System.out.println(e1.attributeValue("title"));
        List<Element> s=root.elements("event");
        for (Element e:s){
            String id = e.attribute("ID").getValue();
            String title = e.attribute("title").getValue();
            System.out.println(id+":"+title);
        }
    }

    @Test
    public void testRead () throws DocumentException{
        File f = new File("F:/WS_YU/wday/WebContent/FullCanlendar/note.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(f);
        Element root = document.getRootElement();

        List<Element> list = root.elements("event");
        StringBuffer sb = new StringBuffer("[");
        for (int i=0;i<list.size();i++){
            StringBuffer sbTemp = new StringBuffer("{");
            Element e = list.get(i);
            Attribute abID = e.attribute("ID");
            Attribute abTitle = e.attribute("title");
            String idValue = abID.getValue();
            String start = idValue.substring(1);
            String title = abTitle.getValue();
            sbTemp.append("\"start\":\""+start+"\",");
            sbTemp.append("\"title\":\""+title+"\"");
            if (i<list.size()-1){
                sbTemp.append("},");
            }else{
                sbTemp.append("}");
            }
            sb.append(sbTemp);
        }
        sb.append("]");
        System.out.println(sb);
    }

    @Test
    public void testWrite () throws IOException, DocumentException {
        File f = new File("F:/WS_YU/wday/WebContent/FullCanlendar/note.xml");

        SAXReader reader = new SAXReader();
        Document document = reader.read(f);

        Element root = document.getRootElement();

        Element e1 = root.addElement("event");
        e1.addAttribute("ID", "S2016-12-12");
        e1.addAttribute("title", "国庆节");

        FileOutputStream fos = new FileOutputStream(f);
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("UTF-8");
        of.setIndent("	");
        XMLWriter writer = new XMLWriter(fos,of);

        writer.write(document);
        writer.flush();
        writer.close();
    }

}
