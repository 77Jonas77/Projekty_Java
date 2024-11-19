package Db;

import Model_Samochod.Samochod;

import java.util.List;

public interface ISamochodDb {
    List<Samochod> getSamochodTyp(String typ);
}
