package com.lczyfz.demo.sys.service;

import com.lczyfz.demo.sys.entity.ExamPaper;
import com.lczyfz.demo.sys.entity.ExamPaperStudent;
import com.lczyfz.demo.sys.entity.ExamPaperStudentExample;
import com.lczyfz.demo.sys.entity.ExamUser;
import com.lczyfz.demo.sys.mapper.ExamPaperStudentMapper;
import com.lczyfz.demo.sys.utils.DateUtil;
import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 试卷-学生 Service
 *
 * @author 天狗
 * @version 2022-10-21
 */
@Service
@Transactional(readOnly = true)
public class ExamPaperStudentService extends CrudService<ExamPaperStudentMapper, ExamPaperStudent, ExamPaperStudentExample> {

    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    MyMailService mailService;

    public boolean isDuplicate(String paper, List<String> studentList) {
        ExamPaperStudentExample examPaperStudentExample = new ExamPaperStudentExample();
        examPaperStudentExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperStudent.DEL_FLAG_NORMAL)
                .andPaperEqualTo(paper);
        List<ExamPaperStudent> examPaperStudents = mapper.selectByExample(examPaperStudentExample);
        HashMap<String, Integer> hash = new HashMap<>();
        for (String subject : studentList) {
            hash.put(subject,1);
        }
        for (ExamPaperStudent examPaperStudent : examPaperStudents) {
            if (hash.get(examPaperStudent.getPaper())==null || hash.get(examPaperStudent.getPaper()).equals(1)) {
                return true;
            }
        }
        return false;
    }

    public int deleteByPaperId(String paper) {
        ExamPaperStudentExample examPaperStudentExample = new ExamPaperStudentExample();
        examPaperStudentExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperStudent.DEL_FLAG_NORMAL)
                .andPaperEqualTo(paper)
                .andStartTimeIsNull();
        List<ExamPaperStudent> examPaperStudents = mapper.selectByExample(examPaperStudentExample);
        int cnt = 0;
        for (ExamPaperStudent examPaperStudent : examPaperStudents) {
            cnt += delete(examPaperStudent);
        }
        return cnt;
    }



    public Page<ExamPaper> getPageByStudent(String id, int pageNo, int pageSize, String orderBy) {
        ExamPaperStudentExample examPaperStudentExample = new ExamPaperStudentExample();
        examPaperStudentExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperStudent.DEL_FLAG_NORMAL)
                .andStudentEqualTo(id);

        if (StringUtils.isNotBlank(orderBy)) {
            examPaperStudentExample.setOrderByClause(orderBy);
        }

        Page<ExamPaper> page = new Page<>(pageNo, pageSize, orderBy);
        page.setCount(mapper.countByExample(examPaperStudentExample));
        examPaperStudentExample.setPageSize(page.getMaxResults());
        examPaperStudentExample.setLimitStart(page.getFirstResult());

        List<ExamPaperStudent> examPaperStudents = mapper.selectByExample(examPaperStudentExample);
        ArrayList<ExamPaper> examPaperList = new ArrayList<>();
        for (ExamPaperStudent examPaperStudent : examPaperStudents) {
            examPaperList.add(examPaperService.get(examPaperStudent.getId()));
        }
        page.setList(examPaperList);

        return page;
    }


    @Transactional(readOnly = false)
    public int submitPaper(String paper, String student) {
        ExamPaperStudentExample examPaperStudentExample = new ExamPaperStudentExample();
        examPaperStudentExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperStudent.DEL_FLAG_NORMAL)
                .andSubmitFlagEqualTo(ExamPaperStudent.PAPER_UNSUBMITTED)
                .andPaperEqualTo(paper)
                .andStudentEqualTo(student);

        List<ExamPaperStudent> examPaperStudents = mapper.selectByExample(examPaperStudentExample);
        int cnt = 0;
        for (ExamPaperStudent examPaperStudent : examPaperStudents) {
            Integer costTime = DateUtil.getMinutesFromNow(examPaperStudent.getStartTime());
            if (this.isOverTime(examPaperStudent.getPaper(), costTime)) {
                ExamUser teacher = examPaperService.getTeacherByPaper(paper);
                if (StringUtils.isNotBlank(teacher.getEmail())) {
                    mailService.sendOverTimeMail(teacher.getEmail(),"学生"+examPaperStudent.getStudent()+"交卷时间超过50分钟！");
                } else {
                    logger.info("老师邮箱为空！");
                }
            } else {
                examPaperStudent.setCostTime(costTime);
                examPaperStudent.setSubmitFlag(ExamPaperStudent.PAPER_SUBMITTED);
                cnt += this.save(examPaperStudent);
            }
        }
        return cnt;
    }

    @Transactional(readOnly = false)
    public int startPaper(String paper, String student) {
        ExamPaperStudentExample examPaperStudentExample = new ExamPaperStudentExample();
        examPaperStudentExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperStudent.DEL_FLAG_NORMAL)
                .andSubmitFlagEqualTo(ExamPaperStudent.PAPER_UNSUBMITTED)
                .andStartTimeIsNull()
                .andPaperEqualTo(paper)
                .andStudentEqualTo(student);

        List<ExamPaperStudent> examPaperStudents = mapper.selectByExample(examPaperStudentExample);
        int cnt = 0;
        for (ExamPaperStudent examPaperStudent : examPaperStudents) {
            examPaperStudent.setStartTime(new Date());
            cnt += this.save(examPaperStudent);
        }
        return cnt;
    }

    public boolean isOverTime (String paper, Integer costTime) {
        ExamPaperStudentExample examPaperStudentExample = new ExamPaperStudentExample();
        examPaperStudentExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperStudent.DEL_FLAG_NORMAL)
                .andSubmitFlagEqualTo(ExamPaperStudent.PAPER_SUBMITTED)
                .andPaperEqualTo(paper);
        List<ExamPaperStudent> examPaperStudents = mapper.selectByExample(examPaperStudentExample);
        for (ExamPaperStudent examPaperStudent : examPaperStudents) {
            Integer time = examPaperStudent.getCostTime();
            if (costTime - time > 50) {
                return true;
            }
        }
        return false;
    }



}
