package app.services.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import app.dto.RoleDTO;
import app.models.Role;
import app.services.RoleService;
import app.repositories.RoleRepository;
import app.single_point_access.RepositorySinglePointAccess;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository = RepositorySinglePointAccess.getRoleRepository();

    @Override public Role save(Role entity)      { return roleRepository.save(entity);   }
    @Override public List<Role> findAll()           { return roleRepository.findAll();      }
    @Override public Role findByKey(String key)     { return roleRepository.findByKey(key); }
    @Override public Role findById(Integer id)      { return roleRepository.findById(id);   }
    @Override public Role update(Role entity)    { return roleRepository.update(entity); }
    @Override public boolean delete(Role entity)    { return roleRepository.delete(entity); } 
}
