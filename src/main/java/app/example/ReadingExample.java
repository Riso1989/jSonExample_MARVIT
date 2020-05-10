package app.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

public class ReadingExample {

    public static void main(String[] args) {
        String jsonSingleObject = "/object.json";
        String jsonArrayOfObjects = "/objects.json";
//--------------------------------------------------
        // skusim na zaciatok nieco jednoduche.
        // Json sluzi na ukladanie objektov do suborov. To co tam chceme mat je na nas kazdopadne plati, ze JSON pozostava z nazvu property a jej hodnoty,
        // v Jave teda vieme Json charakterizovat ako mapu.
        // Mapu pouzivame na ukladanie objektov podla kluca (key) a hodnoty (value) pripadajucej  k danemu klucu
        // napr: name = john doe age = 22 atd....
        // viac info ku mapam tu: https://www.javatpoint.com/java-map
        // Treba povedat ze je vela sposobov ako ukladat a manipulovat s objektami, na to nam sluzia tzv. Kolekcie, z nich jeden pristup je pomocou mapy...

        //inicializujem si mapu a definujem ze kluc aj hodnota budu Stringy:
        Map<String, String> map = new HashMap<String, String>();
        // pridam hodnoty do mapy ("kluc", "hodnota"), ak by som definoval mapu inak napr Map<int, String> tak by som pridaval hodnoty inak (1, "John Doe") atd...
        map.put("name", "jon doe");
        map.put("age", "22");
        map.put("city", "chicago");

        // z takejto mapy viem vytvorit JSON object s ktorym viem potom pracovat:
        JSONObject jo = new JSONObject(map);
        // z takeho objektu viem vytiahnut hodnotu na zaklade kluca takto:
        System.out.println(jo.get("name"));
        System.out.println(jo.get("city"));

        // rovnako viem ale vytiahnut tieto data aj z mapy... cize zatial je rozdiel len v zapise :):
        System.out.println(map);
        System.out.println(jo);

        System.out.println("------------------------------------------------------");
        System.out.println("READING FROM FILE");
        // ked taham data zo suboru, postup je trosku zlozitejsi... potrebujem vytvorit tzv stream zo suboru, potom pomocou tokenera tento stream
        // treba pretvorit (parsovat) na JSON objekt...
        // na stream zo suboru pouzijem metodu nasej class getResourceAsStream("nazov suboru"); -- ak by som chcel tento stream citat tak to musim
        // prekonvertovat na nieco co sa citat da... to ale nie je potrebne pre JSON objekt..
        InputStream is = ReadingExample.class.getResourceAsStream(jsonSingleObject);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + jsonSingleObject);
        }
        //Z naseho input streamu viem za pomoci  tzv. tokenera vytvorit JSON objekt
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        // z neho uz viem rovnako ako pri mape vytiahnut to co potrebujem pomocou kluca:
        System.out.println(object.get("job"));

        System.out.println("------------------------------------------------------");
        System.out.println("READING FROM ARRAY");
        // ak mam v Jsone viacero objektov postup bude podobny ale namiesto JSONObject pouzijem JSONArray
        InputStream isArray = ReadingExample.class.getResourceAsStream(jsonArrayOfObjects);
        if (isArray == null) {
            throw new NullPointerException("Cannot find resource file " + jsonArrayOfObjects);
        }
        JSONTokener tokenerArr = new JSONTokener(isArray);
        JSONArray objectArr = new JSONArray(tokenerArr);

        // 1 prvok pola (polia zacinaju cislovanie nulou):
        System.out.println(objectArr.get(0));

        // posledny prvok pola bude vzdy length -1:
        System.out.println(objectArr.get(objectArr.length() - 1));

        // co je podstatne tieto prvky pola uz viem jednoducho citat. na citanie jednotlivych poloziek mozem pouzit For loopy, uvediem 3 rovnake priklady:
        System.out.println("BASIC FOR LOOP: ");
        for (int i = 0; i < objectArr.length(); i++) {
            System.out.println("NAME IS - >" + objectArr.getJSONObject(i).get("name"));
            System.out.println("JOB IS - >" + objectArr.getJSONObject(i).get("job"));
            //JSONArray vie citat zo Stringu:
            JSONArray cars = new JSONArray(objectArr.getJSONObject(i).get("cars").toString());
            for (int y = 0; y < cars.length(); y++) {
                System.out.println(cars.get(y));
            }
        }
        // min java 5
        System.out.println("JAVA 5 FOR LOOP");
        for (Object o : objectArr) {
            // kedze org.JSONArray pouziva Object namiesto JSONObject treba kazdu polozku pola preparsovat na JSONObject napr takto:
            JSONObject obj = (JSONObject) o;
            System.out.println("NAME IS - > " + obj.get("name"));
            System.out.println("JOB IS - > " + obj.get("job"));
            JSONArray cars = new JSONArray(obj.get("cars").toString());
            for (Object car : cars) {
                System.out.println(car);
            }
        }
        // min java 8 - vacsinou v praxi pouzivame toto, kvoli kratkosti zapisu, nevyhoda je ze sa to tazsie debuguje...
        System.out.println("JAVA 8 forEach loop");
        objectArr.forEach(item -> {
            JSONObject obj = (JSONObject) item;
            System.out.print("NAME IS -> " + obj.get("name"));
            System.out.print("JOB IS -> " + obj.get("job"));
            JSONArray cars = new JSONArray(obj.get("cars").toString());
            //toto je len trik pre vypisanie pola stringov pomocou foreach. Kratsie sa to uz zapisat neda :)
            cars.forEach(System.out::println);
        });

        // to je asi vsetko ku citaniu JSON suborov... ak by Vam nieco napadlo alebo nieco nebolo jasne dajte mi vediet cez slack...
    }
}
