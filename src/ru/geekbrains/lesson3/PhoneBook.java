package ru.geekbrains.lesson3;
//Created by Pugaev Denis

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
Задача:
Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров. В этот телефонный
справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.
 */
public class PhoneBook {
    static HashMap<String, List<String>> contactMapBook = new HashMap<>(100);

    public static void main(String[] args) {

        PhoneBook.add("Сидоров", "34-21-21");  // записываем контакты в HashMap через метод add() класса PhoneBook;
        PhoneBook.add("Зайчиков", "90-09-10");
        PhoneBook.add("Попов", "45-32-21");
        PhoneBook.add("Сидоров", "8(999)1249999");
        PhoneBook.add("Зайчиков", "34-54-23");
        PhoneBook.add("Бубенков", "8(111)1249119");
        PhoneBook.add("Зайчиков", "405");
        PhoneBook.add("Горшков", "56-78-78");
        PhoneBook.add("Зайчиков", "+7(111)0000143");
        PhoneBook.add("Горшков", "+7(777)0770189");

        PhoneBook.get("Сидоров");  // вводя ключ получаем значение из HashMap через метод get() класса PhoneBook;
        PhoneBook.get("Попов");
        PhoneBook.get("Зайчиков");
        PhoneBook.get("Бубенков");
        PhoneBook.get("Горшков");
    }

    public static void add(String lastName, String number) {
        List<String> contact = contactMapBook.get(lastName);// ищем по фамилии в справочнике есть ли контакт с такой же фамилией;
        if (contact == null) {                            // если не находим создаем новый контакт;
            List<String> newContact = new ArrayList<>();
            newContact.add(number);
            contactMapBook.put(lastName, newContact);
        } else {                                       // иначе если находим, добавляем номер с этой фамилией;
            contact.add(number);
            contactMapBook.put(lastName, contact);
        }
    }

    public static void get(String lastName) {
        System.out.println("Фамилия: " + lastName);
        System.out.print("Номер(а): ");
        for (String x : contactMapBook.get(lastName)) { // ищем все номера, привязанные к фамилии;
            System.out.print(x + " | ");
        }
        System.out.printf("%n %n");
    }
}
