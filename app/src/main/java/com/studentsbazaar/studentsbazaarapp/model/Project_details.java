package com.studentsbazaar.studentsbazaarapp.model;

public class Project_details {

    private String Event_Id;
    private String Event_Coordinator;
    private String Event_Title;
    private String Event_Type;
    private String Event_Name;
    private String Event_Start_Date;
    private String Event_End_Date;
    private String Event_Instagram;
    private String Conducted_By;
    private String Degree;
    private String Dept;
    private String College_Address;
    private String College_District;
    private String College_State;
    private String Event_Organiser;
    private String Event_Details;
    private String Event_Discription;
    private String Event_Website;
    private String College_Website;
    private String Contact_Person1_Name;
    private String Contact_Person1_No;
    private String Contact_Person2_Name;
    private String Contact_Person2_No;
    private String Poster;
    private String Entry_Fees;
    private String Accepted;
    private String Event_Lat;
    private String Event_Long;
    private String Event_guest;
    private String Event_pro_nites;
    private String Event_accomodations;
    private String Event_how_to_reach;
    private String Event_sponsors;
    private String Last_date_registration;
    private String Event_status;
    private String Created_Date;
    private String Modified_Date;
    private String Comments;

    public Project_details(String event_Id, String event_Coordinator, String event_Title, String event_Type, String event_Name, String event_Start_Date, String event_End_Date, String college_Name, String degree, String dept, String college_Address, String college_District, String college_State, String event_Organiser, String event_Details, String event_Discription, String event_Website, String college_Website, String contact_Person1_Name, String contact_Person1_No, String contact_Person2_Name, String contact_Person2_No, String poster, String entry_Fees, String accepted, String event_Lat, String event_Long, String event_guest, String event_pro_nites, String event_accomodations, String event_how_to_reach, String event_sponsors, String last_date_registration, String event_status, String created_Date, String modified_Date, String comments) {
        Event_Id = event_Id;
        Event_Coordinator = event_Coordinator;
        Event_Title = event_Title;
        Event_Type = event_Type;
        Event_Name = event_Name;
        Event_Start_Date = event_Start_Date;
        Event_End_Date = event_End_Date;
        Degree = degree;
        Dept = dept;
        College_Address = college_Address;
        College_District = college_District;
        College_State = college_State;
        Event_Organiser = event_Organiser;
        Event_Details = event_Details;
        Event_Discription = event_Discription;
        Event_Website = event_Website;
        College_Website = college_Website;
        Contact_Person1_Name = contact_Person1_Name;
        Contact_Person1_No = contact_Person1_No;
        Contact_Person2_Name = contact_Person2_Name;
        Contact_Person2_No = contact_Person2_No;
        Poster = poster;
        Entry_Fees = entry_Fees;
        Accepted = accepted;
        Event_Lat = event_Lat;
        Event_Long = event_Long;
        Event_guest = event_guest;
        Event_pro_nites = event_pro_nites;
        Event_accomodations = event_accomodations;
        Event_how_to_reach = event_how_to_reach;
        Event_sponsors = event_sponsors;
        Last_date_registration = last_date_registration;
        Event_status = event_status;
        Created_Date = created_Date;
        Modified_Date = modified_Date;
        Comments = comments;
    }

    // Getter Methods
    public String getEvent_Instagram() {
        return Event_Instagram;
    }

    public void setEvent_Instagram(String event_Instagram) {
        Event_Instagram = event_Instagram;
    }


    public String getConducted_By() {
        return Conducted_By;
    }

    public void setConducted_By(String conducted_By) {
        Conducted_By = conducted_By;
    }

    public String getEvent_Id() {
        return Event_Id;
    }

    public String getEvent_Coordinator() {
        return Event_Coordinator;
    }

    public String getEvent_Title() {
        return Event_Title;
    }

