package pl.mati.taco.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.mati.taco.Taco;

import java.net.ContentHandler;
import java.util.List;

@Repository
public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

    Taco save(Taco taco);

    List<Taco> findAll(PageRequest pageRequest);
}
