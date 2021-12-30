package com.example.projectdemo.mapper;

import com.example.projectdemo.pojo.ChooseSubject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChooseSubjectMapper {
    /**
     * 查询所有选课记录
     *
     * @return 所有限选课记录
     */
    @Select({"select * from tbl_choose_subject"})
    @Results(id = "result", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "chooseTime", column = "choose_time"),
            @Result(property = "confirmTime", column = "confirm_time"),
            @Result(property = "status", column = "status")
    })
    List<ChooseSubject> queryAll();

    /**
     * 添加选课记录
     *
     * @param chooseSubject 选课实体类
     * @return 添加结果
     */
    @Insert("insert into tbl_choose_subject (subject_id, student_id, choose_time, status) " +
            "values (#{subjectId}, #{studentId}, now(), #{status})")
    boolean selectSubject(ChooseSubject chooseSubject);

    /**
     * 查询该学生是否已经选课
     *
     * @param studentId 学生id
     * @return 查询结果
     */
    @ResultMap("result")
    @Select("select * from tbl_choose_subject where student_id like #{studentId}")
    ChooseSubject alreadySelected(String studentId);

    /**
     * 同意学生选课（修改选课关系表状态为3）
     *
     * @param chooseSubject 选课关系实体类
     * @return 结果
     */
    @Update("update tbl_choose_subject\n" +
            "set confirm_time = now(),\n" +
            "    status       = 3\n" +
            "where student_id = #{studentId}\n" +
            "  and subject_id = #{subjectId}")
    boolean agreeStudent(ChooseSubject chooseSubject);

    /**
     * 拒绝学生选课（修改选课关系表状态为4）
     *
     * @param chooseSubject 选课关系实体类
     * @return 结果
     */
    @Update("update tbl_choose_subject\n" +
            "set confirm_time = now(),\n" +
            "    status       = 4\n" +
            "where student_id = #{studentId}\n" +
            "  and subject_id = #{subjectId}")
    boolean refuseStudent(ChooseSubject chooseSubject);

    /**
     * 退选课程（修改选课记录为5）
     *
     * @param id 学生id
     * @return 结果
     */
    @Update("update tbl_choose_subject set status = 5 where student_id = #{studentId}")
    boolean cancelSubject(String id);
}
