package com.kehui.importandexport.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kehui.importandexport.entity.StudentVO;
import com.kehui.importandexport.mapper.StudentMapper;
import com.kehui.importandexport.mapper.StudentVOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eternity
 * @create 2020-08-29 16:13
 */
@Service
public class StudentService{

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentVOMapper studentVOMapper;

    /**
     * 分页查询
     */
   /* public List<Student> testSelectPage() {

        *//*Page<Student> page = new Page<>(1,3);
        IPage<Student> students = studentMapper.selectPage(page, null);
        students.

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());

        return students;*//*
        List<Student> students = studentMapper.selectList(null);
        if (students.isEmpty()) System.out.println("查询数据失败！");
        return students;
    }*/
//
    /**
     * 上传文件存入数据库
     * @param file
     * @return
     */
//    public String readExcelFile(MultipartFile file) throws Exception {
//        String result ="";
//        /*//创建处理EXCEL的类
//        XLSXImportExcelUtil readExcel=new XLSXImportExcelUtil();
//        //解析excel，获取上传的事件单
//        List<Student> students = readExcel.getExcelInfo(file);*/
//        List<Student> students = ExcelUtil.getExcel(file);
//        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
//        for (Student student : students) {
//            studentMapper.insert(student);
//        }
//        //和你具体业务有关,这里不做具体的示范
//        if(students != null && !students.isEmpty()){
//            result = "上传成功";
//        }else{
//            result = "上传失败";
//        }
//        return result;
//    }

//    public void deleteMap(){
//        Map map = new HashMap<String, Object>();
//        map.put("name","张三");
//        int i = studentMapper.deleteByMap(map);
//        System.out.println(i);
//    }

    public IPage<StudentVO> selectStudentList() {
        List<StudentVO> studentVOS = studentMapper.selectStudentList();
        Page<StudentVO> page = new Page<>(1,3);
        IPage<StudentVO> studentVOIPage = studentVOMapper.selectPage(page, new QueryWrapper<StudentVO>());

        return studentVOIPage;
    }
}
