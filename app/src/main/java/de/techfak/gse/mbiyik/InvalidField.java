package de.techfak.gse.mbiyik;

public class InvalidField extends Exception {
    public InvalidField() { }
    public InvalidField(String message) {
        super(message);
    }
}
