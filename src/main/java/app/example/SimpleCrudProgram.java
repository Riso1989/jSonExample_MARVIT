package app.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class SimpleCrudProgram {

    public static void main(String[] args) throws IOException {
        SimpleCrudProgram simpleCrudProgram = new SimpleCrudProgram();
        simpleCrudProgram.programLoop();
    }

    private void programLoop() throws IOException {
        String jsonArrayOfObjects = "/objects.json";
        InputStream isArray = ReadingExample.class.getResourceAsStream(jsonArrayOfObjects);
        if (isArray == null) {
            throw new NullPointerException("Cannot find resource file " + jsonArrayOfObjects);
        }
        JSONTokener tokenerArr = new JSONTokener(isArray);
        JSONArray objectArr = new JSONArray(tokenerArr);
        Boolean stop = false;

        while (!stop) {
            printHeader();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            String name = reader.readLine();

            switch (name) {
                case "1":
                    //READ
                    System.out.println(objectArr.toString(4));
                    break;
                case "2":
                    //CREATE
                    objectArr = addToArray(objectArr);
                    System.out.println(objectArr.toString());
                    System.out.println("OBJECT ADDED!");
                    break;
                case "3":
                    //REMOVE
                    objectArr = removeFromArray(objectArr);
                    System.out.println(objectArr.toString());
                    System.out.println("OBJECT REMOVED!");
                    break;
                case "4":
                    //UPDATE
                    objectArr = updateArray(objectArr);
                    System.out.println(objectArr.toString());
                    System.out.println("OBJECT UPDATED!");
                    break;
                case "5":
                    //SAVE
                    saveJsonArray(objectArr,jsonArrayOfObjects);
                    break;
                case "6":
                    //EXIT
                    stop = true;
                    break;
                default:
                    //ak neplati nic z predchadzajucich vykona sa toto
                    System.out.println("Wrong input!");
                    break;
            }
        }
    }

    private void saveJsonArray(JSONArray objectArr, String jsonArrayOfObjects) {
        try {
            FileWriter file = new FileWriter("src\\main\\resources" + jsonArrayOfObjects);
            String jsonArrString = objectArr.toString(4);
            file.write(jsonArrString);
            file.flush();
            file.close();
            System.out.println("NEW JSON IS SAVED TO FILE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONArray updateArray(JSONArray objectArr) throws IOException {
        System.out.print("Update object number: ");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String index = reader.readLine();
        //overil som si ci je index cislo a zaroven ci je v rozsahu objectArr :)
        if (isNumeric(index) && Integer.parseInt(index) < objectArr.length()) {
            JSONObject secondJson = (JSONObject) objectArr.get(0);
            System.out.println("Change name: ");
            BufferedReader b =
                    new BufferedReader(new InputStreamReader(System.in));
            String name = b.readLine();
            secondJson.put("name", name);
            //updatujem iba na pozicii povodneho objektu...
            objectArr.put(Integer.parseInt(index),secondJson);
        } else {
            System.out.println("wrong index!");
            System.out.println();
        }

        return objectArr;
    }

    private JSONArray addToArray(JSONArray objectArr) throws IOException {
        String jsonSingleObject = "/object.json";
        //nechcem buildovat nic zlozite... pridam len object.json, keby som chcel novy objekt tak by som to robil cez entitu.. bolo by to viac kodu...
        InputStream is = ReadingExample.class.getResourceAsStream(jsonSingleObject);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + jsonSingleObject);
        }
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        return objectArr.put(object);
    }

    private JSONArray removeFromArray(JSONArray objectArr) throws IOException {
        System.out.print("Delete object number: ");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String index = reader.readLine();
        //overil som si ci je index cislo a zaroven ci je v rozsahu objectArr :)
        if (isNumeric(index) && Integer.parseInt(index) < objectArr.length()) {
            objectArr.remove(Integer.parseInt(index));
            System.out.println("removed index number " + index);
            System.out.println();
        } else {
            System.out.println("wrong index!");
            System.out.println();
        }
        return objectArr;
    }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void printHeader() {
        System.out.println("WELCOME TO MASTER PROGRAM");
        System.out.println("write 1 for print array");
        System.out.println("write 2 for add to array");
        System.out.println("write 3 for remove from array");
        System.out.println("write 4 for update array");
        System.out.println("write 5 for save array to file");
        System.out.println("write 6 for exit");
        System.out.print(":");
    }
}

