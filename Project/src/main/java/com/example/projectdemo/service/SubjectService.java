package com.example.projectdemo.service;

import cn.dev33.satoken.stp.StpUtil;
import com.example.projectdemo.mapper.SubjectMapper;
import com.example.projectdemo.pojo.Student;
import com.example.projectdemo.pojo.StudentSubject;
import com.example.projectdemo.pojo.Subject;
import com.example.projectdemo.pojo.Teacher;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Zero
 */
@Service
public class SubjectService {
    private final Logger logger = Logger.getLogger("zero");
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ChooseSubjectService chooseSubjectService;
    @Autowired
    private StringRedisTemplate redis;
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;

    public List<Subject> queryAll() {
        return subjectMapper.queryAll();
    }

    public List<Subject> querySubjectByTeacherId() {
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        List<Subject> subjects = subjectMapper.querySubjectByTeacherId(teacher.getId());
        for (int i = 0; i < subjects.size(); i++) {
            String key = subjects.get(i).getId() + "_num";
            String s = redis.opsForValue().get(key);
            if (s == null) {
                redis.opsForValue().set(key, String.valueOf(subjects.get(i).getAmount()));
                s = "0";
            }
            subjects.get(i).setSelected(subjects.get(i).getAmount() - Integer.parseInt(s));
        }
        return subjects;
    }

    public List<Subject> querySubjectByTeacherId(String id) {
        List<Subject> subjects = subjectMapper.querySubjectByTeacherId(id);
        for (int i = 0; i < subjects.size(); i++) {
            String selected = redis.opsForValue().get(subjects.get(i).getId() + "_num");
            if (selected == null) {
                redis.opsForValue().set(subjects.get(i).getId() + "_num", String.valueOf(subjects.get(i).getAmount()));
                selected = String.valueOf(subjects.get(i).getAmount());
            }
            subjects.get(i).setSelected(subjects.get(i).getAmount() - Integer.parseInt(selected));
        }
        return subjects;
    }

    public Subject queryById(int id) {
        return subjectMapper.queryById(id);
    }

    public boolean deleteSubject(int id) {
        return subjectMapper.delete(id);
    }

    public boolean updateSubjectById(int id, String name, String basicRequirement, String researchObjective, String reference, int amount, String url) {
        Subject subject = new Subject(id, name, basicRequirement, researchObjective, reference, amount, url);
        return subjectMapper.updateSubjectById(subject);
    }

    public boolean insert(String name, String basicRequirement, String researchObjective, String reference, int amount, String url) {
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        Subject subject = new Subject(name, basicRequirement, researchObjective, reference, amount, 0, 0, teacher.getId(), url);
        return subjectMapper.insert(subject);
    }

    public List<List<Subject>> subjectDisplay() {
        List<Teacher> teachers = teacherService.queryAll();
        List<List<Subject>> teacherSubject = new ArrayList<>();
        for (Teacher teacher : teachers) {
            List<Subject> subjects = subjectMapper.querySubjectByTeacherId(teacher.getId());
            for (int i = 0; i < subjects.size(); i++) {
                String key = subjects.get(i).getId() + "_num";
                String s = redis.opsForValue().get(key);
                if (s == null) {
                    redis.opsForValue().set(key, String.valueOf(subjects.get(i).getAmount()));
                    s = String.valueOf(subjects.get(i).getAmount());
                }
                subjects.get(i).setSelected(subjects.get(i).getAmount() - Integer.parseInt(s));
            }
            if (subjects.size() != 0) {
                teacherSubject.add(subjects);
            }
        }
        return teacherSubject;
    }

