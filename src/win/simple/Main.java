package win.simple;

import net.minecraft.server.v1_13_R2.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
						if(args.length >= 2) {
							if(!args[1].equals("")) {
								naijiu = Integer.parseInt(args[1]);
							}
						}
						addItem(player, args[0], naijiu, true);
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

	public static ItemStack addItem(Player player, String Grade, int naijiu, boolean giveplayer) {
		boolean isjx = false;
		ItemStack gz = new ItemStack(Material.DIAMOND_PICKAXE);
		gz.setDurability((short) naijiu);
		List<String> lore = new ArrayList<>();
		if(Grade.equals("1")) {
			lore.add("§7连锁挖矿 I");
			isjx = true;
		}
		if(Grade.equals("2")) {
			lore.add("§7连锁挖矿 II");
			isjx = true;
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
				if(lore.equals("§7连锁挖矿 I")) {
					DestructionNumber = 4;
					isfm = true;
					break;
				}

				if(lore.equals("§7连锁挖矿 II")) {
					DestructionNumber = 8;
					isfm = true;
					break;
				}
			}
			if(isfm) {
				EntityPlayer ep = ((CraftPlayer) e.getPlayer()).getHandle();
				Location oldloc = e.getBlock().getLocation();
				org.bukkit.Material PlayerDestructionType = e.getBlock().getType();
				String biaoji = oldloc.getBlockX() + "@" + oldloc.getBlockY() + "@" + oldloc.getBlockZ();

				new Chain(e.getBlock().getWorld(),
						oldloc, PlayerDestructionType,
						biaoji,
						DestructionNumber,
						e.getPlayer());
			}
		}
	}
	
}
