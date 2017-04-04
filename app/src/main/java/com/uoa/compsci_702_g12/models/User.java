package com.uoa.compsci_702_g12.models;

/**
 * Created by z on 31/03/17.
 */

public class User {

    private int option;         // international, research or tutoring -> [ 1, 2, 3 ]
    private int type;           // volunteer or newbie -> [ 1001, 1002 ]
    private String firstName;
    private String lastName;
    private int gender;         // male or female -> [ 1, 2 ]
    private String email;
    private String phoneNumber;
    private String ethnicity;
    private String languages;
    private String details;
    private int gradType;       // undergrad or postgrad -> [ 0, 1 ]

    // Incorporate preferences?

    public User(int option, int type, String firstName, String lastName, int gender, String email,
                String phoneNumber, String ethnicity, String languages, String details, int gradType) {
        this.option = option;
        this.type = type;
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

    public int getType() { return type; }
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
