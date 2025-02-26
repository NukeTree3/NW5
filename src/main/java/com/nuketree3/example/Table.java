package com.nuketree3.example;

public class Table {

    private final Node root;
    private final int countOfPhilosophers;

    public Table(int countOfPhilosophers, int timesToEat) {
        root = init(countOfPhilosophers, timesToEat);
        this.countOfPhilosophers = countOfPhilosophers;
        dinnerTime();
    }

    private void dinnerTime() {
        System.out.println("dinner time");
        Node currentNode = root;
        int i = 0;
        while (i < countOfPhilosophers) {
            if (currentNode.getNodeValue() instanceof Philosopher) {
                Thread thread = new Thread(((Philosopher) currentNode.getNodeValue()));
                thread.start();
                i++;
            }
            currentNode = currentNode.getRightEntity();
        }
    }

    public void addNode(Node head, int countOfPhilosophers, int timesToEat, Node lastNode) {
        if (countOfPhilosophers == 0 && lastNode.getNodeValue() instanceof Fork) {
            lastNode.setRightEntity(head);
            head.setLeftEntity(lastNode);
        } else if (lastNode.getNodeValue() instanceof Philosopher) {
            Node fork = new Node();
            fork.setNodeValue(new Fork());
            lastNode.setRightEntity(fork);
            fork.setLeftEntity(lastNode);
            addNode(head, countOfPhilosophers, timesToEat, fork);
        } else if (lastNode.getNodeValue() instanceof Fork) {
            Node philosopher = new Node();
            philosopher.setNodeValue(new Philosopher(timesToEat, "Philosopher №" + (countOfPhilosophers), this));
            lastNode.setRightEntity(philosopher);
            philosopher.setLeftEntity(lastNode);
            addNode(head, countOfPhilosophers - 1, timesToEat, philosopher);
        }
    }

    public Node init(int countOfPhilosophers, int timesToEat) {
        Node philosopher = new Node();
        philosopher.setNodeValue(new Philosopher(timesToEat, "Philosopher №" + countOfPhilosophers, this));
        addNode(philosopher, countOfPhilosophers - 1, timesToEat, philosopher);
        return philosopher;
    }



    public synchronized boolean tryGetForks(String name) {
        Node entity = root;
        while (true) {
            if (entity.getNodeValue() instanceof Philosopher) {
                if (((Philosopher) entity.getNodeValue()).getName().equals(name)) {
                    if (!((Fork) entity.getLeftEntity().getNodeValue()).isUse() && !((Fork) entity.getRightEntity().getNodeValue()).isUse()) {
                        ((Fork) entity.getLeftEntity().getNodeValue()).setUse(true);
                        ((Fork) entity.getRightEntity().getNodeValue()).setUse(true);
                        return true;
                    }
                    return false;
                }
            }
            entity = entity.getRightEntity();
        }
    }

    public void putForks(String name) {
        Node entity = root;
        while (true) {
            if (entity.getNodeValue() instanceof Philosopher) {
                if (((Philosopher) entity.getNodeValue()).getName().equals(name)) {
                    ((Fork) entity.getLeftEntity().getNodeValue()).setUse(false);
                    ((Fork) entity.getRightEntity().getNodeValue()).setUse(false);
                    break;
                }
            }
            entity = entity.getRightEntity();
        }
    }
}
