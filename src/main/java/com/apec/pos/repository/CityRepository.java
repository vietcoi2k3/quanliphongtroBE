package com.apec.pos.repository;

import com.apec.pos.entity.AccountEntity;
import com.apec.pos.entity.CityEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepository extends BaseRepository<CityEntity, Integer>{

    public CityRepository() {
        super(CityEntity.class);
    }
}
