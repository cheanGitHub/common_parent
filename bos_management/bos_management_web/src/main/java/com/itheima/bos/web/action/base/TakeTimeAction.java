package com.itheima.bos.web.action.base;

import com.itheima.bos.base.service.TakeTimeService;
import com.itheima.bos.domain.base.TakeTime;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: Nov 30, 2017 8:39:00 AM <br/>
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class TakeTimeAction extends CommonAction<TakeTime> {


    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value = "takeTimeAction_save", results = {@Result(name = "success",
            location = "/pages/base/takeTime.html", type = "redirect")})
    public String save() {
        takeTimeService.save(getModel());
        return SUCCESS;
    }


    @Action(value = "takeTimeAction_findAll")
    public String findAll() throws IOException {
        List<TakeTime> list = takeTimeService.pageQuery();
        list2Json(list, null);
        return NONE;
    }

}