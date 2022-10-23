package com.lczyfz.demo.sys.controller;

import com.lczyfz.demo.sys.entity.ExamSubject;
import com.lczyfz.demo.sys.entity.ExamUser;
import com.lczyfz.demo.sys.service.ExamSubjectService;
import com.lczyfz.demo.sys.service.ExamUserService;
import com.lczyfz.demo.sys.vo.ExamSubjectPageVO;
import com.lczyfz.demo.sys.vo.ExamSubjectVO;
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
@RequestMapping(value = "${apiPath}/exam/subject")
@Api(value = "ExamSubjectController", tags = "考试管理系统题目接口")
public class ExamSubjectController extends BaseController {

    @Autowired
    ExamSubjectService examSubjectService;
    @Autowired
    ExamUserService examUserService;

    @ApiOperation(value = "新建题目", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "2022-10-20 16:35:32"),
            @ApiImplicitParam(
                    name = "username",
                    value = "密文用户名(获取token同款Bcd加密方式)",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "049E2D2898405813FA065566E40B37FEFA853C79123704D655D17DD311C0B77AE67044A05BDC821FB55A0457D057A36062820EB5FB6B9AC9027E5AE81E083A603C7D725AB3AAD84038E47B3302762C597F4B15920FF8876B8C43B03C2DA8D76B82050140"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密文用户名",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "04683B24138786A654BDF49D92F7CF203620D88171B035112E469198B32810F95E10BDA23F87D0B370EA4E66AD7D7266B4060D101CF276F93F382465210010599E0B47FEB94183F8428A8D1FCCFF30C9DEFAC9750FBA96D514CE08951FB903C5848A266F5C3A"
            )
    })
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult create(@Validated({Create.class}) @RequestBody @ApiParam("系统题目VO") ExamSubjectVO vo, BindingResult bindingResult,
                               @RequestParam String username, @RequestParam String password) {
        CommonResult result = (new CommonResult()).init();
        // 参数验证
        if (bindingResult.hasErrors()) {
            return (CommonResult)result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        ExamSubject examSubject = new ExamSubject();
        BeanCustomUtils.copyProperties(vo,examSubject);

        if (0 < examSubjectService.save(examSubject)) {
            result.success("examSubject", examSubject);
            logger.info("创建新题目: {}成功！", vo.toString());
        } else {
            result.error(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("创建新题目: {}失败！", vo.toString());
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "修改题目", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "2022-10-20 16:35:32"),
            @ApiImplicitParam(
                    name = "username",
                    value = "密文用户名(获取token同款Bcd加密方式)",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "049E2D2898405813FA065566E40B37FEFA853C79123704D655D17DD311C0B77AE67044A05BDC821FB55A0457D057A36062820EB5FB6B9AC9027E5AE81E083A603C7D725AB3AAD84038E47B3302762C597F4B15920FF8876B8C43B03C2DA8D76B82050140"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密文密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "04683B24138786A654BDF49D92F7CF203620D88171B035112E469198B32810F95E10BDA23F87D0B370EA4E66AD7D7266B4060D101CF276F93F382465210010599E0B47FEB94183F8428A8D1FCCFF30C9DEFAC9750FBA96D514CE08951FB903C5848A266F5C3A"
            ),
            @ApiImplicitParam(
                    name = "id",
                    value = "题目编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "502995575516426240")

    })
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public CommonResult update(@PathVariable String id, @Validated(Update.class) @RequestBody @ApiParam("题目VO") ExamSubjectVO vo, BindingResult bindingResult,
                               @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 参数验证
        if (bindingResult.hasErrors()) {
            return (CommonResult)result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            return (CommonResult) result.end();
        }
        ExamSubject examSubject = examSubjectService.get(id);
        BeanCustomUtils.copyProperties(vo,examSubject);
        if (0 < examSubjectService.save(examSubject)) {
            result.success("examSubject",examSubject);
            logger.info("更新题目信息：{}成功！",vo.toString());
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("更新题目信息：{}失败！",vo.toString());
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "删除题目信息", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    name = "username",
                    value = "密文用户名(获取token同款Bcd加密方式)",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "049E2D2898405813FA065566E40B37FEFA853C79123704D655D17DD311C0B77AE67044A05BDC821FB55A0457D057A36062820EB5FB6B9AC9027E5AE81E083A603C7D725AB3AAD84038E47B3302762C597F4B15920FF8876B8C43B03C2DA8D76B82050140"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密文密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "04683B24138786A654BDF49D92F7CF203620D88171B035112E469198B32810F95E10BDA23F87D0B370EA4E66AD7D7266B4060D101CF276F93F382465210010599E0B47FEB94183F8428A8D1FCCFF30C9DEFAC9750FBA96D514CE08951FB903C5848A266F5C3A"
            ),
            @ApiImplicitParam(
                    name = "id",
                    value = "题目编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "502995575516426240")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public CommonResult delete(@PathVariable String id, @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            return (CommonResult) result.end();
        }
        ExamSubject examSubject = examSubjectService.get(id);
        if (0 < examSubjectService.delete(examSubject)) {
            result.success();
            logger.info("删除题目信息：{}成功！", id);
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("删除题目信息：{}失败！", id);
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获取单条题目信息", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    name = "username",
                    value = "密文用户名(获取token同款Bcd加密方式)",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "049E2D2898405813FA065566E40B37FEFA853C79123704D655D17DD311C0B77AE67044A05BDC821FB55A0457D057A36062820EB5FB6B9AC9027E5AE81E083A603C7D725AB3AAD84038E47B3302762C597F4B15920FF8876B8C43B03C2DA8D76B82050140"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密文密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "04683B24138786A654BDF49D92F7CF203620D88171B035112E469198B32810F95E10BDA23F87D0B370EA4E66AD7D7266B4060D101CF276F93F382465210010599E0B47FEB94183F8428A8D1FCCFF30C9DEFAC9750FBA96D514CE08951FB903C5848A266F5C3A"
            ),
            @ApiImplicitParam(
                    name = "id",
                    value = "题目编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "502995575516426240")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public CommonResult select(@PathVariable String id, @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
        } else {
            ExamSubject examSubject = examSubjectService.get(id);
            logger.info(examSubject.toString());
            result.success("examSubject", examSubject);
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "题目列表", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    name = "username",
                    value = "密文用户名(获取token同款Bcd加密方式)",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "049E2D2898405813FA065566E40B37FEFA853C79123704D655D17DD311C0B77AE67044A05BDC821FB55A0457D057A36062820EB5FB6B9AC9027E5AE81E083A603C7D725AB3AAD84038E47B3302762C597F4B15920FF8876B8C43B03C2DA8D76B82050140"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密文密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "04683B24138786A654BDF49D92F7CF203620D88171B035112E469198B32810F95E10BDA23F87D0B370EA4E66AD7D7266B4060D101CF276F93F382465210010599E0B47FEB94183F8428A8D1FCCFF30C9DEFAC9750FBA96D514CE08951FB903C5848A266F5C3A"
            )
    })
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public PageResult<ExamSubject> examSubjectList(@Validated ExamSubjectPageVO pageVO, BindingResult bindingResult,
                                                @RequestParam String username, @RequestParam String password) {
        PageResult<ExamSubject> result = new PageResult<ExamSubject>().init();
        // 参数验证
        if (bindingResult.hasErrors()) {
            return (PageResult<ExamSubject>) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (PageResult<ExamSubject>) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        Page<ExamSubject> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize(), pageVO.getOrderBy());
        result.success(examSubjectService.findPage(page,pageVO));
        return (PageResult<ExamSubject>) result.end();
    }







}
