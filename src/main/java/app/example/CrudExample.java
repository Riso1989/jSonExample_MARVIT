package app.example;

import app.example.entity.Employee;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CrudExample {

    //Crud znamena Create, Read, Update, Delete - skusim popisat vkladanie noveho JSON objektu do suboru, Update objektu v subore, Citanie a Mazanie
    public static void main(String[] args) {
        CrudExample crudExample = new CrudExample();

        String jsonArrayOfObjects = "/objects.json";
        String jsonSingleObject = "/object.json";

        System.out.println("-----------------PART1----------------------");
        // prva vec co potrebujem je, ze si vytiahnem data zo suboru, viac info v reading example
        InputStream isArray = ReadingExample.class.getResourceAsStream(jsonArrayOfObjects);
        if (isArray == null) {
            throw new NullPointerException("Cannot find resource file " + jsonArrayOfObjects);
        }
        JSONTokener tokenerArr = new JSONTokener(isArray);
        JSONArray objectArr = new JSONArray(tokenerArr);

        // teraz ked mam vsetky data v objectArr vytiahnem podobnym sposobom dalsi objekt zo suboru ktory potom pridam do jsonArr
        InputStream is = ReadingExample.class.getResourceAsStream(jsonSingleObject);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + jsonSingleObject);
        }
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);

        // takyto objekt viem pridat do objectArr
        objectArr.put(object);
        System.out.println(objectArr);
        System.out.println("-----------------PART2----------------------");

        //ked mam data v JSONArray mozem s nimi zacat manipulovat v podstate ako potrebujem, akykolvek objekt ale potrebujem pretransformovat
        //do JSONObjektu

        //vytvorim si svoj vlastny objekt employee (nachadza sa v /app/example/entity/Employee) ktory sa pokusim pridat do JSONArray, na
        // to potrebujem nejake stringy a jeden "List of cars"
        List<String> cars = new ArrayList<String>();
        cars.add("volvo");
        cars.add("skoda");
        cars.add("Toyota");
        Employee employee = new Employee("John Doe", "Kosice", cars,"Tourist");

        // vytvoril som si vlastnu metodu toJson na parsovanie objektu do JSONObjektu:
        JSONObject employeeAsJson = crudExample.toJson(employee);
        objectArr.put(employeeAsJson);
        System.out.println(objectArr);

        System.out.println("-----------------PART3----------------------");
        // ked potrebujem vymazat objekt z pola staci zavolat metodu remove a vlozit jej index na ktorom sa nachadza dany objekt v poli
        // [0,1,2,3,4...N-1]
        objectArr.remove(2);
        System.out.println(objectArr);

        System.out.println("-----------------PART4----------------------");
        // ked potrebujem vytiahnut objekt z pola pouzijem get a index na ktorom sa nachadza dany objekt
        JSONObject secondJson = (JSONObject) objectArr.get(0);
        System.out.println(secondJson);

        System.out.println("-----------------PART5----------------------");
        //ak chcem v takomto objekte nieco zmenit, pouzijem to iste co aj v Mape, teda put a kedze kluc je unikatny tak mu zmeni len value
        secondJson.put("name", "Julius");
        // tento objektviem jednoducho pridat nazad do JSONArr na jeho povodne miesto
        objectArr.put(0,secondJson);

        // tento put je trosku iny ako ten predtym, v tomto pouzivam index a objekt... v Jave moze mat metoda rovnaky nazov ale ine parametre (overloading)
        // alebo rovnaky nazov a inu implementaciu (overRiding)
        System.out.println(objectArr);

        try {
            FileWriter file = new FileWriter("src\\main\\resources" + jsonArrayOfObjects);
            String jsonArrString = objectArr.toString(4);
            file.write(jsonArrString);
            file.flush();
            file.close();
            System.out.println("NEW JSON IS IN FILE");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //tato metoda vrati objekt typu JSONObject a ocakava ze jej poslem ako parameter Objekt typu Employee
    private JSONObject toJson(Employee e) {
        JSONObject jo = new JSONObject();
        // do jsonObjektu pridavam parametre rovnako ako v Mape - teda key a value:
        jo.put("name", e.name);
        jo.put("city", e.city);
        jo.put("job", e.job);
        jo.put("cars", e.cars);
        return jo;
    }


}
