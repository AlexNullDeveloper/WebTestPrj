package com.talismanov;

import com.talismanov.apiparsers.testHHVacancies;
import org.apache.log4j.Logger;

public class Main {

    public static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        log.info("start");


        testHHVacancies test = new testHHVacancies();
//        test.getCountOfSkillsByVacancyAndList("java");
//        test.getCountOfSkillsByVacancy("java", "maven", "jsp");
//        test.getCountOfSkillsByVacancy("сотрудник","обучаемость","стрессоустойчивость","прилежность","Дисциплинированность");
//        test.getCountOfSkillsByVacancy("Управляющий дополнительным офисом", "Знание нормативных документов",
//                "опыт работы в должности руководителя", "опыт активных продаж", "знания продуктов розничного",
//                "на руководящей должности не менее 2-х", "продаж", "Знание норм гражданского кодекса", "Опыт руководства коллективом", "высшее образование");

        log.info("exiting programm");

    }
}