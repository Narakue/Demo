package com.example.projectdemo.mapper;

import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.StudentSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentMapper {
    /**
     * 学生登录验证
     *
     * @param id       学生id
     * @param password 学生密码
     * @return 登陆学生
     */
    @Select("select * from tbl_student where id like #{id} and password like #{password}")
    Student studentLogin(String id, String password);

    /**
     * 学生修改密码
     *
     * @param id          学生id
     * @param password    学生密码
     * @param newPassword 学生新密码
     * @return 修改结果
     */
    @Update("update tbl_student set password = #{newPassword} where id like #{id} and password like #{password}")
    boolean changePassword(String id, String password, String newPassword);

    /**
     * 查询所有学生
     *
     * @return 学生列表
     */
    @Select("select * from tbl_student")
    List<Student> queryAll();

    /**
     * 根据id查询学生
     *
     * @param id 学生id
     * @return 学生
     */
    @Select("select * from tbl_student where id like #{id}")
    Student queryById(String id);

    /**
     * 选课
     *
     * @param id 学生id
     * @return 选课结果
     */
    @Update("update tbl_student set status = 2 where id = #{id}")
    boolean selectSubject(String id);

    /**
     * 得到已选课学生信息
     *
     * @param studentId 学生id
     * @return 学生信息
     */
    @Select("select tbl_student.id   as studentId,\n" +
            "       tbl_student.name,\n" +
            "       tbl_student.phone,\n" +
            "       tbl_student.qq,\n" +
            "       tbl_student.status,\n" +
            "       tbl_subject.id   as subjectId,\n" +
            "       tbl_subject.name as subjectName,\n" +
            "       tbl_teacher.name as teacherName\n" +
            "from tbl_subject,\n" +
            "     tbl_student,\n" +
            "     tbl_teacher\n" +
            "where tbl_subject.id in\n" +
            "      (\n" +
            "          select subject_id\n" +
            "          from tbl_choose_subject\n" +
            "          where student_id like #{studentId}\n" +
            "      )\n" +
            "  and tbl_student.id like #{studentId}\n" +
            "  and tbl_teacher.id in\n" +
            "      (\n" +
            "          select tbl_subject.teacher_id\n" +
            "          from tbl_subject,\n" +
            "               tbl_student\n" +
            "          where tbl_subject.id in\n" +
            "                (\n" +
            "                    select subject_id\n" +
            "                    from tbl_choose_subject\n" +
            "                    where student_id like #{studentId}\n" +
            "                )\n" +
            "            and tbl_student.id like #{studentId}\n" +
            "      );")
    List<StudentSubject> getSelectedMessage(String studentId);

    /**
     * 同意学生选课（修改学生表学生状态）
     *
     * @param id 学生id
     * @return 结果
     */
    @Update("update tbl_student\n" +
            "set status = 3\n" +
            "where id like #{id}")
    boolean agreeStudent(String id);

    /**
     * 拒绝学生选课（修改学生表学生状态）
     *
     * @param id 学生id
     * @return 结果
     */
    @Update("update tbl_student\n" +
            "set status = 4\n" +
            "where id like #{id}")
    boolean refuseStudent(String id);

    /**
     * 退选课程（修改学生状态为1）
     *
     * @param id 学生id
     * @return 结果
     */
    @Update("update tbl_student set status = 1 where id = #{id}")
    boolean cancelSubject(String id);
}
