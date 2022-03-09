package kz.kre.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

public class Launcher {

    public static void main(String[] args) {
        int matrixRow = 4;
        int matrixColumn = 5;
        int[][] matrix = new int[matrixRow][matrixColumn];
        int temp;

        //Распечатка условия задания
        System.out.println("*************************************\n"
                + "Найти максимальный элемент второй строки.\n"
                + "Если он больше первого элемента третьей строки,\n"
                + "то поменять элементы местами\n");

        try {
            //Узнаём абсолютный путь каталога
            String dir = new File(".").getAbsoluteFile().getParentFile().getAbsolutePath() + System.getProperty("file.separator");
            
            String s1 = dir + "portable_data.xml"; //имя XML-файла
            String s2 = dir + "portable_data.prop"; //имя Prop-файла
            File fileXML = new File(s1); //нужна для доступа к portable_data.xml
            File fileProp = new File(s2); //нужна для доступа к portable_data.prop
            String currentDate = new Date().toString(); //дата записи
            Properties properties = new Properties(); //хранит свойства            
            
            if ((fileXML.exists() == false) || (fileProp.exists() == false)) {//если файл(ы) отсутствуют
                //случайные данные для файла
                for (int i = 0; i < matrixRow; i++) {
                    for (int j = 0; j < matrixColumn; j++) {
                        temp = (int) Math.round(Math.random() * 9);
                        properties.put("i" + i + "_j" + j, String.valueOf(temp));
                    }
                }
                //сохранение результата в файлы
                properties.storeToXML(new FileOutputStream(fileXML), currentDate);
                properties.store(new FileOutputStream(fileProp), currentDate);                
            } else {//если файл есть, то загружаем его данные
                properties.loadFromXML(new FileInputStream(fileXML));
                //properties.load(new FileInputStream(fileProp));            
            }
            
            System.out.println("Matrix:");
            //считывание из файла
            for (int i = 0; i < matrixRow; i++) {
                for (int j = 0; j < matrixColumn; j++) {
                    temp = Integer.valueOf(properties.getProperty("i" + i + "_j" + j, "0"));
                    matrix[i][j] = temp;
                    System.out.print(temp + " ");
                }
                System.out.print("\n");
            }
            
            //моё решение
            int min = matrix[0][0], mini = 0, minj = 0;
            int max = matrix[0][0], maxi = 0, maxj = 0;
            
            for (int i = 0; i < matrixRow; i++) {
                for (int j = 0; j < matrixColumn; j++) {
                    temp = matrix[i][j];
                    if (temp > max) {
                        max = temp;
                        maxi = i;
                        maxj = j;
                    }
                    if (temp < min) {
                        min = temp;
                        mini = i;
                        minj = j;
                    }
                }
            }
            temp = matrix[maxi][maxj];
            matrix[maxi][maxj] = matrix[mini][minj];
            matrix[mini][minj] = temp;
            
            //сохраняем свойства
            for (int i = 0; i < matrixRow; i++) {
                for (int j = 0; j < matrixColumn; j++) {
                    properties.put("I" + i + "_J" + j, String.valueOf(matrix[i][j]));
                }
            }
            //запись свойства
            properties.storeToXML(new FileOutputStream(fileXML), currentDate);
            properties.store(new FileOutputStream(fileProp), "<><><><><><><><>");
            
            //считывание результата из файла
            System.out.println("New matrix:");
            for (int i = 0; i < matrixRow; i++) {
                for (int j = 0; j < matrixColumn; j++) {
                    System.out.print(properties.getProperty("I" + i + "_J" + j, "?") + " ");
                }
                System.out.print("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ошибка XML-файла");
        }
    }

}
