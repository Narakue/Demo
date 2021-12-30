package com.example.projectdemo.service;

import cn.dev33.satoken.stp.StpUtil;
import com.example.projectdemo.mapper.FileMapper;
import com.example.projectdemo.pojo.FilePojo;
import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Zero
 */
@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public boolean teacherUpload(String url) {
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        FilePojo file = new FilePojo();
        file.setTeacherId(teacher.getId());
        file.setDate(new Date());
        file.setUrl(url);
        return fileMapper.teacherInsert(file);
    }

    public boolean studentUpload(String url) {
        Student student = (Student) StpUtil.getSession().get("user");
        FilePojo file = new FilePojo();
        file.setStudentId(student.getId());
        file.setDate(new Date());
        file.setUrl(url);
        return fileMapper.studentInsert(file);
    }

    public List<FilePojo> queryByTeacherId() {
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        return fileMapper.queryByTeacherId(teacher.getId());
    }

    public String studentUploadFile(MultipartFile srcFile) {
        if (srcFile.isEmpty()) {
            return "请选择一个文件";
        }
        String url = "";
        Student student = (Student) StpUtil.getSession().get("user");
        try {
            //构建上传目标路径，找到了项目的target的classes目录
            java.io.File destFile = new java.io.File(ResourceUtils.getURL("classpath:").getPath());
            if (!destFile.exists()) {
                destFile = new java.io.File("");
            }
            //输出目标文件的绝对路径
            System.out.println("file path:" + destFile.getAbsolutePath());
            //拼接子路径
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String times = sf.format(new Date());
            java.io.File upload = new java.io.File(destFile.getAbsolutePath(), "files/" + times);
            //若目标文件夹不存在，则创建
            if (!upload.exists()) {
                boolean mkdirs = upload.mkdirs();
            }
            //System.out.println("完整的上传路径：" + upload.getAbsolutePath() + "/" + user.getName() + srcFile);
            //根据srcFile大小，准备一个字节数组
            byte[] bytes = srcFile.getBytes();
            //拼接上传路径
            //Path path = Paths.get(UPLOAD_FOLDER + srcFile.getOriginalFilename());
            //通过项目路径，拼接上传路径
            Path path = Paths.get(upload.getAbsolutePath() + "/" + student.getName() + srcFile.getOriginalFilename());
            url = path.toString();
            //** 开始将源文件写入目标地址
            Files.write(path, bytes);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 获得文件原始名称
            String fileName = srcFile.getOriginalFilename();
            // 获得文件后缀名称
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            // 生成最新的uuid文件名称
            String newFileName = uuid + "." + suffixName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentUpload(url) ? url : "文件上传失败";
    }

    public String teacherUploadFile(MultipartFile srcFile) {
        if (srcFile.isEmpty()) {
            return "请选择一个文件";
        }
        String url = "";
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        try {
            //构建上传目标路径，找到了项目的target的classes目录
            java.io.File destFile = new java.io.File(ResourceUtils.getURL("classpath:").getPath());
            if (!destFile.exists()) {
                destFile = new java.io.File("");
            }
            //输出目标文件的绝对路径
            System.out.println("file path:" + destFile.getAbsolutePath());
            //拼接子路径
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String times = sf.format(new Date());
            java.io.File upload = new java.io.File(destFile.getAbsolutePath(), "files/" + times);
            //若目标文件夹不存在，则创建
            if (!upload.exists()) {
                boolean mkdirs = upload.mkdirs();
            }
            //System.out.println("完整的上传路径：" + upload.getAbsolutePath() + "/" + user.getName() + srcFile);
            //根据srcFile大小，准备一个字节数组
            byte[] bytes = srcFile.getBytes();
            //拼接上传路径
            //Path path = Paths.get(UPLOAD_FOLDER + srcFile.getOriginalFilename());
            //通过项目路径，拼接上传路径
            Path path = Paths.get(upload.getAbsolutePath() + "/" + teacher.getName() + srcFile.getOriginalFilename());
            url = path.toString();
            //** 开始将源文件写入目标地址
            Files.write(path, bytes);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 获得文件原始名称
            String fileName = srcFile.getOriginalFilename();
            // 获得文件后缀名称
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            // 生成最新的uuid文件名称
            String newFileName = uuid + "." + suffixName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherUpload(url) ? url : "文件上传失败";
    }

    public boolean delete(int id) {
        return fileMapper.delete(id);
    }
}
