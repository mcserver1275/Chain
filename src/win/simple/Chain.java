package win.simple;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Chain {
	private static Map<String, Integer> Blockmess = new HashMap<>();

	private String BlockIdentification = "";
	private Location OldLocation = null;
	private org.bukkit.Material PlayerDestructionType = null;
	private World world = null;
	private int DestructionNumber = 0;
	private Player player = null;


	public Chain(World world, Location OldLocation, org.bukkit.Material PlayerDestructionType, String BlockIdentification, int DestructionNumber, Player player) {
		this.OldLocation = OldLocation;
		this.PlayerDestructionType = PlayerDestructionType;
		this.world = world;
		this.BlockIdentification = BlockIdentification;
		this.DestructionNumber = DestructionNumber;
		this.player = player;


		if(Blockmess.get(BlockIdentification) == null) {
			Blockmess.put(BlockIdentification, 0);
		}else {
			Blockmess.put(BlockIdentification, Blockmess.get(BlockIdentification) + 1);
		}

		Start();

	}

	public void Start() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Chain"), new Runnable() {
		@Override
		public void run() {
			Location NewLoc = null;

			NewLoc = new Location(world, OldLocation.getBlockX(), OldLocation.getBlockY(), OldLocation.getBlockZ());
			NewLoc.setX(OldLocation.getX() + 1);
			setBlock(NewLoc);
				
			NewLoc = new Location(world, OldLocation.getBlockX(), OldLocation.getBlockY(), OldLocation.getBlockZ());
			NewLoc.setX(OldLocation.getX() - 1);
			setBlock(NewLoc);
				
			NewLoc = new Location(world, OldLocation.getBlockX(), OldLocation.getBlockY(), OldLocation.getBlockZ());
			NewLoc.setY(OldLocation.getY() + 1);
			setBlock(NewLoc);
				
			NewLoc = new Location(world, OldLocation.getBlockX(), OldLocation.getBlockY(), OldLocation.getBlockZ());
			NewLoc.setY(OldLocation.getY() - 1);
			setBlock(NewLoc);
				
			NewLoc = new Location(world, OldLocation.getBlockX(), OldLocation.getBlockY(), OldLocation.getBlockZ());
			NewLoc.setZ(OldLocation.getZ() + 1);
			setBlock(NewLoc);
				
			NewLoc = new Location(world, OldLocation.getBlockX(), OldLocation.getBlockY(), OldLocation.getBlockZ());
			NewLoc.setZ(OldLocation.getZ() - 1);
			setBlock(NewLoc);
		}
			
		});
	}

	public void setBlock(Location NewLoc) {
		if(NewLoc.getBlock().getType().equals(this.PlayerDestructionType)) {
			if(Blockmess.get(this.BlockIdentification) != null) {
				if(Blockmess.get(this.BlockIdentification) < this.DestructionNumber) {
					ItemStack MainItem = this.player.getInventory().getItemInMainHand();

					MainItem.setDurability((short) (MainItem.getDurability() + 1));
					if(MainItem.getDurability() <= 1561) {
						if(MainItem.getDurability() != -1) {
							WorldServer ws = ((CraftBlock) NewLoc.getBlock()).getCraftWorld().getHandle();
							ws.setAir(new BlockPosition(NewLoc.getBlockX(), NewLoc.getBlockY(), NewLoc.getBlockZ()), true);
							new Chain(this.world, NewLoc, this.PlayerDestructionType, this.BlockIdentification, this.DestructionNumber, this.player);
						}
					}else {
						this.player.getInventory().remove(MainItem);
					}
				}else {
					Blockmess.remove(this.BlockIdentification);
				}
			}
		}
	}
}
