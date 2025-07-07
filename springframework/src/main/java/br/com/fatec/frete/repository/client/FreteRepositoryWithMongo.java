package br.com.fatec.frete.repository.client;

import br.com.fatec.frete.repository.orm.FreteOrm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreteRepositoryWithMongo extends MongoRepository<FreteOrm, String> {

}
