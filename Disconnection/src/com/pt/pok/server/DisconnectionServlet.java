package com.pt.pok.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DisconnectionServlet
    extends HttpServlet {

    static String ga = "<script type=\"text/javascript\"> var _gaq = _gaq || [];_gaq.push(['_setAccount', 'UA-28460709-1']);_gaq.push(['_trackPageview']);(function() {var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);})();</script>";
    static String click = "<script>window.onload = function(){  document.getElementById(\"click\").click();}</script>";

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");

        writer.println("<HTML><head>");
        writer.println(ga);
        if (req.getRequestURI().startsWith("/htcmda")) {
            writer.println(click);
        }

        writer.println("</head><BODY>");

        if (req.getRequestURI().startsWith("/htcmd")) {
            writer.print("<a id=\"click\" href=\"htcmd:connStats?");
            writer.print("ip=" + req.getRemoteAddr() + "&");
            writer.print("language=" + req.getHeader("Accept-Language") + "&");
            writer.print("userAgent=" + req.getHeader("User-Agent") + "&");
            writer.print("country=" + req.getHeader("X-AppEngine-Country") + "&");
            writer.print("region=" + req.getHeader("X-AppEngine-Region") + "&");
            writer.print("city=" + req.getHeader("X-AppEngine-City") + "&");
            writer.print("cityLatLong=" + req.getHeader("X-AppEngine-CityLatLong") + "\"");

            writer.print(">click here</a>");
        }

        if (req.getRequestURI().startsWith("/headers")) {
            writer.println("<table>");
            writer.println("<tr><td>URI:</td><td>" + req.getRequestURI() + "</td></tr>");
            writer.println("<tr><td>Date:</td><td>" + new Date() + "</td></tr>");
            writer.println("<tr><td>RemoteAddr:</td><td>" + req.getRemoteAddr() + "</td></tr>");
            writer.println("<tr><td>RemoteHost:</td><td>" + req.getRemoteHost() + "</td></tr>");
            writer.println("<tr><td>RemotePort:</td><td>" + req.getRemotePort() + "</td></tr>");
            writer.println("<tr><td>RemoteUser:</td><td>" + req.getRemoteUser() + "</td></tr>");

            Enumeration<?> headerNames = req.getHeaderNames();
            writer.println("<tr><td colspan=2>Hearers</td></tr>");
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                writer.println("<tr><td>" + headerName + ":</td><td>" + req.getHeader(headerName) + "</td></tr>");
            }

            writer.println("</table>");
        }

        if (req.getHeader("X-AppEngine-CityLatLong") != null && req.getRequestURI().equalsIgnoreCase("/location")) {
            String[] split = req.getHeader("X-AppEngine-CityLatLong").split(",");
            writer.println("<img src=\"http://maps.googleapis.com/maps/api/staticmap?markers=color:blue%7Clabel:O%7C" + split[0] + "," + split[1]
                           + "&amp;zoom=12&amp;size=400x400&amp;sensor=false\">");

        }

        writer.println("</body></html>");
    }
}
