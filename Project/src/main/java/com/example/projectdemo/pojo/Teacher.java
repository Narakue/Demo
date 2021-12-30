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
public class Teacher {
    private String id;
    private String password;
    private String name;
    private int phone;
    private int qq;
}
