package com.wudima.docApp.account;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Account {

    private Long id;
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

    }

    public Account(String name, String surname, String birthPlace, String sex, String docNumber, Long idNumber, String docType, LocalDate birthDate, File documentFirstPage, File documentSecondPage, File photo) {
        this.name = name;
        this.surname = surname;
        this.birthPlace = birthPlace;
        this.sex = sex;
        this.docNumber = docNumber;
        this.idNumber = idNumber;
        this.docType = docType;
        this.birthDate = birthDate;
        this.documentFirstPage = documentFirstPage;
        this.documentSecondPage = documentSecondPage;
        this.photo = photo;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;

        return Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(surname, account.surname) && Objects.equals(birthPlace, account.birthPlace) && Objects.equals(sex, account.sex) && Objects.equals(docNumber, account.docNumber) && Objects.equals(idNumber, account.idNumber) && Objects.equals(docType, account.docType) && Objects.equals(birthDate, account.birthDate) && Objects.equals(documentFirstPage, account.documentFirstPage) && Objects.equals(documentSecondPage, account.documentSecondPage) && Objects.equals(photo, account.photo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(surname);
        result = 31 * result + Objects.hashCode(birthPlace);
        result = 31 * result + Objects.hashCode(sex);
        result = 31 * result + Objects.hashCode(docNumber);
        result = 31 * result + Objects.hashCode(idNumber);
        result = 31 * result + Objects.hashCode(docType);
        result = 31 * result + Objects.hashCode(birthDate);
        result = 31 * result + Objects.hashCode(documentFirstPage);
        result = 31 * result + Objects.hashCode(documentSecondPage);
        result = 31 * result + Objects.hashCode(photo);
        return result;
    }
}
