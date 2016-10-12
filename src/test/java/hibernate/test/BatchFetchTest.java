package hibernate.test;

import java.util.List;
import javax.ejb.EJB;
import org.hibernate.Hibernate;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import hibernate.ejb.DummyService;
import hibernate.model.Item;


@RunWith(Arquillian.class)
public class BatchFetchTest
{
    @EJB
    private DummyService service;

    @Deployment
    public static WebArchive createDeployment()
    {
        // Create deploy file
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.addPackages(true, "hibernate");

        // Import Maven runtime dependencies
//        File[] files = Maven.resolver()
//            .loadPomFromFile("pom.xml")
//            .importRuntimeDependencies()
//            .resolve()
//            .withTransitivity()
//            .asFile();
//
//        for(File file : files)
//        {
//            war.addAsLibrary(file);
//        }

        war.addAsResource("META-INF/persistence.xml");
        war.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        // Show the deploy structure
        System.out.println(war.toString(true));

        return war;
    }

    @Before
    public void setUp()
    {
        service.populate();
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
