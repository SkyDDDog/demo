package com.lczyfz.demo.sys.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import com.lczyfz.edp.springboot.core.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

;

/**
 * 考试题目PageVO
 *
 * @author 天狗
 * @version 2022-10-19
 */
@ApiModel(value = "ExamSubjectPageVO", description = "ExamSubjectPageVO")
public class ExamSubjectPageVO extends PageVO {

    @ApiModelProperty(value = "题目", example = "xxxxxx是什么?")
    private String question;

    @ApiModelProperty(value = "答案", example = "xxxx是xxxxxx")
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