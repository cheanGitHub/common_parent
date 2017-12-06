package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.utils.base.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
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
public class AreaAction extends CommonAction<Area> {

    @Autowired
    private AreaService areaService;

    private File areaFile;

    public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }

    @Action(value = "areaAction_importXLS")
    public String importXLS() throws IOException, InvalidFormatException {
        List<Area> list = new ArrayList<Area>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(areaFile));
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        for (Row row : hssfSheet) {

            // 跳过第一行
            if (row.getRowNum() == 0) {
                continue;
            }

            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            // 截掉省市区的最后一个字符
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            // 生成城市编码
            String citycode =
                    PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
            // 生成简码
            String[] headByString = PinYin4jUtils
                    .getHeadByString(province + city + district);
            String shortcode =
                    PinYin4jUtils.stringArrayToString(headByString);

            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            area.setPostcode(postcode);
            area.setCitycode(citycode);
            area.setShortcode(shortcode);

            // 添加到集合
            list.add(area);
        }

        areaService.save(list);

        return NONE;
    }

    @Action(value = "areaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.pageQuery(pageable);
        page2Json(page, new String[] {"subareas"});
        return NONE;
    }

    private String q;

    public void setQ(String q) {
        this.q = q;
    }

    @Action(value = "areaAction_findAll")
    public String findAll() throws IOException {
        List<Area> list = null;
        if (StringUtils.isEmpty(q)) {
            list = areaService.pageQuery();
        }else {
            list = areaService.findByQ(q);
        }
        list2Json(list, new String[] {"subareas"});
        return NONE;
    }

}
