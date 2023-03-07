package cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.repository;

import cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.domain.Fruita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class Repository for Fruita, to management SQL DDBB.
 */
@Repository
public interface FruitaRepo extends JpaRepository<Fruita, Integer> {
    boolean existsByNom(String nom);

}
