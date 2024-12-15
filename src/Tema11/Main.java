package Tema11;

package com.example.evenimente.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Eveniment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denumire;
    private String locatie;
    private LocalDate data;
    private LocalTime timp;
    private float pretBilet;

    // Constructori, getteri și setteri

    public Eveniment() {
    }

    public Eveniment(String denumire, String locatie, LocalDate data, LocalTime timp, float pretBilet) {
        this.denumire = denumire;
        this.locatie = locatie;
        this.data = data;
        this.timp = timp;
        this.pretBilet = pretBilet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getTimp() {
        return timp;
    }

    public void setTimp(LocalTime timp) {
        this.timp = timp;
    }

    public float getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(float pretBilet) {
        this.pretBilet = pretBilet;
    }
}


public interface EvenimentRepository extends JpaRepository<Eveniment, Long> {

    // Găsește evenimentele care au loc într-o anumită locație
    List<Eveniment> findByLocatie(String locatie);

    // Găsește evenimentele care au loc într-o anumită dată
    List<Eveniment> findByData(LocalDate data);
}

@RestController
@RequestMapping("/jpa/evenimente")
public class EvenimenteController {

    @Autowired
    private EvenimentRepository evenimentRepository;

    // Afișează toate evenimentele
    @GetMapping
    public List<Eveniment> getAllEvenimente() {
        return evenimentRepository.findAll();
    }

    // Afișează evenimentele dintr-o anumită locație
    @GetMapping("/locatie/{locatie}")
    public List<Eveniment> getEvenimenteByLocatie(@PathVariable String locatie) {
        return evenimentRepository.findByLocatie(locatie);
    }

    // Afișează evenimentele dintr-o anumită dată
    @GetMapping("/data/{data}")
    public List<Eveniment> getEvenimenteByData(@PathVariable String data) {
        LocalDate localDate = LocalDate.parse(data);
        return evenimentRepository.findByData(localDate);
    }

    // Adăugarea unui eveniment
    @PostMapping
    public Eveniment addEveniment(@RequestBody Eveniment eveniment) {
        return evenimentRepository.save(eveniment);
    }

    // Actualizarea unui eveniment
    @PutMapping
    public Eveniment updateEveniment(@RequestBody Eveniment eveniment) {
        return evenimentRepository.save(eveniment);
    }

    // Ștergerea unui eveniment după id
    @DeleteMapping("/id/{id}")
    public void deleteEveniment(@PathVariable Long id) {
        evenimentRepository.deleteById(id);
    }
}
@SpringBootApplication
public class EvenimenteApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvenimenteApplication.class, args);
    }
}



