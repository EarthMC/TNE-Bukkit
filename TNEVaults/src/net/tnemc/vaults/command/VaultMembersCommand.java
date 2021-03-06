package net.tnemc.vaults.command;

import com.github.tnerevival.user.IDFinder;
import net.tnemc.core.TNE;
import net.tnemc.core.commands.TNECommand;
import net.tnemc.core.common.WorldVariant;
import net.tnemc.core.common.account.WorldFinder;
import net.tnemc.vaults.VaultManager;
import net.tnemc.vaults.VaultsModule;
import net.tnemc.vaults.vault.Vault;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 11/12/2017.
 */
public class VaultMembersCommand extends TNECommand {
  public VaultMembersCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "members";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "tne.vault.members";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
      "/vault members [player] - View the members of a vault."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {

    final boolean self = arguments.length < 1;
    final UUID id = (!self)? IDFinder.getID(arguments[0]) : IDFinder.getID(sender);
    final String world = WorldFinder.getWorld(sender, WorldVariant.BALANCE);

    Vault vault = null;
    if(!self) {
      if(!VaultsModule.instance().manager().hasVault(id, world)) {
        sender.sendMessage(ChatColor.RED + "That player currently does not have a vault.");
        return false;
      }
      vault = VaultsModule.instance().manager().getVault(id, world);
      if(!vault.getMembers().containsKey(IDFinder.getID(sender))) {
        sender.sendMessage(ChatColor.RED + "You do not have access to that player's vault.");
        return false;
      }

      List<String> members = new ArrayList<>();

      vault.getMembers().keySet().forEach(identifier->members.add(IDFinder.getUsername(identifier.toString())));

      sender.sendMessage(ChatColor.WHITE + "Vault Members: " + String.join(", ", members));
      return true;
    }

    if(!VaultsModule.instance().manager().hasVault(id, world)) {
      if(VaultManager.cost.compareTo(BigDecimal.ZERO) > 0) {
        sender.sendMessage(ChatColor.RED + "you do not have a vault. Please use /vault buy to get one.");
        return false;
      }
      VaultsModule.instance().manager().addVault(new Vault(id, world));
    }
    vault = VaultsModule.instance().manager().getVault(id, world);

    List<String> members = new ArrayList<>();

    vault.getMembers().keySet().forEach(identifier->members.add(IDFinder.getUsername(identifier.toString())));

    sender.sendMessage(ChatColor.WHITE + "Vault Members: " + String.join(", ", members));
    return true;
  }
}