package com.example.demo.utils.observer;

import java.sql.SQLException;

public interface Observer {
    void update() throws SQLException;
}