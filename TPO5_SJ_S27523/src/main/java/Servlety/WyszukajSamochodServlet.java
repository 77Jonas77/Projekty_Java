package Servlety;

import Db.ISamochodDb;
import Db.SamochodDb;
import Model_Samochod.Samochod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "szuksamoch", value = "/szuk-samoch")
public class WyszukajSamochodServlet extends HttpServlet {

    private final ISamochodDb samochodDb = new SamochodDb();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typ = req.getParameter("typ");

        System.out.println("Received parameter 'typ': " + typ);

        if (typ == null || typ.isEmpty()) {
            req.setAttribute("error", "Please provide a type of car.");
            req.getRequestDispatcher("/samoch-form").forward(req, resp);
            return;
        }

        List<Samochod> samochody = samochodDb.getSamochodTyp(typ);
        req.setAttribute("samochody", samochody);
        req.getRequestDispatcher("/rezultat-samoch").forward(req, resp);
    }
}
