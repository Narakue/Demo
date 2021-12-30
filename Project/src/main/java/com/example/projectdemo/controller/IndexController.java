package com.example.projectdemo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.projectdemo.service.IndexService;
import com.example.projectdemo.service.StudentService;
import com.example.projectdemo.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zero
 */
@Api(tags = "页面")
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 登录页
     * method Get
     */
    @ApiIgnore
    @GetMapping(value = {"/login", "/"})
    @SentinelResource(value = "resource")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    /**
     * 返回登录信息
     *
     * @return 登录信息
     */
    @ApiOperation(value = "登录信息")
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    @ResponseBody
    public String message() {
        return indexService.message();
    }

    /**
     * 教师登录
     * method POST
     *
     * @param id       教师id
     * @param password 教师密码
     * @param remember 是否记住我
     * @return 登陆结果(成功则返回教师name, 失败则返回 " fail ")
     */
    @ApiOperation(value = "教师登录")
    @RequestMapping(value = "/teacherLogin", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教师id", required = true),
            @ApiImplicitParam(name = "password", required = true, value = "教师password"),
            @ApiImplicitParam(name = "remember", value = "记住我")
    })
    @ResponseBody
    public String teacherLogin(String id, String password, String remember) {
        return teacherService.teacherLogin(id, password, remember);
    }

    /**
     * 学生登录验证
     *
     * @param id       学生id
     * @param password 学生password
     * @param remember 记住我
     * @return 登陆结果(成功则返回学生name, 失败则返回 " fail ")
     */
    @ApiOperation(value = "学生登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "学生id"),
            @ApiImplicitParam(name = "password", required = true, value = "学生password"),
            @ApiImplicitParam(name = "remember", value = "记住我(为空则不开启)")
    })
    @RequestMapping(value = "/studentLogin", method = {RequestMethod.POST})
    @ResponseBody
    public String studentLogin(String id, String password, String remember) {
        return studentService.studentLogin(id, password, remember);
    }

    /**
     * 退出登录
     */
    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/signOut", method = {RequestMethod.GET})
    public String signOut() {
        StpUtil.logout();
        return "select";
    }
}
