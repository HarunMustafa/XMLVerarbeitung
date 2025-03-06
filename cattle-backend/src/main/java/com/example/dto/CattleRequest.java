package com.example.dto;

public class CattleRequest {

    public String name;
    public String description;
    
    // Standardkonstruktor (wird für die JSON-Deserialisierung benötigt)
    public CattleRequest() {
    }
    
    // Konstruktor mit Parametern für einfacheres Testen
    public CattleRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
