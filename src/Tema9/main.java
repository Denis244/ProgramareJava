package Tema9;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

@Table("masini")
public class Masina {

    @Id
    private Integer id;
    private String numarInmatriculare;
    private String marca;
    private int anFabricatie;
    private String culoare;
    private int kilometri;

    // Constructori, getteri și setteri
    public Masina(String numarInmatriculare, String marca, int anFabricatie, String culoare, int kilometri) {
        this.numarInmatriculare = numarInmatriculare;
        this.marca = marca;
        this.anFabricatie = anFabricatie;
        this.culoare = culoare;
        this.kilometri = kilometri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumarInmatriculare() {
        return numarInmatriculare;
    }

    public void setNumarInmatriculare(String numarInmatriculare) {
        this.numarInmatriculare = numarInmatriculare;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnFabricatie() {
        return anFabricatie;
    }

    public void setAnFabricatie(int anFabricatie) {
        this.anFabricatie = anFabricatie;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public int getKilometri() {
        return kilometri;
    }

    public void setKilometri(int kilometri) {
        this.kilometri = kilometri;
    }
}
public interface MasinaRepository extends CrudRepository<Masina, Integer> {

    // Adăugarea unei mașini
    Masina save(Masina masina);

    // Ștergerea unei mașini după numărul de înmatriculare
    void deleteByNumarInmatriculare(String numarInmatriculare);

    // Căutarea unei mașini după numărul de înmatriculare
    Masina findByNumarInmatriculare(String numarInmatriculare);

    // Extragerea tuturor mașinilor
    List<Masina> findAll();

    // Determinarea numărului de mașini din BD care au o anumită marcă
    @Query("SELECT COUNT(*) FROM masini WHERE marca = :marca")
    long countByMarca(String marca);

    // Determinarea numărului de mașini care au sub 100.000 km
    @Query("SELECT COUNT(*) FROM masini WHERE kilometri < 100000")
    long countByKilometriLessThan(int kilometri);

    // Extragerea mașinilor mai noi de 5 ani
    @Query("SELECT * FROM masini WHERE an_fabricatie > :anFabricatie")
    List<Masina> findByAnFabricatieGreaterThan(int anFabricatie);
}

@Service
public class MasinaService {

    @Autowired
    private MasinaRepository masinaRepository;

    // Adăugarea unei mașini
    public void adaugaMasina(Masina masina) {
        masinaRepository.save(masina);
    }

    // Ștergerea unei mașini după numărul de înmatriculare
    public void stergeMasina(String numarInmatriculare) {
        masinaRepository.deleteByNumarInmatriculare(numarInmatriculare);
    }

    // Căutarea unei mașini
    public Masina cautaMasina(String numarInmatriculare) {
        return masinaRepository.findByNumarInmatriculare(numarInmatriculare);
    }

    // Extragerea tuturor mașinilor
    public List<Masina> toateMasinile() {
        return masinaRepository.findAll();
    }

    // Determinarea numărului de mașini cu o anumită marcă
    public long numarMasiniMarca(String marca) {
        return masinaRepository.countByMarca(marca);
    }

    // Determinarea numărului de mașini cu sub 100.000 km
    public long numarMasiniSub100000Km() {
        return masinaRepository.countByKilometriLessThan(100000);
    }

    // Extragerea mașinilor mai noi de 5 ani
    public List<Masina> masiniMaiNoiDe5Ani() {
        int anCurent = java.time.LocalDate.now().getYear();
        return masinaRepository.findByAnFabricatieGreaterThan(anCurent - 5);
    }
}

@RestController
@RequestMapping("/masini")
public class MasinaController {

    @Autowired
    private MasinaService masinaService;

    @PostMapping
    public void adaugaMasina(@RequestBody Masina masina) {
        masinaService.adaugaMasina(masina);
    }

    @DeleteMapping("/{numarInmatriculare}")
    public void stergeMasina(@PathVariable String numarInmatriculare) {
        masinaService.stergeMasina(numarInmatriculare);
    }

    @GetMapping("/{numarInmatriculare}")
    public Masina cautaMasina(@PathVariable String numarInmatriculare) {
        return masinaService.cautaMasina(numarInmatriculare);
    }

    @GetMapping
    public List<Masina> toateMasinile() {
        return masinaService.toateMasinile();
    }

    @GetMapping("/marca/{marca}")
    public long numarMasiniMarca(@PathVariable String marca) {
        return masinaService.numarMasiniMarca(marca);
    }

    @GetMapping("/km")
    public long numarMasiniSub100000Km() {
        return masinaService.numarMasiniSub100000Km();
    }

    @GetMapping("/maiNoiDe5Ani")
    public List<Masina> masiniMaiNoiDe5Ani() {
        return masinaService.masiniMaiNoiDe5Ani();
    }
}

@SpringBootApplication
public class MasiniApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasiniApplication.class, args);
    }
}



