package com.example.proj.repository;

import com.example.proj.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(String name);
}
