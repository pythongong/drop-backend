package com.drop.service.edu.service;

import com.drop.service.edu.entity.subject.OneSubject;
import com.drop.service.edu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程分类 服务类
 * </p>
 *
 * @author alex
 * @since 2020-12-07
 */
public interface EduSubjectService extends IService<EduSubject> {

    void importSubjectData(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getSubjects();
}
