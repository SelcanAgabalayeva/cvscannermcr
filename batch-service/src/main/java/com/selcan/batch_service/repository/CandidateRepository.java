package com.selcan.batch_service.repository;

import com.selcan.batch_service.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}