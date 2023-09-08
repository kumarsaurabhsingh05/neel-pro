package com.saurabh.neelpro.repository;

import com.saurabh.neelpro.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDetailRepository extends JpaRepository<FileDetails, Integer> {
}
