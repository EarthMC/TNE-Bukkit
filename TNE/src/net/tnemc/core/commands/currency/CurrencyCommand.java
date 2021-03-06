package net.tnemc.core.commands.currency;

import net.tnemc.core.TNE;
import net.tnemc.core.commands.TNECommand;

/**
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 7/10/2017.
 */
public class CurrencyCommand extends TNECommand {

  public CurrencyCommand(TNE plugin) {
    super(plugin);
    addSub(new CurrencyListCommand(plugin));
    addSub(new CurrencyRenameCommand(plugin));
    addSub(new CurrencyTiersCommand(plugin));
  }

  @Override
  public String name() {
    return "currency";
  }

  @Override
  public String[] aliases() {
    return new String[] {
        "cur", "tnecur"
    };
  }

  @Override
  public String node() {
    return "";
  }

  @Override
  public boolean console() {
    return true;
  }
}