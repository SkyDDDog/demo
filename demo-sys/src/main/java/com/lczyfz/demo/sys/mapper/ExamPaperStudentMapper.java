package com.lczyfz.demo.sys.mapper;

import com.lczyfz.demo.sys.entity.ExamPaperStudent;
import com.lczyfz.demo.sys.entity.ExamPaperStudentExample;
import com.lczyfz.demo.sys.entity.ExamPaperSubject;
import com.lczyfz.demo.sys.entity.ExamPaperSubjectExample;
import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ExamPaperStudentMapper extends CrudMapper<ExamPaperStudent, ExamPaperStudentExample> {



}