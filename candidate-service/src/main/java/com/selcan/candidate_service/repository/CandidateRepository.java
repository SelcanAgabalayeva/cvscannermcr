package com.selcan.candidate_service.repository;

import com.selcan.candidate_service.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findBySkillsContainingIgnoreCaseAndExperienceYearsGreaterThanEqual(
            String skill,
            Integer experienceYears
    );
}
