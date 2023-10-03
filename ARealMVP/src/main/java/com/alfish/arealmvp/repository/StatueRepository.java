package com.alfish.arealmvp.repository;

import com.alfish.arealmvp.model.Statue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatueRepository extends JpaRepository<Statue, Integer> {

    Statue findByStatueName(String statueName);

}
