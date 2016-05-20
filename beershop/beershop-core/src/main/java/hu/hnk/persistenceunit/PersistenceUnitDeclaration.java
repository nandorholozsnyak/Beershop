package hu.hnk.persistenceunit;

/**
 * A persistence.xml fájlhoz tartozó Java deklarációs fájl, amiben állítható a
 * <code>PersistenceContext</code> számára használandó perzisztens egység
 * hivatkozása.
 * 
 * @author Nandi
 *
 */
public class PersistenceUnitDeclaration {

	/**
	 * A MySQL-es beállításra vonatkozó konstans.
	 */
	public static final String MYSQL = "BeerShopUnit";

	/**
	 * Az Apache Derby-s beállításra vonatkozó konstans.
	 */
	public static final String DERBY = "BeerShopUnitDerby";

	/**
	 * Az egyetemi Oracle-s szerverre vonatkozó konstans.
	 */
	public static final String ORACLE = "BeerShopUnitOra";

	/**
	 * A lokális H2 in-memory DB-re vonatkozó konstans.
	 */
	public static final String H2 = "BeerShopUnitH2";

	/**
	 * Az aktuálisan használandó perzisztens egység neve.
	 */
	public static final String PERSISTENCE_UNIT = H2;

	/**
	 * Privát konstuktor.
	 */
	private PersistenceUnitDeclaration() {

	}

}
