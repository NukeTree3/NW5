package com.nuketree3.example;

import lombok.Data;

import java.util.Random;

import static java.lang.Thread.sleep;

@Data
public class Philosopher extends EntityInTable implements Runnable{

    private int timesToEat;
    private final String name;
    private Random rand;
    private Table table;

    public Philosopher(int timesToEat, String name, Table table) {
        this.timesToEat = timesToEat;
        this.name = name;
        this.rand = new Random();
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (timesToEat > 0) {
                thinking();
                eating();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(name + " наелся");
    }

    private void thinking() throws InterruptedException {
        System.out.println(name + " думает");
        sleep(rand.nextLong(100, 2000));
    }

    private void eating() throws InterruptedException {
        if(table.tryGetForks(name)){
            System.out.println(name + " ест");
            sleep(rand.nextLong(3000, 6000));
            table.putForks(name);
            System.out.println(name + " положил вилки");
            timesToEat--;
        }
    }



}
