package com.drop.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.service.edu.entity.EduSubject;
import com.drop.service.edu.entity.excel.ExcelSubjectData;
import com.drop.service.edu.entity.subject.OneSubject;
import com.drop.service.edu.entity.subject.TwoSubject;
import com.drop.service.edu.listener.ExcelSubjectListener;
import com.drop.service.edu.mapper.EduSubjectMapper;
import com.drop.service.edu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程分类 服务实现类
 * </p>
 *
 * @author alex
 * @since 2020-12-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            //sheet是指excel表的哪个sheet，可以在括号内设置名称。
            EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectListener(subjectService))
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getSubjects() {

        //查询所有一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper);

        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        //ne表示不等于
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //封装
        List<OneSubject> finalList = new ArrayList<>();

        for(EduSubject subject : oneSubjectList){
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);

            List<TwoSubject> finalTwoList = new ArrayList<>();
            for (EduSubject eduSubject : twoSubjectList){
                TwoSubject twoSubject = new TwoSubject();
                if(eduSubject.getParentId().equals(oneSubject.getId())){
                    BeanUtils.copyProperties(eduSubject, twoSubject);
                    finalTwoList.add(twoSubject);
                }
            }
            oneSubject.setChildren(finalTwoList);

            finalList.add(oneSubject);
        }

        return finalList;
    }
}
