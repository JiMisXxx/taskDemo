package taskRun;

import java.io.FileWriter;
import java.io.IOException;

public class creatBat {
    static Runtime run = Runtime.getRuntime();
    public static void executeBat(String file,String runfileName) {
        try {
            String pan = file.substring(0, 2);
            FileWriter writer = new FileWriter(file + "÷¥––√¸¡Ó.bat");
            writer.write("@echo   off ");
            writer.write("\r\n ");
            writer.write(pan);
            writer.write("\r\n ");
            writer.write("cd " + file);
            writer.write("\r\n ");
            writer.write("start cmd /k \"" + runfileName + "\"");
            writer.write("\r\n ");
            writer.write("@echo   on ");
            writer.close();
            Process var4 = run.exec(file + "÷¥––√¸¡Ó.bat");
        } catch (IOException e)  {
            e.printStackTrace();
        } // TODO Auto-generated catch block


    }

}
