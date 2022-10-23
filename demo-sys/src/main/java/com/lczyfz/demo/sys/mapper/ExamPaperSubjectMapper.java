package com.lczyfz.demo.sys.mapper;

import com.lczyfz.demo.sys.entity.ExamPaperSubject;
import com.lczyfz.demo.sys.entity.ExamPaperSubjectExample;
import java.util.List;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamPaperSubjectMapper extends CrudMapper<ExamPaperSubject, ExamPaperSubjectExample> {



}