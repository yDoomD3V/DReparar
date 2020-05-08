package com.ydoom.reparar.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ydoom.reparar.api.CoreItemAPI;
import com.ydoom.reparar.main.DReparar;

public class RepararCommand implements CommandExecutor {
	
	DReparar plugin = DReparar.getPlugin();
	
	@SuppressWarnings("deprecation")
	ItemStack verde = new ItemStack(plugin.getConfig().getInt("Item_Confirmar.Id"), 1, (short)plugin.getConfig().getInt("Item_Confirmar.Data"));
	ItemStack confirmar = new CoreItemAPI(verde).setDisplayName(plugin.getConfig().getString("Item_Confirmar.Nome").replace("&", "§"));
	@SuppressWarnings("deprecation")
	ItemStack vermelho = new ItemStack(plugin.getConfig().getInt("Item_Cancelar.Id"), 1, (short)plugin.getConfig().getInt("Item_Cancelar.Data"));
	ItemStack cancelar = new CoreItemAPI(vermelho).setDisplayName(plugin.getConfig().getString("Item_Cancelar.Nome").replace("&", "§"));
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player)sender;
		ItemStack hand = p.getItemInHand();
		
		if(hand.getType() == Material.AIR) {
			p.sendMessage(plugin.getConfig().getString("Invalid_Item").replace("&", "§"));
			return true;
		}
		if(hand.getType().getMaxDurability() == 0) {
			p.sendMessage(plugin.getConfig().getString("Invalid_Item").replace("&", "§"));
			return true;
		}
		Inventory inv = Bukkit.createInventory(null, 27, plugin.getConfig().getString("Principal_Menu.Nome").replace("&", "§"));
		inv.setItem(11, confirmar);
		inv.setItem(13, hand);
		inv.setItem(15, cancelar);
		p.openInventory(inv);
		
		return false;
	}

}
