package hu.hnk.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TestEJB {
	void doSomething() {
		System.out.println("Hello World!");
	}
}
