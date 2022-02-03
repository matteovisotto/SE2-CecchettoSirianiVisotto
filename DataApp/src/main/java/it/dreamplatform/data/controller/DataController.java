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
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all the controller used by a Data entity or a DataBean of every genre.
 */
public class DataController {

    @Inject
    private GeoUtil geoUtil;

    @Inject
    private DataService dataService;

    @Inject
    public DataController (GeoUtil geoUtil, DataService dataService) {
        this.geoUtil = geoUtil;
        this.dataService = dataService;
    }

    public DistrictBean createSingleDistrict(String districtId) throws Exception {
        return geoUtil.createSingleDistrict(districtId);
    }

    public DataSetBean createDataSet (DataSource dataSource, DistrictBean districtOfInterest) throws URISyntaxException {
        List<Data> dataList = dataService.getDataByDataSourceId(dataSource.getDataSourceId());
        //creo dei dataBean indicando a quale distretto appartengono
        List<DataBean> dataBeans = geoUtil.valueSingleDistrict(dataList, districtOfInterest);
        //filtro tenendo solo i dati inerenti al distretto di mio interesse
        List<DataBean> filteredDataBeans = dataBeans.stream()
                                                    .filter(dataBean -> dataBean.getDistrict().equals(districtOfInterest.getName()))
                                                    .collect(Collectors.toList());
        //calcolo il punto medio del distretto sulla base dei dati raccolti
        System.out.println(dataList.size());
        System.out.println(dataBeans.size());
        System.out.println(filteredDataBeans.size());

        Point mediumPoint = geoUtil.mediumPoint(filteredDataBeans);
        //ora che ho il punto medio, divido i dati nelle 4 aree di interesse che andremo a confrontare, andando a valorizzare il campo zone
        filteredDataBeans = geoUtil.valueZone(filteredDataBeans, mediumPoint);
        //calcola un valore medio inerente per ogni zona del distretto per la la datasource in considerazione
        return geoUtil.calculateZoneValue(filteredDataBeans);
    }

    public List<RankingBean> createRanking (String districtId) throws Exception {
        List<DataSetBean> dataSetBeans = new ArrayList<>();
        List<DataSource> dataSources = new ArrayList<>();
        DataSource ds = new DataSource();
        ds.setDataSourceId(1L);
        ds.setName("Meta");
        ds.setDataTypeId(1L);
        dataSources.add(ds);
        //creo il distretto corretto
        DistrictBean districtOfInterest = createSingleDistrict(districtId);
        for (DataSource dataSource: dataSources) {
            DataSetBean dataSetBean = createDataSet(dataSource, districtOfInterest);
            dataSetBeans.add(dataSetBean);
        }
        return geoUtil.calculateRanking(dataSetBeans, districtOfInterest.getName());
    }

}
