package Servlety;

import Model_Samochod.Samochod;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "rezultatsamoch", value = "/rezultat-samoch")
public class RezultatSamochodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Samochod> samochody = (List<Samochod>) req.getAttribute("samochody");

        resp.setContentType("text/html");
        StringBuilder htmlResponse = new StringBuilder("<html><body><table border='1'>");
        htmlResponse.append("<tr><th>Id</th><th>Marka Samochodu</th><th>Typ</th><th>Rok Produkcji</th></tr>");
        for (Samochod samochod : samochody) {
            htmlResponse.append("<tr>")
                    .append("<td>").append(samochod.getId()).append("</td>")
                    .append("<td>").append(samochod.getRokProdukcji()).append("</td>")
                    .append("<td>").append(samochod.getTyp()).append("</td>")
                    .append("<td>").append(samochod.getMarka()).append("</td>")
                    .append("</tr>");
        }
        htmlResponse.append("</table></body></html>");

        resp.getWriter().write(htmlResponse.toString());
    }
}