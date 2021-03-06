package com.kehui.importandexport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kehui.importandexport.entity.Student;
import com.kehui.importandexport.entity.StudentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author eternity
 * @create 2020-08-29 16:14
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    List<StudentVO> selectStudentList();
}
