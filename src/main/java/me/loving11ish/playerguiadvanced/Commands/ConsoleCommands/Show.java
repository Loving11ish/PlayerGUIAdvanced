package me.loving11ish.playerguiadvanced.Commands.ConsoleCommands;

import me.loving11ish.playerguiadvanced.Commands.ConsoleCommand;
import me.loving11ish.playerguiadvanced.PlayerGUIAdvanced;
import me.loving11ish.playerguiadvanced.Utils.ColorUtils;
import org.bukkit.ChatColor;

public class Show extends ConsoleCommand {

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return ChatColor.AQUA + "If SuperVanish or PremiumVanish plugins are installed, this will set you out of vanish.";
    }

    @Override
    public String getSyntax() {
        return ChatColor.AQUA + "/pl show";
    }

    @Override
    public void perform(String[] args) {
        System.out.println(ColorUtils.translateColorCodes(PlayerGUIAdvanced.getPlugin().getConfig().getString("Player-only-command")));
    }

}
