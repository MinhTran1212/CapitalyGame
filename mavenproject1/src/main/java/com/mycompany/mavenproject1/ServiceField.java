/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author user
 */
public class ServiceField extends Field {
        private final int amount;
    
    public ServiceField(int amount){
        this.amount = amount;
    }
    
    public int getReduction(){
        return this.amount;
    }
    
    @Override
    public void stepOn(Player player) {
       player.decreaseBalance(this.amount);
    }
}
