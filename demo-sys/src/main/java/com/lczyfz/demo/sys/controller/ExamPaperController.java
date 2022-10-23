package com.lczyfz.demo.sys.controller;

import com.lczyfz.demo.sys.entity.ExamPaper;
import com.lczyfz.demo.sys.entity.ExamPaperSubject;
import com.lczyfz.demo.sys.service.ExamPaperService;
import com.lczyfz.demo.sys.service.ExamPaperSubjectService;
import com.lczyfz.demo.sys.service.ExamUserService;
import com.lczyfz.demo.sys.vo.*;
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

import java.util.List;

@RestController
@RequestMapping(value = "${apiPath}/exam/paper")
@Api(value = "ExamPaperController", tags = "考试管理系统试卷接口")
public class ExamPaperController extends BaseController {

    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    ExamUserService examUserService;
    @Autowired
    ExamPaperSubjectService examPaperSubjectService;

    @ApiOperation(value = "新建空试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    value = "密文用户名",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "04683B24138786A654BDF49D92F7CF203620D88171B035112E469198B32810F95E10BDA23F87D0B370EA4E66AD7D7266B4060D101CF276F93F382465210010599E0B47FEB94183F8428A8D1FCCFF30C9DEFAC9750FBA96D514CE08951FB903C5848A266F5C3A"
            )
    })
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult create(@Validated({Create.class}) @RequestBody @ApiParam("试卷VO") ExamPaperVO vo, BindingResult bindingResult,
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

        ExamPaper examPaper = new ExamPaper();
        BeanCustomUtils.copyProperties(vo,examPaper);
        examPaper.setCount(0);

        if (0 < examPaperService.save(examPaper)) {
            result.success("examPaper", examPaper);
            logger.info("创建新试卷: {}成功！", vo.toString());
        } else {
            result.error(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("创建新试卷: {}失败！", vo.toString());
        }
        return (CommonResult) result.end();
    }


    @ApiOperation(value = "修改试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    value = "试卷编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503341378827186176")

    })
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public CommonResult update(@PathVariable String id, @Validated(Update.class) @RequestBody @ApiParam("试卷VO") ExamPaperVO vo, BindingResult bindingResult,
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

        ExamPaper examPaper = examPaperService.get(id);
        BeanCustomUtils.copyProperties(vo,examPaper);
        if (0 < examPaperService.save(examPaper)) {
            result.success("examPaper",examPaper);
            logger.info("更新试卷信息：{}成功！",vo.toString());
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("更新试卷信息：{}失败！",vo.toString());
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "删除试卷信息", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    value = "试卷编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503341378827186176")
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
        ExamPaper examPaper = examPaperService.get(id);
        if (0 < examPaperService.delete(examPaper)) {
            result.success();
            logger.info("删除试卷信息：{}成功！", id);
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("删除试卷信息：{}失败！", id);
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获取单条试卷信息", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    value = "试卷编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503341378827186176")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public CommonResult select(@PathVariable String id, @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            logger.info("是老师");
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }
        logger.info("不是！");

        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
        } else {
            ExamPaper examPaper = examPaperService.get(id);
            logger.info(examPaper.toString());
            result.success("examPaper", examPaper);
            result.putItem("subjectList",examPaperSubjectService.getSubjectListByPaper(id));
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "试卷列表", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
    public PageResult<ExamPaper> examPaperList(@Validated ExamPaperPageVO pageVO, BindingResult bindingResult,
                                                 @RequestParam String username, @RequestParam String password) {
        PageResult<ExamPaper> result = new PageResult<ExamPaper>().init();
        // 参数验证
        if (bindingResult.hasErrors()) {
            return (PageResult<ExamPaper>) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (PageResult<ExamPaper>) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        Page<ExamPaper> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize(), pageVO.getOrderBy());
        result.success(examPaperService.findPage(page,pageVO));
        return (PageResult<ExamPaper>) result.end();
    }


    @ApiOperation(value = "选择题目组卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    value = "试卷编码id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171500"
            )
    })
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.POST)
    public CommonResult organizeSubjects(@PathVariable String id, @RequestBody @ApiParam(value = "题目编号",required = true) List<String> subjectList,
                                         @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
        } else {
            if (0 < examPaperService.organizeSubjectList(id,subjectList)) {
                result.success();
            } else {
                result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            }
        }
        return (CommonResult) result.end();
    }


}
