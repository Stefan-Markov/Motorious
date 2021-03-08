package projectdefence.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import projectdefence.models.entities.Role;
import projectdefence.models.serviceModels.RoleServiceModel;
import projectdefence.repositories.RoleRepository;
import projectdefence.service.RoleService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.save(new Role("ROLE_KINESITHERAPIST"));
            this.roleRepository.save(new Role("ROLE_USER"));
            this.roleRepository.save(new Role("ROLE_ADMIN"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
