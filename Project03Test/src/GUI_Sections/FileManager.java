package GUI_Sections;

import Panels.VBDPanel;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public FileManager() {
    }

    public static void saveDataToFile(ArrayList<VBDPanel> vbdPanels) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("data.bin")) {
            for (VBDPanel vbd : vbdPanels) {
                int smsSentCounter = vbd.getVbdThread().getSmsSentCounter();
                byte[] pdu = vbd.getVbdThread().getPdu();

                // Zapisz smsSentCounter jako 4 bajty
                for (int i = 0; i < 4; i++) {
                    byte b = (byte) (smsSentCounter >> (24 - i * 8));
                    fileOutputStream.write(b);
                }

                // Zapisz każdy bajt pdu
                for (byte b : pdu) {
                    fileOutputStream.write(b);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
