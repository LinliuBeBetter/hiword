package com.liulin.service;

import com.liulin.dao.CadiDao;
import com.liulin.entity.CadiPdcaLeftKpiTarget;
import com.liulin.util.CadiDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadiService {
    @Autowired
    private CadiDao cadiDao;

    public void save(List<CadiPdcaLeftKpiTarget> cadiDataListenerList){
      cadiDao.save(cadiDataListenerList);
    }
}
