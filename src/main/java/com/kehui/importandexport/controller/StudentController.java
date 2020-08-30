package com.kehui.importandexport.controller;

import com.kehui.importandexport.entity.Student;
import com.kehui.importandexport.export.CsvExportUtil;
import com.kehui.importandexport.iimport.ExcelUtil;
import com.kehui.importandexport.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eternity
 * @create 2020-08-29 15:52
 */
@Controller
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 导出报表.xls格式
     * @return
     */
    @RequestMapping(value = "/exportXLS")
    @ResponseBody
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //获取数据
        List<Student> students = studentService.testSelectPage();

        //excel标题
        String[] title = {"编号","名称","出生日期","性别"};

        //excel文件名
        String fileName = "学生信息表"+System.currentTimeMillis()+".xls";

        //sheet名
        String sheetName = "学生信息表";
        String [][] content = new String[students.size()][];
        for (int i = 0; i < students.size(); i++) {
            content[i] = new String[title.length];
            Student obj = students.get(i);
            content[i][0] = obj.getId().toString();
            content[i][1] = obj.getName();
            content[i][2] = obj.getBirth();
            content[i][3] = obj.getSex();
            /*content[i][3] = obj.get("stuSchoolName").tostring();
            content[i][4] = obj.get("stuClassName").tostring();*/
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端 　　　　　　
        try {
             this.setResponseHeader(response, fileName);
             OutputStream os = response.getOutputStream();
             wb.write(os);
             os.flush();
             os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) { ex.printStackTrace(); }
    }

        //导出CSV格式
    @GetMapping("/exportCSV")
    public void exportCSV(HttpServletResponse response) throws Exception {

        // 查询需要导出的数据
        List<Student> students = studentService.testSelectPage();

        if (CollectionUtils.isEmpty(students)) {
           throw new Exception("导出数据失败请查询后重试！");
        }



        //CSV格式
        // 构造导出数据结构
        String titles = "编号,姓名,出生日期,性别";  // 设置表头
        String keys = "id,name,birth,sex";  // 设置每列字段

        // 构造导出数据
        List<Map<String, Object>> datas = new ArrayList<>();
        Map<String, Object> map = null;
        for (Student student : students) {
            map = new HashMap<>();
            map.put("id", student.getId());
            map.put("name", student.getName());
            map.put("birth", student.getBirth());
            map.put("sex", student.getSex());
            datas.add(map);
        }

        // 设置导出文件前缀
        String fName = "用户信息_";

        // 文件导出
        try {
            OutputStream os = response.getOutputStream();
            CsvExportUtil.responseSetProperties(fName, response);
            CsvExportUtil.doExport(datas, titles, keys, os);
            os.close();
        } catch (Exception e) {
            log.error("导出失败", e.getMessage());
           throw new Exception( "导出失败");
        }
    }

 /*   @GetMapping("/import")
    public void imporExcel() throws Exception {
        studentService.leading_in();
    }*/

    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ResponseBody
    public String  upload(@RequestParam(value="file",required = false) MultipartFile file) throws Exception {
        /*File file1 = new File("E:\\用户信息.xlsx");
        InputStream inputStream = new FileInputStream(file1);
        MultipartFile multipartFile = new MockMultipartFile(file1.getName(), inputStream);
        String result = studentService.readExcelFile(multipartFile);*/
        String result = studentService.readExcelFile(file);
        return result;
    }
}
