package it.dreamplatform.data.utils;

import com.esri.core.geometry.*;
import com.google.gson.JsonObject;
import it.dreamplatform.data.bean.DataSetBean;
import it.dreamplatform.data.bean.RankingBean;
import it.dreamplatform.data.bean.DataBean;
import it.dreamplatform.data.bean.DistrictBean;
import it.dreamplatform.data.entity.Data;
import it.dreamplatform.data.mapper.DataMapper;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.*;

public class GeoUtil {

    @Inject
    private DataMapper dataMapper;

    public DistrictBean createSingleDistrict(String geoJson) {
        DistrictBean districtBean = new DistrictBean();

        Polygon polygon = (Polygon) OperatorImportFromGeoJson.local().execute(GeoJsonImportFlags.geoJsonImportDefaults, Geometry.Type.Polygon, geoJson, null).getGeometry();
        districtBean.setPolygon(polygon);

        JsonObject element = com.google.gson.JsonParser.parseString(geoJson).getAsJsonObject();
        JsonObject properties = element.get("properties").getAsJsonObject();
        districtBean.setName(properties.get("Dist_Name").getAsString());

        return districtBean;
    }

    public List<DistrictBean> createDistrict(List<String> geoJsons) {
        List<DistrictBean> districts = new ArrayList<>();
        for (String geoJson : geoJsons) {
            DistrictBean districtBean = new DistrictBean();

            Polygon polygon = (Polygon) OperatorImportFromGeoJson.local().execute(GeoJsonImportFlags.geoJsonImportDefaults, Geometry.Type.Polygon, geoJson, null).getGeometry();
            districtBean.setPolygon(polygon);

            JsonObject element = com.google.gson.JsonParser.parseString(geoJson).getAsJsonObject();
            JsonObject properties = element.get("properties").getAsJsonObject();
            districtBean.setName(properties.get("Dist_Name").getAsString());

            districts.add(districtBean);
        }
        return districts;
    }

    public List<DataBean> valueDistricts(List<Data> dataList, List<DistrictBean> districts) {
        List<DataBean> toReturn = new ArrayList<>();
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

    public List<DataBean> valueSingleDistrict(List<Data> dataList, DistrictBean district) {
        List<DataBean> toReturn = new ArrayList<>();
        SpatialReference spatialReference = SpatialReference.create(1984);
        for (Data data: dataList) {
            Point dataPoint = new Point(data.getLongitude(), data.getLatitude());
            boolean contains = OperatorContains.local().execute(district.getPolygon(), dataPoint, spatialReference,null);
            if(contains){
                data.setDistrict(district.getName());
                DataBean dataBean = dataMapper.mapEntityToBean(data);
                toReturn.add(dataBean);
                break;
            }
        }
        return toReturn;
    }

    //input DataBean che hanno stesso district
    public Point mediumPoint (List<DataBean> dataBeanList) {
        double mediumLatitude = 0;
        double mediumLongitude = 0;
        int counter = dataBeanList.size();
        for (DataBean data: dataBeanList) {
            mediumLatitude+= data.getLatitude();
            mediumLongitude+= data.getLongitude();
        }
        mediumLatitude = mediumLatitude / counter;
        mediumLongitude= mediumLongitude / counter;

        return new Point(mediumLongitude, mediumLatitude);
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

    public List<RankingBean> calculateRanking(List<DataSetBean> dataSetBeans, String district) throws Exception {
        double rankNE = 0;
        double rankNW = 0;
        double rankSE = 0;
        double rankSW = 0;

        RankingBean zoneNE = new RankingBean();
        RankingBean zoneNW = new RankingBean();
        RankingBean zoneSE = new RankingBean();
        RankingBean zoneSW = new RankingBean();

        zoneNE.setDistrict(district);
        zoneNW.setDistrict(district);
        zoneSE.setDistrict(district);
        zoneSW.setDistrict(district);

        zoneNE.setZone(ZoneEnum.NORTH_EAST);
        zoneNW.setZone(ZoneEnum.SOUTH_WEST);
        zoneSE.setZone(ZoneEnum.SOUTH_EAST);
        zoneSW.setZone(ZoneEnum.SOUTH_WEST);

        for (DataSetBean dataSet : dataSetBeans) {
            if(!dataSet.getDistrict().equals(district)){
                throw new Exception("DataSet must belong to district " + district+ "!\n");
            }
            rankNE += dataSet.getValueNE();
            rankNW += dataSet.getValueNW();
            rankSE += dataSet.getValueSE();
            rankSW += dataSet.getValueSW();
        }
        zoneNE.setValue(rankNE);
        zoneNW.setValue(rankNW);
        zoneSE.setValue(rankSE);
        zoneSW.setValue(rankSW);

        List<RankingBean> rankingList = new ArrayList<>();
        rankingList.add(zoneNE);
        rankingList.add(zoneNW);
        rankingList.add(zoneSE);
        rankingList.add(zoneSW);

        Comparator<RankingBean> compareByValue = Comparator.comparingDouble(RankingBean::getValue);
        rankingList.sort(compareByValue);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (int i = 0; i<rankingList.size(); i++) {
            rankingList.get(i).setTimestamp(timestamp);
            rankingList.get(i).setPosition(i+1);
        }
        return rankingList;
    }
}
