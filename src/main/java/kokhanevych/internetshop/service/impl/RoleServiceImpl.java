package kokhanevych.internetshop.service.impl;

import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.lib.Service;
import kokhanevych.internetshop.dao.RoleDao;
import kokhanevych.internetshop.model.Role;
import kokhanevych.internetshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Inject
    private static RoleDao roleDao;

    @Override
    public Role get(Role.RoleName roleName) {
        return  roleDao.get(roleName);
    }
}
