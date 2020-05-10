package app.example.entity;

import java.util.List;

public class Employee {
    public String name;
    public String city;
    public List<String> cars;
    public String job;

    public Employee(String name, String city, List<String> cars, String job) {
        this.name = name;
        this.city = city;
        this.cars = cars;
        this.job = job;
    }
}
