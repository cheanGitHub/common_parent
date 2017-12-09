package com.itheima.bos.fore.web.action;

import com.itheima.bos.fore.domain.Customer;
import com.itheima.bos.utils.base.MailUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

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

    /* 发送 验证码 短信 */
    @Action("customerAction_sendSms")
    public String sendSms() {
        String telephone = getModel().getTelephone();
        String code = RandomStringUtils.randomNumeric(4);
        ServletActionContext.getRequest().getSession().setAttribute(telephone, code);
        System.out.println("telephone = " + telephone + "\tcode = " + code);
        //SmsUtils.sendSmsByWebService(telephone, "尊敬的客户你好，您本次获取的验证码为：" + code);
        return NONE;
    }

    /* 注册 */
    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Autowired
    private RedisTemplate redisTemplate;

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

            //生成邮件激活码 , 发送激活邮件
            String activeCode = RandomStringUtils.randomNumeric(32);

            redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 2, TimeUnit.DAYS);

            String email = model.getEmail();
            String telephone = model.getTelephone();
            if (StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(telephone)) {
                MailUtils.sendMail(email, "速运物流系统 激活邮件", "感谢注册, 请在两天内点击<a href='http://localhost:8100/bos_fore/customerAction_active.action?telephone=" + telephone + "&activeCode=" + activeCode + "'>链接</a>激活账户!");
            } else {
                return ERROR;
            }
            return SUCCESS;
        }
        return ERROR;
    }

    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Action(value = "customerAction_active",
            results = {
                    @Result(name = "success", location = "/login.html", type = "redirect"),
                    @Result(name = "error", location = "/index.html", type = "redirect")
            })
    public String active() {
        String telephone = model.getTelephone();
        String activeCode_redis = (String) redisTemplate.opsForValue().get(telephone);
        if (StringUtils.isNotEmpty(activeCode) && StringUtils.isNotEmpty(activeCode_redis) && activeCode_redis.equals(activeCode)) {
            Customer customer = WebClient.create("http://localhost:8090/crm/webService/customerService/findByTelephone")
                    .query("telephone", telephone)
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(Customer.class);

            if (customer != null && customer.getType() != null && customer.getType() != 1) {
                customer.setType(1);
                WebClient.create("http://localhost:8090/crm/webService/customerService/save")
                        .type(MediaType.APPLICATION_JSON)
                        .post(customer);
                return SUCCESS;
            }
        }
        return ERROR;
    }


    @Action(value = "customerAction_login",
            results = {
                    @Result(name = "success", location = "/userinfo.html", type = "redirect"),
                    @Result(name = "error", location = "/login.html", type = "redirect")
            })
    public String login() {
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        if (StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(validateCode) && validateCode.equalsIgnoreCase(checkcode)) {
            Customer customer = WebClient.create("http://localhost:8090/crm/webService/customerService/findByTelephone")
                    .query("telephone", model.getTelephone())
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(Customer.class);
            if (customer != null && StringUtils.isNotEmpty(customer.getPassword()) && customer.getPassword().equals(model.getPassword())) {
                ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
                return SUCCESS;
            }
        }
        return ERROR;
    }


}
