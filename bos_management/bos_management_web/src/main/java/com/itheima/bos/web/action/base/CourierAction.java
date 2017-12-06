package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
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
public class CourierAction extends CommonAction<Courier> {


    @Autowired
    private CourierService courierService;

    @Action(value = "courierAction_save", results = {@Result(name = "success",
            location = "/pages/base/courier.html", type = "redirect")})
    public String save() {
        courierService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);

        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(getModel().getCourierNum())) {
                    Predicate p1 = cb.like(root.get("courierNum").as(String.class), getModel().getCourierNum());
                    list.add(p1);
                }

                if (StringUtils.isNotEmpty(getModel().getCompany())) {
                    Predicate p2 = cb.like(root.get("company").as(String.class), getModel().getCompany());
                    list.add(p2);
                }

                if (StringUtils.isNotEmpty(getModel().getType())) {
                    Predicate p3 = cb.equal(root.get("type").as(String.class), getModel().getType());
                    list.add(p3);
                }

                Standard standard = getModel().getStandard();
                if (standard != null && StringUtils.isNotEmpty(standard.getName())) {
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.like(join.get("name").as(String.class), standard.getName());
                    list.add(p4);
                }

                Predicate[] ps = new Predicate[list.size()];
                list.toArray(ps);
                return cb.and(ps);
            }
        };
        Page<Courier> page = courierService.pageQuery(specification, pageable);
        page2Json(page, new String[]{"fixedAreas"});
        return NONE;
    }

    @Action(value = "courierAction_findAll")
    public String findAll() throws IOException {
        List<Courier> list = courierService.pageQuery();
        list2Json(list, new String[] {"fixedAreas"} /*null*/);
        return NONE;
    }

    @Action(value = "courierAction_findByDeltagIsNull")
    public String findByDeltagIsNull() throws IOException {
        List<Courier> list = courierService.findByDeltagIsNull();
        list2Json(list, new String[] {"fixedAreas"} /*null*/);
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courierAction_bathDel",
            results = {@Result(name = "success",
                    location = "/pages/base/courier.html", type = "redirect")})
    public String bathDel() {
        courierService.bathDel(ids.split(","));
        return SUCCESS;
    }
}