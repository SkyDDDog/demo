package com.lczyfz.demo.sys.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

;

/**
 * 考试题目VO
 *
 * @author 天狗
 * @version 2022-10-19
 */
@ApiModel(value = "ExamSubjectVO", description = "ExamSubjectVO")
public class ExamSubjectVO extends BaseVO {

    @ApiModelProperty(value = "题目", example = "xxxxxx是什么?")
    @NotBlank(message = "题目不能为空")
    private String question;

    @ApiModelProperty(value = "答案", example = "xxxx是xxxxxx")
    @NotBlank(message = "答案不能为空")
    private String answer;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    @Override
    public String toString() {
        return "ExamSubjectVO{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}