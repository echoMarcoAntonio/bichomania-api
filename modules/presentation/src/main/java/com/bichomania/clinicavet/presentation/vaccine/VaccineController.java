package com.bichomania.clinicavet.presentation.vaccine;

import com.bichomania.clinicavet.application.vaccine.dto.VaccineRequest;
import com.bichomania.clinicavet.application.vaccine.dto.VaccineResponse;
import com.bichomania.clinicavet.application.vaccine.dto.VaccineUpdateRequest;
import com.bichomania.clinicavet.application.vaccine.services.VaccineService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST para operações de Vaccine (Vacina).
 *
 * Endpoints disponíveis:
 * - POST   /api/vaccines              - Criar vacina
 * - GET    /api/vaccines              - Listar todas (com paginação opcional)
 * - GET    /api/vaccines/{id}         - Buscar por ID
 * - PUT    /api/vaccines/{id}         - Atualizar vacina
 * - DELETE /api/vaccines/{id}         - Deletar vacina
 * - GET    /api/vaccines/search/name  - Buscar por nome
 * - GET    /api/vaccines/search/manufacturer - Buscar por fabricante
 * - GET    /api/vaccines/with-validity - Listar vacinas com período de validade
 * - GET    /api/vaccines/count        - Contar total de vacinas
 */
@RestController
@RequestMapping("/api/vaccines")
@CrossOrigin(origins = "*") // Configurar adequadamente em produção
public class VaccineController {

    private final VaccineService service;

    public VaccineController(VaccineService service) {
        this.service = service;
    }

    /**
     * POST /api/vaccines
     * Cria uma nova vacina.
     *
     * @param request Dados da vacina
     * @return 201 Created com a vacina criada
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VaccineResponse> create(@Valid @RequestBody VaccineRequest request) {
        VaccineResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/vaccines/{id}
     * Atualiza uma vacina existente.
     *
     * @param id ID da vacina
     * @param request Novos dados
     * @return 200 OK com a vacina atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<VaccineResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody VaccineUpdateRequest request
    ) {
        VaccineResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/vaccines/{id}
     * Deleta uma vacina.
     *
     * @param id ID da vacina
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/vaccines/{id}
     * Busca vacina por ID.
     *
     * @param id ID da vacina
     * @return 200 OK com a vacina encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<VaccineResponse> findById(@PathVariable UUID id) {
        VaccineResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/vaccines
     * Lista todas as vacinas com paginação opcional.
     *
     * Query params:
     * - page: número da página (default 0)
     * - size: tamanho da página (default 20)
     * - sort: campo de ordenação (default name,asc)
     *
     * Exemplos:
     * - /api/vaccines
     * - /api/vaccines?page=0&size=10
     * - /api/vaccines?sort=name,desc
     * - /api/vaccines?page=1&size=20&sort=createdAt,desc
     *
     * @param pageable Configuração de paginação
     * @return 200 OK com página de vacinas
     */
    @GetMapping
    public ResponseEntity<Page<VaccineResponse>> findAll(
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<VaccineResponse> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * GET /api/vaccines/all
     * Lista TODAS as vacinas sem paginação.
     * Use com cuidado - pode retornar muitos dados.
     *
     * @return 200 OK com lista completa
     */
    @GetMapping("/all")
    public ResponseEntity<List<VaccineResponse>> findAllUnpaged() {
        List<VaccineResponse> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * GET /api/vaccines/search/name
     * Busca vacinas por nome (parcial, case-insensitive).
     *
     * Query param:
     * - q: termo de busca
     *
     * Exemplo: /api/vaccines/search/name?q=v8
     *
     * @param query Termo de busca
     * @return 200 OK com lista de vacinas encontradas
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<VaccineResponse>> searchByName(@RequestParam("q") String query) {
        List<VaccineResponse> list = service.findByName(query);
        return ResponseEntity.ok(list);
    }

    /**
     * GET /api/vaccines/search/manufacturer
     * Busca vacinas por fabricante (parcial, case-insensitive).
     *
     * Query param:
     * - q: termo de busca
     *
     * Exemplo: /api/vaccines/search/manufacturer?q=bayer
     *
     * @param query Termo de busca
     * @return 200 OK com lista de vacinas encontradas
     */
    @GetMapping("/search/manufacturer")
    public ResponseEntity<List<VaccineResponse>> searchByManufacturer(@RequestParam("q") String query) {
        List<VaccineResponse> list = service.findByManufacturer(query);
        return ResponseEntity.ok(list);
    }

    /**
     * GET /api/vaccines/with-validity
     * Lista vacinas que possuem período de validade configurado.
     *
     * @return 200 OK com lista de vacinas com validade
     */
    @GetMapping("/with-validity")
    public ResponseEntity<List<VaccineResponse>> findWithValidityPeriod() {
        List<VaccineResponse> list = service.findVaccinesWithValidityPeriod();
        return ResponseEntity.ok(list);
    }

    /**
     * GET /api/vaccines/count
     * Retorna o total de vacinas cadastradas.
     *
     * @return 200 OK com o contador
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        long count = service.count();
        return ResponseEntity.ok(count);
    }

    /**
     * GET /api/vaccines/exists
     * Verifica se existe vacina com o nome especificado.
     *
     * Query param:
     * - name: nome da vacina
     *
     * Exemplo: /api/vaccines/exists?name=V8
     *
     * @param name Nome da vacina
     * @return 200 OK com boolean (true/false)
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByName(@RequestParam("name") String name) {
        boolean exists = service.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}