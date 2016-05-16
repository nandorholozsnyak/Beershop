package hu.hnk.service.dbinjector;

import java.time.LocalDate;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.Role;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.BeerDao;
import hu.hnk.interfaces.CartDao;
import hu.hnk.interfaces.RoleDao;
import hu.hnk.interfaces.StorageDao;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.cobertura.annotation.CoverageIgnore;

/**
 * @author Nandi
 *
 */
@Startup
@Singleton
@CoverageIgnore
public class DatabaseInjector {
	/**
	 * Az osztály loggere.
	 */
	@CoverageIgnore
	public static final Logger logger = LoggerFactory.getLogger(DatabaseInjector.class);

	/**
	 * A felhasználókat kezelő adathozzáférési objektum.
	 */
	@EJB
	private UserDao userDao;

	/**
	 * A söröket kezelő adathozzáférési objektum.
	 */
	@EJB
	private BeerDao beerDao;

	/**
	 * A jogköröket kezelő adathozzáférési objektum.
	 */
	@EJB
	private RoleDao roleDao;

	/**
	 * A kosarat kezelő adathozzáférési objektum.
	 */
	@EJB
	private CartDao cartDao;

	/**
	 * A raktárt kezelő adathozzáférési objektum.
	 */
	@EJB
	private StorageDao storageDao;

	/**
	 * Az adatbázisba mentendő sörök nevei.
	 */
	@CoverageIgnore
	private String[] beerNames = { "Ultra sör", "Bivaly sör", "Habos sör", "Jópofa sör", "Bebarnult sör", "Lime sör",
			"Meghabosodott sör", "Party hordó", "25L-es hordó", "Szerencse sör", "Csapos party hordó", "Barátság sör",
			"Jéghegy sör", "Őszi sör", "Fehér-Barna sör", "Sárga sör" };
	/**
	 * Az adatbázisba mentendő sörök leírása.
	 */
	@CoverageIgnore
	private String[] comments = { " világ egyik legerősebb söre.", "A világ főleg Európai országaiban kedvelt sör.",
			"Egy igazán habos sör az unalmas hétköznapokra.", "Egy jó buliban mindig van szükség egy jó pofa sörre.",
			"Az egyik legfinomabb barna sör amit inni fog.", "Egy igazán jól elkészített lime ízesítésű sör.",
			"Egy igazán habosra készített söröcske.",
			"A nyári bulik egyik alapvető kelléke, egy 50 literes party hordó.",
			"Egy meleg nyári délutánon a haverokkal akár 25 litert is képesek vagyunk elfogyasztani, jéghidegen a legjobb.",
			"A mindennapok szerencséire való hűsítő ital. (http://www.iconka.com)", "Csappal ellátott party hordó.",
			"A legjobb barátok szerencse söre, most párban. (http://www.iconka.com)", "Igazán hűsítő dobozos söröcske.",
			"Hűsítő, októberi finomság.", "Kezdetben fehér aztán pedig barna sör is akár.",
			"Sárga sör, az unalmas hétköznapokra." };

	/**
	 * Inicializáló metódus, feltölti az adatbázist a Singleton bean
	 * elindulásakor.
	 */
	@PostConstruct
	@CoverageIgnore
	public void init() {

		logger.info("DatabaseInjector started...");
		// Sörök generálása
		generateBeers();
		// Raktár feltöltése
		fillStorage();

		Role userRole;
		Role adminRole;
		// Default jogkörök.
		createRoles();
		userRole = roleDao.findByName("ROLE_USER");
		adminRole = roleDao.findByName("ROLE_ADMIN");

		// Default user létrehozása, ha kell
		createDefaultUser(userRole, adminRole);

	}

	/**
	 * A raktárt feltöltő metódus.
	 */
	@CoverageIgnore
	private void fillStorage() {
		if (storageDao.findAll()
				.isEmpty()) {
			for (Beer b : beerDao.findAll()) {
				try {
					StorageItem item = StorageItem.builder()
							.beer(b)
							.quantity(20)
							.build();
					item = storageDao.save(item);
					logger.info("Storage Item saved:" + item);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * A söröket generáló metódus.
	 */
	@CoverageIgnore
	private void generateBeers() {
		if (beerDao.findAll()
				.isEmpty()) {
			Beer beer;
			for (int i = 0; i < beerNames.length; i++) {
				beer = Beer.builder()
						.name(beerNames[i])
						.comment(comments[i])
						.alcoholLevel(11.6)
						.discountAmount(0)
						.capacity(0.5)
						.price(250.0)
						.build();
				try {
					beerDao.save(beer);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * A jogköröket létrehozó metódus.
	 */
	@CoverageIgnore
	private void createRoles() {
		Role userRole;
		Role adminRole;
		if (roleDao.findAll()
				.isEmpty()) {
			userRole = Role.builder()
					.name("ROLE_USER")
					.build();
			adminRole = Role.builder()
					.name("ROLE_ADMIN")
					.build();
			try {
				roleDao.save(adminRole);
				roleDao.save(userRole);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}
	}

	/**
	 * Az alapértelmezett felhasználót létrehozó metódus, melynek két
	 * paramétereként meg kell adnunk az alapértelmezett felhaszlóni illetve
	 * adminisztrátori jogkört leíró <code>Role</code> entitást.
	 * 
	 * @param userRole
	 *            alapértelmezett felhasználó jogkör.
	 * @param adminRole
	 *            alapértelmezett adminisztrátori jogkör.
	 */
	@CoverageIgnore
	private void createDefaultUser(Role userRole, Role adminRole) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (userDao.findAll()
				.isEmpty()) {

			User defUser = User.builder()
					.username("admin")
					.password(encoder.encode("admin"))
					.roles(Arrays.asList(userRole, adminRole))
					.email("admin@admin.com")
					.experiencePoints(5000.0)
					.money(5000.0)
					.points(0.0)
					.dateOfBirth(LocalDate.of(1995, 10, 20))
					.build();
			try {
				userDao.save(defUser);
				User foundUser = userDao.findByUsername("admin");
				Cart cart = cartDao.save(Cart.builder()
						.user(foundUser)
						.build());
				foundUser.setCart(cart);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Beállítja a felhasználókat kezelő adathozzáféréi objektumot.
	 * 
	 * @param userDao
	 *            a felhasználókat kezelő adathozzáférési objektum.
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Beállítja a söröket kezelő adathozzáféréi objektumot.
	 * 
	 * @param beerDao
	 *            a söröket kezelő adathozzáférési objektum.
	 */
	public void setBeerDao(BeerDao beerDao) {
		this.beerDao = beerDao;
	}

	/**
	 * Beállítja a jogköröket kezelő adathozzáféréi objektumot.
	 * 
	 * @param roleDao
	 *            a jogköröket kezelő adathozzáférési objektum.
	 */
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * Beállítja a kosarat kezelő adathozzáféréi objektumot.
	 * 
	 * @param cartDao
	 *            a kosarat kezelő adathozzáférési objektum.
	 */
	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}

	/**
	 * Beállítja a felhasználókat kezelő adathozzáféréi objektumot.
	 * 
	 * @param storageDao
	 *            a raktárt kezelő adathozzáférési objektum.
	 */
	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	}

}
