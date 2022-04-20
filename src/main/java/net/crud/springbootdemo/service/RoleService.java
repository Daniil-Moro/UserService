package net.crud.springbootdemo.service;

import net.crud.springbootdemo.repository.RoleRepository;
import net.crud.springbootdemo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String roleName) { return roleRepository.findByName(roleName); }

    public Role findById(Long id){
        return roleRepository.findById(id).get();
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role saveRole(Role Role){
        return roleRepository.save(Role);
    }

    public void deleteById(Long id){
        roleRepository.deleteById(id);
    }

    public Role updateRole(Role Role) { return roleRepository.save(Role); }
}
