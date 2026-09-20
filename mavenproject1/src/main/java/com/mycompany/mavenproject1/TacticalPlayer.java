/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author user
 */
public class TacticalPlayer extends Player{
    private boolean canBuy = true;

    public TacticalPlayer(String name) {
        super(name);
    }

    @Override
    public boolean shouldBuy(int cost) {
        boolean decision = this.canBuy;
        this.canBuy = !this.canBuy;
        
        if (this.getBalance() < cost) {
            return false;
        }
        return decision;
    }
}