    public String getEvent_Type() {
        return Event_Type;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public String getEvent_Start_Date() {
        return Event_Start_Date;
    }

    public String getEvent_End_Date() {
        return Event_End_Date;
    }


    public String getDegree() {
        return Degree;
    }

    public String getDept() {
        return Dept;
    }

    public String getCollege_Address() {
        return College_Address;
    }

    public String getCollege_District() {
        return College_District;
    }

    public String getCollege_State() {
        return College_State;
    }

    public String getEvent_Organiser() {
        return Event_Organiser;
    }

    public String getEvent_Details() {
        return Event_Details;
    }

    public String getEvent_Discription() {
        return Event_Discription;
    }

    public String getEvent_Website() {
        return Event_Website;
    }

    public String getCollege_Website() {
        return College_Website;
    }

    public String getContact_Person1_Name() {
        return Contact_Person1_Name;
    }

    public String getContact_Person1_No() {
        return Contact_Person1_No;
    }

    public String getContact_Person2_Name() {
        return Contact_Person2_Name;
    }

    public String getContact_Person2_No() {
        return Contact_Person2_No;
    }

    public String getPoster() {
        return Poster;
    }

    public String getEntry_Fees() {
        return Entry_Fees;
    }

    public String getAccepted() {
        return Accepted;
    }

    public String getEvent_Lat() {
        return Event_Lat;
    }

    public String getEvent_Long() {
        return Event_Long;
    }

    public String getEvent_guest() {
        return Event_guest;
    }

    public String getEvent_pro_nites() {
        return Event_pro_nites;
    }

    public String getEvent_accomodations() {
        return Event_accomodations;
    }

    public String getEvent_how_to_reach() {
        return Event_how_to_reach;
    }

    public String getEvent_sponsors() {
        return Event_sponsors;
    }

    public String getLast_date_registration() {
        return Last_date_registration;
    }

    public String getEvent_status() {
        return Event_status;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    public String getModified_Date() {
        return Modified_Date;
    }

    public String getComments() {
        return Comments;
    }

    // Setter Methods

    public void setEvent_Id(String Event_Id) {
        this.Event_Id = Event_Id;
    }

    public void setEvent_Coordinator(String Event_Coordinator) {
        this.Event_Coordinator = Event_Coordinator;
    }

    public void setEvent_Title(String Event_Title) {
        this.Event_Title = Event_Title;
    }

    public void setEvent_Type(String Event_Type) {
        this.Event_Type = Event_Type;
    }

    public void setEvent_Name(String Event_Name) {
        this.Event_Name = Event_Name;
    }

    public void setEvent_Start_Date(String Event_Start_Date) {
        this.Event_Start_Date = Event_Start_Date;
    }

    public void setEvent_End_Date(String Event_End_Date) {
        this.Event_End_Date = Event_End_Date;
    }

    public void setDegree(String Degree) {
        this.Degree = Degree;
    }

    public void setDept(String Dept) {
        this.Dept = Dept;
    }

    public void setCollege_Address(String College_Address) {
        this.College_Address = College_Address;
    }

    public void setCollege_District(String College_District) {
        this.College_District = College_District;
    }

    public void setCollege_State(String College_State) {
        this.College_State = College_State;
    }

    public void setEvent_Organiser(String Event_Organiser) {
        this.Event_Organiser = Event_Organiser;
    }

    public void setEvent_Details(String Event_Details) {
        this.Event_Details = Event_Details;
    }

    public void setEvent_Discription(String Event_Discription) {
        this.Event_Discription = Event_Discription;
    }

    public void setEvent_Website(String Event_Website) {
        this.Event_Website = Event_Website;
    }

    public void setCollege_Website(String College_Website) {
        this.College_Website = College_Website;
    }

    public void setContact_Person1_Name(String Contact_Person1_Name) {
        this.Contact_Person1_Name = Contact_Person1_Name;
    }

    public void setContact_Person1_No(String Contact_Person1_No) {
        this.Contact_Person1_No = Contact_Person1_No;
    }

    public void setContact_Person2_Name(String Contact_Person2_Name) {
        this.Contact_Person2_Name = Contact_Person2_Name;
    }

    public void setContact_Person2_No(String Contact_Person2_No) {
        this.Contact_Person2_No = Contact_Person2_No;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public void setEntry_Fees(String Entry_Fees) {
        this.Entry_Fees = Entry_Fees;
    }

    public void setAccepted(String Accepted) {
        this.Accepted = Accepted;
    }

    public void setEvent_Lat(String Event_Lat) {
        this.Event_Lat = Event_Lat;
    }

    public void setEvent_Long(String Event_Long) {
        this.Event_Long = Event_Long;
    }

    public void setEvent_guest(String Event_guest) {
        this.Event_guest = Event_guest;
    }

    public void setEvent_pro_nites(String Event_pro_nites) {
        this.Event_pro_nites = Event_pro_nites;
    }

    public void setEvent_accomodations(String Event_accomodations) {
        this.Event_accomodations = Event_accomodations;
    }

    public void setEvent_how_to_reach(String Event_how_to_reach) {
        this.Event_how_to_reach = Event_how_to_reach;
    }

    public void setEvent_sponsors(String Event_sponsors) {
        this.Event_sponsors = Event_sponsors;
    }

    public void setLast_date_registration(String Last_date_registration) {
        this.Last_date_registration = Last_date_registration;
    }

    public void setEvent_status(String Event_status) {
        this.Event_status = Event_status;
    }

    public void setCreated_Date(String Created_Date) {
        this.Created_Date = Created_Date;
    }

    public void setModified_Date(String Modified_Date) {
        this.Modified_Date = Modified_Date;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }
}

