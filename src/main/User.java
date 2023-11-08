package main;

import fileio.input.UserInput;

public class User {
    private final String username;
    private final int age;
    private final String city;

    public User(final UserInput input) {
        username = input.getUsername();
        age = input.getAge();
        city = input.getCity();
    }
}
