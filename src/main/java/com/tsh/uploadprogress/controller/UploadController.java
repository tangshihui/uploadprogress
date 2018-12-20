package com.tsh.uploadprogress.controller;

import java.io.File;
import java.io.IOException;

import com.tsh.uploadprogress.dto.InfoMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/upload")
public class UploadController {
    private static final String TMP_PATH = "e:/projects/tmp";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String fileUploadInit() {
        // InfoMsg infoMsg = new InfoMsg();

        return "upload";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public InfoMsg fileUpload(@RequestParam("uploadFile") MultipartFile file) {
        Long start = System.currentTimeMillis();
        InfoMsg infoMsg = new InfoMsg("","",null);
        if (file.isEmpty()) {
            infoMsg.setCode("error");
            infoMsg.setMsg("Please select a file to upload");
            return infoMsg;
        }

        try {
            File tmp = new File(TMP_PATH, file.getOriginalFilename());
            if (!tmp.getParentFile().exists()) {
                tmp.getParentFile().mkdirs();
            }
            file.transferTo(tmp);

            Long end = System.currentTimeMillis();

            System.out.println("上传耗时：" + (end - start) + "ms");

            infoMsg.setCode("success");
            infoMsg.setMsg("You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            infoMsg.setCode("error");
            infoMsg.setMsg("Uploaded file failed");
        }

        return infoMsg;
    }
}
