package it.dreamplatform.data.dataapp.utils;

import com.esri.core.geometry.*;
import com.google.gson.JsonObject;
import it.dreamplatform.data.dataapp.bean.DataBean;
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

    public List<DataBean> checkArea(List<Data> dataList, List<DistrictBean> districts) {
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
}
