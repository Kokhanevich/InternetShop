package kokhanevych.internetshop.dao.hibernate;

import kokhanevych.internetshop.lib.Dao;
import kokhanevych.internetshop.dao.RoleDao;
import kokhanevych.internetshop.model.Role;
import kokhanevych.internetshop.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Dao
public class RoleDaoHibernateImpl implements RoleDao {
    @Override
    public Role get(Role.RoleName roleName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Role where roleName=:roleName");
            query.setParameter("roleName", roleName);
            Role role = (Role) query.uniqueResult();
            return role;
        }
    }
}
