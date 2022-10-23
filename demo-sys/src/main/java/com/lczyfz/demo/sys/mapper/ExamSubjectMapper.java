package com.lczyfz.demo.sys.mapper;

import com.lczyfz.demo.sys.entity.ExamSubject;
import com.lczyfz.demo.sys.entity.ExamSubjectExample;
import java.util.List;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamSubjectMapper extends CrudMapper<ExamSubject, ExamSubjectExample> {


}