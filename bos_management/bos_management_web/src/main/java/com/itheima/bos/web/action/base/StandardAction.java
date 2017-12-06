package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
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

import java.io.IOException;
import java.util.List;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: Nov 28, 2017 8:49:00 PM <br/>
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class StandardAction extends CommonAction<Standard> {

    @Autowired
    private StandardService standardService;

    @Action(value = "standardAction_save", results = {@Result(name = "success",
            location = "/pages/base/standard.html", type = "redirect")})
    public String save() {
        standardService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> page = standardService.pageQuery(pageable);

        page2Json(page, null);

        return NONE;
    }

    @Action(value = "standardAction_findAll")
    public String findAll() throws IOException {
        List<Standard> list = standardService.pageQuery();
        list2Json(list, null);
        return NONE;
    }


}
