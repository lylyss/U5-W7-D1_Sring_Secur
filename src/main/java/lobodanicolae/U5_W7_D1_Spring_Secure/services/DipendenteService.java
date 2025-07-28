package lobodanicolae.U5_W7_D1_Spring_Secure.services;

import lobodanicolae.U5_W7_D1_Spring_Secure.entities.Dipendente;
import lobodanicolae.U5_W7_D1_Spring_Secure.exceptions.BadRequestException;
import lobodanicolae.U5_W7_D1_Spring_Secure.exceptions.ResourceNotFoundException;
import lobodanicolae.U5_W7_D1_Spring_Secure.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DipendenteService {
    private final DipendenteRepository repository;

    @Autowired
    public DipendenteService(DipendenteRepository repository) {
        this.repository = repository;
    }

    public List<Dipendente> findAll() {
        return repository.findAll();
    }

    public Dipendente save(Dipendente dipendente) {
        try {
            return repository.save(dipendente);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Violazione vincolo dati: probabilmente username o email già esistenti.");
        } catch (Exception e) {
            throw new BadRequestException("Errore durante il salvataggio del dipendente.");
        }
    }

    public Dipendente findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dipendente con ID " + id + " non trovato."));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Dipendente findByEmail(String email) {
        return this.repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Dipendente con email " + email + " non trovato."));
    }


}
