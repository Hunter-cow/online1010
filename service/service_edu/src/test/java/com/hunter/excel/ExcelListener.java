package com.hunter.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    //一行一行读取
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("****:"+data);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头内容:"+headMap);
    }

    //读取表头
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
