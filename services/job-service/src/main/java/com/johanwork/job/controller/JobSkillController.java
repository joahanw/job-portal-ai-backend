package com.johanwork.job.controller;

import com.johanwork.job.dto.JobSkillRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobSkillResponse;
import com.johanwork.job.service.IJobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-skills")
public class JobSkillController {

    private final IJobSkillService service;

    @PostMapping
    public ResponseEntity<GenericResponse<JobSkillResponse>> createSkill(@RequestBody @Valid JobSkillRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createSkill(req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<JobSkillResponse>>> getAllSkills(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<JobSkillResponse>> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<JobSkillResponse>> updateSkill(@PathVariable Long id,
                                                                         @RequestBody @Valid JobSkillRequest req) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updatedSkill(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteSkill(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteSkill(id));
    }

}
