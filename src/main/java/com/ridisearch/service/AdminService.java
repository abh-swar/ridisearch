package com.ridisearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/18/13
 * Time: 10:44 PM
 */
public class AdminService {
    @Autowired
    JdbcTemplate jdbcTemplate;
}
