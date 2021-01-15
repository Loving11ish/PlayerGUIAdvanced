package me.loving11ish.playerguiadvanced.Commands.Commands;

import me.loving11ish.playerguiadvanced.MenuSystem.Menus.PunishMenu;
import me.loving11ish.playerguiadvanced.MenuSystem.PlayerMenuUtility;
import me.loving11ish.playerguiadvanced.PlayerGUIAdvanced;
import me.loving11ish.playerguiadvanced.Utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Punish implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("playergui.ban")){
                if (args.length == 1){
                    Player PlayerToMod = Bukkit.getPlayerExact(args[0]);
                    if (Bukkit.getServer().getOnlinePlayers().contains(PlayerToMod)) {
                        PlayerMenuUtility playerMenuUtility = PlayerGUIAdvanced.getPlayerMenuUtility(player);
                        playerMenuUtility.setPlayerToMod(Bukkit.getPlayer(args[0]));
                        new PunishMenu(PlayerGUIAdvanced.getPlayerMenuUtility(player)).open();
                    }else {
                        player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Punish-command-invalid-player").replace("%target%", args[0])));
                    }
                }else{
                    player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Punish-command-no-player-provided")));
                }
            }else{
                player.sendMessage(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Punish-command-no-permission")));
            }
        }else{
            System.out.println(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Player-only-command")));
        }
        return true;
    }
}