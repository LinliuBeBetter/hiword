package com.liulin.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.util.ListUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liulin.dao.CadiDao;
import com.liulin.entity.CadiPdcaLeftKpiTarget;
import com.liulin.service.CadiService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;


// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class CadiDataListener extends AnalysisEventListener<Map<Integer, String>> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<Map<Integer, Map<Integer, String>>> list = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private CadiService cadiService;

    public CadiDataListener() {

    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param
     */
    public CadiDataListener(CadiService cadiService) {
        this.cadiService = cadiService;
    }


    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        ReadRowHolder readRowHolder = context.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        String sheetName = readSheetHolder.getSheetName();
        if(sheetName.equals("CT5")||sheetName.equals("XT5")){
        }else{
            if(!(rowIndex >1)){
                return;
            }
        }
        Map<Integer, Map<Integer, String>> map = new HashMap<>();
        log.info("解析到一条数据：{}, currentRowIndex: {}----", data.toString(), context.readRowHolder().getRowIndex());
        map.put(context.readRowHolder().getRowIndex(), data);
        list.add(map);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData(context);
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData(context);
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData(AnalysisContext context) {
         String sheetName = context.readSheetHolder().getSheetName();
         ArrayList<CadiPdcaLeftKpiTarget> cadiPdcaLeftKpiTargets = new ArrayList<>();
        if(sheetName.equals("CT5")||sheetName.equals("XT5")){
            for(int i=0;i<list.size();i++){
                Map<Integer, Map<Integer, String>> map = list.get(i);
                Map<Integer, String> targetMap = map.get(i + 1);
                CadiPdcaLeftKpiTarget cadiPdcaLeftKpiTarget = new CadiPdcaLeftKpiTarget();
                cadiPdcaLeftKpiTarget.setSeries_or_major_issue(sheetName);
                cadiPdcaLeftKpiTarget.setWeek("2022-10-3");
                cadiPdcaLeftKpiTarget.setLabel(targetMap.get(0));
                cadiPdcaLeftKpiTarget.setCurrent_month_target_value(targetMap.get(2));
                cadiPdcaLeftKpiTargets.add(cadiPdcaLeftKpiTarget);
            }
        }else{
            for(int i=0;i<list.size();i++){
                Map<Integer, Map<Integer, String>> map = list.get(i);
                Map<Integer, String> targetMap = map.get(i + 2);
                CadiPdcaLeftKpiTarget cadiPdcaLeftKpiTarget = new CadiPdcaLeftKpiTarget();
                cadiPdcaLeftKpiTarget.setSeries_or_major_issue(sheetName);
                cadiPdcaLeftKpiTarget.setWeek("2022-10-3");
                cadiPdcaLeftKpiTarget.setLabel(targetMap.get(0));
                cadiPdcaLeftKpiTarget.setCurrent_month_target_value(targetMap.get(3));
                cadiPdcaLeftKpiTarget.setWeek1_target_value(targetMap.get(4));
                cadiPdcaLeftKpiTarget.setWeek2_target_value(targetMap.get(5));
                cadiPdcaLeftKpiTarget.setWeek3_target_value(targetMap.get(6));
                cadiPdcaLeftKpiTarget.setWeek4_target_value(targetMap.get(7));
                cadiPdcaLeftKpiTarget.setWeek5_target_value(targetMap.get(8));
                cadiPdcaLeftKpiTarget.setMonthly_cumulative_actual(targetMap.get(9));
                cadiPdcaLeftKpiTarget.setCurrent_week_actual(targetMap.get(10));
                cadiPdcaLeftKpiTargets.add(cadiPdcaLeftKpiTarget);
            }
        }
        cadiService.save(cadiPdcaLeftKpiTargets);
        list.clear();
        log.info("存储数据库成功！");
    }
}