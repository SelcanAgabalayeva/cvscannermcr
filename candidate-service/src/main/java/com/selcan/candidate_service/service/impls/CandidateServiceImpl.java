package com.selcan.candidate_service.service.impls;

import com.selcan.candidate_service.model.Candidate;
import com.selcan.candidate_service.repository.CandidateRepository;
import com.selcan.candidate_service.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository repository;

    @Override
    public List<Candidate> filterCandidates(String skill, Integer minExp) {
        return repository.findBySkillsContainingIgnoreCaseAndExperienceYearsGreaterThanEqual(
                skill,
                minExp
        );
    }

    @Override
    public Candidate save(Candidate candidate) {
        return repository.save(candidate);
    }
}
