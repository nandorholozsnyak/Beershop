/**
 * 
 */
package hu.hnk.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.service.interfaces.BeerService;
import hu.hnk.interfaces.BeerDao;
import hu.hnk.service.cobertura.annotation.CoverageIgnore;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(BeerService.class)
public class BeerServiceImpl implements BeerService {

	/**
	 * A söröket kezelő adathozzáférési objektum.
	 */
	@EJB
	private BeerDao beerDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CoverageIgnore
	public List<Beer> findAll() throws Exception {
		return beerDao.findAll();
	}

}
