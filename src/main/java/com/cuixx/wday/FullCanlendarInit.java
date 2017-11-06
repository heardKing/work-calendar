package com.cuixx.wday;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cuixx.wday.util.DateUtils;
import com.cuixx.wday.util.Dom4JUtil;
import com.cuixx.wday.util.EveryDayUtils;
import com.cuixx.wday.util.ResponseSendJson;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * Servlet implementation class FullCanlendarInit
 */
@WebServlet("/FullCanlendarInit")
public class FullCanlendarInit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FullCanlendarInit() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletContext().getRealPath("/");
        File file = new File(path+"FullCanlendar/note.xml");
        Document document = Dom4JUtil.parse(file);
        Element root = document.getRootElement();
        Dom4JUtil.clear(root, root.elements("event"));
        Dom4JUtil.write(file, document);
        ResponseSendJson.sendJson(response, "{\"success\":\"clear\"}");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletContext().getRealPath("/");
        File file = new File(path+"FullCanlendar/note.xml");
        Document document = Dom4JUtil.parse(file);
        Element root = document.getRootElement();
        Dom4JUtil.clear(root, root.elements("event"));
        String parmYear = request.getParameter("year");
        Calendar c = Calendar.getInstance();
        int year = 2016;
        if (EveryDayUtils.strIsNum(parmYear)){
            year = Integer.valueOf(parmYear);
        }else{
            year = c.get(Calendar.YEAR);
        }

        for (int i=0;i<12;i++){
            c.set(year, i, 1);
            int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int x=1;x<=lastDay;x++){
                c.set(year, i, x);
                if(DateUtils.checkHoliday(c)){
                    String ymd = String.format("%d-%02d-%02d", year, (i + 1), x);
                    System.out.print(ymd + " format ");
                    Element curEle = root.addElement("event");
                    curEle.addAttribute("ID", "S"+ymd);
                    curEle.addAttribute("start", ymd);
                    curEle.addAttribute("title", "休息日");
                }else{
                    System.out.print(String.format("%d-%02d-%02d", year, (i + 1), x)+"	");
                }
            }
            System.out.println();
        }
        Dom4JUtil.write(file, document);
        ResponseSendJson.sendJson(response, "{\"success\":\"init\"}");
    }

}
