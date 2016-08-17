package hu.hnk.beershop.integration;

import hu.hnk.beershop.model.Beer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.SpringServletContainerInitializer;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

/**
 * Created by Nandi on 2016.08.17..
 */
@RunWith(Arquillian.class)
public class UserServiceIntegrationTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(JavaArchive.class,"test.jar")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addPackage(Beer.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;


    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void firstTest() {
        Assert.assertNotNull(em);
    }

    @Test
    public void makeABeer() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        Beer beer = Beer.builder().name("Teszt Sör").alcoholLevel(10.0).discountAmount(10).price(10.0).build();
        utx.begin();
        em.joinTransaction();
        em.persist(beer);
        utx.commit();
        Assert.assertNotNull(beer.getId());
        Assert.assertNotNull(em.find(Beer.class,beer.getId()));
        Long id = beer.getId();
        logger.info("HELLO");
        Assert.assertEquals("Teszt Sör",em.find(Beer.class,id).getName());
        // clear the persistence context (first-level cache)
        em.clear();

    }
}
