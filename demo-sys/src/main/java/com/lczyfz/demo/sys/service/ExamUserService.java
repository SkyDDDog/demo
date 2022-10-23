package com.lczyfz.demo.sys.service;

import com.lczyfz.demo.sys.entity.ExamUser;
import com.lczyfz.demo.sys.entity.ExamUserExample;
import com.lczyfz.demo.sys.mapper.ExamUserMapper;
import com.lczyfz.demo.sys.utils.EncodeUtils;
import com.lczyfz.demo.sys.vo.ExamUserPageVO;
import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.CryptoUtils;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考试系统用户service
 *
 * @author 天狗
 * @version 2022-10-18
 */
@Service
@Transactional(readOnly = true)
public class ExamUserService extends CrudService<ExamUserMapper, ExamUser, ExamUserExample> {

    public static final String ROLE_STUDENT = "ROLE_STUDENT";
    public static final String ROLE_TEACHER = "ROLE_TEACHER";

    public Page<ExamUser> findPage(Page<ExamUser> page, ExamUserPageVO pageVO) {
        // 创建条件查询
        ExamUserExample examUserExample = new ExamUserExample();
        examUserExample.createCriteria().andDelFlagEqualTo(ExamUser.DEL_FLAG_NORMAL);

        // 排序方式
        if (StringUtils.isNotBlank(pageVO.getOrderBy())) {
            examUserExample.setOrderByClause(pageVO.getOrderBy());
        }

        page.setCount(mapper.countByExample(examUserExample));
        examUserExample.setPageSize(page.getMaxResults());
        examUserExample.setLimitStart(page.getFirstResult());
        page.setList(mapper.selectByExample(examUserExample));

        return page;
    }

    @Override
    public int save(ExamUser entity) {
        entity.setPassword(EncodeUtils.encodePassword(entity.getPassword()));
        return super.save(entity);
    }

    public boolean isStudent(String plainPassword, ExamUser examUser) {
        boolean flag = false;
        if (EncodeUtils.validatePassword(plainPassword,examUser.getPassword()) && StringUtils.isNotBlank(examUser.getRole())) {
            flag = ROLE_STUDENT.equals(examUser.getRole());
        }
        return flag;
    }

    public boolean isStudent(String username, String plainPassword) {
        username = CryptoUtils.decryptFromBcdBySystem(username);
        plainPassword = CryptoUtils.decryptFromBcdBySystem(plainPassword);

        ExamUserExample examUserExample = new ExamUserExample();
        examUserExample.createCriteria()
                .andUsernameEqualTo(username)
                .andDelFlagEqualTo(ExamUser.DEL_FLAG_NORMAL);
        boolean result = false;
        List<ExamUser> list = this.findList(examUserExample);
        for (ExamUser examUser : list) {
            result = isStudent(plainPassword, examUser);
            if (result) {
                break;
            }
        }
        return result;
    }

    public boolean isTeacher(String plainPassword, ExamUser examUser) {
        boolean flag = false;
        if (EncodeUtils.validatePassword(plainPassword,examUser.getPassword()) && StringUtils.isNotBlank(examUser.getRole())) {
            flag = ROLE_TEACHER.equals(examUser.getRole());
        }
        return flag;
    }

    public boolean isTeacher(String username, String plainPassword) {
        username = CryptoUtils.decryptFromBcdBySystem(username);
        plainPassword = CryptoUtils.decryptFromBcdBySystem(plainPassword);

        ExamUserExample examUserExample = new ExamUserExample();
        examUserExample.createCriteria()
                .andUsernameEqualTo(username)
                .andDelFlagEqualTo(ExamUser.DEL_FLAG_NORMAL);
        boolean result = false;
        List<ExamUser> list = this.findList(examUserExample);
        for (ExamUser examUser : list) {
            result = isTeacher(plainPassword,examUser);
            if (result) {
                break;
            }
        }
        return result;
    }




}
