package ru.geekbrains.lesson3;
//Created by Pugaev Denis
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/*
Задача:
 Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список уникальных слов,
из которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.
 */
public class Words {
    public static void main(String[] args) {
        arrayWords();
    }

    private static void arrayWords() {
        List<String> words = new ArrayList<>();
        Collections.addAll(words,"издание", "будущее", "союз", "общение", "пункт", "зуб", "голос", "мир",
                "издание", "будущее", "зуб", "впечатление", "издание", "союз", "будущее", "зуб", "страсть",
                "союз", "издание", "пункт");
        System.out.printf("Количество уникальных слов из массива: %s %n", words.stream().distinct().count()); // изучил статью Stream API и выбрал данный подход.
        System.out.printf("Уникальные слова: %s %n", words.stream().distinct().collect(Collectors.toList()));
        }
}





