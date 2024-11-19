package Servlety;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "samochform", value = "/samoch-form")
public class SamochodFormatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        resp.getWriter().write("<html><body>" +
                "<form action='szuk-samoch' method='post'>" +
                "Type of Car: <input type='text' name='typ' />" +
                "<input type='submit' value='Search' />" +
                "</form>" +
                "</body></html>");
    }
}
