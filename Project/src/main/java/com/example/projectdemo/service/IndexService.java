package com.example.projectdemo.service;

import cn.dev33.satoken.stp.StpUtil;
import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.StudentSubject;
import com.example.projectdemo.pojo.Teacher;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zero
 */
@Service
public class IndexService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    private Gson gson = new Gson();

    public String getName() {
        Object user = StpUtil.getSession().get("user");
        if (user instanceof Student) {
            Student student = (Student) user;
            return student.getName();
        } else {
            Teacher teacher = (Teacher) user;
            return teacher.getName();
        }
    }

    public String message() {
        Object user = StpUtil.getSession().get("user");
        StringBuilder stringBuilder = new StringBuilder();
        if (user == null) {
            return "未登录";
        }
        if (user instanceof Student) {
            Student student = (Student) user;
            Student stu= studentService.queryById(student.getId());
            String json = gson.toJson(stu);
            stringBuilder.append(json);
//            if (stu.getStatus() == 2) {
//                // 学生已选课
//                List<StudentSubject> message = studentService.getSelectedMessage(stu.getId());
//                for (StudentSubject studentSubject : message) {
//                    stringBuilder.append(studentSubject.toString()).append("\n");
//                }
//            }
        } else {
            Teacher teacher = (Teacher) user;
            Teacher t = teacherService.queryById(teacher.getId());
            String json = gson.toJson(t);
            stringBuilder.append(json);
//            List<String> roleList = StpUtil.getRoleList();
//            for (String s : roleList) {
//                stringBuilder.append(s);
//            }
        }
        return stringBuilder.toString();
    }
}
