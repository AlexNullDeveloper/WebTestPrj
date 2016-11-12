package com.talismanov.apiparsers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.talismanov.Main;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class testHHVacancies {
    private static final String USER_AGENT = "Mozilla/5.0";
    private ConcurrentHashMap<String, AtomicInteger> atomicIntegers = null;
    private double countOfVacaniesFound = 500;


    public void getCountOfSkillsByVacancyAndList(String vacancy) {
        String skills = "Kotlin, Реляционные базы данных,Корпоративная разработка,Веб-разработка,Архитектура, шаблоны проектирования,Системы контроля версий,Сервера приложений,JavaScript Frameworks,Web Services," +
                "Непрерывная интеграция,ORM, J2EE, JSE, Spring, JavaScript, HTML, PL/SQL, SQL, UNIX, XML, Hibernate, CSS, Scala, Groovy, " +
                "Владение английским языком, NoSQL, Big Data, Oracle, Postgre, Microsoft SQL Server, MySQL, DB2, Firebird, SQLite, HSQLDB, Sybase, MongoDB, Redis, Cassandra, Neo4j, MyBatis, Tomcat, JBoss, WebLogic, GlassFish," +
                "AJAX, GWT, REST, SOAP, JSON, XML, Vaadin, Bootstrap, jQuery, Angular, Ext JS, ReactJS, Git, SVN, CVS, Maven, Ant, Jenkins, TeamCity, Gradle";


        String[] strings = skills.split(", ");
        List<String> list = Arrays.asList(strings);

        String[] array = new String[list.size()];
        array = list.toArray(array);
//        String[] array = (String[]) list.stream().toArray(new String[list.size()]);
        getCountOfSkillsByVacancy(vacancy, array);
    }


    public void getCountOfSkillsByVacancy(String vacancy, String... skills) {

        Main.log.info("begining");

        if (vacancy == null || skills == null) {
            throw new IllegalArgumentException("Вакансия или список навыков не могут быть пустыми");
        }

        atomicIntegers = new ConcurrentHashMap<>();

        for (String skill : skills) {
            AtomicInteger atomicInteger = new AtomicInteger();
            atomicIntegers.put(skill, atomicInteger);
        }

        for (int i = 0; i < countOfVacaniesFound/500; i ++) {
//            System.out.println("countOfVacaniesFound " + countOfVacaniesFound);
            InputStream conInputStream = null;
            try {
                int page = i;
                URL url = new URL("https://api.hh.ru/vacancies?text=" + URLEncoder.encode(vacancy,"UTF-8") + "&area=1&per_page=500&page="+ URLEncoder.encode(String.valueOf(page),"UTF-8"));
                conInputStream = getInputStreamFromUrl(conInputStream, url);
                if (conInputStream == null) {
                    System.out.println("SOMETHING WENT WRONG HTTP STATUS NOT 200");
                    break;
                }
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String json = getStringDataFromInputStream(conInputStream);

            Map<String, Object> retMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {
            }.getType());
//            System.out.println("retMap.size()" + retMap.size());


            System.out.println(retMap);
            @SuppressWarnings("unchecked")
            List<Object> items = (ArrayList<Object>) retMap.get("items");

            countOfVacaniesFound = (double) retMap.get("found");

//            System.out.println("items.size() " + items.size());
//            System.out.println(items);


            List<String> skillsCollection = Arrays.asList(skills);

            for (String skill : skillsCollection) {
                AtomicInteger atomicInteger = new AtomicInteger();
                items.stream().forEach(elem -> {

//                System.out.println(elem);
                    @SuppressWarnings("unchecked")
                    Map map = (LinkedTreeMap<String, Object>) elem;
                    //System.out.println(map.size());
                    @SuppressWarnings("unchecked")
                    Map map1 = (LinkedTreeMap<String, Object>) map.get("snippet");
                    String str = (String) map1.get("requirement");
                    //  System.out.println(str);
                    if (str != null && skill != null) {
                        if (StringUtils.contains(str.toLowerCase(), skill.toLowerCase())) {
                            atomicIntegers.get(skill).incrementAndGet();
                            atomicInteger.incrementAndGet();
                        }
                    }
                });
//                System.out.println("Количество упоминаний умения " + skill + " для вакансии " + vacancy +  " равно " + atomicInteger);
            }
        }


        atomicIntegers.entrySet()
                .stream()
                .sorted((a,b) -> Integer.compare(b.getValue().get(), a.getValue().get()))
                .forEach((entry) -> System.out.println("Количество упоминаний умения " + entry.getKey() + " для вакансии " + vacancy +  " равно " + entry.getValue()) );
//        atomicIntegers.forEach((k,v) -> {
//            System.out.println("Количество упоминаний умения " + k + " для вакансии " + vacancy +  " равно " + v);
//        });
//        System.out.println(skill + " count" + atomicInteger.get());


    }

    private String getStringDataFromInputStream(InputStream conInputStream) {
        StringBuilder stringBuilder = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conInputStream));) {
            stringBuilder = new StringBuilder();

            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private InputStream getInputStreamFromUrl(InputStream conInputStream, URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int response = con.getResponseCode();
//        System.out.println("response = " + response);
        if (response != 200) {
            return null;
        }
        conInputStream = con.getInputStream();
        return conInputStream;
    }

    @Deprecated
    public void getCountOfTomcatForJavaVacancy() {
        InputStream conInputStream = null;
        try {
            URL url = new URL("https://api.hh.ru/vacancies?text=java&area=1&per_page=500&page=0");
            conInputStream = getInputStreamFromUrl(conInputStream, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = getStringDataFromInputStream(conInputStream);

        Map<String, Object> retMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        System.out.println("retMap.size()" + retMap.size());


        System.out.println(retMap);
        @SuppressWarnings("unchecked")
        List<Object> items = (ArrayList<Object>) retMap.get("items");
        System.out.println(items.size());
        System.out.println(items);

        AtomicInteger atomicInteger = new AtomicInteger();

        items.stream().forEach(elem -> {

            System.out.println(elem);
            @SuppressWarnings("unchecked")
            Map map = (LinkedTreeMap<String, Object>) elem;
            //System.out.println(map.size());
            @SuppressWarnings("unchecked")
            Map map1 = (LinkedTreeMap<String, Object>) map.get("snippet");
            String str = (String) map1.get("requirement");
            //  System.out.println(str);
            if (str != null) {
                if (str.toLowerCase().contains("tomcat")) {
                    atomicInteger.incrementAndGet();
                }
            }
        });
        System.out.println("Tomcat count" + atomicInteger.get());
    }
}