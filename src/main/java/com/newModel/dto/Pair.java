package com.newModel.dto;

/**
 *
 * DTO for passing content of dictionary record from EndCoach2 to Controller
 *
 */
public class Pair {

    // show this string first
    private String first;

    // and then show this one
    private String second;

    public Pair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                '}';
    }
}
