package com.wudima.docApp.settings;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class AppSettings {



    private  final String photoPath = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocApp" + File.separator + "photo";
    private  final String logoPath = System.getProperty("user.home") +File.separator + "Documents"+ File.separator + "DocApp" + File.separator + "logo";

    private static final File configFile = new File("config.json");
    private String progName = "Default Name";
    private  boolean photoFit =true;
    private  boolean documentsFit =true;

    private String defaultLogo = "/com/wudima/docApp/imgs/loadImg 2.png";
    private String mainLogo ;
    private String noPhotoImg = "/com/wudima/docApp/imgs/noPhotoImg.jpg";
    private String noDocImg = "/com/wudima/docApp/imgs/noDocImg.jpg";


    public String getPhotoPath() {
        return photoPath;
    }

    public String getLogoPath() {
        return logoPath;
    }

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

    public String getDefaultLogo() {
        return defaultLogo;
    }

    public String getMainLogo() {
        return mainLogo;
    }

    public String getNoPhotoImg() {
        return noPhotoImg;
    }

    public String getNoDocImg() {
        return noDocImg;
    }

    public void setMainLogo(String mainLogo) {
        this.mainLogo = mainLogo;
    }

    public static AppSettings load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("[AppSettings]-[load]:: config file loaded");
            System.out.println("Config path:"+configFile.getAbsolutePath());
            return mapper.readValue(configFile, AppSettings.class);
        } catch (IOException e) {
            System.out.println("[AppSettings]-[load]:: Not find config file. Made new!");
            System.out.println("Config path:"+configFile.getAbsolutePath());
            e.printStackTrace();
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
