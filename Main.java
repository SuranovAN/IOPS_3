package IOPS_FILES.Load;

import IOPS_FILES.Save.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\Games\\savegames\\zipSaves.zip";
        String path2 = "D:\\Games\\savegames\\";
        openZip(path, path2);
        System.out.println(openProgress(path2).toString());
    }

    static void openZip(String pathToFile, String pathToFolder) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToFile))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(pathToFolder + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static GameProgress openProgress(String pathToFile) {
        GameProgress gameProgress = null;
        try (FileInputStream fos = new FileInputStream(pathToFile + "packed_save1.dat");
             ObjectInputStream oos = new ObjectInputStream(fos)) {
            try {
                gameProgress = (GameProgress) oos.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }
}
