package cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.repository;

import cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.domain.Fruita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Class Repository for Fruita, to management MongoDB.
 */
@Repository
public interface FruitaRepo extends MongoRepository <Fruita, String> {
}


