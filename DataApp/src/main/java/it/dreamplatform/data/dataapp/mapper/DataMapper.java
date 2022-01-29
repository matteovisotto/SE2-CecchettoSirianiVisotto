package it.dreamplatform.data.dataapp.mapper;

import it.dreamplatform.data.dataapp.bean.DataBean;
import it.dreamplatform.data.dataapp.entity.Data;

import java.util.List;

public class DataMapper {
    public DataBean mapEntityToBean (Data entity){
        return mapEntityToBean(entity, new DataBean());
    }

    public DataBean mapEntityToBean(Data entity, DataBean bean){
        bean.setDataId(entity.getDataId());
        bean.setValue(entity.getValue());
        bean.setLongitude(entity.getLongitude());
        bean.setLatitude(entity.getLatitude());
        bean.setTimestamp(entity.getTimestamp());
        bean.setDataSourceId(entity.getDataSourceId());
        bean.setDistrict(entity.getDistrict());
        return bean;
    }

    public Data mapBeanToEntity (DataBean bean){
        return mapBeanToEntity(new Data(),bean);
    }

    public Data mapBeanToEntity(Data entity, DataBean bean){
        entity.setDataId(bean.getDataId());
        entity.setValue(bean.getValue());
        entity.setLongitude(bean.getLongitude());
        entity.setLatitude(bean.getLatitude());
        entity.setTimestamp(bean.getTimestamp());
        entity.setDataSourceId(bean.getDataSourceId());
        entity.setDistrict(bean.getDistrict());
        return entity;
    }
}
