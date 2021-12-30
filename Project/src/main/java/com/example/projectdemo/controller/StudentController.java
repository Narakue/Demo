package com.example.projectdemo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.StudentSubject;
import com.example.projectdemo.pojo.Subject;
import com.example.projectdemo.pojo.Teacher;
import com.example.projectdemo.service.StudentService;
import com.example.projectdemo.service.SubjectService;
import com.example.projectdemo.service.TeacherService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.util.List;

/**
 * @author Zero
 */
@Api(tags = "学生")
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private WsServerEndpoint wsServerEndpoint;
    private Gson gson = new Gson();

    /**
     * 学生修改密码
     *
     * @param password    学生旧密码
     * @param newPassword 学生新密码
     * @return 修改结果
     */
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", required = true, value = "旧密码"),
            @ApiImplicitParam(name = "newPassword", required = true, value = "新密码")
    })
    @ResponseBody
    @RequestMapping(value = "/changePassword", method = {RequestMethod.POST})
    public String changePassword(String password, String newPassword) {
        return studentService.changePassword(password, newPassword);
    }

    /**
     * 学生选课
     *
     * @param id 课程编号
     * @return 选课结果
     */
    @ApiOperation(value = "选课")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, value = "课程编号")})
    @ResponseBody
    @RequestMapping(value = "/selectSubject", method = RequestMethod.GET)
    public String selectSubject(int id) throws Exception {
        String result = subjectService.selectSubject(id);
        Teacher teacher = teacherService.queryBySubjectId(id);
        wsServerEndpoint.onMessage(teacher.getId());
        return result;
    }

    /**
     * 查询该学生选的课
     *
     * @return 课程json
     */
    @ApiOperation(value = "查询该学生选的课")
    @ResponseBody
    @GetMapping("/queryByStudentId")
    public String queryByStudentId() {
        return gson.toJson(subjectService.queryByStudentId());
    }

    /**
     * 学生自拟课题
     *
     * @param name              课题名
     * @param basicRequirement  基本需求
     * @param researchObjective 研究目标
     * @param reference         参考文献
     * @param amount            课题总数
     * @param url               文档链接
     * @return 插入结果
     */
    @ApiOperation(value = "插入课题")
    @GetMapping("/insertSubject")
    @ResponseBody
    public String insertSubject(String name,
                                String basicRequirement,
                                String researchObjective,
                                String reference,
                                int amount,
                                String url) {
        return String.valueOf(subjectService.insert(name, basicRequirement, researchObjective, reference, amount, url));
    }

    /**
     * 查询所有教师
     *
     * @return 教师json
     */
    @ApiOperation(value = "查询所有教师")
    @GetMapping("/queryAllTeacher")
    @ResponseBody
    private String queryAllTeacher() {
        return gson.toJson(teacherService.queryAll());
    }

    /**
     * 查询教师的题目根据id
     *
     * @param id 教师id
     * @return 课程json
     */
    @ApiOperation(value = "查询教师的题目根据id")
    @ApiImplicitParam(name = "id", required = true, value = "教师id")
    @GetMapping("/querySubjectByTeacherId")
    @ResponseBody
    private String queryByTeacherId(String id) {
        return gson.toJson(subjectService.querySubjectByTeacherId(id));
    }

    /**
     * 查询学生申请的教师
     *
     * @return 课题json
     */
    @ApiOperation(value = "查询学生申请的教师")
    @GetMapping("/querySelectedTeacher")
    @ResponseBody
    private String querySelectedTeacher() {
        Student stu = (Student) StpUtil.getSession().get("user");
        List<StudentSubject> message = studentService.getSelectedMessage(stu.getId());
        if (message.size() == 0) {
            return "";
        }
        return gson.toJson(message.get(0));
    }

    /**
     * 退选
     *
     * @return 结果
     */
    @ApiOperation(value = "退选", notes = "退选成功返回\"true\", 失败返回\"false\"")
    @GetMapping("/cancelSubject")
    @ResponseBody
    private String cancelSubject() {
        return subjectService.cancelSubject();
    }
}
