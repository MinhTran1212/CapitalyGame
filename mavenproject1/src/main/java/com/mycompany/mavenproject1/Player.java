package com.mycompany.mavenproject1;
import java.util.*;

public abstract class Player {
    private int balance;
    private boolean isBankrupt;
    private String name;
    private ArrayList<PropertyField> fields;
    private int position;

    public Player(String name){
        this.balance = 10000;
        this.isBankrupt = false;
        this.name = name;
        this.fields = new ArrayList<>();
        this.position = 0;
    }
    
    public ArrayList<PropertyField> getFields(){
        return this.fields;
    }
    
    public String getName(){
        return this.name;
    }

    public int getBalance(){
        return this.balance;
    }

    public void setBalance(int amount){
        this.balance = amount;
    }

    public boolean getBankrupt(){ 
        return this.isBankrupt; 
    }
    
    public int getPosition(){
        return this.position;
    }
    
    public void move(int steps, int boardSize){
        this.position = (this.position + steps) % boardSize;
    }
    

    public void bankrupt(){ 
        if (this.balance < 0){
            this.isBankrupt = true;
        } 
    }

    public void increaseBalance(int amount){
        this.balance += amount;
    }

    public void decreaseBalance(int amount){
        this.balance -= amount;
        if (this.balance < 0){
            this.bankrupt();
        }
    }
    
    public void addProperty(PropertyField property){
        this.fields.add(property);
    }

    public abstract boolean shouldBuy(int cost);
}