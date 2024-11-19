package Servlety;

import Db.ISamochodDb;
import Db.SamochodDb;
import Model_Samochod.Samochod;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/szuksamoch")
public class WyszukajSamochodServlet extends HttpServlet {

    private final ISamochodDb samochodDb = new SamochodDb();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typ = req.getParameter("typ");
        List<Samochod> samochody = samochodDb.getSamochodTyp(typ);
        req.setAttribute("samochody", samochody);
        req.getRequestDispatcher("/wyniksamochwyszukiw").forward(req, resp);
    }
}