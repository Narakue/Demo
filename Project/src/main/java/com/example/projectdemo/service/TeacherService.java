package com.example.projectdemo.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.example.projectdemo.mapper.TeacherMapper;
import com.example.projectdemo.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zero
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ChooseSubjectService chooseSubjectService;
    @Autowired
    private StudentService studentService;

    public String teacherLogin(String id, String password, String remember) {
        Teacher teacher = teacherMapper.teacherLogin(id, password);
        if (teacher != null) {
            if (remember != null) {
                StpUtil.login(id, new SaLoginModel().setTimeout(60 * 60 * 24 * 7));
            }
            StpUtil.login(id);
            StpUtil.getSession().set("user", teacher);
            return teacher.getName();
        } else {
            return "fail";
        }
    }

    public List<Teacher> queryAll() {
        return teacherMapper.queryAll();
    }

    public String changePassword(String password, String newPassword) {
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        boolean jud = teacherMapper.changePassword(teacher.getId(), password, newPassword);
        return String.valueOf(jud);
    }

    public boolean selectSubject(String id) {
        return teacherMapper.selectSubject(id);
    }

    public boolean agreeStudent(int subjectId, String studentId) {
        boolean jud1 = subjectService.agreeStudent(subjectId);
        boolean jud2 = studentService.agreeStudent(studentId);
        boolean jud3 = chooseSubjectService.agreeStudent(subjectId, studentId);
        return jud1 && jud2 && jud3;
    }

    public boolean refuseStudent(int subjectId, String studentId) {
        boolean jud1 = subjectService.refuseStudent(subjectId);
        boolean jud2 = studentService.refuseStudent(studentId);
        boolean jud3 = chooseSubjectService.refuseStudent(subjectId, studentId);
        return jud1 && jud2 && jud3;
    }

    public Teacher queryById(String id) {
        return teacherMapper.queryById(id);
    }

    public Teacher queryBySubjectId(int id) {
        return teacherMapper.queryBySubjectId(id);
    }
}
