//package com.apec.pos.service;
//
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.apec.pos.entity.TableEntity;
//import com.apec.pos.entity.output.TableOutput;
//import com.apec.pos.entity.search.TableSearch;
//import com.apec.pos.repository.TableRepository;
//
//@Service
//public class TableService extends BaseService<TableRepository, TableEntity, Integer> {
//
//	@Autowired
//	TableRepository tableRepository;
//
//	@Override
//	TableRepository getRepository() {
//		// TODO Auto-generated method stub
//		return tableRepository;
//	}
//
//	//Search
//	public List<TableEntity> searchTable(TableSearch search) {
//
//		return tableRepository.search(search);
//	}
//	//Detail
//	//Add
//	//Update
//	//Delete
//
//
//}
