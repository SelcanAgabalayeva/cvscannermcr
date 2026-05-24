package com.selcan.candidate_service.service;

import com.selcan.candidate_service.model.Candidate;

import java.util.List;

public interface CandidateService {
    List<Candidate> filterCandidates(String skill, Integer minExp);

    Candidate save(Candidate candidate);

}
