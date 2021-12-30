package com.example.projectdemo.mapper;

import com.example.projectdemo.pojo.FilePojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Zero
 */
@Mapper
public interface FileMapper {
    /**
     * 查询所有文件
     *
     * @return 文件列表
     */
    @Results(id = "result", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "teacherId", column = "teacher_id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "url", column = "url")
    })
    @Select("select * from tbl_file")
    List<FilePojo> queryAll();

    /**
     * 教师插入文件
     *
     * @param file 文件实体类
     * @return 插入结果
     */
    @Insert("insert into tbl_file (teacher_id, date, url) values(#{teacherId}, #{date}, #{url})")
    boolean teacherInsert(FilePojo file);

    /**
     * 学生插入文件
     *
     * @param file 文件实体类
     * @return 插入结果
     */
    @Insert("insert into tbl_file (student_id, date, url) values(#{studentId}, #{date}, #{url})")
    boolean studentInsert(FilePojo file);

    /**
     * 根据教师id查询上传的文件
     *
     * @param teacherId 教师id
     * @return 文件列表
     */
    @ResultMap("result")
    @Select("select * from tbl_file where teacher_id like #{teacherId}")
    List<FilePojo> queryByTeacherId(String teacherId);

    /**
     * 根据id删除文件
     *
     * @param id 文件id
     * @return 删除结果
     */
    @Delete("delete from tbl_file where id like #{id}")
    boolean delete(int id);
}
