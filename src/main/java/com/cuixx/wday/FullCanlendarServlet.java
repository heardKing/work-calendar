package com.cuixx.wday;

import com.cuixx.wday.util.Dom4JUtil;
import com.cuixx.wday.util.ResponseSendJson;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * qq80303857
 * Servlet implementation class FullCanlendarServlet
 */
@WebServlet("/FullCanlendarServlet")
public class FullCanlendarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FullCanlendarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings({"unused", "unchecked"})
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String path = request.getServletContext().getRealPath("/");
        File file = new File(path + "FullCanlendar/note.xml");
        Document document = Dom4JUtil.parse(file);
        Element root = document.getRootElement();

        List<Element> list = root.elements("event");

        String json = Dom4JUtil.XML2Json(list);

        ResponseSendJson.sendJson(response, json);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
