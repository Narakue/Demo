package com.example.projectdemo.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.example.projectdemo.mapper.StudentMapper;
import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.StudentSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zero
 */
@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public String studentLogin(String id, String password, String remember) {
        Student student = studentMapper.studentLogin(id, password);
        if (student != null) {
            if (remember != null) {
                StpUtil.login(id, new SaLoginModel().setTimeout(60 * 60 * 24 * 7));
            } else {
                StpUtil.login(id);
            }
            StpUtil.getSession().set("user", student);
            return student.getName();
        } else {
            return "fail";
        }
    }

    public String changePassword(String password, String newPassword) {
        Student student = (Student) StpUtil.getSession().get("user");
        boolean jud = studentMapper.changePassword(student.getId(), password, newPassword);
        return String.valueOf(jud);
    }

    public List<Student> queryAll() {
        return studentMapper.queryAll();
    }

    public Student queryById(String id) {
        return studentMapper.queryById(id);
    }

    public boolean selectSubject(String id) {
        return studentMapper.selectSubject(id);
    }

    public boolean agreeStudent(String id) {
        return studentMapper.agreeStudent(id);
    }

    public boolean refuseStudent(String id) {
        return studentMapper.refuseStudent(id);
    }

    public List<StudentSubject> getSelectedMessage(String studentId) {
        return studentMapper.getSelectedMessage(studentId);
    }

    public boolean cancelSubject(String id) {
        return studentMapper.cancelSubject(id);
    }
}
