package com.kehui.importandexport.iimport;

import com.kehui.importandexport.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author eternity
 * @create 2020-08-30 16:01
 */
public class ExcelUtil {


    /**
     * Excel导入
     * @param myFile
     * @throws Exception
     */
    public static List<Student> getExcel(MultipartFile myFile) throws Exception{
        Workbook workbook = null;
//        String filename = myFile.getOriginalFilename();
        String filename = myFile.getName();
        InputStream inS = myFile.getInputStream();
        if (filename.endsWith("xls")) {//2003版本
            workbook = new HSSFWorkbook(inS);
        }else if (filename.endsWith("xlsx")) {//2007版本
            workbook = new XSSFWorkbook(inS);
        }else {
            throw new Exception("文件不是Excel文件");
        }
        //如有多个工作薄，workbook.getSheetAt(2);获取第三个工作薄
        Sheet sheet = workbook.getSheet("sheet1");
        //Row row1 = sheet.getRow(0);获取第一行
        //Cell cell = row1.getCell(6);获取第一行第六列信息
        int rows = sheet.getLastRowNum();//获取文件有多少行。不包含第一行
        //如需要获取多少列：sheet.getRow(0).getPhysicalNumberOfCells();这是获取第一行有多少列
        if (rows == 0) {
            throw new Exception("请确认上传文件数据是否为空！");
        }
        LinkedList<Student> students = new LinkedList<>();
        for (int i = 1; i <= rows+1; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {//该行不为空
                Student student = new Student();
                student.setName(getCellValue(row.getCell(0)));//第二列数据  名称
                student.setBirth(getCellValue(row.getCell(1)));//第二列数据  出生年月
                student.setSex(getCellValue(row.getCell(2)));//第三列数据  性别
                students.add(student);
            }
        }
        return students;
    }

    public static String getCellValue(Cell cell){
        String value = null;
        if (cell != null) {
            switch (cell.getCellType()) {//判断cell的类型
                case HSSFCell.CELL_TYPE_NUMERIC://数字
                    value = cell.getDateCellValue() + "";
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {//判断cell是不是时间
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        }else {
                            value = "";
                        }
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING://字符
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BLANK://空白
                    break;
                case HSSFCell.CELL_TYPE_ERROR://错误
                    break;
                default:
                    break;
            }
        }
        return value;
    }

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
}
