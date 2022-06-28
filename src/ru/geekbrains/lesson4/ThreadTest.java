package ru.geekbrains.lesson4;
//Created by Pugaev Denis
/*
Задача:
1. Необходимо написать два метода, которые делают следующее:
1) Создают одномерный длинный массив, например:

static final int size = 10000000;
static final int h = size / 2;
float[] arr = new float[size];

2) Заполняют этот массив единицами;
3) Засекают время выполнения: long a = System.currentTimeMillis();
4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
5) Проверяется время окончания метода System.currentTimeMillis();
6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);

Отличие первого метода от второго:
Первый просто бежит по массиву и вычисляет значения.
Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.

Пример деления одного массива на два:

System.arraycopy(arr, 0, a1, 0, h);
System.arraycopy(arr, h, a2, 0, h);

Пример обратной склейки:

System.arraycopy(a1, 0, arr, 0, h);
System.arraycopy(a2, 0, arr, h, h);

Примечание:
System.arraycopy() – копирует данные из одного массива в другой:
System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
По замерам времени:
Для первого метода надо считать время только на цикл расчета:

for (int i = 0; i < size; i++) {
arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
}

Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
 */

//Если закоментировать выводы  в консоль промежуточных расчетов времени, скорость выполнения метода увеличится.

public class ThreadTest {
    static final int SIZE = 10000000;

    static final int HALF = SIZE / 2;
    static float timeFirst;
    static float timeSecond;

    public static void main(String[] args) {
        firstMethod();

        try {
            secondMethod();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        difference(timeFirst, timeSecond);
    }

    public static void firstMethod() {
        float[] arr = new float[SIZE];
        arr[0] = 0.0f;
        float a = 1.0f;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = a;
            a++;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        timeFirst = (System.currentTimeMillis() - startTime);
        System.out.println("Время выполнения первого метода: " + timeFirst + " мс.");
    }

    public static void secondMethod() throws InterruptedException {
        float[] arr = new float[SIZE];
        arr[0] = 0.0f;
        float x = 1.0f;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = x;
            x++;
        }
        long startTime = System.currentTimeMillis();
        float[] arr1 = new float[HALF]; // Создаем два массива для левой и правой части исходного
        float[] arr2 = new float[HALF];

        System.arraycopy(arr, 0, arr1, 0, HALF);    // Копируем в них значения из большого массива
        System.arraycopy(arr, HALF, arr2, 0, HALF);
        System.out.println("Время разбивки массива: " + (System.currentTimeMillis() - startTime) + " мс.");


        Thread thr1 = new Thread(() -> {
            long thrTime = System.currentTimeMillis();
            for (int i = 0; i < arr1.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.out.println("Время подсчёта 1-й половины массива: " + (System.currentTimeMillis() - thrTime) + " мс.");
        });
        Thread thr2 = new Thread(() -> {
            long thrTime = System.currentTimeMillis();
            for (int i = 0; i < arr2.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2)); //Запускает два потока и параллельно просчитываем каждый малый массив
            }
            System.out.println("Время подсчёта 2-й половины массива: " + (System.currentTimeMillis() - thrTime) + " мс.");
        });
        thr1.start();
        thr2.start();

        thr1.join();
        thr2.join();

        long arrTime = System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr, 0, HALF);// Склеиваем малые массивы обратно в один большой
        System.arraycopy(arr2, 0, arr, HALF, HALF);
        System.out.println("Время склейки массива: " + (System.currentTimeMillis() - arrTime) + " мс.");
        timeSecond = ((System.currentTimeMillis() - startTime));
        System.out.println("Время выполнения второго метода: " + timeSecond + " мс.");
    }

    public static void difference(float firstT, float SecondT) {
        float diff = (firstT / SecondT) - 1;
        int productive = (int) (diff * 100);

        System.out.println("Производительность " + productive + "%");
    }
}