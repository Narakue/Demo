package com.example.projectdemo.mapper;

import com.example.projectdemo.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Zero
 */
@Mapper
public interface TeacherMapper {
    /**
     * 教师登录验证
     *
     * @param id       教师id
     * @param password 教师密码
     * @return 登陆的教师
     */
    @Select("select * from tbl_teacher where id like #{id} and password like #{password}")
    Teacher teacherLogin(String id, String password);

    /**
     * 查询所有教师
     *
     * @return 教师列表
     */
    @Select("select * from tbl_teacher")
    List<Teacher> queryAll();

    /**
     * 教师修改密码
     *
     * @param id          教师id
     * @param password    教师密码
     * @param newPassword 教师新密码
     * @return 修改结果
     */
    @Update("update tbl_teacher set password = #{newPassword} where id like #{id} and password like #{password}")
    boolean changePassword(String id, String password, String newPassword);

    /**
     * 选课
     *
     * @param id 教师id
     * @return 结果
     */
    @Update("update tbl_teacher set selected = selected + 1 where id = #{id}")
    boolean selectSubject(String id);

    /**
     * 根据id查询教师
     *
     * @param id 教师id
     * @return 教师信息
     */
    @Select("select * from tbl_teacher where id = #{id}")
    Teacher queryById(String id);

    /**
     * 通过课题id查询教师信息
     *
     * @param id 课题id
     * @return 教师实体类
     */
    @Select("select *\n" +
            "from tbl_teacher\n" +
            "where id in\n" +
            "      (\n" +
            "          select teacher_id\n" +
            "          from tbl_subject\n" +
            "          where id = #{id}\n" +
            "      )")
    Teacher queryBySubjectId(int id);
}
