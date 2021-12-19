package com.v.im.user.controller;

import com.v.im.common.utils.ChatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 文件上传控制器
 *
 * @author 乐天
 * @since 2018-10-07
 */
@Controller
@RequestMapping("/api/oss")
public class UploadController {

    @Value("${web.upload-path}")
    private String uploadPath;

    /**
     * 上传接口
     *
     * @param file    文件
     * @param request 请求
     * @return json
     */
    @ResponseBody
    @RequestMapping(value = "/upload",method = {OPTIONS,POST})
    public Map<String, String> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String,String> res = new HashMap<>();
        try {
            String host = ChatUtils.getHost(request);
            String name = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "." +name.substring(name.lastIndexOf(".") + 1);
            File targetFile = new File(uploadPath);
            if (!targetFile.exists()) {
                if (!targetFile.mkdirs()) {
                    res.put("msg", "error");
                    return res;
                }
            }
            File tempFile = new File(uploadPath, fileName);
            file.transferTo(tempFile);
            res.put("msg", "success");
            res.put("filePath", host + "/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "error");
            return res;
        }
        return res;
    }
}
