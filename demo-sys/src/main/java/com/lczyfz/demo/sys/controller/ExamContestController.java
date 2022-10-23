package com.lczyfz.demo.sys.controller;


import com.lczyfz.demo.sys.entity.ExamPaper;
import com.lczyfz.demo.sys.entity.ExamUser;
import com.lczyfz.demo.sys.service.ExamPaperService;
import com.lczyfz.demo.sys.service.ExamPaperStudentService;
import com.lczyfz.demo.sys.service.ExamUserService;
import com.lczyfz.edp.springboot.core.controller.BaseController;
import com.lczyfz.edp.springboot.core.entity.CommonResult;
import com.lczyfz.edp.springboot.core.entity.PageResult;
import com.lczyfz.edp.springboot.core.utils.MsgCodeUtils;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${apiPath}/exam/contest")
@Api(value = "ExamContestController", tags = "考试管理系统考试接口")
public class ExamContestController extends BaseController {

    @Autowired
    ExamUserService examUserService;
    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    ExamPaperStudentService examPaperStudentService;

    @ApiOperation(value = "下发试卷给学生", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
            ),
            @ApiImplicitParam(
                    name = "id",
                    value = "试卷编码",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171500"
            )
    })
    @RequestMapping(value = "/handout/{id}", method = RequestMethod.POST)
    public CommonResult handoutPaper(@PathVariable String id, @RequestBody @ApiParam(value = "学生编号",required = true) List<String> studentList,
                                     @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
        } else {
            if (0 < examPaperService.handoutPapersToStudents(id,studentList)) {
                result.success();
            } else {
                result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            }
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "撤回下发的试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
            ),
            @ApiImplicitParam(
                    name = "id",
                    value = "试卷编码",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171500"
            )
    })
    @RequestMapping(value = "/handout/{id}", method = RequestMethod.DELETE)
    public CommonResult withdrawPaper(@PathVariable String id,
                                      @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
        if (!examUserService.isTeacher(username,password)) {
            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
        }

        if (StringUtils.isBlank(id)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            return (CommonResult) result.end();
        }

        if (0 < examPaperService.withdrawPapersFromStudent(id)) {
            result.success();
            logger.info("撤回下发试卷信息：{}成功！", id);
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("撤回下发试卷信息：{}失败！", id);
        }
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "查看拥有的试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
            ),
            @ApiImplicitParam(
                    name = "id",
                    value = "试卷编号id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171520"
            ),
            @ApiImplicitParam(
                    value = "当前页码",
                    name = "pageNo",
                    example = "1",
                    required = true
            ),
            @ApiImplicitParam(
                    value = "每页数量",
                    name = "pageSize",
                    example = "20",
                    required = true
            ),
            @ApiImplicitParam(
                    value = "排序方式",
                    name = "orderBy",
                    example = " update_date desc",
                    required = false
            )
    })
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    public PageResult<ExamPaper> paperList(@PathVariable String id,
                                           @RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String orderBy,
                                           @RequestParam String username, @RequestParam String password) {
        PageResult<ExamPaper> result = new PageResult<ExamPaper>().init();
        logger.info(id);
        if (examUserService.isTeacher(username,password)) {
            result.success(examPaperService.getPageByTeacher(id, pageNo, pageSize, orderBy));
        } else if (examUserService.isStudent(username,password)) {
            result.success(examPaperStudentService.getPageByStudent(id, pageNo, pageSize, orderBy));
        } else {
            return (PageResult<ExamPaper>) result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN).end();
        }
        return (PageResult<ExamPaper>) result.end();
    }

    @ApiOperation(value = "开始作答试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
            ),
            @ApiImplicitParam(
                    name = "paper",
                    value = "试卷编号",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171500"
            ),
            @ApiImplicitParam(
                    name = "student",
                    value = "学生编号",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "502588669157175296"
            )
    })
    @RequestMapping(value = "/start/{paper}/{student}", method = RequestMethod.POST)
    public CommonResult startPaper(@PathVariable String paper, @PathVariable String student,
                                    @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
//        if (!examUserService.isStudent(username,password)) {
//            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
//        }

        if (StringUtils.isBlank(paper) || StringUtils.isBlank(student)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            return (CommonResult) result.end();
        }

        if (0 < examPaperStudentService.startPaper(paper,student)) {
            result.success();
            logger.info("{}学生开始作答试卷{}：成功！", student, paper);
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("{}学生开始作答试卷{}：失败！", student, paper);
        }
        return (CommonResult) result.end();

    }

    @ApiOperation(value = "提交试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
            ),
            @ApiImplicitParam(
                    name = "paper",
                    value = "试卷编号",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "503242351880171500"
            ),
            @ApiImplicitParam(
                    name = "student",
                    value = "学生编号",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "502588669157175296"
            )
    })
    @RequestMapping(value = "/submit/{paper}/{student}", method = RequestMethod.POST)
    public CommonResult submitPaper(@PathVariable String paper, @PathVariable String student,
                                    @RequestParam String username, @RequestParam String password) {
        CommonResult result = new CommonResult().init();
        // 权限验证
//        if (!examUserService.isStudent(username,password)) {
//            return (CommonResult) result.fail(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
//        }

        if (StringUtils.isBlank(paper) || StringUtils.isBlank(student)) {
            result.fail(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            return (CommonResult) result.end();
        }

        if (0 < examPaperStudentService.submitPaper(paper,student)) {
            result.success();
            logger.info("{}学生提交试卷{}：成功！", student, paper);
        } else {
            result.fail(MsgCodeUtils.MSG_CODE_UNKNOWN);
            logger.info("{}学生提交试卷{}：失败！", student, paper);
        }
        return (CommonResult) result.end();

    }


}
