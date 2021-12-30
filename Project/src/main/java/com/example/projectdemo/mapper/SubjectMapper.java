package com.example.projectdemo.mapper;

import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.StudentSubject;
import com.example.projectdemo.pojo.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Zero
 */
@Mapper
public interface SubjectMapper {
    /**
     * 查询所有课题
     *
     * @return 课题列表
     */
    @Select({"select * from tbl_subject"})
    @Results(id = "resultSubject", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "basicRequirement", column = "basic_requirement"),
            @Result(property = "researchObjective", column = "research_objective"),
            @Result(property = "reference", column = "reference"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "selected", column = "selected"),
            @Result(property = "passed", column = "passed"),
            @Result(property = "teacherId", column = "teacher_id")
    })
    List<Subject> queryAll();

    /**
     * 根据id修改课题
     *
     * @param subject 课题实体类
     * @return 修改结果
     */
    @Update("update tbl_subject\n" +
            "set name               = #{name},\n" +
            "    basic_requirement  = #{basicRequirement},\n" +
            "    research_objective = #{researchObjective},\n" +
            "    reference          = #{reference},\n" +
            "    amount             = #{amount},\n" +
            "    url                = #{url}\n" +
            "where id = #{id}")
    boolean updateSubjectById(Subject subject);

    /**
     * 查询某教师的所有课题
     *
     * @param teacherId 教师id
     * @return 课题列表
     */
    @ResultMap("resultSubject")
    @Select("select * from tbl_subject where teacher_id like #{teacherId}")
    List<Subject> querySubjectByTeacherId(String teacherId);

    /**
     * 通过课题id查询
     *
     * @param id 课题id
     * @return 课题
     */
    @ResultMap("resultSubject")
    @Select("select * from tbl_subject where id = #{id}")
    Subject queryById(int id);

    /**
     * 通过课题id删除课题
     *
     * @param id 课题id
     * @return 删除结果
     */
    @Delete("delete from tbl_subject where id = #{id}")
    boolean delete(int id);

    /**
     * 插入课题
     *
     * @param subject 课题类
     * @return 插入结果
     */
    @Insert("insert into tbl_subject (name, basic_requirement, research_objective, reference, amount, selected, passed, teacher_id, url)" +
            " values (#{name}, #{basicRequirement}, #{researchObjective}, #{reference}, #{amount}, #{selected}, #{passed}, #{teacherId}, #{url})")
    boolean insert(Subject subject);

//    /**
//     * 选课
//     *
//     * @param subject 课程实体类
//     * @return 选课结果
//     */
//    @Update("update tbl_subject set selected = selected + 1, version = version + 1 where id = #{id} and version = #{version}")
//    boolean selectSubject(Subject subject);

    /**
     * redis mq选课
     *
     * @param id 课程id
     * @return 结果
     */
    @Update("update tbl_subject set selected = selected + 1 where id = #{id}")
    boolean selectSubject(int id);

    /**
     * 根据教师id查询选择了该课程的学生列表
     *
     * @param id 教师id
     * @return 学生列表
     */
    @Select("select tbl_student.id   as studentId,\n" +
            "       tbl_student.password,\n" +
            "       tbl_student.name,\n" +
            "       tbl_student.phone,\n" +
            "       tbl_student.qq,\n" +
            "       tbl_student.status,\n" +
            "       tbl_subject.id   as subjectId,\n" +
            "       tbl_subject.name as subjectName\n" +
            "from tbl_student,\n" +
            "     tbl_subject\n" +
            "where tbl_student.id in\n" +
            "      (\n" +
            "          select student_id\n" +
            "          from tbl_choose_subject\n" +
            "          where subject_id in\n" +
            "                (\n" +
            "                    select id\n" +
            "                    from tbl_subject\n" +
            "                    where teacher_id like #{id} and status = 2\n" +
            "                )\n" +
            "      )\n" +
            "  and tbl_subject.id in (\n" +
            "    select subject_id\n" +
            "    from tbl_choose_subject\n" +
            "    where subject_id in\n" +
            "          (\n" +
            "              select id\n" +
            "              from tbl_subject\n" +
            "              where teacher_id like #{id}\n" +
            "          )\n" +
            ")")
    List<StudentSubject> selectStudentByTeacherId(String id);

    /**
     * 查询该教师已同意的学生
     *
     * @param id 教师id
     * @return 学生列表
     */
    @Select("select tbl_student.id,\n" +
            "       tbl_student.password,\n" +
            "       tbl_student.name,\n" +
            "       tbl_student.phone,\n" +
            "       tbl_student.qq,\n" +
            "       tbl_student.status,\n" +
            "       tbl_subject.id   as subjectId,\n" +
            "       tbl_subject.name as subjectName,\n" +
            "       tbl_subject.url\n" +
            "from tbl_student,\n" +
            "     tbl_subject\n" +
            "where tbl_student.id in\n" +
            "      (\n" +
            "          select student_id\n" +
            "          from tbl_choose_subject\n" +
            "          where subject_id in\n" +
            "                (\n" +
            "                    select id\n" +
            "                    from tbl_subject\n" +
            "                    where teacher_id like 't123'\n" +
            "                )\n" +
            "      )\n" +
            "  and tbl_subject.id in (\n" +
            "    select subject_id\n" +
            "    from tbl_choose_subject\n" +
            "    where subject_id in\n" +
            "          (\n" +
            "              select id\n" +
            "              from tbl_subject\n" +
            "              where teacher_id like 't123'\n" +
            "          )\n" +
            ")\n" +
            "  and tbl_student.status = 3")
    List<StudentSubject> queryAgree(String id);

    /**
     * 同意学生选课（修改课程表课程状态）
     *
     * @param id 课程id
     * @return 结果
     */
    @Update("update tbl_subject\n" +
            "set selected = selected - 1,\n" +
            "    passed   = passed + 1\n" +
            "where id = #{id}")
    boolean agreeStudent(int id);

    /**
     * 拒绝学生选课（修改课程表课程状态）
     *
     * @param id 课程id
     * @return 结果
     */
    @Update("update tbl_subject\n" +
            "set selected = selected - 1\n" +
            "where id = #{id}")
    boolean refuseStudent(int id);

    /**
     * 查询学生选的课
     *
     * @param studentId 学生id
     * @return 课题列表
     */
    @Select("select *\n" +
            "from tbl_subject\n" +
            "where id in\n" +
            "      (\n" +
            "          select subject_id\n" +
            "          from tbl_choose_subject\n" +
            "          where student_id = #{studentId} and status != 5\n" +
            "      )")
    List<Subject> queryByStudentId(String studentId);
}
