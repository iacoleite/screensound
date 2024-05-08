package com.iaco.screensound.models;

public enum Categoria {
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String categoriaDigitada;

    Categoria(String categoriaDigitada) {
        this.categoriaDigitada = categoriaDigitada;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaDigitada.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + text);
    }

}
