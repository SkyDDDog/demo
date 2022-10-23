package com.lczyfz.demo.sys.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import com.lczyfz.edp.springboot.core.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

;

/**
 * 考试试卷Entity
 *
 * @author 天狗
 * @version 2022-10-20
 */
@ApiModel(value = "ExamPaper", description = "ExamPaper")
public class ExamPaperPageVO extends PageVO {
    @ApiModelProperty(value = "试卷名", example = "2022高等数学期中考试卷")
    private String name;

    @ApiModelProperty(value = "组卷者Id" , example = "503242351880171520")
    private String organizeBy;

    @ApiModelProperty(value = "考试时间", example = "2022-10-21 14:30:00")
    private Date startTime;

    @ApiModelProperty(value = "考试时长(分钟)", example = "90")
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
        return "ExamPaperVO{" +
                "name='" + name + '\'' +
                ", organizeBy='" + organizeBy + '\'' +
                ", startTime=" + startTime +
                ", durationTime=" + durationTime +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}