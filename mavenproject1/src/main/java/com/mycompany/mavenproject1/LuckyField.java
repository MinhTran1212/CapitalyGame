/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author user
 */
public class LuckyField extends Field{
    private final int amount;
    
    public LuckyField(int amount){
        this.amount = amount;
    }
    
    public int getReward(){
        return this.amount;
    }
    
    @Override
    public void stepOn(Player player) {
       player.increaseBalance(this.amount);
    }
}
