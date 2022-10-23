package com.lczyfz.demo.sys.service;

import org.springframework.stereotype.Service;
import com.lczyfz.edp.springboot.core.entity.MailMessage;
import com.lczyfz.edp.springboot.core.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class MyMailService extends MailService {

    //设置返回日期格式
    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void sendOverTimeMail(String sendTo,String sendMsg) {
        String time=sdf.format(new Date());
        MailMessage mailMessage = new MailMessage();
        mailMessage.setSendTo(sendTo);
        mailMessage.setContent(sendMsg+"\n"+time);
        mailMessage.setSubject("[考试管理系统] 消息提醒");
        log.info(getFrom());
        this.sendMessageCarryFile(mailMessage);
    }

}
