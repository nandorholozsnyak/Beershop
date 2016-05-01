package hu.hnk.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.hnk.beershop.model.BaseEntity;
import hu.hnk.interfaces.BaseDao;
import hu.hnk.persistenceunit.PersistenceUnitDeclaration;

/**
 * Egy bázis adathozzáférési osztály amely az alapvetõ adatbázis mûveletek fogja
 * nekünk szolgáltatni egy abstract osztályként. A négy darab alap mûveletet itt
 * írjuk le, kihasználva az öröklõdést, ezzel gyorsítva a fejlesztés menetét,
 * minden egyes DAO megírásakor csak ezt az osztályt kell kiterjeszteni és
 * azonnal használhatóvá válik a négy darab mûvelet.
 * {@link BaseDaoImpl#save(BaseEntity)} - egy új entitás mentése az adatbázisba
 * {@link BaseDaoImpl#update(BaseEntity)} - már egy meglévõ entitás frissítése
 * {@link BaseDaoImpl#delete(Long)} - egy meglévõ entitás törlés, paramétere az
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
	 * Az entitásokat kezelõ entitás menedzser objektum.
	 */
	@PersistenceContext(unitName = PersistenceUnitDeclaration.PERSISTENCE_UNIT)
	protected EntityManager entityManager;

	protected Class<E> entityClass;

	/**
	 * Az osztály konstuktora.
	 * 
	 * @param entityClass
	 *            a kezelendõ entitás.
	 */
	public BaseDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E save(E entity) throws Exception {
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(E entity) throws Exception {
		this.entityManager.merge(entity);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Long id) throws Exception {
		E e = this.entityManager.find(entityClass, id);
		this.entityManager.remove(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E find(Long id) throws Exception {
		return this.entityManager.find(entityClass, id);
	}

}
