package org.example.app.Enums;

public enum EBookAttribute {
    ID("Идентификатор книги"),
    AUTHOR("Автор книги"),
    TITLE("Название книги"),
    SIZE("Количество страниц");

    private String value;

    EBookAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
