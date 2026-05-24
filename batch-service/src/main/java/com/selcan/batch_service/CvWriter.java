package com.selcan.batch_service;

import com.selcan.batch_service.model.Candidate;
import com.selcan.batch_service.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CvWriter implements ItemWriter<Candidate> {

    private final CandidateRepository repository;

    @Override
    public void write(Chunk<? extends Candidate> chunk) {
        repository.saveAll(chunk.getItems());
    }
}
