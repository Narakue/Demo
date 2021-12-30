package com.example.projectdemo.controller;

import com.example.projectdemo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author Zero
 */
@Api(tags = "文件")
@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * method POST
     *
     * @param srcFile 文件
     * @return 上传成功返回文件url，失败返回文件上传失败
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/teacher/uploadFile")
    @ResponseBody
    public String teacherUpload(@RequestParam(value = "file", required = false) MultipartFile srcFile) {
        return fileService.teacherUploadFile(srcFile);
    }

    /**
     * 文件上传
     * method POST
     *
     * @param srcFile 文件
     * @return 上传成功返回文件url，失败返回文件上传失败
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/student/uploadFile")
    @ResponseBody
    public String studentUpload(@RequestParam(value = "file", required = false) MultipartFile srcFile) {
        return fileService.studentUploadFile(srcFile);
    }

    /**
     * 文件下载
     *
     * @param path     目标文件路径
     * @param response 相应
     * @return 浏览器文件下载
     * @throws UnsupportedEncodingException 异常
     */
    @ApiOperation(value = "文件下载")
    @ApiImplicitParam(name = "path", required = true)
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadFile(String path, HttpServletResponse response) throws UnsupportedEncodingException {
        if (path == null) {
            return null;
        }
        File file = new File(path);
        response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }
}
