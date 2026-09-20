/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author user
 */
public class GreedyPlayer extends Player{

    public GreedyPlayer(String name) {
        super(name);
    }
    
    @Override
    public boolean shouldBuy(int cost){
        return this.getBalance() >= cost;
    }
}
