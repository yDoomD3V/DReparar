package com.ydoom.reparar.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ydoom.reparar.main.DReparar;

public class ItemClickListener implements Listener {

	DReparar plugin = DReparar.getPlugin();

	@EventHandler
	public void onClickItem(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		ItemStack hand = p.getItemInHand();
		Inventory inv = e.getInventory();

		if(e.getClickedInventory() == null) return;
		if (inv.getTitle().equals(plugin.getConfig().getString("Principal_Menu.Nome").replace("&", "§"))) {
			if (!(item.getType() == Material.EYE_OF_ENDER) || item != null && item.getType() != Material.AIR) {
				e.setCancelled(true);
			}
			if (item.getItemMeta().hasDisplayName()) {
				if (item.getItemMeta().getDisplayName()
						.equalsIgnoreCase(plugin.getConfig().getString("Item_Confirmar.Nome").replace("&", "§"))) {
					if (p.hasPermission(plugin.getConfig().getString("Permission_Repair_Free"))) {
						if (hand.getDurability() == 0) {
							p.closeInventory();
							p.sendMessage(plugin.getConfig().getString("Already_Max_Durability").replace("&", "§"));
							return;
						}
						p.closeInventory();
						hand.setDurability((short) 0);
						p.sendMessage(plugin.getConfig().getString("Free_Repair").replace("&", "§").replace("%item%",
								"" + hand.getType()));
						return;
					}
					if (plugin.economy.has(p, plugin.getConfig().getInt("Cost"))) {
						if (hand.getDurability() == 0) {
							p.closeInventory();
							p.sendMessage(plugin.getConfig().getString("Already_Max_Durability").replace("&", "§"));
							return;
						}
						p.closeInventory();
						hand.setDurability((short) 0);
						p.sendMessage(plugin.getConfig().getString("Repair").replace("&", "§")
								.replace("%item%", "" + hand.getType())
								.replace("%cost%", "" + plugin.getConfig().getInt("Cost")));
						plugin.economy.withdrawPlayer(p, plugin.getConfig().getInt("Cost"));
					} else {
						p.closeInventory();
						p.sendMessage(plugin.getConfig().getString("Insufficient_Money").replace("&", "§")
								.replace("%cost%", "" + plugin.getConfig().getInt("Cost")));
					}
					return;
				}
				if (item.getItemMeta().hasDisplayName()) {
					if (item.getItemMeta().getDisplayName()
							.equalsIgnoreCase(plugin.getConfig().getString("Item_Cancelar.Nome").replace("&", "§"))) {
						p.closeInventory();
					}
				}
			}
		}
	}

}
