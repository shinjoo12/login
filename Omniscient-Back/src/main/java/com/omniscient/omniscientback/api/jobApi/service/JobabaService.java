package com.omniscient.omniscientback.api.jobApi.service;

import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobabaEntity;
import com.omniscient.omniscientback.api.jobApi.repository.JobabaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobabaService {

    private final JobabaRepository jobabaRepository;

    @Autowired
    public JobabaService(JobabaRepository jobabaRepository) {
        this.jobabaRepository = jobabaRepository;
    }

    public void saveJob(JobTotalDTO jobDTO) {
        JobabaEntity jobabaEntity = new JobabaEntity();

        jobabaEntity.setJobabaCompanyName(jobDTO.getCompanyName());
        jobabaEntity.setJobabaInfoTitle(jobDTO.getInfoTitle());
        jobabaEntity.setJobabaWageType(jobDTO.getWageType());
        jobabaEntity.setJobabaSalary(jobDTO.getSalary());
        jobabaEntity.setJobabaLocation(jobDTO.getLocation());
        jobabaEntity.setJobabaEmploymentType(jobDTO.getEmploymentType());
        jobabaEntity.setJobabaCareerCondition(jobDTO.getCareerCondition());

        // 날짜 변환
        jobabaEntity.setJobabaPostedDate(jobDTO.getPostedDate());  // LocalDate로 저장
        jobabaEntity.setJobabaClosingDate(jobDTO.getClosingDate());  // LocalDate로 저장

        jobabaRepository.save(jobabaEntity);
    }

    public List<JobabaEntity> getAllJobs() {
        return jobabaRepository.findAll();
    }

    public Optional<JobabaEntity> getJobById(String id) {
        return jobabaRepository.findById(Integer.parseInt(id));
    }
}
