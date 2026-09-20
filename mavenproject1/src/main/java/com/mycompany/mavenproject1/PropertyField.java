package com.mycompany.mavenproject1;

public class PropertyField extends Field {
    private boolean hasHouse;
    private Player owner;

    public PropertyField(){
        this.hasHouse = false;
        this.owner = null;
    }

    @Override
    public void stepOn(Player player){
        if (owner == null){
            if (player.shouldBuy(1000)){
                player.decreaseBalance(1000);
                this.owner = player;
                player.addProperty(this);
            }
        } else if (player == this.owner){
            if (!this.hasHouse && player.shouldBuy(4000)){
                player.decreaseBalance(4000);
                this.hasHouse = true;
            }
        } else if (this.hasHouse == false){
            if (player.getBalance() < 500){
                this.owner.increaseBalance(player.getBalance());
                player.decreaseBalance(500);
            } else {
                player.decreaseBalance(500);
                this.owner.increaseBalance(500);
            } 
        } else {
            if (player.getBalance() < 2000){
                this.owner.increaseBalance(player.getBalance());
                player.decreaseBalance(2000);
            } else {
                player.decreaseBalance(2000);
                this.owner.increaseBalance(2000);
            }
        } 
    }

    public void reset(){       
        this.hasHouse = false;
        this.owner = null;
    }
}