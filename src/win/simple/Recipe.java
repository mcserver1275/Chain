package win.simple;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipe {
	public static void addRecipe() {
		ItemStack OneStack = new ItemStack(Material.BOOK);
		ItemMeta OneMeta = OneStack.getItemMeta();
		OneMeta.setDisplayName("§e连锁挖矿");
		List<String> lore = null;
		lore = new ArrayList<>();
		lore.add("§7连锁挖矿 I");
		OneMeta.setLore(lore);
		OneStack.setItemMeta(OneMeta);
		ShapedRecipe shapedRecipe = new ShapedRecipe(OneStack);
		shapedRecipe.shape(new String[]{
				"NNN",
				"MTM",
				" S "
		});
		shapedRecipe.setIngredient('N', Material.SLIME_BALL);
		shapedRecipe.setIngredient('M', Material.ENDER_PEARL);
		shapedRecipe.setIngredient('T', Material.IRON_INGOT);
		shapedRecipe.setIngredient('S', Material.BOOK);
		Bukkit.getServer().addRecipe(shapedRecipe);
		
		ItemStack TowStack = new ItemStack(Material.BOOK);
		ItemMeta TwoMeta = TowStack.getItemMeta();
		TwoMeta.setDisplayName("§e连锁挖矿");
		lore = new ArrayList<>();
		lore.add("§7连锁挖矿 II");
		TwoMeta.setLore(lore);
		TowStack.setItemMeta(TwoMeta);
		ShapedRecipe TwoBook = new ShapedRecipe(TowStack);
		TwoBook.shape(new String[]{
				"NNN",
				"MZM",
				" S "
		});
		TwoBook.setIngredient('N', Material.SLIME_BALL);
		TwoBook.setIngredient('M', Material.ENDER_PEARL);
		TwoBook.setIngredient('Z', Material.DIAMOND);
		TwoBook.setIngredient('S', Material.BOOK);
		Bukkit.getServer().addRecipe(TwoBook);
		
		ShapedRecipe OnePickaxe = new ShapedRecipe(Main.addItem(null, "1", 0, false));
		OnePickaxe.shape(new String[]{
			"   ",
			" S ",
			" G "
		});
		OnePickaxe.setIngredient('S', OneStack.getData());
		OnePickaxe.setIngredient('G', Material.DIAMOND_PICKAXE);
		Bukkit.getServer().addRecipe(OnePickaxe);
		
		ShapedRecipe TwoPickaxe = new ShapedRecipe(Main.addItem(null, "2", 0, false));
		TwoPickaxe.shape(new String[]{
			"   ",
			" B ",
			" Z "
		});
		TwoPickaxe.setIngredient('B', TowStack.getData());
		TwoPickaxe.setIngredient('Z', Material.DIAMOND_PICKAXE);
		Bukkit.getServer().addRecipe(TwoPickaxe);
	}
}
