package com.cuixx.wday.util;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Dom4JUtil {
    public static Document parse(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return document;
    }

    public static void bar(Document document) throws DocumentException {

        Element root = document.getRootElement();

        // iterate through child elements of root
        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            // do something
        }

        // iterate through child elements of root with element name "foo"
        for (Iterator i = root.elementIterator("foo"); i.hasNext(); ) {
            Element foo = (Element) i.next();
            // do something
        }

        // iterate through attributes of root
        for (Iterator i = root.attributeIterator(); i.hasNext(); ) {
            Attribute attribute = (Attribute) i.next();
            // do something
        }
    }


    public Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        Element author1 = root.addElement("author")
                .addAttribute("name", "James")
                .addAttribute("location", "UK")
                .addText("James Strachan");

        Element author2 = root.addElement("author")
                .addAttribute("name", "Bob")
                .addAttribute("location", "US")
                .addText("Bob McWhirter");

        return document;
    }

    public static String XML2Json(List<Element> list) {
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < list.size(); i++) {
            StringBuffer sbTemp = new StringBuffer("{");
            Element e = list.get(i);
            Attribute abID = e.attribute("ID");
            Attribute abTitle = e.attribute("title");
            Attribute abStart = e.attribute("start");
            String idValue = abID.getValue();
            String title = abTitle.getValue();
            String start = abStart.getValue();
            sbTemp.append("\"start\":\"" + start + "\",");
            sbTemp.append("\"title\":\"" + title + "\",");
            sbTemp.append("\"id\":\"" + idValue + "\"");
            if (i < list.size() - 1) {
                sbTemp.append("},");
            } else {
                sbTemp.append("}");
            }
            sb.append(sbTemp);
        }
        sb.append("]");
        return sb.toString();
    }

    public static void write(File file, Document document) throws IOException {

        FileOutputStream fos = new FileOutputStream(file);
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("UTF-8");
//        of.setIndent("	");
        XMLWriter writer = new XMLWriter(fos, of);

        writer.write(document);
        writer.flush();
        writer.close();
    }

    public static void clear(Element parentElement, List<Element> list) {
        for (Element ele : list) {
            parentElement.remove(ele);
        }
    }
}
