package services.xenlan.xabilities.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import services.xenlan.xabilities.util.chatUtil;
import services.xenlan.xabilities.xAbilities;

import java.util.List;

public enum AbilityEvents {

    TANKINGOT(xAbilities.config.getConfig().getString("TankIngot.Name"),
            xAbilities.config.getConfig().getStringList("TankIngot.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("TankIngot.Item")), 1)),
    PREPEARL(xAbilities.config.getConfig().getString("PrePearl.Name"),
            xAbilities.config.getConfig().getStringList("PrePearl.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PrePearl.Item")), 1)),
    AGROPEARL(xAbilities.config.getConfig().getString("AgroPearl.Name"),
            xAbilities.config.getConfig().getStringList("AgroPearl.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("AgroPearl.Item")), 1)),
    EXOTICBONE(xAbilities.config.getConfig().getString("ExoticBone.Name"),
            xAbilities.config.getConfig().getStringList("ExoticBone.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("ExoticBone.Item")), 1)),
    POCKETBARD(xAbilities.config.getConfig().getString("PocketBard.PocketBard.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.PocketBard.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.PocketBard.Item")), 1)),
    INVIS(xAbilities.config.getConfig().getString("PocketBard.Invis.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.Invis.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.Invis.Item")), 1)),
    STRENGTH(xAbilities.config.getConfig().getString("PocketBard.Strength.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.Strength.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.Strength.Item")), 1)),
    NINJASTAR(xAbilities.config.getConfig().getString("NinjaStar.Name"),
            xAbilities.config.getConfig().getStringList("NinjaStar.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("NinjaStar.Item")), 1)),
    JUMP(xAbilities.config.getConfig().getString("PocketBard.JumpBoost.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.JumpBoost.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.JumpBoost.Item")), 1)),
    SPEED(xAbilities.config.getConfig().getString("PocketBard.Speed.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.Speed.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.Speed.Item")), 1)),
    REGEN(xAbilities.config.getConfig().getString("PocketBard.Regen.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.Regen.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.Regen.Item")), 1)),
    RES(xAbilities.config.getConfig().getString("PocketBard.Resistance.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.Resistance.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.Resistance.Item")), 1)),
    WITHER(xAbilities.config.getConfig().getString("PocketBard.Wither.Name"),
            xAbilities.config.getConfig().getStringList("PocketBard.Wither.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PocketBard.Wither.Item")), 1)),
    LUCKYINGOT(xAbilities.config.getConfig().getString("LuckyIngot.Name"),
            xAbilities.config.getConfig().getStringList("LuckyIngot.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("LuckyIngot.Item")), 1)),
    TELEBOW(xAbilities.config.getConfig().getString("TeleBow.Name"),
            xAbilities.config.getConfig().getStringList("TeleBow.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("TeleBow.Item")), 1)),
    BELCHBOMB(xAbilities.config.getConfig().getString("BelchBomb.Name"),
            xAbilities.config.getConfig().getStringList("BelchBomb.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("BelchBomb.Item")), 1)),
    SWITCHER(xAbilities.config.getConfig().getString("Switcher.Name"),
            xAbilities.config.getConfig().getStringList("Switcher.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Switcher.Item")), 1)),
    SECONDCHANCE(xAbilities.config.getConfig().getString("SecondChance.Name"),
            xAbilities.config.getConfig().getStringList("SecondChance.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("SecondChance.Item")), 1)),
    BROINVIS(xAbilities.config.getConfig().getString("BroInvis.Name"),
            xAbilities.config.getConfig().getStringList("BroInvis.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("BroInvis.Item")), 1)),
    ANTITRAPSTAR(xAbilities.config.getConfig().getString("AntiTrapStar.Name"),
            xAbilities.config.getConfig().getStringList("AntiTrapStar.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("AntiTrapStar.Item")), 1)),
    FAKELOGGER(xAbilities.config.getConfig().getString("FakeLogger.Name"),
            xAbilities.config.getConfig().getStringList("FakeLogger.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("FakeLogger.Item")), 1)),
    DEBUFFFISH(xAbilities.config.getConfig().getString("DebuffFish.Name"),
            xAbilities.config.getConfig().getStringList("DebuffFish.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("DebuffFish.Item")), 1)),
    SWAPPERAXE(xAbilities.config.getConfig().getString("SwapperAxe.Name"),
            xAbilities.config.getConfig().getStringList("SwapperAxe.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("SwapperAxe.Item")), 1)),
    STICK(xAbilities.config.getConfig().getString("SwitchStick.Name"),
            xAbilities.config.getConfig().getStringList("SwitchStick.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("SwitchStick.Item")), 1)),
    ANTIFALL(xAbilities.config.getConfig().getString("AntiFall.Name"),
            xAbilities.config.getConfig().getStringList("AntiFall.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("AntiFall.Item")), 1)),
    GRAPPLER(xAbilities.config.getConfig().getString("Grappler.Name"),
            xAbilities.config.getConfig().getStringList("Grappler.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Grappler.Item")), 1)),
    FREEZER(xAbilities.config.getConfig().getString("Freezer.Name"),
            xAbilities.config.getConfig().getStringList("Freezer.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Freezer.Item")), 1)),
    WITHERGUN(xAbilities.config.getConfig().getString("WitherGun.Name"),
            xAbilities.config.getConfig().getStringList("WitherGun.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("WitherGun.Item")), 1)),
    ABILITYRESET(xAbilities.config.getConfig().getString("Ability-Reset.Name"),
            xAbilities.config.getConfig().getStringList("Ability-Reset.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Ability-Reset.Item")), 1)),
    WEBGUN(xAbilities.config.getConfig().getString("WebGun.Name"),
            xAbilities.config.getConfig().getStringList("WebGun.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("WebGun.Item")), 1)),
    MIXER(xAbilities.config.getConfig().getString("Mixer.Name"),
            xAbilities.config.getConfig().getStringList("Mixer.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Mixer.Item")), 1)),
    RAGEABILITY(xAbilities.config.getConfig().getString("RageAbility.Name"),
            xAbilities.config.getConfig().getStringList("RageAbility.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("RageAbility.Item")), 1)),
    GUARDIAN(xAbilities.config.getConfig().getString("GuardianAngel.Name"),
            xAbilities.config.getConfig().getStringList("GuardianAngel.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("GuardianAngel.Item")), 1)),
    HARPOON(xAbilities.config.getConfig().getString("Harpoon.Name"),
            xAbilities.config.getConfig().getStringList("Harpoon.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Harpoon.Item")), 1)),
    ANTIREDSTONE(xAbilities.config.getConfig().getString("AntiRedstone.Name"),
            xAbilities.config.getConfig().getStringList("AntiRedstone.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("AntiRedstone.Item")), 1)),
    ROTTENEGG(xAbilities.config.getConfig().getString("RottenEgg.Name"),
            xAbilities.config.getConfig().getStringList("RottenEgg.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("RottenEgg.Item")), 1)),
    POTIONCOUNTER(xAbilities.config.getConfig().getString("PotionCounter.Name"),
            xAbilities.config.getConfig().getStringList("PotionCounter.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PotionCounter.Item")), 1)),
    POTIONINHERITOR(xAbilities.config.getConfig().getString("PotionInheritor.Name"),
            xAbilities.config.getConfig().getStringList("PotionInheritor.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PotionInheritor.Item")), 1)),
    ANTIPOT(xAbilities.config.getConfig().getString("AntiPotion.Name"),
            xAbilities.config.getConfig().getStringList("AntiPotion.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("AntiPotion.Item")), 1)),
    CLEAVE(xAbilities.config.getConfig().getString("Cleave.Name"),
            xAbilities.config.getConfig().getStringList("Cleave.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Cleave.Item")), 1)),
    ENDERBUTT(xAbilities.config.getConfig().getString("EnderButt.Name"),
            xAbilities.config.getConfig().getStringList("EnderButt.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("EnderButt.Item")), 1)),
    BACKSTAB(xAbilities.config.getConfig().getString("BackStab.Name"),
            xAbilities.config.getConfig().getStringList("BackStab.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("BackStab.Item")), 1)),
    POISONDART(xAbilities.config.getConfig().getString("PoisonDart.Name"),
            xAbilities.config.getConfig().getStringList("PoisonDart.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("PoisonDart.Item")), 1)),
    RAGEBRICK(xAbilities.config.getConfig().getString("RageBrick.Name"),
            xAbilities.config.getConfig().getStringList("RageBrick.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("RageBrick.Item")), 1)),
    IMPULSE(xAbilities.config.getConfig().getString("ImpulseBomb.Name"),
            xAbilities.config.getConfig().getStringList("ImpulseBomb.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("ImpulseBomb.Item")), 1)),
    FAKEPEARL(xAbilities.config.getConfig().getString("FakePearl.Name"),
            xAbilities.config.getConfig().getStringList("FakePearl.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("FakePearl.Item")), 1)),
    ROCKET(xAbilities.config.getConfig().getString("Rocket.Name"),
            xAbilities.config.getConfig().getStringList("Rocket.Lore"),
            new ItemStack(Material.valueOf(xAbilities.config.getConfig().getString("Rocket.Item")), 1));

    private String display;
    private List<String> lore;
    public ItemStack stack;

    AbilityEvents(String display, List<String> lore, ItemStack stack) {
        this.display = display;
        this.lore = lore;
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public String getDisplay() {
        return chatUtil.chat(display);
    }

    public List<String> getLore() {
        return chatUtil.list(lore);
    }

    public boolean isSimilar(ItemStack toCompare) {
        return toCompare != null &&
                toCompare.getType().equals(stack.getType()) &&
                toCompare.hasItemMeta() &&
                toCompare.getItemMeta().hasDisplayName() &&
                toCompare.getItemMeta().getDisplayName().equals(chatUtil.chat(getDisplay())) &&
                toCompare.getItemMeta().hasLore() &&
                toCompare.getItemMeta().getLore().equals(chatUtil.list(getLore()));
    }

    public static void takeItem(Player player, ItemStack stack) {
        if (stack.getAmount() > 1) {
            stack.setAmount(stack.getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
    }

    public static void takeItem(ItemStack stack) {
        if (stack.getAmount() > 1) {
            stack.setAmount(stack.getAmount() - 1);
        } else {
            stack.setAmount(0);
        }
    }

    public static int getPots(Player player) {
        int pots = 0;
        ItemStack[] contents;
        for (int length = (contents = player.getInventory().getContents()).length, i = 0; i < length; ++i) {
            ItemStack stack = contents[i];
            if (stack != null && stack.getType() != null) {
                if (stack.getType() != Material.AIR) {
                    if (stack.getType() == Material.POTION) {
                        ++pots;
                    }
                }
            }
        }
        return pots;
    }
    
    public static boolean checkLocation(Location loc, int radius, int x, int z) {
        radius = radius + 1;
        int cornerx = x + radius;
        int cornerz = z + radius;
        int cornerx1 = x - radius;
        int cornerz1 = z - radius;
        if (loc.getBlockX() < cornerx && loc.getBlockX() > cornerx1) {
            if (loc.getBlockZ() < cornerz && loc.getBlockZ() > cornerz1) {
                return true;
            }
        }
        return false;
    }
}