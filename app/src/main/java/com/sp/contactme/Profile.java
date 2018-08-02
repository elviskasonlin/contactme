package com.sp.contactme;

public class Profile {
    private String profileName;
    private String data;

    public void setProfile(String profileName, String data) {
        this.profileName = profileName;
        this.data = data;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getProfileData() {
        return data;
    }
}
