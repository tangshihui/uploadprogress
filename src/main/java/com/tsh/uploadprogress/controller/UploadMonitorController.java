package com.tsh.uploadprogress.controller;

import com.tsh.uploadprogress.dto.InfoMsg;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UploadMonitorController {

    @RequestMapping("/upload/progress")
    @ResponseBody
    public InfoMsg getProgerss(HttpSession session) {
        if (session == null) {
            return new InfoMsg("0","获取进度成功", "0%");
        }
        //取出监听器MyProgress在session中保存的进度信息
        String progress = (String) session.getAttribute("progress");
        if (StringUtils.isEmpty(progress) || "null".equals(progress)) {
            progress = "0%";
            session.setAttribute("progress", progress);
        }
        System.out.println("上传进度：" + progress);

        return new InfoMsg("0", "获取进度成功", progress);
    }

    @PostMapping("/upload/progress/reset")
    @ResponseBody
    public String setProgress(HttpSession session) {
        if (session == null) {
            return "";
        }

        session.setAttribute("progress", "0%");
        String msg = "重置上传进度成功";
        System.out.println(msg);
        return msg;
    }
}
