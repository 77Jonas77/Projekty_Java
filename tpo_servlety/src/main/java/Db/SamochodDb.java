package Db;

import Model_Samochod.Samochod;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

            String sql = "SELECT * FROM samochody WHERE typ = ?";
            PreparedStatement preCompiled = conn.prepareStatement(sql);
            preCompiled.setString(1, typ);

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
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC!: " + e.getMessage());
        }
        return result;
    }

}

