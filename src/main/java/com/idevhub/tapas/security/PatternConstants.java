package com.idevhub.tapas.security;

public class PatternConstants {
    public static final String PHONE_NUMBER = "^\\+[\\d]{9,15}";
    public static final String NUMBERS = "^["
        + Ranges.NUMBERS
        + "]*$";
    public static final String LATIN_TEXT = "^["
        + Ranges.NUMBERS
        + Ranges.LATIN_ALPHABET
        + Ranges.SPLITTER_FOR_WORDS
        + Ranges.SPLITTER_MARKS
        + "]*$";
    public static final String CYRILLIC_TEXT = "^["
        + Ranges.CYRILLIC_ALPHABET
        + Ranges.SPLITTER_FOR_WORDS
        + Ranges.SPLITTER_MARKS
        + "]*$";
    public static final String CYRILLIC_TEXT_AND_NUMBERS = "^["
        + Ranges.NUMBERS
        + Ranges.CYRILLIC_ALPHABET
        + Ranges.SPLITTER_FOR_WORDS
        + Ranges.SPLITTER_MARKS
        + "]*$";
    public static final String LATIN_AND_NUMBERS = "^["
        + Ranges.NUMBERS
        + Ranges.LATIN_ALPHABET
        + "]*$";
    public static final String CYRILLIC_AND_NUMBERS = "^["
        + Ranges.NUMBERS
        + Ranges.CYRILLIC_ALPHABET
        + "]*$";
    public static final String TEXT = "^["
        + Ranges.LATIN_ALPHABET
        + Ranges.CYRILLIC_ALPHABET
        + Ranges.SPLITTER_FOR_WORDS
        + Ranges.SPLITTER_MARKS
        + "]*$";
    public static final String TEXT_FOR_NAMES = "^["
        + Ranges.LATIN_ALPHABET
        + Ranges.CYRILLIC_ALPHABET
        + Ranges.SPLITTER_FOR_WORDS
        + "]*$";

    public static class Ranges {
        public static final String NUMBERS = "0-9";
        public static final String LATIN_ALPHABET = "a-zA-Z";
        public static final String CYRILLIC_ALPHABET = "ЁёА-Яа-яієїґІЄЇҐ";
        public static final String SPLITTER_MARKS = "!'\"(),.\\-/:;?«»";
        public static final String SPLITTER_FOR_WORDS = " '\\-_";
    }
}
