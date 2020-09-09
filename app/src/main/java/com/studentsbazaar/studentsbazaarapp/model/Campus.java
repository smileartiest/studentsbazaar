package com.studentsbazaar.studentsbazaarapp.model;

public class Campus {

    private String Domain;
    private String Package;
    private String Company_Name;
    private String No_of_Students;
    private String Date;
    private String College_Name;

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public String getNo_of_Students() {
        return No_of_Students;
    }

    public void setNo_of_Students(String no_of_Students) {
        No_of_Students = no_of_Students;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCollege_Name() {
        return College_Name;
    }

    public void setCollege_Name(String college_Name) {
        College_Name = college_Name;
    }

    public Campus(String domain, String aPackage, String company_Name, String no_of_Students, String date, String college_Name) {
        Domain = domain;
        Package = aPackage;
        Company_Name = company_Name;
        No_of_Students = no_of_Students;
        Date = date;
        College_Name = college_Name;
    }
}
