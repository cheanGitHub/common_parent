package com.itheima.crm.service;

import com.itheima.crm.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
访问路径
    服务器ip:端口号/项目名/cxfServlet指定的路径/Spring配置文件指定的路径
    /对应bean类上@Path指定的路径/方法上@Path指定的路径*/
public interface CustomerService {

/*
1)	请求方式
    @GET： 查询操作
    @POST： 添加操作
    @DELETE ： 删除操作
    @PUT: 修改操作

2)	路径
    @Path : 设置访问路径. 可以用在方法 \ 类上面, 最终路径为两个的叠加

3)	数据格式
    @Consumers： 定义方法参数类型,常用值为 : “application/xml”, “application/json”
    @Producers： 定义方法返回值类型,常用值为 : “application/xml”, “application/json”

4)	参数
    @QueryParam : 查询参数. 客户端传参：url?id=10
    @PathParam : 路径参数. 客户端传参：url/10
*/


    @POST
    @Path("save")
    @Consumes({MediaType.APPLICATION_JSON})
    public void save(Customer customer);

    @GET
    @Path("findAll")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Customer> findAll();

    @GET
    @Path("findUnAssociatedCustomers")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Customer> findUnAssociatedCustomers();

    @GET
    @Path("findAssociatedCustomers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    List<Customer> findAssociatedCustomers(@QueryParam("fixedAreaId") String fixedAreaId);

    @PUT
    @Path("assignCustomers2FixedArea")
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON})
    void assignCustomers2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId, @QueryParam("customerIds") List<Long> customerIds);

}
