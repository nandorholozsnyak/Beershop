[![Build Status](https://travis-ci.org/Holi60k/Beershop.svg?branch=master)](https://travis-ci.org/Holi60k/Beershop)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/7d87cbfeb50f4dfdb2589ad9c9f9d31a)](https://www.codacy.com)


# Beershop
## Programming technologies and environments

This is going to be a project for a course called Programming Environments.

## Used technologies:
### DAO layer:
1. JPA
    1. Hibernate
2. JavaEE
    1. EJB

### Service layer
1. JavaEE
    1. EJB

### Web/Presentation layer:
1. JSF 
2. Spring Security - for credentials

## Install
You need Java 1.8 at least and Maven 3.x+.

A Glassfish with configured connection pool and data sources is needed, use the beerShopCon as the datasource name (if you use MySQL), othwerwise follow the instructions to use it with embedded-glassfish.

1. `mvn clean install`  in the main directory called beershop
2. `cd beershop-ear` then `mvn -P glassfish embedded-glassfish:run` - it start a local embedded glassfish on your computer and the webapp can be accessed on http://127.0.0.1/beershop (default is uses an embedded H2 in memory DB)
3. Default username/password is admin.



## What is it about?
The beershop is a shop where people can register & login and buy some nice beer.
When you register you have to add some of your personal (sure :) ) informations and then you can buy beers.
There are some kind of restrictions, there are a few ranks you have to earn,
first when you register and you can only make 3 orders a day, then later it can be more.
With every order your experience points are increased and you can level up.

There are always good discounts so you should check us evey day :)

## Beer icons link and license:

Ultra sör - http://www.iconarchive.com/show/multiple-smiley-icons-by-icons-land/Beer-Beaten-icon.html
	License: Free for non-commercial use.

Habos sör - http://www.iconarchive.com/show/beach-icons-by-dapino/beer-icon.html
	License:CC Attribution-Noncommercial 4.0

Bebarnult sör - http://www.iconarchive.com/show/brilliant-food-icons-by-iconshock/beer-icon.html
	License: Free for non-commercial use.

Meghabosodott sör - http://www.iconarchive.com/show/real-vista-food-icons-by-iconshock/beer-icon.html
	License:Free for non-commercial use.

Party hordó - http://www.clker.com/clipart-3486.html

25 Literes hordó - http://www.clker.com/clipart-10128.html

Csapos party hordó - http://www.clker.com/clipart-beer-keg.html

Jéghegy sör - http://www.clker.com/clipart-beer-13.html

Őszi sör - http://www.clker.com/clipart-oktoberfest-glass-1.html

Fehér barna sör - http://www.clker.com/clipart-pumpkin-ale.html

Sárga sör - http://www.clker.com/clipart-yellow-beer-mug.html
	License: - http://www.clker.com/faq.html

Szerencse sör:http://www.iconarchive.com/show/st-patricks-day-icons-by-iconka/beer-icon.html

Barátság sör:http://www.iconarchive.com/show/st-patricks-day-icons-by-iconka/beer-clink-cheers-icon.html
	License: Linkware (Backlink to http://www.iconka.com required)

Bivaly sör: templateben van.
Jópofa sör: templateben van.
Lime sör: templateben van.


Credits:
| GroupId                      | ArtifactId                    | Version       | Type | License                                   |
|------------------------------|-------------------------------|---------------|------|-------------------------------------------|
| com.h2database               | h2                            | 1.4.191       | jar  | MPL 2.0, and EPL 1.0                      |
| com.zaxxer                   | HikariCP                      | 2.4.5         | jar  | The Apache Software License, Version 2.0  |
| javax.servlet                | servlet-api                   | 2.5           | jar  | -                                         |
| mysql                        | mysql-connector-java          | 5.1.38        | jar  | The GNU General Public License, Version 2 |
| org.hibernate                | hibernate-core                | 5.1.0.Final   | jar  | GNU Lesser General Public License         |
| org.hibernate                | hibernate-entitymanager       | 5.1.0.Final   | jar  | GNU Lesser General Public License         |
| org.hibernate                | hibernate-java8               | 5.1.0.Final   | jar  | GNU Lesser General Public License         |
| org.hibernate                | hibernate-validator           | 5.2.4.Final   | jar  | Apache License, Version 2.0               |
| org.hibernate.common         | hibernate-commons-annotations | 5.0.1.Final   | jar  | GNU Lesser General Public License         |
| org.jboss.logging            | jboss-logging                 | 3.3.0.Final   | jar  | Apache License, version 2.0               |
| org.primefaces               | primefaces                    | 5.3           | jar  | The Apache Software License, Version 2.0  |
| org.primefaces.extensions    | primefaces-extensions         | 4.0.0         | jar  | Apache 2                                  |
| org.primefaces.themes        | bootstrap                     | 1.0.10        | jar  | -                                         |
| org.projectlombok            | lombok                        | 1.16.8        | jar  | The MIT License                           |
| org.slf4j                    | slf4j-api                     | 1.7.5         | jar  | MIT License                               |
| org.springframework.security | spring-security-config        | 4.0.1.RELEASE | jar  | The Apache Software License, Version 2.0  |
| org.springframework.security | spring-security-core          | 4.0.1.RELEASE | jar  | The Apache Software License, Version 2.0  |
| org.springframework.security | spring-security-taglibs       | 3.2.7.RELEASE | jar  | The Apache Software License, Version 2.0  |
| org.springframework.security | spring-security-web           | 4.0.1.RELEASE | jar  | The Apache Software License, Version 2.0  |
| org.springframework.webflow  | spring-binding                | 2.4.0.RELEASE | jar  | The Apache Software License, Version 2.0  |
| org.springframework.webflow  | spring-faces                  | 2.4.0.RELEASE | jar  | The Apache Software License, Version 2.0  |
| org.springframework.webflow  | spring-webflow                | 2.4.0.RELEASE | jar  | The Apache Software License, Version 2.0  |
| ch.qos.logback 	       | logback-classic               | 1.1.7         | jar  | Eclipse Public License - v 1.0, GNU Lesser General Public License |
| ch.qos.logback               | logback-core                  | 1.1.7         | jar  | Eclipse Public License - v 1.0, GNU Lesser General Public License |
| junit              | junit                   | 4.8.1       | jar | Common Public License Version 1.0        |
| org.apache.openejb | javaee-api              | 6.0-6       | jar | The Apache Software License, Version 2.0 |
| org.apache.openejb | openejb-core            | 4.7.2       | jar | Apache License, Version 2.0              |
| org.apache.openejb | openejb-junit           | 4.7.2       | jar | Apache License, Version 2.0              |
| org.mockito        | mockito-all             | 2.0.2-beta  | jar | The MIT License                          |
| org.mockito        | mockito-core            | 2.0.53-beta | jar | The MIT License                          |
| org.powermock      | powermock-api-mockito   | 1.6.5       | jar | The Apache Software License, Version 2.0 |
| org.powermock      | powermock-core          | 1.6.5       | jar | The Apache Software License, Version 2.0 |
| org.powermock      | powermock-module-junit4 | 1.6.5       | jar | The Apache Software License, Version 2.0 |
| javax         | javaee-api        | 6.0     | jar  | CDDL + GPLv2 with classpath exception |
| javax.servlet | javax.servlet-api | 3.1.0   | jar  | CDDL + GPLv2 with classpath exception |
