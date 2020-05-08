package com.ydoom.reparar.api;

import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import org.bukkit.Material;

import net.minecraft.server.v1_8_R3.*;
import java.math.*;
import java.io.*;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class CoreItemAPI extends ItemStack
{
    public CoreItemAPI() {
    }
    
    public CoreItemAPI(final ItemStack itemStack) {
        super(itemStack);
    }
    
    public CoreItemAPI(final Material material) {
        super(material);
    }
    
    public CoreItemAPI setAmounts(final int amount) {
        this.setAmount(amount);
        return this;
    }
    
    public CoreItemAPI setDurabilitys(final short durability) {
        this.setDurability(durability);
        return this;
    }
    
    public CoreItemAPI setDisplayName(final String name) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(name);
        this.setItemMeta(itemMeta);
        return this;
    }
    
    public String getDisplayName() {
        return this.getItemMeta().getDisplayName();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CoreItemAPI setLore(final String... lore) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore((List)Arrays.asList(lore));
        this.setItemMeta(itemMeta);
        return this;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CoreItemAPI setLore(final List<String> lore) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore((List)lore);
        this.setItemMeta(itemMeta);
        return this;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CoreItemAPI addLineLore(final String line) {
        final List<String> lore = new ArrayList<String>();
        if (this.getItemMeta().hasLore()) {
            lore.addAll(this.getItemMeta().getLore());
        }
        lore.add(line);
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore((List)lore);
        this.setItemMeta(itemMeta);
        return this;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CoreItemAPI clearLore() {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore((List)new ArrayList());
        this.setItemMeta(itemMeta);
        return this;
    }
    
    public CoreItemAPI addFlag(final ItemFlag flag) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addItemFlags(new ItemFlag[] { flag });
        this.setItemMeta(itemMeta);
        return this;
    }
    
    public CoreItemAPI addFlag(final ItemFlag... flag) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addItemFlags(flag);
        this.setItemMeta(itemMeta);
        return this;
    }
    
    public CoreItemAPI addEnchant(final Enchantment enchantment, final int level) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        this.setItemMeta(itemMeta);
        return this;
    }
    
    public CoreItemAPI addFakeEnchant() {
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy((ItemStack)this);
        NBTTagCompound tag = null;
        try {
            if (!nmsStack.hasTag()) {
                tag = new NBTTagCompound();
                nmsStack.setTag(tag);
            }
            if (tag == null) {
                tag = nmsStack.getTag();
            }
            final NBTTagList ench = new NBTTagList();
            tag.set("ench", (NBTBase)ench);
            nmsStack.setTag(tag);
            this.setItemMeta(CraftItemStack.asCraftMirror(nmsStack).getItemMeta());
        }
        catch (Exception ex) {}
        return this;
    }
    
    public CoreItemAPI setUnbreakable(final boolean b) {
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.spigot().setUnbreakable(b);
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
        this.setItemMeta(itemMeta);
        return this;
    }
    
    public CoreItemAPI setOwner(final String owner) {
        final SkullMeta skullMeta = (SkullMeta)this.getItemMeta();
        skullMeta.setOwner(owner);
        this.setItemMeta((ItemMeta)skullMeta);
        return this;
    }
    
    public CoreItemAPI setColor(final Color color) {
        final LeatherArmorMeta itemMeta = (LeatherArmorMeta)this.getItemMeta();
        itemMeta.setColor(color);
        this.setItemMeta((ItemMeta)itemMeta);
        return this;
    }
    
    @SuppressWarnings("deprecation")
	public CoreItemAPI setColor(final DyeColor color) {
        this.setDurability((short)color.getData());
        return this;
    }
    
    public CoreItemAPI setFireworkColor(final Color color) {
        final ItemMeta itemMeta = this.getItemMeta();
        final FireworkEffectMeta fireworkMeta = (FireworkEffectMeta)itemMeta;
        final FireworkEffect fireworkEffect = FireworkEffect.builder().withColor(color).build();
        fireworkMeta.setEffect(fireworkEffect);
        this.setItemMeta((ItemMeta)fireworkMeta);
        return this;
    }
    
    public CoreItemAPI setFireworkColor(final int blue, final int green, final int red) {
        final ItemMeta itemMeta = this.getItemMeta();
        final FireworkEffectMeta fireworkMeta = (FireworkEffectMeta)itemMeta;
        final FireworkEffect fireworkEffect = FireworkEffect.builder().withColor(Color.fromBGR(blue, green, red)).build();
        fireworkMeta.setEffect(fireworkEffect);
        this.setItemMeta((ItemMeta)fireworkMeta);
        return this;
    }
    
    public String toJson() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final OutputStream dataOutput = new DataOutputStream(outputStream);
        final NBTTagCompound outputObject = new NBTTagCompound();
        final CraftItemStack craft = CraftItemStack.asCraftCopy((ItemStack)this);
        if (craft != null) {
            CraftItemStack.asNMSCopy((ItemStack)craft).save(outputObject);
        }
        try {
            NBTCompressedStreamTools.a(outputObject, dataOutput);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }
    
    public ItemStack fromJson(final String json) {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(json, 32).toByteArray());
        NBTTagCompound item = null;
        try {
            item = NBTCompressedStreamTools.a((InputStream)inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final net.minecraft.server.v1_8_R3.ItemStack stack = net.minecraft.server.v1_8_R3.ItemStack.createStack(item);
        return (ItemStack)CraftItemStack.asCraftMirror(stack);
    }
    
    public String toJson2() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final OutputStream dataOutput = new DataOutputStream(outputStream);
        final NBTTagCompound outputObject = new NBTTagCompound();
        final CraftItemStack craft = CraftItemStack.asCraftCopy((ItemStack)this);
        if (craft != null) {
            CraftItemStack.asNMSCopy((ItemStack)craft).save(outputObject);
        }
        NBTCompressedStreamTools.a(outputObject, dataOutput);
        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }
    
    public ItemStack fromJson2(final String json) throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(json, 32).toByteArray());
        NBTTagCompound item = null;
        item = NBTCompressedStreamTools.a((InputStream)inputStream);
        final net.minecraft.server.v1_8_R3.ItemStack stack = net.minecraft.server.v1_8_R3.ItemStack.createStack(item);
        return (ItemStack)CraftItemStack.asCraftMirror(stack);
    }
    
    public NBTTagCompound getTag() {
        final net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy((ItemStack)this);
        NBTTagCompound tag;
        if (itemNms != null && itemNms.hasTag()) {
            tag = itemNms.getTag();
        }
        else {
            tag = new NBTTagCompound();
        }
        return tag;
    }
    
    public boolean hasTag() {
        return this.getTag() != null;
    }
    
    public CoreItemAPI setTag(final NBTTagCompound tag) {
        final net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy((ItemStack)this);
        CraftItemStack.setItemMeta(itemNms, this.getItemMeta());
        itemNms.setTag(tag);
        final ItemStack asBukkitCopy = CraftItemStack.asBukkitCopy(itemNms);
        this.setItemMeta(asBukkitCopy.getItemMeta());
        return this;
    }
    
    public CoreItemAPI addString(final String name, final String value) {
        final NBTTagCompound tag = this.getTag();
        tag.setString(name, value);
        this.setTag(tag);
        return this;
    }
    
    public CoreItemAPI addBoolean(final String name, final boolean value) {
        final NBTTagCompound tag = this.getTag();
        tag.setBoolean(name, value);
        this.setTag(tag);
        return this;
    }
    
    public CoreItemAPI addDouble(final String name, final double value) {
        final NBTTagCompound tag = this.getTag();
        tag.setDouble(name, value);
        this.setTag(tag);
        return this;
    }
    
    public ItemStack addInt(final String name, final int value) {
        final NBTTagCompound tag = this.getTag();
        tag.setInt(name, value);
        return this.setTag(tag);
    }
    
    public boolean hasKey(final String name) {
        final NBTTagCompound tag = this.getTag();
        return tag != null && tag.hasKey(name);
    }
    
    public String getString(final String name) {
        final NBTTagCompound tag = this.getTag();
        return tag.getString(name);
    }
    
    public int getInt(final String name) {
        final NBTTagCompound tag = this.getTag();
        return tag.getInt(name);
    }
    
    public double getDouble(final String name) {
        final NBTTagCompound tag = this.getTag();
        return tag.getDouble(name);
    }
    
    public boolean getBoolean(final String name) {
        final NBTTagCompound tag = this.getTag();
        return tag.getBoolean(name);
    }
}
