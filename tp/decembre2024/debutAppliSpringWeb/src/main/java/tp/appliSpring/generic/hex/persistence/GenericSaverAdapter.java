package tp.appliSpring.generic.hex.persistence;

import tp.appliSpring.generic.model.WithId;
import tp.appliSpring.generic.hex.spi.Saver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import tp.appliSpring.generic.converter.GenericMapper;
import tp.appliSpring.generic.exception.EntityNotFoundException;

@Transactional
public class GenericSaverAdapter<T extends WithId,E,ID> implements Saver<T,ID> {
    private Class<T> modelClass;
    private Class<E> entityClass;
    private CrudRepository<E,ID> crudRepository;

    public GenericSaverAdapter(Class<T> modelClass ,
                               Class<E> entityClass ,
                               CrudRepository<E,ID> crudRepository){
        this.entityClass=entityClass;
        this.modelClass=modelClass;
        this.crudRepository=crudRepository;
    }


    @Override
    public T saveNew(T obj) {
        E entity = GenericMapper.MAPPER.map(obj,entityClass);
        E savedEntity = crudRepository.save(entity);
        return  GenericMapper.MAPPER.map(savedEntity,modelClass);
    }

    @Override
    public void updateExisting(T obj) throws EntityNotFoundException {
        ID id = (ID) obj.extractId();
        if(!crudRepository.existsById(id))
            throw new EntityNotFoundException("entity not found with id="+id);
        E entity = GenericMapper.MAPPER.map(obj,entityClass);
        E updatedEntity = crudRepository.save(entity);
    }

    @Override
    public void deleteFromId(ID id) throws EntityNotFoundException {
        if(!crudRepository.existsById(id))
            throw new EntityNotFoundException("entity not found with id="+id);
        crudRepository.deleteById(id);
    }
}
