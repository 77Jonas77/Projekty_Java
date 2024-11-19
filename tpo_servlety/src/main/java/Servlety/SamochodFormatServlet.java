package Servlety;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "samochform", value = "/samoch-form")
public class SamochodFormatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        resp.getWriter().write("<html><body>" +
                "<form action='szuksamoch' method='post'>" +
                "Type of Car: <input type='text' name='typeOfCar' />" +
                "<input type='submit' value='Search' />" +
                "</form>" +
                "</body></html>");
    }
}
