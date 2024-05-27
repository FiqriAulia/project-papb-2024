package com.example.tambahtemanversi2.friendrequest;

import java.util.Map;

public class FriendRequestPro6 {
    private String id;
    private String name;
    private String profileImageUrl;
    private Map<String, Boolean> keywords;
    public FriendRequestPro6() {
    }

    public FriendRequestPro6(String id, String name, String profileImageUrl, Map<String, Boolean> keywords) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.keywords = keywords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Map<String, Boolean> getKeywords() {
        return keywords;
    }

    public void setKeywords(Map<String, Boolean> keywords) {
        this.keywords = keywords;
    }
}
