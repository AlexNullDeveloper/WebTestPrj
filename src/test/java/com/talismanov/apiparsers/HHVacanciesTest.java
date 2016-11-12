package com.talismanov.apiparsers;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Александр on 12.11.2016.
 */
public class HHVacanciesTest {
    private String[] javaSkillsArray = null;
    private testHHVacancies test = null;
    private static double NANO_SECONDS_IN_SECOND = 1000000000.0;


    @Before
    public void setArray() {
        String skills = "Kotlin, Реляционные базы данных,Корпоративная разработка,Веб-разработка,Архитектура, шаблоны проектирования,Системы контроля версий,Сервера приложений,JavaScript Frameworks,Web Services," +
                "Непрерывная интеграция,ORM, J2EE, JSE, Spring, JavaScript, HTML, PL/SQL, SQL, UNIX, XML, Hibernate, CSS, Scala, Groovy, " +
                "Владение английским языком, NoSQL, Big Data, Oracle, Postgre, Microsoft SQL Server, MySQL, DB2, Firebird, SQLite, HSQLDB, Sybase, MongoDB, Redis, Cassandra, Neo4j, MyBatis, Tomcat, JBoss, WebLogic, GlassFish," +
                "AJAX, GWT, REST, SOAP, JSON, XML, Vaadin, Bootstrap, jQuery, Angular, Ext JS, ReactJS, Git, SVN, CVS, Maven, Ant, Jenkins, TeamCity, Gradle";

        javaSkillsArray = skills.split(", ");
        test = new testHHVacancies();
    }

    @Test
    public void testParsing() {
        long timeBefore = System.nanoTime();
        test.getCountOfSkillsByVacancy("java", javaSkillsArray);
        long timeAfter = System.nanoTime();
        System.out.println("Тест был выполнен за " + (timeAfter - timeBefore)/ NANO_SECONDS_IN_SECOND + " секунд");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParsingNullArguments1() {
        test.getCountOfSkillsByVacancy(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParsingNullArguments2() {
        test.getCountOfSkillsByVacancy(null, javaSkillsArray);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParsingNullArguments3() {
        test.getCountOfSkillsByVacancy("java", null);

    }
}
