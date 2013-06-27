package com.ridisearch.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/27/13
 * Time: 7:20 PM
 */
public class SearchHits {
    private String itemId;
    private String content;
    private String uploadedBy;
    private String fileName;
    private int hitsCount;


    public String getItemId() {
        return itemId;
    }

    public SearchHits setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SearchHits setContent(String content) {
        this.content = content;
        return this;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public SearchHits setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public SearchHits setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public int getHitsCount() {
        return hitsCount;
    }

    public SearchHits setHitsCount(int hitsCount) {
        this.hitsCount = hitsCount;
        return this;
    }
}
