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
public class Subject {
    private int id;
    private String name;
    private String basicRequirement;
    private String researchObjective;
    private String reference;
    private int amount;
    private int selected;
    private int passed;
    private String teacherId;
    private String url;
    private int version;

    public Subject(int id, String name, String basicRequirement, String researchObjective, String reference, int amount, String url) {
        this.id = id;
        this.name = name;
        this.basicRequirement = basicRequirement;
        this.researchObjective = researchObjective;
        this.reference = reference;
        this.amount = amount;
        this.url = url;
    }

    public Subject(String name, String basicRequirement, String researchObjective, String reference, int amount, int selected, int passed, String teacherId, String url) {
        this.name = name;
        this.basicRequirement = basicRequirement;
        this.researchObjective = researchObjective;
        this.reference = reference;
        this.amount = amount;
        this.selected = selected;
        this.passed = passed;
        this.teacherId = teacherId;
        this.url = url;
    }
}
