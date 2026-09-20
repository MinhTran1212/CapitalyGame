/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author user
 */
public class CarefulPlayer extends Player{
    public CarefulPlayer(String name) {
        super(name);
    }
    
    @Override
    public boolean shouldBuy(int cost){
        return this.getBalance()/2 >= cost;
    }
}
