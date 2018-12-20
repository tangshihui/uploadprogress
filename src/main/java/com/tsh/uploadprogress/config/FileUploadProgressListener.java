package com.tsh.uploadprogress.config;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;

@Component
public class FileUploadProgressListener implements ProgressListener {

    private HttpSession session;

    public void setSession(HttpServletRequest request) {
        session = request.getSession();
    }

    @Override
    public void update(long read, long total, int items) {
        //将数据进行格式化
        //已读取数据由字节转换为M
        //double readM = pBytesRead / 1024.0 / 1024.0;
        //已读取数据由字节转换为M
        //double totalM = pContentLength / 1024.0 / 1024.0;
        //已读取百分百
        //double percent = readM / totalM;
        double percent = read*1.0 / total*1.0;

        //进度百分百
        NumberFormat format = NumberFormat.getPercentInstance();
        String newProgress = format.format(percent);

        String sessionProgress = (String) session.getAttribute("progress");
        if (StringUtils.isEmpty(sessionProgress)){
            session.setAttribute("progress", "0%");
        }

        if (!newProgress.equals(sessionProgress)) {
            session.setAttribute("progress", newProgress);
            System.out.println("文件上传进度 read:[" + read + "] total:[" + total + "] progress:[" + newProgress + "] ");
        }

    }

    /**
     * 格式化读取数据的显示
     *
     * @param data 要格式化的数据 单位byte
     * @return 格式化后的数据，如果小于1M显示单位为KB，如果大于1M显示单位为M
     */
    public String dataFormat(double data) {
        String formdata = "";
        if (data >= 1024 * 1024) {//大于等于1M
            formdata = Double.toString(data / 1024 / 1024) + "M";
        } else if (data >= 1024) {//大于等于1KB
            formdata = Double.toString(data / 1024) + "KB";
        } else {//小于1KB
            formdata = Double.toString(data) + "byte";
        }
        return formdata.substring(0, formdata.indexOf(".") + 2);
    }
}
