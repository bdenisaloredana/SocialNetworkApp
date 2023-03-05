package com.example.demo.utils.observer;
import com.example.demo.utils.Event;

import java.sql.SQLException;

public interface Observer {
    void update() throws SQLException;
}