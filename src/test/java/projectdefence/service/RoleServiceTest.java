package projectdefence.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import projectdefence.models.entities.Role;
import projectdefence.models.serviceModels.RoleServiceModel;
import projectdefence.repositories.RoleRepository;
import projectdefence.service.impl.RoleServiceImpl;

import java.util.List;
import java.util.Set;

public class RoleServiceTest {

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;
    private RoleService roleService;
    private Role role;

    @Before
    public void init() {
        roleRepository = Mockito.mock(RoleRepository.class);
        roleService = new RoleServiceImpl(roleRepository, new ModelMapper());


        role = new Role();
        role.setAuthority("ROLE_USER");


        Mockito.when(roleRepository.count())
                .thenReturn(1L);
    }

    @Test
    public void testSeedRolesInDb() {
        roleService.seedRolesInDb();
        Assert.assertEquals(1, roleRepository.count());
    }

    @Test
    public void tesFindAllRoles() {
        Mockito.when(roleRepository.findAll()).thenReturn(List.of(role));
        Set<RoleServiceModel> allRoles = roleService.findAllRoles();
        Assert.assertEquals(1, allRoles.size());
    }


    @Test
    public void tesFindByAuthority() {
        Mockito.when(roleRepository.findByAuthority("ROLE_USER")).thenReturn(role);
        String roleStr = "ROLE_USER";
        roleService.findByAuthority(roleStr);
        Assert.assertEquals(role.getAuthority(), roleStr);
    }

}
