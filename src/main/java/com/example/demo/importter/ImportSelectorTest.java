package com.example.demo.importter;

import exclude.ExcludeBean;
import exclude.ImportConf;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zengxi.song
 * @date 2023/11/7
 */
public class ImportSelectorTest implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{ExcludeBean.class.getName(), ImportConf.class.getName()};
    }
}
