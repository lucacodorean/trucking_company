package app.services.implementation;

import java.util.List;
import app.models.Trailer;
import app.services.TrailerService;
import app.repositories.TrailerRepository;
import org.springframework.stereotype.Service;
import app.single_point_access.RepositorySinglePointAccess;

@Service
public class TrailerServiceImpl implements TrailerService {
    
    private TrailerRepository trailerRepository = RepositorySinglePointAccess.getTrailerRepository();

    @Override public Trailer save(Trailer trailer)     { return trailerRepository.save(trailer);  }
    @Override public List<Trailer> findAll()           { return trailerRepository.findAll();      }
    @Override public Trailer findByKey(String key)     { return trailerRepository.findByKey(key); }
    @Override public Trailer findById(Integer id)      { return trailerRepository.findById(id);   }
    @Override public Trailer update(Trailer trailer)   { return trailerRepository.update(trailer);}
    @Override public boolean delete(Trailer trailer)   { return trailerRepository.delete(trailer);}
}
