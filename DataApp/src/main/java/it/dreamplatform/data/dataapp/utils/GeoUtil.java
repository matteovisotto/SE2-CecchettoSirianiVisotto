package it.dreamplatform.data.dataapp.utils;

import com.esri.core.geometry.*;
import com.google.gson.JsonObject;
import it.dreamplatform.data.dataapp.bean.DataBean;
import it.dreamplatform.data.dataapp.bean.DataSetBean;
import it.dreamplatform.data.dataapp.bean.DistrictBean;
import it.dreamplatform.data.dataapp.entity.Data;
import it.dreamplatform.data.dataapp.mapper.DataMapper;

import javax.inject.Inject;
import java.util.List;

public class GeoUtil {
    @Inject
    OperatorImportFromGeoJson operatorImportFromGeoJson;
    @Inject
    DataMapper dataMapper;

    public List<DistrictBean> createDistrict(List<String> geoJsons) {
        List<DistrictBean> districts = null;
        for (String geoJson : geoJsons) {
            DistrictBean districtBean = null;

            Polygon polygon = (Polygon) operatorImportFromGeoJson.execute(GeoJsonImportFlags.geoJsonImportDefaults, Geometry.Type.Polygon, geoJson, null).getGeometry();
            districtBean.setPolygon(polygon);

            JsonObject element = com.google.gson.JsonParser.parseString(geoJson).getAsJsonObject();
            JsonObject properties = element.get("properties").getAsJsonObject();
            districtBean.setName(properties.get("Dist_Name").getAsString());

            districts.add(districtBean);
        }
        return districts;
    }

    public List<DataBean> valueDistrict(List<Data> dataList, List<DistrictBean> districts) {
        List<DataBean> toReturn = null;
        SpatialReference spatialReference = SpatialReference.create(1984);
        for (Data data: dataList) {
            Point dataPoint = new Point(data.getLongitude(), data.getLatitude());
            for (DistrictBean district : districts) {
                boolean contains = OperatorContains.local().execute(district.getPolygon(), dataPoint, spatialReference,null);
                if(contains){
                    data.setDistrict(district.getName());
                    DataBean dataBean = dataMapper.mapEntityToBean(data);
                    toReturn.add(dataBean);
                    break;
                }
            }
        }
        return toReturn;
    }

    //input DataBean che hanno stesso district
    public Point mediumPoit (List<DataBean> dataBeanList) {
        double mediumLatitude = 0;
        double mediumLongitude = 0;
        int counter = dataBeanList.size();
        for (DataBean data: dataBeanList) {
            mediumLatitude+= data.getLatitude();
            mediumLongitude+= data.getLongitude();
        }
        mediumLatitude = mediumLatitude / counter;
        mediumLongitude= mediumLongitude / counter;

        Point dataPoint = new Point(mediumLongitude, mediumLatitude);
        return dataPoint;
    }

    public List<DataBean> valueZone (List<DataBean> dataBeanList, Point mediumPoint) {
        for (DataBean dataBean:dataBeanList) {
            if(dataBean.getLongitude() > mediumPoint.getX()){
                if(dataBean.getLatitude() > mediumPoint.getY()){
                    dataBean.setZone(ZoneEnum.NORTH_EAST);
                } else {
                    dataBean.setZone(ZoneEnum.SOUTH_EAST);
                }
            } else {
                if(dataBean.getLatitude() > mediumPoint.getY()){
                    dataBean.setZone(ZoneEnum.NORTH_WEST);
                } else {
                    dataBean.setZone(ZoneEnum.SOUTH_WEST);
                }
            }
        }
        return dataBeanList;
    }

    public DataSetBean CalculateZoneValue (List<DataBean> dataBeanList) {
        DataSetBean dataSetBean = new DataSetBean();

        double valueNE = 0;
        double valueNW = 0;
        double valueSE = 0;
        double valueSW = 0;

        int counterNE = 0;
        int counterNW = 0;
        int counterSE = 0;
        int counterSW = 0;

        for (DataBean dataBean:dataBeanList) {
            if(dataBean.getZone() == ZoneEnum.NORTH_EAST){
                valueNE += dataBean.getValue();
                counterNE ++;
            }
            else if(dataBean.getZone() == ZoneEnum.NORTH_WEST){
                valueNW += dataBean.getValue();
                counterNW ++;
            }
            else if(dataBean.getZone() == ZoneEnum.SOUTH_EAST){
                valueSE += dataBean.getValue();
                counterSE ++;
            }
            else if(dataBean.getZone() == ZoneEnum.SOUTH_WEST){
                valueSW += dataBean.getValue();
                counterSW ++;
            }
        }
        valueNE = valueNE / counterNE;
        valueNW = valueNW / counterNW;
        valueSE = valueSE / counterSE;
        valueSW = valueSW / counterSW;

        dataSetBean.setDataSourceId(dataBeanList.get(0).getDataSourceId());
        dataSetBean.setDistrict(dataBeanList.get(0).getDistrict());
        dataSetBean.setValueNE(valueNE);
        dataSetBean.setValueNW(valueNW);
        dataSetBean.setValueSE(valueSE);
        dataSetBean.setValueSW(valueSW);

        return dataSetBean;
    }


}
