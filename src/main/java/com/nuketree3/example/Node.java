package com.nuketree3.example;

import lombok.Data;

@Data
public class Node {
    private EntityInTable nodeValue;
    private Node leftEntity;
    private Node rightEntity;
}
