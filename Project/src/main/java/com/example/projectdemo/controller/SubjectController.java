package com.example.projectdemo.controller;

import com.example.projectdemo.service.SubjectService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zero
 */
@Api(tags = "课题")
@Controller
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
    private final Gson gson = new Gson();

    /**
     * 展示每个老师的课题
     *
     * @return json串
     */
    @ApiOperation(value = "课题展示")
    @RequestMapping(value = "/subjectDisplay", method = {RequestMethod.GET})
    @ResponseBody
    public String subjectDisplay() {
        return gson.toJson(subjectService.subjectDisplay());
    }

    /**
     * 查询教师的所有课程
     *
     * @return 课程json
     */
    @ApiOperation(value = "查询教师的所有课程")
    @ResponseBody
    @RequestMapping(value = "/querySubjectByTeacherId", method = RequestMethod.GET)
    public String querySubjectByTeacherId() {
        return gson.toJson(subjectService.querySubjectByTeacherId());
    }
}
