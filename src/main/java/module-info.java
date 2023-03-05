module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.dto;
    exports com.example.demo.controllers;
    exports com.example.demo.utils;
    exports com.example.demo.utils.observer;
    exports com.example.demo.entities;
    exports com.example.demo.services;
    exports com.example.demo.repositories;
}