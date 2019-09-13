package net.tnemc.core.commands.module;

import net.tnemc.core.TNE;
import net.tnemc.core.commands.TNECommand;
import net.tnemc.core.common.Message;
import net.tnemc.core.common.module.cache.ModuleFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 8/9/2019.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class ModuleAvailableCommand extends TNECommand {

  public ModuleAvailableCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String name() {
    return "available";
  }

  @Override
  public String[] aliases() {
    return new String[] {
        "avail"
    };
  }

  @Override
  public String node() {
    return "tne.module.available";
  }

  @Override
  public boolean console() {
    return true;
  }

  @Override
  public String helpLine() {
    return "Messages.Commands.Module.Available";
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    final String url = (arguments.length > 0)? arguments[0] : TNE.coreURL;

    Bukkit.getScheduler().runTaskAsynchronously(TNE.instance(), ()->{
      List<ModuleFile> available = TNE.instance().moduleCache().getModules(url);

      Message header = new Message("Messages.Module.AvailableHeader");
      header.addVariable("$url", url);
      header.translate(TNE.instance().defaultWorld, sender);

      for(ModuleFile file : available) {

        Message entry = new Message("Messages.Module.AvailableEntry");
        entry.addVariable("$module", file.getName());
        entry.addVariable("$version", file.getVersion());
        entry.translate(TNE.instance().defaultWorld, sender);
      }
    });
    return true;
  }
}