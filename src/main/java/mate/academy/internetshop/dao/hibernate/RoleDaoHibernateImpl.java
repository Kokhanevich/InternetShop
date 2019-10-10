package mate.academy.internetshop.dao.hibernate;

import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.util.HibernateUtil;
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
