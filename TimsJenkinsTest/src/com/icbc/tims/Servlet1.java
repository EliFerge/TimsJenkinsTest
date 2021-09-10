package com.icbc.tims;

import cn.hutool.core.date.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/test1")
public class Servlet1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String dateStr = "8888-05-05";
        Date date = DateUtil.parse(dateStr);
        String format = DateUtil.format(date, "yyyy/MM/dd");

//        new Thread(() -> {
//            List<String> list = new ArrayList<>();
//            while (true) {
//                list.add(new String("你弄啊是个浓缩的内功的giaso"));
//            }
//        }).start();

        response.getWriter().write(format);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
