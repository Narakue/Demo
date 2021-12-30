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
public class StudentSubject{
    private String studentId;
    private String name;
    private String sex;
    private int phone;
    private int qq;
    private int status;
    private int subjectId;
    private String subjectName;
    private String teacherName;
    private String url;
}
