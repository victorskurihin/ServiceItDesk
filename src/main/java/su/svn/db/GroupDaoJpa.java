/*
 * GroupDaoJpa.java
 * This file was last modified at 2019.01.23 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package su.svn.db;

import su.svn.models.Group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@TransactionAttribute(SUPPORTS)
public class GroupDaoJpa implements GroupDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    public static final String SELECT_ALL = "SELECT g FROM Group g";

    public static final String SELECT_WHERE_NAME = SELECT_ALL + " WHERE g.name LIKE :name";

    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE g.description LIKE :desc";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDaoJpa.class);

    public GroupDaoJpa() { /* None */}

    public GroupDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    public List<Group> emptyList() {
        //noinspection unchecked
        return Collections.EMPTY_LIST;
    }

    @Override
    public Group findById(Long id)
    {
        try {
            return em.find(Group.class, id);
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return null;
        }
    }

    @Override
    public List<Group> findAll()
    {
        try {
            return em.createQuery(SELECT_ALL, Group.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return emptyList();
        }
    }

    @Override
    public List<Group> findByName(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_NAME, Group.class)
                .setParameter("name", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by name: {} because had the exception {}", value, e);
            return emptyList();
        }
    }

    @Override
    public List<Group> findByDescription(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_DESC, Group.class)
                .setParameter("desc", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by description: {} because had the exception {}", value, e);
            return emptyList();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean save(Group entity)
    {
        try {
            if (null == entity.getId() || 0 == entity.getId()) {
                em.persist(entity);
                em.flush();
            } else {
                em.merge(entity);
            }
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save group with id: {} because had the exception {}", entity.getId(), e);
            return false;
        }

        LOGGER.info("Save group with id: {}", entity.getId());
        return true;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        try {
            Group merged = em.merge(findById(id));
            em.remove(merged);
            LOGGER.info("Delete genre id: {}", merged.getId());
            return true;
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save group with id: {} because had the exception {}", id, e);
            return false;
        }
    }

    EntityManager getEntityManager()
    {
        return em;
    }

    void setEntityManager(EntityManager entityManager)
    {
        this.em = entityManager;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF