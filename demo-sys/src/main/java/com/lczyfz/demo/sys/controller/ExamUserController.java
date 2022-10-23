package com.lczyfz.demo.sys.controller;

import com.lczyfz.demo.sys.entity.ExamSubject;
import com.lczyfz.demo.sys.entity.ExamUser;
import com.lczyfz.demo.sys.service.ExamUserService;
import com.lczyfz.demo.sys.vo.ExamSubjectVO;
import com.lczyfz.demo.sys.vo.ExamUserPageVO;
import com.lczyfz.demo.sys.vo.ExamUserVO;
import com.lczyfz.edp.springboot.core.controller.BaseController;
import com.lczyfz.edp.springboot.core.entity.CommonResult;
import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.entity.PageResult;
import com.lczyfz.edp.springboot.core.utils.BeanCustomUtils;
import com.lczyfz.edp.springboot.core.utils.MsgCodeUtils;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.lczyfz.edp.springboot.core.validation.Create;
import com.lczyfz.edp.springboot.core.validation.Update;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${apiPath}/exam/user")
@Api(value = "ExamUserController", tags = "考试管理系统用户接口")
public class ExamUserController extends BaseController {

    @Autowired
    ExamUserService examUserService;

    @ApiOperation(value = "新增用户", notes = "Request-DateTime样例：2018-09-29 11:26:20")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2022-10-20 16:36:24"),
            @ApiImplicitParam(name = "type",
                    value = "用户类型(0学生|1教师)",
                    paramType = "path",
                    dataType = "int",
                    required = true,
                    example = "0")
    })
    @RequestMapping(value = "{type}",method = RequestMethod.POST)
    public CommonResult create(@Validated({Create.class}) @RequestBody @ApiParam("系统用户VO") ExamUserVO vo, @PathVariable int type, BindingResult bindingResult) {
        CommonResult result = (new CommonResult()).init();
        // 参数验证
        if (bindingResult.hasErrors()) {
            return (CommonResult)result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        String role = null;
        if (type==0) {
            role = ExamUserService.ROLE_STUDENT;
        } else if (type==1) {
            role = ExamUserService.ROLE_TEACHER;
        } else {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN).end();
        }

        ExamUser examUser = new ExamUser();
        BeanCustomUtils.copyProperties(vo,examUser);
        examUser.setRole(role);

        if (0 < examUserService.save(examUser)) {
            result.success("examUser", examUser);
            logger.info("创建新用户: {}成功！", vo.toString());
        } else {
            result.error(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("创建新用户: {}失败！", vo.toString());
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "用户列表", notes = "Request-DateTime样例：2018-09-29 11:26:20")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime"
                    , value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2022-10-20 16:36:24")
    })
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public PageResult<ExamUser> examUserList(@Validated ExamUserPageVO pageVO, BindingResult bindingResult) {
        PageResult<ExamUser> result = new PageResult<ExamUser>().init();
        // 参数验证
        if (bindingResult.hasErrors()) {
            return (PageResult<ExamUser>) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        Page<ExamUser> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize(), pageVO.getOrderBy());
        result.success(examUserService.findPage(page,pageVO));
        return (PageResult<ExamUser>) result.end();
    }

    @ApiOperation(value = "获取单条用户信息", notes = "Request-DateTime样例：2018-09-29 11:26:20")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2022-10-20 16:36:24"),
            @ApiImplicitParam(
                    name = "id",
                    value = "用户编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171520")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public CommonResult select(@PathVariable String id) {
        CommonResult result = new CommonResult().init();
        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
        } else {
            ExamUser examUser = examUserService.get(id);
            logger.info(examUser.toString());
            result.success("examUser", examUser);
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "更新用户信息", notes = "Request-DateTime样例：2018-09-29 11:26:20")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2022-10-20 16:36:24"),
            @ApiImplicitParam(
                    name = "id",
                    value = "用户编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171520")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public CommonResult update(@PathVariable String id, @Validated(Update.class) @RequestBody @ApiParam("系统用户VO") ExamUserVO vo, BindingResult bindingResult) {
        CommonResult result = new CommonResult().init();
        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            return (CommonResult) result.end();
        }
        // 参数验证
        if (bindingResult.hasErrors()) {
            return (CommonResult)result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        ExamUser examUser = examUserService.get(id);
        BeanCustomUtils.copyProperties(vo, examUser);
        if (0 < examUserService.save(examUser)) {
            result.success("examUser",examUser);
            logger.info("更新用户信息：{}成功！",vo.toString());
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("更新用户信息：{}失败！",vo.toString());
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "删除用户信息", notes = "Request-DateTime样例：2018-09-29 11:26:20")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2022-10-20 16:36:24"),
            @ApiImplicitParam(
                    name = "id",
                    value = "用户编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171520")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public CommonResult delete(@PathVariable String id) {
        CommonResult result = new CommonResult().init();
        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            return (CommonResult) result.end();
        }
        ExamUser examUser = examUserService.get(id);
        if (0 < examUserService.delete(examUser)) {
            result.success();
            logger.info("删除用户信息：{}成功！", id);
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("删除用户信息：{}失败！", id);
        }
        return (CommonResult) result.end();
    }







}
