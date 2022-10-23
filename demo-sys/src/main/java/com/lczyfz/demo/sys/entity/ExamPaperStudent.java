package com.lczyfz.demo.sys.entity;

import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;;

/**
 * 试卷-学生表 Entity
 *
 * @author 天狗
 * @version 2022-10-21
 */
@ApiModel(value = "ExamPaperStudent", description = "ExamPaperStudent")
public class ExamPaperStudent extends DataEntity<ExamPaperStudent> {

    public static final String PAPER_SUBMITTED = "1";
    public static final String PAPER_UNSUBMITTED = "0";

    @ApiModelProperty(value = "考试试卷编号")
    @NotBlank(message = "考试试卷编号不能为空")
    private String paper;

    @ApiModelProperty(value = "考试学生编号")
    @NotBlank(message = "考试学生编号不能为空")
    private String student;

    @ApiModelProperty(value = "开始作答时间")
    @NotBlank(message = "开始作答时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "答卷用时")
    @NotBlank(message = "答卷用时不能为空")
    private Integer costTime;

    @ApiModelProperty(value = "提交标志")
    @NotBlank(message = "提交标志不能为空")
    private String submitFlag;


    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper == null ? null : paper.trim();
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student == null ? null : student.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag == null ? null : submitFlag.trim();
    }

    @Override
    public String toString() {
        return "ExamPaperStudent{" +
                "paper='" + paper + '\'' +
                ", student='" + student + '\'' +
                ", startTime=" + startTime +
                ", costTime=" + costTime +
                ", submitFlag='" + submitFlag + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", delFlag='" + delFlag + '\'' +
                ", officeId='" + officeId + '\'' +
                ", sqlMap=" + sqlMap +
                ", id='" + id + '\'' +
                ", isNewRecord=" + isNewRecord +
                '}';
    }
}