package com.lczyfz.demo.sys.service;

import com.lczyfz.demo.sys.entity.ExamSubject;
import com.lczyfz.demo.sys.entity.ExamSubjectExample;
import com.lczyfz.demo.sys.entity.ExamUser;
import com.lczyfz.demo.sys.mapper.ExamSubjectMapper;
import com.lczyfz.demo.sys.vo.ExamSubjectPageVO;
import com.lczyfz.demo.sys.vo.ExamUserPageVO;
import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 考试题目Service
 *
 * @author 天狗
 * @version 2022-10-19
 */
@Service
@Transactional(readOnly = true)
public class ExamSubjectService extends CrudService<ExamSubjectMapper, ExamSubject, ExamSubjectExample> {

    public Page<ExamSubject> findPage(Page<ExamSubject> page, ExamSubjectPageVO pageVO) {
        // 创建条件查询
        ExamSubjectExample examSubjectExample = new ExamSubjectExample();
        examSubjectExample.createCriteria().andDelFlagEqualTo(ExamSubject.DEL_FLAG_NORMAL);

        // 排序方式
        if (StringUtils.isNotBlank(pageVO.getOrderBy())) {
            examSubjectExample.setOrderByClause(pageVO.getOrderBy());
        }

        page.setCount(mapper.countByExample(examSubjectExample));
        examSubjectExample.setPageSize(page.getMaxResults());
        examSubjectExample.setLimitStart(page.getFirstResult());
        page.setList(mapper.selectByExample(examSubjectExample));

        return page;
    }

}
