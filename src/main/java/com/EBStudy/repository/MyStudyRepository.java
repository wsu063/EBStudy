package com.EBStudy.repository;

import com.EBStudy.entity.MyStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyStudyRepository extends JpaRepository<MyStudy, Long> {
}
