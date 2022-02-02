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
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    public String retrieveDistrict(String districtId) throws URISyntaxException {
        String correctDistrict = getCorrectDistrict(districtId);
        System.out.println("\ngetResource : " + correctDistrict);
        File file = getFileFromResource(correctDistrict);
        printFile(file);
        return "[]";
    }

    private String getCorrectDistrict(String districtId) {
        switch (districtId) {
            case "19_1":
                return "Adilabad.json";
            case "22_2":
                return "BhadradriKothagudem.json";
            case "21_1":
                return "Hyderabad.json";
            case "16_1":
                return "Jagtial.json";
            case "20_2":
                return "Jangoan.json";
            case "21_3":
                return "JayashankarBhupalpally.json";
            case "21_4":
                return "JogulambaGadwal.json";
            case "14_2":
                return "Kamareddy.json";
            case "18_2":
                return "Karimnagar.json";
            case "20_1":
                return "Khammam.json";
            case "22_1":
                return "KumurambheemAsifabad.json";
            case "19_4":
                return "Mahabubabad.json";
            case "21_5":
                return "Mahabubnagar.json";
            case "14_1":
                return "Mancherial.json";
            case "19_3":
                return "Medak.json";
            case "17_1":
                return "MedchalMalkajgiri.json";
            case "15_2":
                return "Mulugu.json";
            case "21_6":
                return "Nagarkurnool.json";
            case "14_3":
                return "Nalgonda.json";
            case "23_1":
                return "Narayanpet.json";
            case "14_5":
                return "Nirmal.json";
            case "19_2":
                return "Nizamabad.json";
            case "18_1":
                return "Peddapalli.json";
            case "20_4":
                return "RajannaSircilla.json";
            case "20_3":
                return "Rangareddy.json";
            case "15_1":
                return "Sangareddy.json";
            case "17_2":
                return "Siddipet.json";
            case "17_3":
                return "Suryapet.json";
            case "23_2":
                return "Vikarabad.json";
            case "15_3":
                return "Wanaparthy.json";
            case "14_4":
                return "WarangalRural.json";
            case "21_2":
                return "WarangalUrban.json";
            case "23_3":
                return "YadadriBhuvanagiri.json";
            default:
                return "";
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }

    private static void printFile(File file) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
