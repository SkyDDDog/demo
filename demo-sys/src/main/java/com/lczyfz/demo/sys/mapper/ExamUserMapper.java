package com.lczyfz.demo.sys.mapper;

import com.lczyfz.demo.sys.entity.ExamUser;
import com.lczyfz.demo.sys.entity.ExamUserExample;
import java.util.List;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamUserMapper extends CrudMapper<ExamUser,ExamUserExample> {



}