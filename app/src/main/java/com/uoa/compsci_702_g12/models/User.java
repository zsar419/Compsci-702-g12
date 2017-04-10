package com.uoa.compsci_702_g12.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by z on 31/03/17.
 */

public class User {

    @SerializedName("UserId")
    private int userId;
    @SerializedName("Option")
    private int option;         // international, research or tutoring -> [ 1, 2, 4 ]
    @SerializedName("Role")
    private int role;           // volunteer or newbie -> [ 1001, 1002 ]
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("Gender")
    private int gender;         // male or female -> [ 1, 2 ]
    @SerializedName("Email")
    private String email;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("Ethnicity")
    private String ethnicity;
    @SerializedName("Languages")
    private String languages;
    @SerializedName("Details")
    private String details;
    @SerializedName("GradType")
    private int gradType;       // undergrad or postgrad -> [ 0, 1 ]

    // Incorporate preferences?

    public User(int option, int role, String firstName, String lastName, int gender, String email,
                String phoneNumber, String ethnicity, String languages, String details, int gradType) {
        this.option = option;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ethnicity = ethnicity;
        this.languages = languages;
        this.details = details;
        this.gradType = gradType;
    }

    public int getUserId() { return userId; }
    public int getRole() { return role; }
    public int getOption() { return option; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getGender() { return gender; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEthnicity(){ return ethnicity; }
    public String getLanguages() { return languages; }
    public String getDetails() { return details; }
    public int getGradType() { return gradType; }

}
