package com.example.projectdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zero
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String id;
    private String password;
    private String name;
    private String sex;
    private int phone;
    private int qq;
    private int status;

    @Override
    public String toString() {
        return "Student(id=" + id + ", name=" + name + ", sex=" + sex + ", phone=" + phone + ", qq=" + qq + ", status=" + status + ")";
    }
}
