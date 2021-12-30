package com.example.projectdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zero
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseSubject {
    private int id;
    private int subjectId;
    private String studentId;
    private Date chooseTime;
    private Date confirmTime;
    private int status;

    public ChooseSubject(int subjectId, String studentId, Date chooseTime, int status) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.chooseTime = chooseTime;
        this.status = status;
    }
}