    public String selectSubject(int id) throws Exception {
//        Student user = (Student) StpUtil.getSession().get("user");
        // 测试高并发选课
        Student user = new Student();
        user.setId("123");
        Student student = studentService.queryById(user.getId());
        // 查询该学生是否已经选课
//        if (student.getStatus() == 2 || student.getStatus() == 3) {
//            logger.info("已经选课");
//            return "已经选课";
//        }
        Subject subject = queryById(id);
        // 判断课程是否已满
        // jud : (1, 选课成功), (2, 课程已满选课失败), (3, 选课失败)
        int jud = 0;
        SessionCallback<Object> callback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String key = id + "_num";
                if (!redis.hasKey(key)) {
                    redis.opsForValue().set(key, String.valueOf(subject.getAmount()));
                }
                operations.watch(key);
                int num = Integer.valueOf(redis.opsForValue().get(key));
                if (num <= 0) {
                    return "fail";
                } else {
                    operations.multi();
                    operations.opsForValue().decrement(key);
                    return operations.exec();
                }
            }
        };
        Object obj = redis.execute(callback);
        if (obj instanceof String) {
            jud = 2;
        } else {
            List<String> list = (List<String>) obj;
            if (list.size() == 0) {
                jud = 3;
            } else {
                jud = 1;
            }
        }
        if (jud == 1 &&
                chooseSubjectService.selectSubject(id, student.getId()) &&
                teacherService.selectSubject(subject.getTeacherId()) &&
                studentService.selectSubject(student.getId())) {
//            rocketMQTemplate.convertAndSend("select-subject", id);
            logger.info("选课成功");
            return "选课成功";
        } else if (jud == 2) {
            logger.info("课程已满,选课失败");
            return "课程已满,选课失败";
        } else {
            logger.info("选课失败");
            return "选课失败";
        }
    }

//    /**
//     * 乐观锁选课
//     *
//     * @param id 课程id
//     * @return 选课结果
//     */
    /*
    public String selectSubject1(int id) {
//        Student user = (Student) StpUtil.getSession().get("user");
        // 测试高并发选课
        Student user = new Student();
        user.setId("123");
        Student student = studentService.queryById(user.getId());
        // 查询该学生是否已经选课
//        if (student.getStatus() == 2 || student.getStatus() == 3) {
//            logger.info("已经选课");
//            return "已经选课";
//        }

        // 判断课程是否已满
        // jud : (1, 选课成功), (2, 课程已满选课失败), (3, 选课失败)
        Subject subject = queryById(id);
        int jud;
        if (subject.getAmount() > subject.getSelected()) {
            if (subjectMapper.selectSubject(subject)) {
                jud = 1;
            } else {
                jud = 3;
            }
        } else {
            jud = 2;
        }
        if (jud == 1 &&
                chooseSubjectService.selectSubject(id, student.getId()) &&
                teacherService.selectSubject(subject.getTeacherId()) &&
                studentService.selectSubject(student.getId())) {
            logger.info("选课成功");
            return "选课成功";
        } else if (jud == 2) {
            logger.info("课程已满,选课失败");
            return "课程已满,选课失败";
        } else {
            logger.info("选课失败");
            return "选课失败";
        }
    }*/


    public List<StudentSubject> selectStudentByTeacherId() {
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        return subjectMapper.selectStudentByTeacherId(teacher.getId());
    }

    public List<StudentSubject> selectStudentByTeacherId(String id) {
        return subjectMapper.selectStudentByTeacherId(id);
    }

    public List<StudentSubject> queryAgree() {
        Teacher teacher = (Teacher) StpUtil.getSession().get("user");
        return subjectMapper.queryAgree(teacher.getId());
    }

    public boolean agreeStudent(int id) {
        return subjectMapper.agreeStudent(id);
    }

    public boolean refuseStudent(int id) {
        return subjectMapper.refuseStudent(id);
    }

    public List<Subject> queryByStudentId() {
        Student student = (Student) StpUtil.getSession().get("user");
        return subjectMapper.queryByStudentId(student.getId());
    }

    public String cancelSubject() {
        Student student = (Student) StpUtil.getSession().get("user");
        Subject subject = queryByStudentId().get(0);
        boolean jud1 = chooseSubjectService.cancelSubject(student.getId());
        boolean jud2 = studentService.cancelSubject(student.getId());
        SessionCallback<Object> callback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String key = subject.getId() + "_num";
                if (redis.hasKey(key) == null) {
                    redis.opsForValue().set(key, String.valueOf(subject.getAmount()));
                }
                operations.watch(key);
                operations.multi();
                operations.opsForValue().increment(key);
                return operations.exec();
            }
        };
        List<Object> exec = (List<Object>) redis.execute(callback);
        if (jud1 && jud2 && Objects.requireNonNull(exec).size() != 0) {
            return "true";
        } else {
            return "false";
        }
    }
}
