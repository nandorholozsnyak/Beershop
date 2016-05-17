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
	 * Privát konstuktor.
	 */
	private PersistenceUnitDeclaration() {

	}

	/**
	 * A MySQL-es beállításra vonatkozó konstans.
	 */
	public static final String MYSQL = "BeerShopUnit";

	/**
	 * Az Apache Derby-s beállításra vonatkozó konstans.
	 */
	public static final String DERBY = "BeerShopUnitDerby";

	/**
	 * 
	 */
	public static final String ORACLE = "BeerShopUnitOra";

	/**
	 * 
	 */
	public static final String H2 = "BeerShopUnitH2";

	/**
	 * 
	 */
	public static final String HSQLDB = "BeerShopUnitHSQLDB";

	/**
	 * Az aktuálisan használandó perzisztens egység neve.
	 */
	public static final String PERSISTENCE_UNIT = H2;

}
