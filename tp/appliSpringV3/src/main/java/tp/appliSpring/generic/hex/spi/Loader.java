package tp.appliSpring.generic.hex.spi;

import java.util.List;
import java.util.Optional;

public  interface Loader<T,ID> {
	public Optional<T> loadById(ID id);
	default public Optional<T> loadById(ID id,String... wishedDetails)  {
		return loadById(id); //without in this default impl to override
	}
	public boolean existsWithThisId(ID id);
	public List<T> loadAll();
}
