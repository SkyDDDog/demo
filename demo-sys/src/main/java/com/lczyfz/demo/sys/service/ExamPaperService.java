package com.lczyfz.demo.sys.service;

import com.lczyfz.demo.sys.entity.*;
import com.lczyfz.demo.sys.mapper.ExamPaperMapper;
import com.lczyfz.demo.sys.vo.ExamPaperPageVO;
import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考试试卷Service
 *
 * @author 天狗
 * @version 2022-10-20
 */
@Service
@Transactional(readOnly = true)
public class ExamPaperService extends CrudService<ExamPaperMapper, ExamPaper, ExamPaperExample> {

    @Autowired
    ExamPaperSubjectService examPaperSubjectService;
    @Autowired
    ExamPaperStudentService examPaperStudentService;
    @Autowired
    ExamUserService examUserService;

    public Page<ExamPaper> findPage(Page<ExamPaper> page, ExamPaperPageVO pageVO) {
        // 创建条件查询
        ExamPaperExample examPaperExample = new ExamPaperExample();
        examPaperExample.createCriteria().andDelFlagEqualTo(ExamPaper.DEL_FLAG_NORMAL);

        // 排序方式
        if (StringUtils.isNotBlank(pageVO.getOrderBy())) {
            examPaperExample.setOrderByClause(pageVO.getOrderBy());
        }

        page.setCount(mapper.countByExample(examPaperExample));
        examPaperExample.setPageSize(page.getMaxResults());
        examPaperExample.setLimitStart(page.getFirstResult());
        page.setList(mapper.selectByExample(examPaperExample));

        return page;
    }

    @Transactional(readOnly = false)
    public int organizeSubjectList(String paper, List<String> subjectList) {
        if (subjectList.isEmpty()) {
            return 0;
        } else {
            if (examPaperSubjectService.isDuplicate(paper, subjectList)) {
//                logger.info("重复了！！！！");
                return 0;
            }
            int cnt = 0;
            for (String subject : subjectList) {
                ExamPaperSubject examPaperSubject = new ExamPaperSubject();
                examPaperSubject.setPaper(paper);
                examPaperSubject.setSubject(subject);
                cnt += examPaperSubjectService.save(examPaperSubject);
            }
            return cnt;
        }
    }

    @Transactional(readOnly = false)
    public int handoutPapersToStudents(String paper, List<String> studentList) {
        if (studentList.isEmpty()) {
            return 0;
        } else {
            if (examPaperStudentService.isDuplicate(paper, studentList)) {
//                logger.info("重复了！！！！");
                return 0;
            }
            int cnt = 0;
            for (String student : studentList) {
                ExamPaperStudent examPaperStudent = new ExamPaperStudent();
                examPaperStudent.setPaper(paper);
                examPaperStudent.setStudent(student);
                examPaperStudent.setSubmitFlag(ExamPaperStudent.PAPER_UNSUBMITTED);
                cnt += examPaperStudentService.save(examPaperStudent);
            }
            ExamPaper examPaper = this.get(paper);
            examPaper.setCount(examPaper.getCount()+cnt);
            this.save(examPaper);
            return cnt;
        }
    }

    @Transactional(readOnly = false)
    public int withdrawPapersFromStudent(String paper) {
        return examPaperStudentService.deleteByPaperId(paper);
    }



    public Page<ExamPaper> getPageByTeacher(String id, int pageNo, int pageSize, String orderBy) {
        ExamPaperExample examPaperExample = new ExamPaperExample();
        examPaperExample.createCriteria()
                .andDelFlagEqualTo(ExamPaper.DEL_FLAG_NORMAL)
                .andOrganizeByEqualTo(id);

        if (StringUtils.isNotBlank(orderBy)) {
            examPaperExample.setOrderByClause(orderBy);
        }

        Page<ExamPaper> page = new Page<>(pageNo, pageSize, orderBy);
        page.setCount(mapper.countByExample(examPaperExample));
        examPaperExample.setPageSize(page.getMaxResults());
        examPaperExample.setLimitStart(page.getFirstResult());
        page.setList(mapper.selectByExample(examPaperExample));

        return page;
    }

    public ExamUser getTeacherByPaper(String paper) {
        ExamPaper examPaper = this.get(paper);
        String teacher = examPaper.getOrganizeBy();
        return examUserService.get(teacher);
    }




}
