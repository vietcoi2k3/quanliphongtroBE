package com.apec.pos.repository;

import com.apec.pos.entity.TypeMotelEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TypeRepository extends BaseRepository<TypeMotelEntity,Integer>{

    public TypeRepository() {
        super(TypeMotelEntity.class);
    }
}
