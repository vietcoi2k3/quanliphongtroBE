package com.apec.pos.repository;

import com.apec.pos.dto.MotelSearch;
import com.apec.pos.entity.MotelEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MotelRepository extends BaseRepository<MotelEntity,Integer>{

    @Autowired
    private EntityManager entityManager;
    public MotelRepository() {
        super(MotelEntity.class);
    }

    public List<MotelEntity> getMotelTop(){
        String queryRaw = "SELECT c FROM MotelEntity c ORDER BY RAND()";
        Query query = entityManager.createQuery(queryRaw);
        return query.setMaxResults(8).getResultList();
    }

    //phân trang
    public List<MotelEntity> pagingMotel(MotelSearch motelSearch){
        String queryRaw = buildQuery(motelSearch);
        PageRequest pageRequest = PageRequest.of(motelSearch.getPageIndex(), motelSearch.getPageSize());
        Map<String, Object> params = getParams(motelSearch);
        return query(queryRaw,false,params,pageRequest);
    }

    //lấy ra danh sách nhà trọ dựa trên userId
    public List<MotelEntity> getMotelByUserId(int id,int pageIndex,int pageSize){
        String queryRaw = "SELECT c FROM MotelEntity c WHERE c.accountEntityID =:id";
        PageRequest pageRequest = PageRequest.of(pageIndex,pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("id",id);
        return query(queryRaw,false,params,pageRequest);
    }

    //đếm tổng số lượng nhà trọ dựa trên các tham số
    public long countMotel(MotelSearch motelSearch){
        String queryRaw ="SELECT count(c.id) " +buildQuery(motelSearch);
        Map<String,Object> params = getParams(motelSearch);
        return count(queryRaw,false,params);
    }

    //tạo ra câu lệnh query dựa trên các tham số
    private String buildQuery(MotelSearch motelSearch) {
        String query = "FROM MotelEntity c WHERE 1=1 ";
        if (motelSearch.getTypeMotelId() > 0) {
            query += " AND c.typeMotelID = :typeMotelID ";
        }
        if (motelSearch.getCityEntityId() > 0) {
            query += " AND c.cityEntityID = :cityEntityID ";
        }
        if (motelSearch.getAcreageCeil() > 0) {
            query += " AND c.acreage <= :acreageCeil ";
        }
        if (motelSearch.getAcreageFloor() > 0) {
            query += " AND c.acreage >= :acreageFloor ";
        }
        if (motelSearch.getPriceFloor() > 0) {
            query += " AND c.price >= :priceFloor ";
        }
        if (motelSearch.getPriceCeil() > 0) {
            query += " AND c.price <= :priceCeil ";
        }
        return query;
    }

    //tạo ra cái cặp param
    public Map<String, Object> getParams(MotelSearch motelSearch) {
	        Map<String, Object> params = new HashMap<>();
            if (motelSearch.getTypeMotelId()>0){
                params.put("typeMotelID",motelSearch.getTypeMotelId());
            }
            if (motelSearch.getCityEntityId()>0){
                params.put("cityEntityID",motelSearch.getCityEntityId());
            }
             if (motelSearch.getAcreageCeil() > 0) {
                 params.put("acreageCeil", motelSearch.getAcreageCeil());
             }
             if (motelSearch.getAcreageFloor() > 0) {
                 params.put("acreageFloor", motelSearch.getAcreageFloor());
             }
             if (motelSearch.getPriceFloor() > 0) {
                 params.put("priceFloor", motelSearch.getPriceFloor());
             }
             if (motelSearch.getPriceCeil() > 0) {
                 params.put("priceCeil", motelSearch.getPriceCeil());
             }
             return params;
	 }
}
