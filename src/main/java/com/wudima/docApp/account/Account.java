package com.wudima.docApp.account;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class Account implements Serializable {


    private static final long serialVersionUID = 5454181708532992731L;
    private int id;
    private String name;
    private String surname;
    private String birthPlace;
    private String sex;
    private String docNumber;
    private Long idNumber;
    private String docType;
    private LocalDate birthDate;
    private File documentFirstPage;
    private File documentSecondPage;
    private File photo;
    private String docBase;

    public Account() {
        this.id = new Random().nextInt();
    }

    public Account(String name, String surname) {
        this.id = new Random().nextInt();
        this.name = name;
        this.surname = surname;
    }

    public String getDocBase() {

        if(photo!=null && documentFirstPage!=null && documentSecondPage!=null){
            return docBase="all docs";
        } else if (photo==null&&documentFirstPage!=null && documentSecondPage!=null) {
            return docBase = "no photo";
        }else if (photo!=null&&documentFirstPage==null && documentSecondPage==null) {
            return docBase = "only photo";
        }else if (photo!=null&&documentFirstPage!=null && documentSecondPage==null) {
            return docBase = "photo/first page";
        }else
        return docBase= "no docs";
    }

    public int getId() {
        return id;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public File getDocumentFirstPage() {
        return documentFirstPage;
    }

    public void setDocumentFirstPage(File documentFirstPage) {
        this.documentFirstPage = documentFirstPage;
    }

    public File getDocumentSecondPage() {
        return documentSecondPage;
    }

    public void setDocumentSecondPage(File documentSecondPage) {
        this.documentSecondPage = documentSecondPage;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }





    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + birthPlace.hashCode();
        result = 31 * result + birthDate.hashCode();
        return result;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;

        return id == account.id && name.equals(account.name) && surname.equals(account.surname) && birthPlace.equals(account.birthPlace) && birthDate.equals(account.birthDate);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", sex='" + sex + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", idNumber=" + idNumber +
                ", docType='" + docType + '\'' +
                ", birthDate=" + birthDate +
                ", docBase='" + docBase + '\'' +
                '}';
    }
}
