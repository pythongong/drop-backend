package com.drop.service.edu.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drop.common.service_base.exceptionhandler.DropException;
import com.drop.service.edu.entity.excel.ExcelSubjectData;
import com.drop.service.edu.entity.EduSubject;
import com.drop.service.edu.service.EduSubjectService;

public class ExcelSubjectListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService subjectService;

    public ExcelSubjectListener(){}

    public ExcelSubjectListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new DropException(20001, "文件数据为空");
        }

        EduSubject oneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (oneSubject == null){
            oneSubject = new EduSubject();
            oneSubject.setTitle(subjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            subjectService.save(oneSubject);
        }

        String pId = oneSubject.getId();
        EduSubject existTwoSubject =
                this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(), pId);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pId);
            subjectService.save(existTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject existOneSubject(EduSubjectService subjectService, String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        //条件判断的SQL语句，即WHERE语句。加一个以上，就会加AND
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        //getOne是不重复。
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }
}
