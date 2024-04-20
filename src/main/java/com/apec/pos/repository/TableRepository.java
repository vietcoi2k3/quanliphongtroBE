//package com.apec.pos.repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.logging.log4j.util.Strings;
//import org.springframework.stereotype.Repository;
//
//import com.apec.pos.entity.TableEntity;
//
//@Repository
//public class TableRepository extends BaseRepository<TableEntity, Integer> {
//
//	public TableRepository() {
//		super(TableEntity.class);
//
//	}
//
//	public List<TableEntity> search(TableSearch search) {
//        String query = buildQuery(search);
//        Map<String, Object> params = getParams(search);
//        return query(query, false, params);
//    }
//
//	private String buildQuery(TableSearch search) {
//        String query = "FROM TableEntity c WHERE 1=1 ";
//
//
//        if (search.getId() > 0) {
//            query += " AND c.id = :id";
//        }
//
//        if (Strings.isNotBlank(search.getCode())) {
//            query += " AND c.table_code = :code";
//        }
//
//
//        return query;
//    }
//
//	 public Map<String, Object> getParams(TableSearch search) {
//	        Map<String, Object> params = new HashMap<>();
//	        if (search.getId() > 0) {
//	            params.put("id", search.getId());
//	        }
//
//	        if (Strings.isNotBlank(search.getCode())) {
//	            params.put("code", search.getCode());
//	        }
//
//	        return params;
//	 }
//
//
//
//
//
//
//
//
//}
