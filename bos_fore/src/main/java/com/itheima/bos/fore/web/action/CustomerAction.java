package com.itheima.bos.fore.web.action;

import com.itheima.bos.fore.domain.Customer;
import com.itheima.bos.utils.base.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

    private Customer model = new Customer();

    @Override
    public Customer getModel() {
        return model;
    }

    @Action("customerAction_sendSms")
    public String sendSms() {
        String telephone = getModel().getTelephone();
        String code = RandomStringUtils.randomNumeric(4);
        ServletActionContext.getRequest().getSession().setAttribute(telephone, code);
        System.out.println("telephone = " + telephone + "\tcode = " + code);
        //SmsUtils.sendSmsByWebService(telephone, "尊敬的客户你好，您本次获取的验证码为：" + code);
        return NONE;
    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "/signup-success.html", type = "redirect"),
                    @Result(name = "error", location = "/signup-fail.html", type = "redirect")
            })
    public String regist() {
        String session_code = (String) ServletActionContext.getRequest().getSession().getAttribute(getModel().getTelephone());
        if (StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(session_code) && checkcode.equals(session_code)) {
            WebClient.create("http://localhost:8090/crm/webService/customerService/save")
                    .type(MediaType.APPLICATION_JSON)
                    .post(model);
            return SUCCESS;
        }
        return ERROR;
    }
}
