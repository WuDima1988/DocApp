package com.wudima.docApp.settings;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class AppSettings {

//    private static final String path = System.getProperty("user.home") + File.separator + "Documents"+ File.separator + "DocFinder" + File.separator + "data" + File.separator + "accountsBase.bin";
//    private static final String photoPath = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocFinder" + File.separator + "photo";
//    private static File photoFolder = new File(photoPath);
//    private static File base = new File(path);
//    static ArrayList<Account> accountsList;


    private static final File configFile = new File("config.json");
    private String progName = "Default Name";
    private  boolean photoFit =true;
    private  boolean documentsFit =true;
//    private String mainLogo = "imgs/MainLogo.png";
    private File mainLogo = new File("src/main/resources/com/wudima/docApp/imgs/loadImg 2.png");

    public String getProgName() {
        return progName;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public boolean isPhotoFit() {
        return photoFit;
    }

    public void setPhotoFit(boolean photoFit) {
        this.photoFit = photoFit;
    }

    public boolean isDocumentsFit() {
        return documentsFit;
    }

    public void setDocumentsFit(boolean documentsFit) {
        this.documentsFit = documentsFit;
    }

    public File getMainLogo() {
        return mainLogo;
    }

    public void setMainLogo(File mainLogo) {
        this.mainLogo = mainLogo;
    }

    public static AppSettings load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("[AppSettings]-[load]:: config file loaded");
            return mapper.readValue(configFile, AppSettings.class);
        } catch (IOException e) {
            System.out.println("[AppSettings]-[load]:: Not find config file. Made new!");
            return new AppSettings();
        }
    }

    public void save(){

        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(configFile,this);
            System.out.println("[AppSettings]-[save]:: config file saved");
        } catch (IOException e) {
            System.out.println("[AppSettings]-[save]:: config file can not save!");
           e.printStackTrace();
        }
    }
}
