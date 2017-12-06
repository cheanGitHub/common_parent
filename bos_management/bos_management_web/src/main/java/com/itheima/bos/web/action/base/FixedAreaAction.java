package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Customer;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.service.base.FixedAreaService;
import org.apache.cxf.jaxrs.client.WebClient;
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

import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;
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
public class FixedAreaAction extends CommonAction<FixedArea> {

    @Autowired
    private FixedAreaService fixedAreaService;

    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> page = fixedAreaService.pageQuery(pageable);
        page2Json(page, new String[]{"couriers", "subareas"});
        return NONE;
    }

    @Action(value = "fixedAreaAction_findAll")
    public String findAll() throws IOException {
        List<FixedArea> list = fixedAreaService.pageQuery();
        list2Json(list, /*new String[] {"couriers"}*/ null);
        return NONE;
    }

    @Action(value = "fixedAreaAction_save", results = {@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save() throws IOException {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException {
        List<Customer> list = (List) WebClient.create("http://localhost:8090/crm/webService/customerService/findUnAssociatedCustomers")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        list2Json(list, null);

        return NONE;
    }

    @Action(value = "fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException {
        List<Customer> list = (List) WebClient.create("http://localhost:8090/crm/webService/customerService/findAssociatedCustomers")
                .query("fixedAreaId", getModel().getId())
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        list2Json(list, null);

        return NONE;
    }

    private List<Long> customerIds;

    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String assignCustomers2FixedArea() throws IOException {
        System.out.println(customerIds);
        WebClient.create("http://localhost:8090/crm/webService/customerService/assignCustomers2FixedArea")
                .query("fixedAreaId", getModel().getId().toString())
                .query("customerIds", customerIds)
                .put(null);  //PUT请求

        return SUCCESS;
    }

    private Long courierId;

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    private Long takeTimeId;

    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    @Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String associationCourierToFixedArea() {
        fixedAreaService.associationCourierToFixedArea(getModel().getId(), courierId, takeTimeId);
        return SUCCESS;
    }
}
