package com.example.CRUD_Demo.DTO;

import jakarta.validation.constraints.*;

public class CreateRequestDTO {
    @NotBlank(message = "Name can not be empty,null or blank")
    @Size(min = 2, max = 50, message = "Student name should be greater than 2 or less than 50 characters")
    private String name;

    @Min(value = 18)
    @Max(value = 60)//validations
    @NotNull(message = "Age must be 18+ and not null")
    private int age;

    @NotEmpty
    private String mob;

    private String sub;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
