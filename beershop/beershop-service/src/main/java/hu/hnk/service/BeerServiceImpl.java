/**
 * 
 */
package hu.hnk.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.service.interfaces.BeerService;
import hu.hnk.interfaces.BeerDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(BeerService.class)
public class BeerServiceImpl implements BeerService {

	@EJB
	BeerDao beerDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.hnk.beershop.service.interfaces.BeerService#findAll()
	 */
	@Override
	public List<Beer> findAll() {
		return beerDao.findAll();
	}

}
