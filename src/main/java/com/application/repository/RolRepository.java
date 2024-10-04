package com.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Rol;
import com.application.entity.RoleEnum;
@Repository
public interface RolRepository extends CrudRepository<Rol,Long>{
    public Rol findRolByName(RoleEnum name);
}
