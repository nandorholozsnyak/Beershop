package hu.hnk.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.BaseEntity;
import hu.hnk.interfaces.BaseDao;
import hu.hnk.persistenceunit.PersistenceUnitDeclaration;

/**
 * Egy bázis adathozzáférési osztály amely az alapvető adatbázis műveletek fogja
 * nekünk szolgáltatni egy abstract osztályként. A négy darab alap műveletet itt
 * írjuk le, kihasználva az öröklődést, ezzel gyorsítva a fejlesztés menetét,
 * minden egyes DAO megírásakor csak ezt az osztályt kell kiterjeszteni és
 * azonnal használhatóvá válik a négy darab művelet.
 * {@link BaseDaoImpl#save(BaseEntity)} - egy új entitás mentése az adatbázisba
 * {@link BaseDaoImpl#update(BaseEntity)} - már egy meglévő entitás frissítése
 * {@link BaseDaoImpl#delete(Long)} - egy meglévő entitás törlés, paramétere az
 * entitás azonosítója {@link BaseDaoImpl#find(Long)} - entitás keresése az
 * azonosítója alapján.
 * 
 * @author Nandi
 * @param <E>
 *            a DAO által kezelt entitás
 *
 */
public abstract class BaseDaoImpl<E extends BaseEntity> implements BaseDao<E> {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	/**
	 * Az entitásokat kezelő entitás menedzser objektum.
	 */
	@PersistenceContext(unitName = PersistenceUnitDeclaration.PERSISTENCE_UNIT)
	protected EntityManager entityManager;

	/**
	 * A bázis adathozzáférési osztály által kezelt entitás osztálya.
	 */
	protected Class<E> entityClass;

	/**
	 * Az osztály konstuktora.
	 * 
	 * @param entityClass
	 *            a kezelendő entitás.
	 */
	public BaseDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E save(E entity) throws Exception {
		logger.info("Persisting entity of class:" + entity.getClass());
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(E entity) throws Exception {
		logger.info("Updating entity of class:" + entity.getClass());
		this.entityManager.merge(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Long id) throws Exception {

		E e = this.entityManager.find(entityClass, id);
		logger.info("Deleting entity of class:" + e.getClass());
		this.entityManager.remove(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E find(Long id) throws Exception {
		logger.info("Finding entity of class:" + entityClass);
		return this.entityManager.find(entityClass, id);
	}

}
