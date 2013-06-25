package com.ridisearch.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/25/13
 * Time: 9:36 PM
 */
public class MultipartFileUploadBean {
    private List<MultipartFile> files;

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }
}
