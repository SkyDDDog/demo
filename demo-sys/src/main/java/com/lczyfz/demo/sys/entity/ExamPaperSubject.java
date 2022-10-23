package com.lczyfz.demo.sys.entity;

import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;;

/**
 * 试卷-题目表Entity
 *
 * @author 天狗
 * @version 2022-10-21
 */
@ApiModel(value = "ExamPaperSubject", description = "ExamPaperSubject")
public class ExamPaperSubject extends DataEntity<ExamPaperSubject> {

    @ApiModelProperty(value = "试卷编号")
    @NotBlank(message = "试卷编号不能为空")
    private String paper;

    @ApiModelProperty(value = "试题编号")
    @NotBlank(message = "试题编号不能为空")
    private String subject;

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper == null ? null : paper.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    @Override
    public String toString() {
        return "ExamPaperSubject{" +
                "paper='" + paper + '\'' +
                ", subject='" + subject + '\'' +
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