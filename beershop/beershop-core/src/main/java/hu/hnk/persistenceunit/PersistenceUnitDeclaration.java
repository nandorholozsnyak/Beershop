package hu.hnk.persistenceunit;

/**
 * A persistence.xml fájlhoz tartozó Java deklarációs fájl, amiben állítható a
 * PersistenceContext számára használandó perzisztens egység hivatkozása.
 * 
 * @author Nandi
 *
 */
public class PersistenceUnitDeclaration {

	/**
	 * A MySQL-es beállításra vonatkozó konstans.
	 */
	public final static String MYSQL = "BeerShopUnit";

	/**
	 * Az Apache Derby-s beállításra vonatkozó konstans.
	 */
	public final static String DERBY = "BeerShopUnitDerby";

	/**
	 * 
	 */
	public final static String ORACLE = "BeerShopUnitOra";

	/**
	 * 
	 */
	public final static String H2 = "BeerShopUnitH2";

	/**
	 * 
	 */
	public final static String HSQLDB = "BeerShopUnitHSQLDB";

	/**
	 * Az aktuálisan használandó perzisztens egység neve.
	 */
	public final static String PERSISTENCE_UNIT = DERBY;

}
