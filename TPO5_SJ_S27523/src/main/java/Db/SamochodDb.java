package Db;

import Model_Samochod.Samochod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SamochodDb implements ISamochodDb {

    public static void main(String[] args) {
        List<Samochod> cars = new SamochodDb().getSamochodTyp("Sedan");
        System.out.println(cars);
    }

    @Override
    public List<Samochod> getSamochodTyp(String typ) {
        List<Samochod> result = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/myTPOdb";
            Connection conn = DriverManager.getConnection(url, "root", "jns123");

            String sql = "SELECT * FROM samochody WHERE TYP = ?";
            PreparedStatement preCompiled = conn.prepareStatement(sql);
            preCompiled.setString(1, typ);

            System.out.println("Executing query: " + preCompiled.toString());
            ResultSet resultSet = preCompiled.executeQuery();

            while (resultSet.next()) {
                Samochod samochod = new Samochod(
                        resultSet.getInt("id"),
                        resultSet.getString("marka"),
                        resultSet.getString("typ"),
                        resultSet.getInt("rokProdukcji"));
                result.add(samochod);
            }

            resultSet.close();
            preCompiled.close();
            conn.close();

            return result;

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Exception: " + e.getMessage());
        }
        return result;
    }
}
