package br.com.fatec.frete.repository;

import br.com.fatec.frete.entity.Endereco;
import br.com.fatec.frete.entity.Frete;
import br.com.fatec.frete.exception.InternalServerException;
import br.com.fatec.frete.exception.NotFoundException;
import br.com.fatec.frete.repository.adapter.FreteRepositoryAdapter;
import br.com.fatec.frete.repository.client.FreteRepositoryWithMongo;
import br.com.fatec.frete.repository.orm.EnderecoOrm;
import br.com.fatec.frete.repository.orm.FreteOrm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FreteRepositoryImpl implements FreteRepository {
    private static final Logger LOG = LoggerFactory.getLogger(FreteRepositoryImpl.class);

    private final FreteRepositoryWithMongo repository;

    public FreteRepositoryImpl(FreteRepositoryWithMongo repository) {
        this.repository = repository;
    }

    @Override
    public Frete save(Frete frete) {
        try {
            Optional<FreteOrm> optional = repository.findById(frete.id());
            if (optional.isEmpty()) {
                return save(UUID.randomUUID().toString(), frete);
            }
            FreteOrm orm = optional.get();
            return save(orm.id(), frete);
        } catch (Exception ex) {
            LOG.error("Erro ao salvar usuario: {} o erro aconteceu na data/hora: {}",
                    ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }

    private Frete save(final String id, Frete frete) {
        Endereco endereco = frete.endereco();
        FreteOrm newFrete = new FreteOrm(
                id,
                frete.client(),
                frete.valor(),
                new EnderecoOrm(
                        endereco.uf(),
                        endereco.logradouro(),
                        endereco.complemento(),
                        endereco.bairro(),
                        endereco.cep()),
                frete.status());
        return FreteRepositoryAdapter.cast(repository.save(newFrete));
    }

    @Override
    @Cacheable(value = "frete-cache", key = "#id")
    public Frete findById(final String id) {
        try {
            Optional<FreteOrm> optional = repository.findById(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("Frete nao existe");
            }
            return FreteRepositoryAdapter.cast(
                    repository.save(optional.get()));
        } catch (NotFoundException ex) {
            LOG.info("Usuario nao encontrado");
            throw ex;
        } catch (Exception ex) {
            LOG.error("Erro ao procurar usuario por id: {} o erro aconteceu na data/hora: {}",
                    ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }

    @Override
    public void delete(final String id) {
        try {
            repository.deleteById(id);
        } catch (Exception ex) {
            LOG.error("Erro ao deletar usuario: {} o erro aconteceu na data/hora: {}",
                    ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }
}
