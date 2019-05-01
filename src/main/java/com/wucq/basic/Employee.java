package com.wucq.basic;

import lombok.Getter;
import lombok.Setter;

public class Employee {
    @Getter
    @Setter
    private String key;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String department;

    public Employee(String key, String dept, String id) {
        this.key = key;
        this.department = dept;
        this.id = id;
    }

    @Override
    public String toString() {
        return key + department + id;
    }

    public String getKey() {
        return key;
    }

    public String getDepartment() {
        return department;
    }

    public String getID() {
        return id;
    }
}