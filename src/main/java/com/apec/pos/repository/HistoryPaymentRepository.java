package com.apec.pos.repository;

import com.apec.pos.entity.HistoryPaymentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryPaymentRepository extends BaseRepository<HistoryPaymentEntity,Integer>{
    public HistoryPaymentRepository() {
        super(HistoryPaymentEntity.class);
    }
}
