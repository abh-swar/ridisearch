package com.ridisearch.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/27/13
 * Time: 3:03 PM
 */
public class DocumentInfo {
    private String itemId;
    private String content;
    private String uploadedBy;
    private String fileName;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
