package com.liulin.dao;

import com.liulin.dao.mapper.CadiMapper;
import com.liulin.entity.CadiPdcaLeftKpiTarget;
import com.liulin.util.CadiDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CadiDao{
    @Autowired
    private CadiMapper cadiMapper;

    public void save(List<CadiPdcaLeftKpiTarget> cadiDataListenerList){
        for (int i=0;i<cadiDataListenerList.size();i++){
            cadiMapper.insert(cadiDataListenerList.get(i));
        }
    }
}
