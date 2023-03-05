package com.example.demo.utils.observer;

import com.example.demo.utils.Event;

public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}
