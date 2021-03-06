package me.loving11ish.playerguiadvanced.MenuSystem;

import org.bukkit.entity.Player;

//Companion class to all menus. This is needed to pass information across the entire
//menu system no matter how many inventories are opened or closed.
//
// Each player has one of these objects, and only one.

public class PlayerMenuUtility {

    private Player owner;
    //store the player that will be moderated so we can access him in the next menu
    public Player playerToMod;

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public Player getPlayerToMod() {
        return playerToMod;
    }

    public void setPlayerToMod(Player playerToMod) {
        this.playerToMod = playerToMod;
    }
}