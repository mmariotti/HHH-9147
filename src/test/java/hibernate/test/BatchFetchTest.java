package hibernate.test;

import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.hibernate.Hibernate;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import hibernate.ejb.DummyService;
import hibernate.model.Item;


public class BatchFetchTest
{
    private static Context ctx;

    private static EJBContainer ejbContainer;

    private DummyService service;

    @BeforeClass
    public void setUp()
    {
        ejbContainer = EJBContainer.createEJBContainer();
        ctx = ejbContainer.getContext();
    }

    @Before
    public void recall() throws NamingException
    {
        service = (DummyService) ctx.lookup(DummyService.LOOKUP);
        Assert.assertNotNull(service);
    }

    @AfterClass
    public void tearDown()
    {
        ejbContainer.close();
        System.out.println("Closing the container");
    }

    @Test
    public void test1()
    {
        service.accept(em ->
        {
            List<Item> items = em.createQuery("select x from Item x where x.code = 'first'", Item.class).getResultList();

            Item item = items.get(0);
            Hibernate.initialize(item.getComponents());
        });
    }

    @Test
    public void test2()
    {
        service.consume(em ->
        {
            List<Item> items = em.createQuery("select x from Item x where x.code = 'first'", Item.class).getResultList();

            Item item = items.get(0);
            Hibernate.initialize(item.getComponents());
        });

    }
}
