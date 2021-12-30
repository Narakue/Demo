package com.example.projectdemo.config;

import java.util.ArrayList;
import java.util.List;

import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.Teacher;
import com.example.projectdemo.service.StudentService;
import com.example.projectdemo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.dev33.satoken.stp.StpInterface;

/**
 * 自定义权限验证接口扩展
 * @author Zero
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = new ArrayList<String>();
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<String>();
        // 添加学生角色
        List<Student> students = studentService.queryAll();
        for (Student student : students) {
            if (loginId.equals(student.getId())) {
                list.add("student");
            }
        }
        // 添加教师角色
        List<Teacher> teachers = teacherService.queryAll();
        for (Teacher teacher : teachers) {
            if (loginId.equals(teacher.getId())) {
                list.add("teacher");
            }
        }
        return list;
    }

}

