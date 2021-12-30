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
public class FilePojo {
    private int id;
    private String teacherId;
    private String studentId;
    private Date date;
    private String url;
}
