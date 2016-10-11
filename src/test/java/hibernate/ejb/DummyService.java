package hibernate.ejb;

import java.io.Serializable;
import java.util.function.Consumer;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import hibernate.model.Component;
import hibernate.model.Item;


@Stateless
@Local
public class DummyService implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** The Constant LOOKUP. */
    public static final String LOOKUP = "java:module/DummyService";

    /** The em. */
    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void consume(Consumer<EntityManager> consumer)
    {
        consumer.accept(em);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void accept(Consumer<EntityManager> consumer)
    {
        consumer.accept(em);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void populate()
    {
        Item item1 = new Item();
        item1.setCode("first");
        em.persist(item1);

        Item item2 = new Item();
        item2.setCode("second");
        em.persist(item2);

        for(int i = 0; i < 10; i++)
        {
            Component comp = new Component();
            comp.setName("comp " + i);
            comp.setItem(item1);
            item1.getComponents().add(comp);
            em.persist(comp);
        }
    }
}
