package win.simple;

import com.sun.org.apache.bcel.internal.generic.INEG;
import net.minecraft.server.v1_13_R2.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Recipe.addRecipe();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(cmd.getName().equals("getls")) {
				Player player = (Player) sender;

				if(args.length >= 1) {
					if(player.isOp()) {
						int naijiu = 0;
						boolean kaiqinaijiu = true;
						if(args.length >= 2) {
							if(args[1].equals("false")) {
								kaiqinaijiu = false;
							}else {
								if(!args[1].equals("")) {
									naijiu = Integer.parseInt(args[1]);
								}
							}

						}
						addItem(player, args[0], naijiu, true, kaiqinaijiu);
					}else {
						player.sendRawMessage("§c你没有权限！");
					}
				}else {
					player.sendRawMessage("§c参数有误！");
				}
				return true;
			}
		}
		return false;
	}

	public static ItemStack addItem(Player player, String Grade, int naijiu, boolean giveplayer, boolean isDurable) {
		boolean isjx = false;
		ItemStack gz = new ItemStack(Material.DIAMOND_PICKAXE);
		gz.setDurability((short) naijiu);
		List<String> lore = new ArrayList<>();
		if(!isDurable) {
			lore.add("§e无限耐久");
		}
		switch(Grade) {
			case "1" :
				lore.add("§7连锁挖矿 I");
				lore.add("§2范围: 5");
				isjx = true;
				break;
			case "2" :
				lore.add("§7连锁挖矿 II");
				lore.add("§2范围: 10");
				isjx = true;
				break;
			case "3" :
				lore.add("§7连锁挖矿 III");
				lore.add("§2范围: 20");
				isjx = true;
				break;
			case "d1" :
				lore.add("§7连锁挖矿 D");
				lore.add("§2范围: 500");
				lore.add("§c容易卡死");
				isjx = true;
				break;
			case "d2" :
				lore.add("§7连锁挖矿 M");
				lore.add("§2范围: 1000");
				lore.add("§c容易卡死[崩服]");
				isjx = true;
				break;
			default:
				lore.add("§7连锁挖矿 ?");
				lore.add("§2范围: " + Grade);
				lore.add("§c自定义");
				isjx = true;
				break;
		}
		if(isjx) {
			ItemMeta itemMeta = gz.getItemMeta();
			itemMeta.setDisplayName("§b连锁钻石镐");
			itemMeta.setLore(lore);
			gz.setItemMeta(itemMeta);
			if(giveplayer) {
				player.getPlayer().getInventory().addItem(gz);
			}
			return gz;
		}
		return null;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {
		if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
			ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();
			boolean isfm = false;
			int DestructionNumber = 0;
			if(itemStack.getItemMeta().getLore() == null) {
				return;
			}
			for(String lore : itemStack.getItemMeta().getLore()) {
				switch(lore) {
					case "§7连锁挖矿 I" :
						DestructionNumber = 4;
						isfm = true;
						break;
					case "§7连锁挖矿 II" :
						DestructionNumber = 9;
						isfm = true;
						break;
					case "§7连锁挖矿 III" :
						DestructionNumber = 19;
						isfm = true;
						break;
					case "§7连锁挖矿 D" :
						DestructionNumber = 500;
						isfm = true;
						break;
					case "§7连锁挖矿 M" :
						DestructionNumber = 1000;
						isfm = true;
						break;
					case "§c自定义" :
						for(String lore1 : itemStack.getItemMeta().getLore()) {
							if(lore1.indexOf("范围") != -1) {
								String[] fg = lore1.split(":");
								if(fg.length >= 1) {
									DestructionNumber = Integer.parseInt(fg[1].trim());
									isfm = true;
									break;
								}
							}
						}
						break;
				}
			}
			if(isfm) {
				EntityPlayer ep = ((CraftPlayer) e.getPlayer()).getHandle();
				Location oldloc = e.getBlock().getLocation();
				org.bukkit.Material PlayerDestructionType = e.getBlock().getType();
				String biaoji = oldloc.getBlockX() + "@" + oldloc.getBlockY() + "@" + oldloc.getBlockZ();
				new Chain(e.getBlock().getWorld(), oldloc, PlayerDestructionType, biaoji, DestructionNumber, e.getPlayer());
			}
		}
	}
	
}
