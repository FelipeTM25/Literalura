package com.challengeLiteralura.literalura.model;

public enum Lenguaje {
    ESPANOL("es", "Español"),
    INGLES("en", "Ingles"),
    FRANCES("fr", "Francés"),
    ITALIANO("it", "Italiano"),
    PORTUGUES("pt", "Portugués"),
    ALEMAN("de", "Alemán"),
    RUSO("ru", "Ruso");

    private String codigoLenguaje;
    private String lenguajeEspanol;

    Lenguaje(String codigoLenguaje, String lenguajeEspanol) {
        this.codigoLenguaje = codigoLenguaje;
        this.lenguajeEspanol = lenguajeEspanol;
    }
    public static Lenguaje fromString(String lenguaje) {
        for (Lenguaje l : Lenguaje.values()) {
            if (l.codigoLenguaje.equalsIgnoreCase(lenguaje)) {
                return l;
            }
        }
        throw new IllegalArgumentException("No existe el lenguaje " + lenguaje);
    }

    public static Lenguaje fromEspanol(String lenguaje) {
        for (Lenguaje l : Lenguaje.values()) {
            if (l.lenguajeEspanol.equalsIgnoreCase(lenguaje)) {
                return l;
            }
        }
        throw new IllegalArgumentException("No existe el lenguaje " + lenguaje);
    }

}
