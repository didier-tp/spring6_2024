package tp.appliSpring.generic.service.hex;

import tp.appliSpring.generic.exception.EntityNotFoundException;
import tp.appliSpring.generic.model.WithId;
import tp.appliSpring.generic.hex.spi.Loader;
import tp.appliSpring.generic.hex.spi.Saver;
import tp.appliSpring.generic.service.GenericService;

import java.util.List;

/*
implementation selon l'architecture hexagonale avec SPI et persistence adapter
 */

public class GenericServiceHexImpl<T extends WithId,E,ID> implements GenericService<T,ID> {

    private Class<T> modelClass;
    private Loader<T,ID> loader;
    private Saver<T,ID> saver;

    public GenericServiceHexImpl(Class<T> modelClass ,
                                 Loader<T,ID> loader,
                                 Saver<T,ID> saver){
        this.modelClass=modelClass;
        this.loader=loader;
        this.saver=saver;
    }

    @Override
    public T searchById(ID id) throws EntityNotFoundException {
        return loader.loadById(id).get();
    }

    @Override
    public List<T> searchAll() {
        return loader.loadAll();
    }

    @Override
    public T create(T obj) {
        return saver.saveNew(obj);
    }

    @Override
    public T update(T obj) throws EntityNotFoundException {
        saver.updateExisting(obj);
        return obj;
    }

    @Override
    public void removeById(ID id) throws EntityNotFoundException {
        saver.deleteFromId(id);
    }
}
