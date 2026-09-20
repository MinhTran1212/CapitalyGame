package com.mycompany.mavenproject1;

public abstract class Player {
    private int balance;
    private boolean isBankrupt;
    private String name;

    public Player(String name){
        this.balance = 10000;
        this.isBankrupt = false;
        this.name = name;
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

    public abstract boolean shouldBuy(int cost);
}