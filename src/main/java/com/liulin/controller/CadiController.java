package com.liulin.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.liulin.entity.CadiPdcaLeftKpiTarget;
import com.liulin.service.CadiService;
import com.liulin.util.CadiDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cadi")
public class CadiController {
    @Autowired
    private CadiService cadiService;
    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
            EasyExcel.read(file.getInputStream(),  new CadiDataListener(cadiService)).doReadAll();
        return "success";
    }
}
