package cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.repository;

import cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.domain.Fruita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitaRepoInter extends JpaRepository<Fruita, Integer> {

    @Query(value = "select * from Fruites where nom like ?1", nativeQuery = true)
    List<Fruita> findbyNom(String nom);

}


