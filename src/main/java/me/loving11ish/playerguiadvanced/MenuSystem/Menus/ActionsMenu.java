package me.loving11ish.playerguiadvanced.MenuSystem.Menus;

import de.myzelyam.api.vanish.VanishAPI;
import me.loving11ish.playerguiadvanced.MenuSystem.Menu;
import me.loving11ish.playerguiadvanced.MenuSystem.PlayerMenuUtility;
import me.loving11ish.playerguiadvanced.PlayerGUIAdvanced;
import me.loving11ish.playerguiadvanced.Utils.ColorUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class ActionsMenu extends Menu {


    public ActionsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Actions-menu-title").replace("%target%",playerMenuUtility.getPlayerToMod().getName()));
    }

    @Override
    public int getSlots() {
        return 18;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        Player target = playerMenuUtility.getPlayerToMod().getPlayer();
        String playerToKick = event.getClickedInventory().getItem(4).getItemMeta().getDisplayName();
        String PlayerToOp = event.getClickedInventory().getItem(4).getItemMeta().getDisplayName();
        String PlayerToMute = event.getClickedInventory().getItem(4).getItemMeta().getDisplayName();
        switch (event.getCurrentItem().getType()){
            case WOODEN_AXE:
                player.performCommand("kick " + playerToKick + " You were kicked by an operator");
                player.sendMessage(ChatColor.RED + "You kicked " + ChatColor.LIGHT_PURPLE + playerToKick);
                player.closeInventory();
                break;
            case PLAYER_HEAD:
                int x = target.getLocation().getBlockX();
                int y = target.getLocation().getBlockY();
                int z = target.getLocation().getBlockZ();
                Location location = new Location(target.getWorld(), x, y + 1, z);
                player.teleport(location);
                if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")){
                    VanishAPI.hidePlayer(player);
                    player.setGameMode(GameMode.SPECTATOR);
                }else {
                    player.setGameMode(GameMode.SPECTATOR);
                }
                player.closeInventory();
                break;
            case LIME_CONCRETE:
                if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-op")){
                    if (player.hasPermission("playergui.op")){
                        getServer().dispatchCommand(Bukkit.getConsoleSender(), "op " + PlayerToOp);
                        player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Targeted-player-op-successful").replace("%target%", PlayerToOp)));
                    }else {
                        player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Player-op-no-permission")));
                    }
                }else {
                    player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Disabled-GUI-Feature")));
                    player.closeInventory();
                }
                break;
            case RED_CONCRETE:
                if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-deop")){
                    if (player.hasPermission("playergui.deop")){
                        getServer().dispatchCommand(Bukkit.getConsoleSender(), "deop " + PlayerToOp);
                        player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Targeted-player-deop-successful").replace("%target%", PlayerToOp)));
                    }else {
                        player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Player-deop-no-permission")));
                    }
                }else {
                    player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Disabled-GUI-Feature")));
                    player.closeInventory();
                }
                break;
            case BARRIER:
                player.closeInventory();
                break;
            case BEDROCK:
                if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-ban-manager")){
                    if (player.hasPermission("playergui.ban")){
                        new BanManagerMenu(playerMenuUtility).open();
                    }else {
                        player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Player-ban-no-permission")));
                        player.closeInventory();
                    }
                }else {
                    player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Disabled-GUI-Feature")));
                    player.closeInventory();
                }
                break;
            case FEATHER:
                if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-mute")){
                    player.performCommand(PlayerGUIAdvanced.getPlugin().getConfig().getString("Mute-command").replace("%target%", PlayerToMute));
                    player.closeInventory();
                }else {
                    player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Disabled-GUI-Feature")));
                    player.closeInventory();
                }
                break;
            case DRAGON_BREATH:
                if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-unmute")){
                    player.performCommand(PlayerGUIAdvanced.getPlugin().getConfig().getString("Unmute-command").replace("%target%", PlayerToMute));
                    player.closeInventory();
                }else {
                    player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Disabled-GUI-Feature")));
                    player.closeInventory();
                }
                break;
            case SHULKER_BOX:
                Inventory PlayerInv = getServer().getPlayerExact(event.getClickedInventory().getItem(4).getItemMeta().getDisplayName()).getInventory();
                player.openInventory(PlayerInv);
                break;
            case ENDER_CHEST:
                Inventory PlayerEnder = getServer().getPlayerExact(event.getClickedInventory().getItem(4).getItemMeta().getDisplayName()).getEnderChest();
                player.openInventory(PlayerEnder);
                break;
            case RED_STAINED_GLASS_PANE:
                player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Disabled-GUI-Feature")));
                player.closeInventory();
                break;
        }
    }

    @Override
    public void setMenuItems() {

        Player target = playerMenuUtility.getPlayerToMod().getPlayer();

        //Item 1 -------------------------------------------------------------------------------------------------------
        ItemStack Kick = new ItemStack(Material.WOODEN_AXE, 1);
        ItemMeta Kmeta = Kick.getItemMeta();
        Kmeta.setDisplayName(ChatColor.RED + "Click To Kick!");
        ArrayList<String> Klore = new ArrayList<>();
        Klore.add(ChatColor.GREEN + "This performs the /kick <message> command");
        Klore.add(ChatColor.GREEN + "and is issued by you, not the console.");
        Klore.add(ChatColor.LIGHT_PURPLE + "YOU REQUIRE KICK PERMISSIONS FOR THIS TO WORK!");
        Kmeta.setLore(Klore);
        Kick.setItemMeta(Kmeta);

        //Item 2 -------------------------------------------------------------------------------------------------------
        ItemStack PlayerName = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) PlayerName.getItemMeta();
        UUID uuid = target.getUniqueId();
        skull.setOwningPlayer(getServer().getOfflinePlayer(uuid));
        PlayerName.setItemMeta(skull);
        ItemMeta Pmeta = PlayerName.getItemMeta();
        Pmeta.setDisplayName(target.getName());
        ArrayList<String> Plore = new ArrayList<>();
        Plore.add(ChatColor.GOLD + "Teleport To This Player");
        Plore.add(ChatColor.GREEN + "This will also set you into Spectator");
        Plore.add(ChatColor.GREEN + "before teleportation");
        Pmeta.setLore(Plore);
        PlayerName.setItemMeta(Pmeta);

        //Item 3 -------------------------------------------------------------------------------------------------------
        ItemStack Op = new ItemStack(Material.LIME_CONCRETE, 1);
        ItemMeta Opmeta = Op.getItemMeta();
        Opmeta.setDisplayName(ChatColor.GOLD + "Op Player!");
        ArrayList<String> Oplore = new ArrayList<>();
        Oplore.add(ChatColor.GREEN + "This grants this player");
        Oplore.add(ChatColor.GREEN + "Operator Status");
        Opmeta.setLore(Oplore);
        Op.setItemMeta(Opmeta);

        //Item 4 -------------------------------------------------------------------------------------------------------
        ItemStack Deop = new ItemStack(Material.RED_CONCRETE, 1);
        ItemMeta Deopmeta = Deop.getItemMeta();
        Deopmeta.setDisplayName(ChatColor.GOLD + "De-Op Player!");
        ArrayList<String> Deoplore = new ArrayList<>();
        Deoplore.add(ChatColor.GREEN + "This revokes this player's");
        Deoplore.add(ChatColor.GREEN + "Operator Status");
        Deopmeta.setLore(Deoplore);
        Deop.setItemMeta(Deopmeta);

        //Item 5 -------------------------------------------------------------------------------------------------------
        ItemStack Back = new ItemStack(Material.BARRIER, 1);
        ItemMeta Bmeta = Back.getItemMeta();
        Bmeta.setDisplayName(ChatColor.RED + "Click to go back");
        ArrayList<String> Blore = new ArrayList<>();
        Blore.add(ChatColor.GREEN + "This takes you back to the");
        Blore.add(ChatColor.GREEN + "Player List menu");
        Bmeta.setLore(Blore);
        Back.setItemMeta(Bmeta);

        //Item 6 -------------------------------------------------------------------------------------------------------
        ItemStack BanManager = new ItemStack(Material.BEDROCK, 1);
        ItemMeta BanManagermeta = BanManager.getItemMeta();
        BanManagermeta.setDisplayName(ChatColor.RED + "Ban This Player!");
        ArrayList<String> BanManagerlore = new ArrayList<>();
        BanManagerlore.add(ChatColor.GREEN + "This opens the Ban Manager GUI");
        BanManagerlore.add(ChatColor.GREEN + "and allows you to choose the ban length");
        BanManagermeta.setLore(BanManagerlore);
        BanManager.setItemMeta(BanManagermeta);

        //Item 7 -------------------------------------------------------------------------------------------------------
        ItemStack Mute = new ItemStack(Material.FEATHER, 1);
        ItemMeta Mutemeta = Mute.getItemMeta();
        Mutemeta.setDisplayName(ChatColor.RED + "Mute This Player!");
        ArrayList<String> Mutelore = new ArrayList<>();
        Mutelore.add(ChatColor.GREEN + "This performs the /mute command");
        Mutelore.add(ChatColor.GREEN + "and is issued by you, not the console");
        Mutelore.add(ChatColor.LIGHT_PURPLE + "YOU REQUIRE MUTE PERMISSIONS FOR THIS TO WORK!");
        Mutemeta.setLore(Mutelore);
        Mute.setItemMeta(Mutemeta);

        //Item 7 -------------------------------------------------------------------------------------------------------
        ItemStack Unmute = new ItemStack(Material.DRAGON_BREATH, 1);
        ItemMeta Unmutemeta = Unmute.getItemMeta();
        Unmutemeta.setDisplayName(ChatColor.RED + "Unmute This Player!");
        ArrayList<String> Unmutelore = new ArrayList<>();
        Unmutelore.add(ChatColor.GREEN + "This performs the /unmute command");
        Unmutelore.add(ChatColor.GREEN + "and is issued by you, not the console");
        Unmutelore.add(ChatColor.LIGHT_PURPLE + "YOU REQUIRE UNMUTE PERMISSIONS FOR THIS TO WORK!");
        Unmutemeta.setLore(Unmutelore);
        Unmute.setItemMeta(Unmutemeta);

        //Item 8 -------------------------------------------------------------------------------------------------------
        ItemStack PlayerInv = new ItemStack(Material.SHULKER_BOX, 1);
        ItemMeta PlayerInvMeta = PlayerInv.getItemMeta();
        PlayerInvMeta.setDisplayName(ChatColor.RED + "View player's inventory");
        ArrayList<String> PlayerInvLore = new ArrayList<>();
        PlayerInvLore.add(ChatColor.GREEN + "This opens the targeted players inventory");
        PlayerInvMeta.setLore(PlayerInvLore);
        PlayerInv.setItemMeta(PlayerInvMeta);

        //Item 8 -------------------------------------------------------------------------------------------------------
        ItemStack PlayerEnder = new ItemStack(Material.ENDER_CHEST, 1);
        ItemMeta PlayerEnderMeta = PlayerEnder.getItemMeta();
        PlayerEnderMeta.setDisplayName(ChatColor.RED + "View player's enderchest");
        ArrayList<String> PlayerEnderLore = new ArrayList<>();
        PlayerEnderLore.add(ChatColor.GREEN + "This opens the targeted players enderchest");
        PlayerEnderMeta.setLore(PlayerEnderLore);
        PlayerEnder.setItemMeta(PlayerEnderMeta);

        //Disabled Feature Item ----------------------------------------------------------------------------------------
        ItemStack Disabled = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta Disabledmeta = Disabled.getItemMeta();
        Disabledmeta.setDisplayName(ChatColor.RED + "This feature has been disabled!");
        Disabled.setItemMeta(Disabledmeta);

        inventory.setItem(0, Kick);
        inventory.setItem(4, PlayerName);
        inventory.setItem(13, Back);
        if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-player-inventory")){
            inventory.setItem(7, PlayerInv);
        }else {
            inventory.setItem(7, Disabled);
        }
        if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-player-enderchest")){
            inventory.setItem(16, PlayerEnder);
        }else {
            inventory.setItem(16, Disabled);
        }
        if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-mute")){
            inventory.setItem(1, Mute);
        }else {
            inventory.setItem(1, Disabled);
        }
        if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-op")){
            inventory.setItem(8, Op);
        }else {
            inventory.setItem(8, Disabled);
        }
        if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-ban-manager")){
            inventory.setItem(9, BanManager);
        }else {
            inventory.setItem(9, Disabled);
        }
        if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-unmute")){
            inventory.setItem(10, Unmute);
        }else {
            inventory.setItem(10, Disabled);
        }
        if (PlayerGUIAdvanced.getPlugin().getConfig().getBoolean("Enable-deop")){
            inventory.setItem(17, Deop);
        }else {
            inventory.setItem(17, Disabled);
        }
    }
}