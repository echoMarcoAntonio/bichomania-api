package com.bichomania.clinicavet.presentation.guardian;

import com.bichomania.clinicavet.application.guardian.dto.GuardianRequest;
import com.bichomania.clinicavet.application.guardian.dto.GuardianResponse;
import com.bichomania.clinicavet.application.guardian.services.GuardianService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/guardians")
public class GuardianController {

    private final GuardianService service;

    public GuardianController(GuardianService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuardianResponse create(@Valid @RequestBody GuardianRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public GuardianResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("/cpf/{cpf}")
    public GuardianResponse findByCpf(@PathVariable String cpf) {
        return service.findByCpf(cpf);
    }

    @PutMapping("/{id}")
    public GuardianResponse update(@PathVariable UUID id,
                                   @Valid @RequestBody GuardianRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }

    @PatchMapping("/{id}/reactivate")
    public void reactivate(@PathVariable UUID id) {
        service.reactivate(id);
    }
}