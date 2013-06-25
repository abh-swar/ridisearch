package com.ridisearch.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/16/13
 * Time: 4:39 PM
 */
public class Role {
    private Long id;
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
