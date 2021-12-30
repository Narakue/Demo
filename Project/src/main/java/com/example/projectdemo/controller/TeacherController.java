package com.example.projectdemo.controller;

import com.example.projectdemo.service.SubjectService;
import com.example.projectdemo.service.TeacherService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zero
 */
@Api(tags = "教师")
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    private Gson gson = new Gson();

    /**
     * 教师修改密码
     *
     * @param password    教师旧密码
     * @param newPassword 教师新密码
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
        return teacherService.changePassword(password, newPassword);
    }

    /**
     * 插入课题
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "课题名称"),
            @ApiImplicitParam(name = "basicRequirement", required = true, value = "基本需求"),
            @ApiImplicitParam(name = "researchObjective", required = true, value = "研究目标"),
            @ApiImplicitParam(name = "reference", required = true, value = "参考文献"),
            @ApiImplicitParam(name = "amount", required = true, value = "课题总数"),
            @ApiImplicitParam(name = "url", required = true, value = "文档链接"),
    })
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
     * 同意学生选课
     *
     * @param subjectId 课程id
     * @param studentId 学生id
     * @return 结果
     */
    @ApiOperation(value = "同意学生选课")
    @ResponseBody
    @RequestMapping(value = "/agreeStudent", method = RequestMethod.GET)
    public String agreeStudent(int subjectId, String studentId) {
        if (teacherService.agreeStudent(subjectId, studentId)) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 拒绝学生选课
     *
     * @param subjectId 课程id
     * @param studentId 学生id
     * @return 结果
     */
    @ApiOperation(value = "拒绝学生选课")
    @ResponseBody
    @RequestMapping(value = "/refuseStudent", method = RequestMethod.GET)
    public String refuseStudent(int subjectId, String studentId) {
        if (teacherService.refuseStudent(subjectId, studentId)) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 通过课题id删除课题
     *
     * @param id 课题id
     * @return 删除结果
     */
    @ApiOperation(value = "删除课题")
    @GetMapping("/deleteSubject")
    @ResponseBody
    public String deleteSubject(int id) {
        return String.valueOf(subjectService.deleteSubject(id));
    }

    /**
     * 修改课题
     *
     * @param id                课题id
     * @param name              课题名
     * @param basicRequirement  基本需求
     * @param researchObjective 研究目标
     * @param reference         参考文献
     * @param amount            课题总数
     * @param url               文件链接
     * @return 修改结果
     */
    @ResponseBody
    @ApiOperation(value = "修改课题")
    @GetMapping("/updateSubjectById")
    public String updateSubjectById(int id, String name, String basicRequirement, String researchObjective, String reference, int amount, String url) {
        return String.valueOf(subjectService.updateSubjectById(id, name, basicRequirement, researchObjective, reference, amount, url));
    }

    /**
     * 根据教师id查询选择了该课程的学生列表
     *
     * @return 学生列表json串
     */
    @ApiOperation(value = "根据教师id查询选择了该课程的学生列表")
    @RequestMapping(value = "/selectStudentByTeacherId", method = RequestMethod.GET)
    @ResponseBody
    public String selectStudentByTeacherId() {
        return gson.toJson(subjectService.selectStudentByTeacherId());
    }

    /**
     * 查询该教师已同意的学生
     *
     * @return 学生json串
     */
    @ApiOperation(value = "查询该教师已同意的学生")
    @GetMapping("/queryStudent")
    @ResponseBody
    public String queryStudent() {
        return gson.toJson(subjectService.queryAgree());
    }
}
