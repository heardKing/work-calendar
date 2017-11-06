package com.cuixx.wday;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cuixx.wday.util.Dom4JUtil;
import com.cuixx.wday.util.ResponseSendJson;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * qq80303857
 * Servlet implementation class FullCanlendarSaveTag
 */
@WebServlet("/FullCanlendarSaveTag")
public class FullCanlendarSaveTag extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FullCanlendarSaveTag() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings("unused")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String path = request.getServletContext().getRealPath("/");
        String start = request.getParameter("start");
        String title = request.getParameter("title");
        if (title!=null){
            title = new String(title.getBytes("ISO-8859-1"),"UTF-8");
        }else{
            title = "休息日";
        }
        File file = new File(path+"FullCanlendar/note.xml");
        Document document = Dom4JUtil.parse(file);
        Element root = document.getRootElement();
        Element element = root.elementByID("S"+start);
        if (element!=null){
            ResponseSendJson.sendJson(response, "{\"success\":\"exist\"}");
        }else{
            Element curEle = root.addElement("event");
            curEle.addAttribute("ID", "S"+start);
            curEle.addAttribute("start", start);
            curEle.addAttribute("title", title);
            Dom4JUtil.write(file, document);
            ResponseSendJson.sendJson(response, "{\"success\":\"sav\"}");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String path = request.getServletContext().getRealPath("/");
        String id = request.getParameter("id");

        File file = new File(path+"FullCanlendar/note.xml");
        Document document = Dom4JUtil.parse(file);
        Element root = document.getRootElement();
        Element element = root.elementByID(id);
        if (element!=null){
            root.remove(element);
            Dom4JUtil.write(file, document);
            ResponseSendJson.sendJson(response, "{\"success\":\"del\"}");
        }else{
            root.remove(element);
            Dom4JUtil.write(file, document);
            ResponseSendJson.sendJson(response, "{\"success\":\"exist\"}");
        }
    }

}
