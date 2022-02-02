package it.dreamplatform.data.integration;

import it.dreamplatform.data.EntityManagerProvider;
import it.dreamplatform.data.bean.RankingBean;
import it.dreamplatform.data.controller.DataController;
import it.dreamplatform.data.mapper.DataMapper;
import it.dreamplatform.data.service.DataService;
import it.dreamplatform.data.utils.GeoUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataIntegrationTest {

    private DataService dataService;

    private DataController dataController;

    @Rule
    public EntityManagerProvider provider = EntityManagerProvider.withUnit("data-test");

    @Before
    public void setUp() {
        dataService = new DataService(this.provider.em());
        DataMapper dataMapper = new DataMapper();
        GeoUtil geoUtil = new GeoUtil(dataMapper);
        dataController = new DataController(geoUtil, dataService);
    }

    @Test
    public void checkDataForNotExistingDistrictTest() throws Exception {
        assertThrows(Exception.class, () -> dataController.createRanking("19_123"));
    }

    @Test
    public void getRankingsForAdilabadDistrictTest() throws Exception {
        List<RankingBean> rankings = dataController.createRanking("19_1");
        assertEquals("Adilabad", rankings.get(0).getDistrict());
    }
}
