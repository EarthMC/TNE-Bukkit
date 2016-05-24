package com.github.tnerevival.commands.shop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.tnerevival.TNE;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.material.MaterialHelper;
import com.github.tnerevival.core.shops.Shop;

public class ShopRemoveCommand extends TNECommand {

	public ShopRemoveCommand(TNE plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "-i" };
	}

	@Override
	public String getNode() {
		return "tne.shop.remove";
	}

	@Override
	public boolean console() {
		return false;
	}

	@Override
	public void help(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "/shop remove <shop> [amount] [item] [cost(gold:amount or trade:name)] - Remove a specific item from your shop. Cost is required if multiple entries exist.");
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] arguments) {
		if(sender instanceof Player && arguments.length >= 1) {
			if(Shop.exists(arguments[0])) {
				if(Shop.canModify(arguments[0], (Player)sender)) {
					Player p = (Player)sender;
					Shop s = Shop.getShop(arguments[0]);
					
					ItemStack item = p.getInventory().getItemInMainHand().clone();
					item.setAmount(1);
					double gold = -1.0;
					ItemStack trade = null;
					
					if(arguments.length > 1) {
						item.setAmount(Integer.parseInt(arguments[1]));
						
						if(arguments.length > 2) {
							int splitArg = (arguments[2].contains(":")) ? 2 : 3;
							
							if(splitArg == 3) {
								Material mat = MaterialHelper.getMaterial(arguments[2]);
								if(mat.equals(Material.AIR)) {

									//TODO: Item not found in shop.
									return false;
								} 
								item.setType(mat);
							}
							
							if(splitArg == 3 && arguments.length >= 4) {
								String[] split = arguments[splitArg].split(":");
								if(split[0].equalsIgnoreCase("gold")) {
									gold = Double.parseDouble(split[1]);
									
									if(s.hasItem(item.getType(), gold)) {

										s.removeItem(item, gold);
										//TODO: Remove item from shop.
										return true;
									}
									
									//TODO: Item not found in shop.
									return false;
								} else {
									Material mat = MaterialHelper.getMaterial(split[1]);
									if(!mat.equals(Material.AIR)) {
										trade = new ItemStack(mat);
										
										if(s.hasItem(item.getType(), mat.toString())) {

											s.removeItem(item, trade);
											//TODO: Remove item from shop.
											return true;
										}
									}

									//TODO: Item not found in shop.
									return false;
								}
							}
						}

						s.removeItem(item);
						//TODO: Remove item from shop.
						return true;
					}
				}
				//TODO: Must be shop owner to do that.
				return false;
			}
			//TODO: Shop doesn't exist message.
			return false;
		} else {
			help(sender);
		}
		return false;
	}
}