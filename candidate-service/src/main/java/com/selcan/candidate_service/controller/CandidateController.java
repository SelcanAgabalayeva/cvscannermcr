package com.selcan.candidate_service.controller;

import com.selcan.candidate_service.model.Candidate;
import com.selcan.candidate_service.repository.CandidateRepository;
import com.selcan.candidate_service.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    public Candidate save(@RequestBody Candidate candidate) {
        return candidateService.save(candidate);
    }

    @GetMapping("/filter")
    public List<Candidate> filter(
            @RequestParam String skill,
            @RequestParam Integer exp
    ) {
        return candidateService.filterCandidates(skill, exp);
    }
}
