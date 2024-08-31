package com.prototype.springP1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@PersistenceContext
@Transactional
public class EntityManagement implements AutoCloseable, Serializable
{

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private EntityManager entityManager = sessionFactory.openSession();

    private int size;

    private int counter = 0;

    private int option = 0;

    public EntityManagement()
    {

        activateTransaction();

    }

    public EntityManagement(Session session)
    {

        this.entityManager = session;

        activateTransaction();

    }

    private void configBatch(int item, int value)
    {

        if(item == 0)
        {

            this.size = value;

            this.entityManager.setProperty("hibernate.jdbc.batch_size", this.size);

        }
        else
        if(item == 1)
        {

            if (value == 1)
            {

                this.entityManager.setProperty("hibernate.order_inserts", true);

                if (this.option == 0)
                {

                    this.option = 1;

                }

                if(this.option == 2)
                {

                    this.option = 3;

                }

            } else
            if (value == 0)
            {

                this.entityManager.setProperty("hibernate.order_inserts", false);

                if (this.option == 3)
                {

                    this.option = 2;

                }

                if(this.option == 1)
                {

                    this.option = 0;

                }

            }

        }
        else
        if(item == 2)
        {

            if (value == 1)
            {

                this.entityManager.setProperty("hibernate.order_updates", true);

                this.entityManager.setProperty("hibernate.batch_versioned_data", true);

                if (this.option == 0)
                {

                    this.option = 2;

                }

                if(this.option == 1)
                {

                    this.option = 3;

                }

            } else
            if (value == 0)
            {

                this.entityManager.setProperty("hibernate.order_updates", false);

                this.entityManager.setProperty("hibernate.batch_versioned_data", false);

                if (this.option == 3)
                {

                    this.option = 1;

                }

                if(this.option == 2)
                {

                    this.option = 0;

                }

            }

        }

    }

    public class ConfigBatch
    {

        private ConfigBatch(){}

        private ConfigBatch size(int temp)
        {

            configBatch(0, temp);

            return this;

        }

        public ConfigBatch configBatchInsert(boolean insertBatchState)
        {

            if(insertBatchState)
            {

                configBatch(1, 1);

            }

            if(!insertBatchState)
            {

                configBatch(1, 0);

            }

            return this;

        }

        public ConfigBatch configBatchUpdate(boolean updateBatchState)
        {

            if(updateBatchState)
            {

                configBatch(2, 1);

            }

            if(!updateBatchState)
            {

                configBatch(2, 0);

            }

            return this;

        }

    }

    public void activateTransaction()
    {

        if(!this.entityManager.getTransaction().isActive())
        {

            this.entityManager.getTransaction().begin();

        }

    }

    public <T> T find(long id, Class<T> type)
    {

        return this.entityManager.find(type, id);

    }

    public void persist(Object object)
    {

        this.entityManager.persist(object);

        if(option == 3 || option == 1)
        {

            counter++;

            if(counter == size)
            {

                counter = 0;

                flush();

                clear();

            }

        }

    }

    public void merge(Object object)
    {

        this.entityManager.merge(object);

        if(option == 3 || option ==2)
        {

            counter++;

            if(counter == size)
            {

                counter = 0;

                flush();

                clear();

            }

        }

    }

    public void remove(Object object)
    {

        this.entityManager.remove(object);

        if(option == 3 || option ==2)
        {

            counter++;

            if(counter == size)
            {

                counter = 0;

                flush();

                clear();

            }

        }

    }

    private boolean isActive()
    {

        if(!this.entityManager.getTransaction().isActive())
        {

            System.out.println("Transaction Is Currently Inactive");

            return false;

        }

        return true;

    }

    public void flush()
    {

        if(isActive())
        {

            this.entityManager.flush();

        }

    }

    public void clear()
    {

        this.entityManager.clear();

    }

    public void commit()
    {

        if (isActive())
        {

            this.entityManager.getTransaction().commit();

        }

    }

    public <T> QBuilder<T> QueryBuilder()
    {

        return new QBuilder<>(this.entityManager);

    }

    public static class QBuilder<T>
    {

        private final EntityManager entityManager;

        public final CriteriaBuilder criteriaBuilder;

        public final CriteriaQuery<T> criteriaQuery;

        private Class<T> type;

        private QBuilder(EntityManager entityManager)
        {

            this.entityManager = entityManager;

            this.criteriaBuilder = this.entityManager.getCriteriaBuilder();

            this.criteriaQuery = this.criteriaBuilder.createQuery(type);

        }

        public List<T> getList()
        {

            return entityManager.createQuery(criteriaQuery).getResultList();

        }

        public T getSingle()
        {

            if(entityManager.createQuery(criteriaQuery).getResultList().size() == 1)
            {

                return entityManager.createQuery(criteriaQuery).getSingleResult();

            }

            return null;

        }

        public int count()
        {

            return getList().size();

        }

    }

    @Override
    public void close()
    {

        commit();

        this.entityManager.close();

        if(sessionFactory.getCurrentSession().isOpen())
        {

            sessionFactory.getCurrentSession().close();

        }

    }

}
