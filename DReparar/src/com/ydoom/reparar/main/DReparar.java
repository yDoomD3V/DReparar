package com.ydoom.reparar.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.ydoom.reparar.commands.RepararCommand;
import com.ydoom.reparar.listeners.ItemClickListener;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class DReparar extends JavaPlugin{
	
	@Getter
	public static DReparar plugin;
	public static Permission permission = null;
	public Economy economy = null;
	public static Chat chat = null;
	
	@Override
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		load();
	}
	@Override
	public void onDisable() {
	}
	public void load() {
		onListeners();
		onCommands();
		setupChat();
		setupEconomy();
		setupPermissions();
	}
	public void onListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ItemClickListener(), this);
	}
	public void onCommands() {
		getCommand("reparar").setExecutor(new RepararCommand());
	}
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
}
