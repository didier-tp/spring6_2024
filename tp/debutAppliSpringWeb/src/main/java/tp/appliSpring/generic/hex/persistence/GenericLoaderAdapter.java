package tp.appliSpring.generic.hex.persistence;

import tp.appliSpring.generic.exception.EntityNotFoundException;
import tp.appliSpring.generic.model.WithId;
import tp.appliSpring.generic.hex.spi.Loader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import tp.appliSpring.generic.converter.GenericMapper;

import java.util.List;
import java.util.Optional;

@Transactional
public class GenericLoaderAdapter<T extends WithId,E,ID> implements Loader<T,ID> {
    private Class<T> modelClass;
    private Class<E> entityClass;
    private CrudRepository<E,ID> crudRepository;

    public GenericLoaderAdapter(Class<T> modelClass ,
                                Class<E> entityClass ,
                                CrudRepository<E,ID> crudRepository){
        this.entityClass=entityClass;
        this.modelClass=modelClass;
        this.crudRepository=crudRepository;
    }

    @Override
    public Optional<T> loadById(ID id) throws EntityNotFoundException {
        Optional<E> optionalEntity = crudRepository.findById(id);
        if(optionalEntity.isPresent())
            return Optional.of(GenericMapper.MAPPER.map(optionalEntity.get(), modelClass));
        else
            return Optional.empty();
    }

    @Override
    public List<T> loadAll() {
        List<E> entityList = (List<E>)crudRepository.findAll();
        return GenericMapper.MAPPER.map(entityList, modelClass);
    }

    @Override
    public boolean existsWithThisId(ID id)  {
        return crudRepository.existsById(id);
    }

}
