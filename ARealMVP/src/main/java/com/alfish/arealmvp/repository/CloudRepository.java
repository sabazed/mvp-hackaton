package com.alfish.arealmvp.repository;

import com.alfish.arealmvp.model.Cloud;
import com.alfish.arealmvp.model.composite.CloudId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface CloudRepository extends JpaRepository<Cloud, CloudId> {

    List<Cloud> findAllByCloudIdStatueStatueId(Integer statueId);

    List<Cloud> findAllByCloudIdStatueStatueIdAndSubmissionTimeBetween(Integer statueId, Instant from, Instant to);

}
