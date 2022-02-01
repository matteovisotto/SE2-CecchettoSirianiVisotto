package it.dreamplatform.data.controller;

import com.esri.core.geometry.Point;
import it.dreamplatform.data.bean.DataBean;
import it.dreamplatform.data.bean.DataSetBean;
import it.dreamplatform.data.bean.DistrictBean;
import it.dreamplatform.data.bean.RankingBean;
import it.dreamplatform.data.entity.Data;
import it.dreamplatform.data.entity.DataSource;
import it.dreamplatform.data.service.DataService;
import it.dreamplatform.data.utils.GeoUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataController {

    @Inject
    private GeoUtil geoUtil;

    @Inject
    private DataService dataService;

    public DistrictBean createSingleDistrict(String geoJsons) {
        return geoUtil.createSingleDistrict(geoJsons);
    }

    public List<DistrictBean> createDistrict(List<String> geoJsons) {
        return geoUtil.createDistrict(geoJsons);
    }

    public String retrieveDistrict(String districtId) {
        return "[]";
    }

    public DataSetBean createDataSet (DataSource dataSource, DistrictBean districtOfInterest) {
        List<Data> dataList = dataService.getDataByDataSourceId(dataSource.getDataSourceId());
        //creo dei dataBean indicando a quale distretto appartengono
        List<DataBean> dataBeans= geoUtil.valueSingleDistrict(dataList, districtOfInterest);
        //filtro tenendo solo i dati inerenti al distretto di mio interesse
        List<DataBean> filteredDataBeans = dataBeans.stream()
                                                    .filter(dataBean -> dataBean.getDistrict().equals(districtOfInterest.getName()))
                                                    .collect(Collectors.toList());
        //calcolo il punto medio del distretto sulla base dei dati raccolti
        Point mediumPoint = geoUtil.mediumPoint(filteredDataBeans);
        //ora che ho il punto medio, divido i dati nelle 4 aree di interesse che andremo a confrontare, andando a valorizzare il campo zone
        filteredDataBeans = geoUtil.valueZone(filteredDataBeans, mediumPoint);
        //calcola un valore medio inerente per ogni zona del distretto per la la datasource in considerazione
        DataSetBean dataSetBean = geoUtil.CalculateZoneValue(filteredDataBeans);
        return dataSetBean;
    }

    public List<RankingBean> createRanking (List<DataSource> dataSources, DistrictBean district) throws Exception {
        List<DataSetBean> dataSetBeans = new ArrayList<>();
        for (DataSource dataSource: dataSources) {
            DataSetBean dataSetBean = createDataSet(dataSource, district);
            dataSetBeans.add(dataSetBean);
        }
        return geoUtil.calculateRanking(dataSetBeans, district.getName());

    }

}
