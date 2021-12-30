package com.example.projectdemo.service;

import com.example.projectdemo.mapper.ChooseSubjectMapper;
import com.example.projectdemo.pojo.ChooseSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Zero
 */
@Service
public class ChooseSubjectService {
    @Autowired
    private ChooseSubjectMapper chooseSubjectMapper;

    public boolean selectSubject(int subjectId, String studentId) {
        Date date = new Date();
        ChooseSubject chooseSubject = new ChooseSubject(subjectId, studentId, new Date(), 2);
        return chooseSubjectMapper.selectSubject(chooseSubject);
    }

    public boolean alreadySelected(String studentId) {
        ChooseSubject obj = chooseSubjectMapper.alreadySelected(studentId);
        return obj != null;
    }

    public boolean agreeStudent(int subjectId, String studentId) {
        ChooseSubject chooseSubject = new ChooseSubject();
        chooseSubject.setConfirmTime(new Date());
        chooseSubject.setSubjectId(subjectId);
        chooseSubject.setStudentId(studentId);
        return chooseSubjectMapper.agreeStudent(chooseSubject);
    }

    public boolean refuseStudent(int subjectId, String studentId) {
        ChooseSubject chooseSubject = new ChooseSubject();
        chooseSubject.setConfirmTime(new Date());
        chooseSubject.setSubjectId(subjectId);
        chooseSubject.setStudentId(studentId);
        return chooseSubjectMapper.refuseStudent(chooseSubject);
    }

    public boolean cancelSubject(String id) {
        return chooseSubjectMapper.cancelSubject(id);
    }
}
