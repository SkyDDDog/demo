package com.lczyfz.demo.sys.entity;

import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;;

/**
 * 考试试卷Entity
 *
 * @author 天狗
 * @version 2022-10-20
 */
@ApiModel(value = "ExamPaper", description = "ExamPaper")
public class ExamPaper extends DataEntity<ExamPaper> {

    @ApiModelProperty(value = "试卷名")
    @NotBlank(message = "试卷名不能为空")
    private String name;

    @ApiModelProperty(value = "组卷者")
    @NotBlank(message = "组卷者不能为空")
    private String organizeBy;

    @ApiModelProperty(value = "题目数量")
    @NotBlank(message = "题目数量不能为空")
    private Integer count;

    @ApiModelProperty(value = "考试时间")
    @NotBlank(message = "考试时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "考试时长(分钟)")
    @NotBlank(message = "考试时长(分钟)不能为空")
    private Integer durationTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrganizeBy() {
        return organizeBy;
    }

    public void setOrganizeBy(String organizeBy) {
        this.organizeBy = organizeBy == null ? null : organizeBy.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    @Override
    public String toString() {
        return "ExamPaper{" +
                "name='" + name + '\'' +
                ", organizeBy='" + organizeBy + '\'' +
                ", count=" + count +
                ", startTime=" + startTime +
                ", durationTime=" + durationTime +
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