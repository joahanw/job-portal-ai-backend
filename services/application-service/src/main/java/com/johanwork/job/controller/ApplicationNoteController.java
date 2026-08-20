package com.johanwork.job.controller;

import com.johanwork.job.dto.AddApplicationNoteRequest;
import com.johanwork.job.dto.response.ApplicationNoteResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.service.IApplicationNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications/{applicationId}/notes")
public class ApplicationNoteController {

    private final IApplicationNoteService service;

    @PostMapping
    public ResponseEntity<GenericResponse<ApplicationNoteResponse>> addApplicationNote(
            @PathVariable Long applicationId,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid AddApplicationNoteRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addNote(applicationId, employerId, request));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<ApplicationNoteResponse>>> getNotes(
            @PathVariable Long applicationId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getNotesByApplication(applicationId));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<GenericResponse<Void>> deleteNotes(
            @PathVariable Long applicationId,
            @PathVariable Long noteId,
            @RequestHeader("X-User-Id") Long employerId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteNote(applicationId, noteId, employerId));
    }

}
