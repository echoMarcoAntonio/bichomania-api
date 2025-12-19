package com.bichomania.clinicavet.presentation.pet;

import com.bichomania.clinicavet.application.pet.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST para endpoints de Pet.
 * Recebe requisições HTTP, delega para o PetService e retorna respostas.
 */
@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    /**
     * POST /api/pets - Cria novo pet
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse create(@Valid @RequestBody PetRequest request) {
        return service.createPet(request);
    }

    /**
     * PATCH /api/pets/{id} - Atualiza dados do pet
     */
    @PatchMapping("/{id}")
    public PetResponse update(@PathVariable UUID id, @Valid @RequestBody PetUpdateRequest request) {
        return service.updatePet(id, request);
    }

    /**
     * DELETE /api/pets/{id} - Remove pet
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.deletePet(id);
    }

    /**
     * GET /api/pets - Lista todos os pets
     */
    @GetMapping
    public List<PetResponse> findAll() {
        return service.findAll();
    }

    /**
     * GET /api/pets/{id} - Busca pet por ID
     */
    @GetMapping("/{id}")
    public PetResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    /**
     * GET /api/pets/guardian/{guardianId} - Lista pets por tutor
     */
    @GetMapping("/guardian/{guardianId}")
    public List<PetResponse> findByGuardianId(@PathVariable UUID guardianId) {
        return service.findByGuardianId(guardianId);
    }

    /**
     * POST /api/pets/{id}/vaccines - Adiciona aplicação de vacina
     */
    @PostMapping("/{id}/vaccines")
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse addVaccineApplication(@PathVariable UUID id, @Valid @RequestBody VaccineApplicationRequest request) {
        return service.addVaccineApplication(id, request);
    }

    /**
     * POST /api/pets/{id}/dewormers - Adiciona aplicação de vermífugo
     */
    @PostMapping("/{id}/dewormers")
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse addDewormerApplication(@PathVariable UUID id, @Valid @RequestBody DewormerApplicationRequest request) {
        return service.addDewormerApplication(id, request);
    }

    /**
     * POST /api/pets/{id}/reminders - Adiciona lembrete
     */
    @PostMapping("/{id}/reminders")
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse addReminder(@PathVariable UUID id, @Valid @RequestBody ReminderRequest request) {
        return service.addReminder(id, request);
    }
}