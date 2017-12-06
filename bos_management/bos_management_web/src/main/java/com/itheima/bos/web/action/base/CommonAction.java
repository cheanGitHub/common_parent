package com.itheima.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    private T model;

    public CommonAction() {
        // 获取到了具体的子类的字节码,AreaAction.class
        Class<? extends CommonAction> clazz = this.getClass();
        // 获取父类的字节码,BaseAction.class
        // Class<?> superclass = clazz.getSuperclass();
        // 获取到的是泛型父类,BaseAction<Area>
        Type genericSuperclass = clazz.getGenericSuperclass();
        // 强制类型转换为ParameterizedType
        ParameterizedType type = (ParameterizedType) genericSuperclass;
        // 获取泛型参数组成的数组
        Type[] actualTypeArguments = type.getActualTypeArguments();
        // 获取数组中的第一个,Area
        Type childType = actualTypeArguments[0];
        // 把获取到的Type强制类型转换为字节码
        Class<T> childClazz = (Class<T>) childType;
        try {
            model = childClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getModel() {
        return model;
    }

    protected int page; // 当前页码
    protected int rows; // 每一页显示多少条数据

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void page2Json(Page<T> page, String[] excludes) throws IOException {
        // 总数据条数
        long totalElements = page.getTotalElements();
        // 当前页面要显示的数据
        List<T> content = page.getContent();

        Map<String, Object> map = new HashMap<>();
        map.put("total", totalElements);
        map.put("rows", content);

        // 把map集合转换成json字符串

        // 指定要忽略的字段
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);

        // JSONObject : 把一个对象或者map集合转换成json字符串
        // JSONArray : 把数组或者list集合转换成json字符串
        String json = JSONObject.fromObject(map, config).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }

    public void list2Json(List list, String[] excludes) throws IOException {

        // 指定要忽略的字段
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);

        String json = JSONArray.fromObject(list, config).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);
    }


}
