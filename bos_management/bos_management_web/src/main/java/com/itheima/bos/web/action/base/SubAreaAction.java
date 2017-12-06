package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.base.SubAreaService;
import com.itheima.bos.utils.base.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: Nov 30, 2017 8:06:01 PM <br/>
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class SubAreaAction extends CommonAction<SubArea> {

    @Autowired
    private SubAreaService subAeaService;

    @Action(value = "subAreaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<SubArea> page = subAeaService.pageQuery(pageable);
        page2Json(page, new String[]{"subareas"});
        return NONE;
    }

    @Action(value = "subAreaAction_findAll")
    public String findAll() throws IOException {
        List<SubArea> list = subAeaService.pageQuery();
        list2Json(list, null);
        return NONE;
    }

    @Action(value = "subAreaAction_save", results = {@Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect")})
    public String save() throws IOException {
        subAeaService.save(getModel());
        return SUCCESS;
    }

}
