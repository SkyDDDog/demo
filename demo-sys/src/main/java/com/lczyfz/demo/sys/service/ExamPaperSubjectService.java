package com.lczyfz.demo.sys.service;

import com.lczyfz.demo.sys.entity.ExamPaper;
import com.lczyfz.demo.sys.entity.ExamPaperSubject;
import com.lczyfz.demo.sys.entity.ExamPaperSubjectExample;
import com.lczyfz.demo.sys.entity.ExamSubject;
import com.lczyfz.demo.sys.mapper.ExamPaperSubjectMapper;
import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 试卷-题目 Service
 *
 * @author 天狗
 * @version 2022-10-21
 */
@Service
@Transactional(readOnly = true)
public class ExamPaperSubjectService extends CrudService<ExamPaperSubjectMapper, ExamPaperSubject, ExamPaperSubjectExample> {

    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    ExamSubjectService examSubjectService;

    public boolean isDuplicate(String paper, List<String> subjectList) {
        ExamPaperSubjectExample examPaperSubjectExample = new ExamPaperSubjectExample();
        examPaperSubjectExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperSubject.DEL_FLAG_NORMAL)
                .andPaperEqualTo(paper);
        List<ExamPaperSubject> examPaperSubjects = mapper.selectByExample(examPaperSubjectExample);
        HashMap<String, Integer> hash = new HashMap<>();
        for (String subject : subjectList) {
            hash.put(subject,1);
        }
        for (ExamPaperSubject examPaperSubject : examPaperSubjects) {
            if (hash.get(examPaperSubject.getPaper())==null || hash.get(examPaperSubject.getPaper()).equals(1)) {
                return true;
            }
        }
        return false;
    }

    public List<ExamSubject> getSubjectListByPaper(String id) {
        ExamPaperSubjectExample examPaperSubjectExample = new ExamPaperSubjectExample();
        examPaperSubjectExample.createCriteria()
                .andDelFlagEqualTo(ExamPaperSubject.DEL_FLAG_NORMAL)
                .andPaperEqualTo(id);
        List<ExamPaperSubject> examPaperSubjects = mapper.selectByExample(examPaperSubjectExample);
        ArrayList<ExamSubject> result = new ArrayList<>();
        for (ExamPaperSubject examPaperSubject : examPaperSubjects) {
            result.add(examSubjectService.get(examPaperSubject.getSubject()));
        }
        return result;
    }

}
