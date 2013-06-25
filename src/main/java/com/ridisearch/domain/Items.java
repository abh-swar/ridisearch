package com.ridisearch.domain;


import java.sql.Blob;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/25/13
 * Time: 2:50 PM
 */
public class Items {
    private long id;
    private String itemName;
    private String storedLocation;
    private String itemType;
    private boolean isPrivate;
    private User user;
    private byte[] file;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoredLocation() {
        return storedLocation;
    }

    public void setStoredLocation(String storedLocation) {
        this.storedLocation = storedLocation;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
