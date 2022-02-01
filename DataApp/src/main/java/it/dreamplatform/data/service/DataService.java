package it.dreamplatform.data.service;

import it.dreamplatform.data.entity.Data;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DataService {
    @PersistenceContext(unitName = "data-persistence-provider")
    private EntityManager em;

    public List<Data> getDataByDataSourceId(Long dataSourceId){
        TypedQuery<Data> query = em.createQuery("SELECT d FROM Data d WHERE d.dataSourceId = :dataSourceId", Data.class);
        query.setParameter("dataSourceId", dataSourceId);
        return query.getResultList();
    }
}
