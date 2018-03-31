package com.chattriggers.ctjs.minecraft.wrappers;

import com.chattriggers.ctjs.minecraft.objects.message.TextComponent;
import com.chattriggers.ctjs.minecraft.wrappers.objects.Block;
import com.chattriggers.ctjs.minecraft.wrappers.objects.Entity;
import com.chattriggers.ctjs.minecraft.wrappers.objects.Inventory;
import com.chattriggers.ctjs.minecraft.wrappers.objects.Item;
import lombok.Getter;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class Player extends Entity {
    @Getter
    private static Player instance;

    @Getter
    private EntityPlayerSP player;
    @Getter
    private Armor armor;

    public Player(EntityPlayerSP player) {
        super(player);
        instance = this;

        this.player = player;
        this.armor = new Armor();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        if (player == null) {
            player = Client.getMinecraft().thePlayer;
            entity = player;
        }
    }

    /**
     * Gets the player's camera pitch.
     *
     * @return the player's camera pitch
     */
    @Override
    public double getPitch() {
        return player == null ? 0 : MathHelper.wrapAngleTo180_float(player.rotationPitch);
    }

    /**
     * Gets the player's camera yaw.
     *
     * @return the player's camera yaw
     */
    @Override
    public double getYaw() {
        return player == null ? 0 : MathHelper.wrapAngleTo180_float(player.rotationYaw);
    }

    /**
     * Gets the player's username.
     *
     * @return the player's username
     */
    public String getName() {
        return Client.getMinecraft().getSession().getUsername();
    }

    /**
     * Gets the player's uuid.
     *
     * @return the player's uuid
     */
    public String getUUID() {
        return Client.getMinecraft().getSession().getProfile().getId().toString();
    }

    /**
     * Gets the player's hp.
     *
     * @return the player's hp
     */
    public float getHP() {
        return player == null ? 0 : player.getHealth();
    }

    /**
     * Gets the player's hunger.
     *
     * @return the player's hunger
     */
    public int getHunger() {
        return player == null ? 0 : player.getFoodStats().getFoodLevel();
    }

    /**
     * Gets the player's saturation.
     *
     * @return the player's saturation
     */
    public float getSaturation() {
        return player == null ? 0 : player.getFoodStats().getSaturationLevel();
    }

    /**
     * Gets the player's armor points.
     *
     * @return the player's armor points
     */
    public int getArmorPoints() {
        return player == null ? 0 : player.getTotalArmorValue();
    }

    /**
     * Gets the player's air level.<br>
     * The returned value will be an integer. If the player is not taking damage, it
     * will be between 300 (not in water) and 0. If the player is taking damage, it
     * will be between -20 and 0, getting reset to 0 every time the player takes damage.
     *
     * @return the player's air level
     */
    public int getAirLevel() {
        return player == null ? 0 : player.getAir();
    }

    /**
     * Gets the player's xp level.
     *
     * @return the player's xp level
     */
    public int getXPLevel() {
        return player == null ? 0 : player.experienceLevel;
    }

    /**
     * Gets the player's xp progress.
     *
     * @return the player's xp progress
     */
    public float getXPProgress() {
        return player == null ? 0 : player.experience;
    }

    /**
     * Gets the biome the player is currently in.
     *
     * @return the biome name
     */
    public String getBiome() {
        if (player == null)
            return "";

        Chunk chunk = World.getWorld().getChunkFromBlockCoords(player.getPosition());
        BiomeGenBase biome = chunk.getBiome(player.getPosition(),
                World.getWorld().getWorldChunkManager());

        return biome.biomeName;
    }

    /**
     * Gets the light level at the player's current position.
     *
     * @return the light level at the player's current position
     */
    public int getLightLevel() {
        if (player == null || World.getWorld() == null) return 0;

        return World.getWorld().getLight(player.getPosition());
    }

    /**
     * Checks if if the player is sneaking.
     *
     * @return true if the player is sneaking, false otherwise
     */
    public boolean isSneaking() {
        return player != null && player.isSneaking();
    }

    /**
     * Checks if the player is sprinting.
     *
     * @return true if the player is sprinting, false otherwise
     */
    public boolean isSprinting() {
        return player != null && player.isSprinting();
    }

    /**
     * Checks if player can be pushed by water.
     *
     * @return true if the player is flying, false otherwise
     */
    public boolean isFlying() {
        return !(player != null && player.isPushedByWater());
    }

    /**
     * Checks if player is sleeping.
     *
     * @return true if the player is sleeping, false otherwise
     */
    public boolean isSleeping() {
        return player != null && player.isPlayerSleeping();
    }

    /**
     * Gets the direction the player is facing.
     *
     * @return The direction the player is facing, one of the four cardinal directions
     */
    public String facing() {
        if (player == null) {
            return "";
        }

        double yaw = getYaw();

        if (yaw < 22.5 && yaw > -22.5) {
            return "South";
        } else if (yaw < 67.5 && yaw > 22.5) {
            return "South West";
        } else if (yaw < 112.5 && yaw > 67.5) {
            return "West";
        } else if (yaw < 157.5 && yaw > 112.5) {
            return "North West";
        } else if (yaw < -157.5 || yaw > 157.5) {
            return "North";
        } else if (yaw > -157.5 && yaw < -112.5) {
            return "North East";
        } else if (yaw > -112.5 && yaw < -67.5) {
            return "East";
        } else if (yaw > -67.5 && yaw < -22.5) {
            return "South East";
        }

        return "";
    }

    /**
     * Gets the player's active potion effects.
     *
     * @return The player's active potion effects.
     */
    public String[] getActivePotionEffects() {
        if (player == null) return new String[]{};

        ArrayList<String> effects = new ArrayList<>();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            effects.add(effect.toString());
        }

        return effects.toArray(new String[effects.size()]);
    }

    /**
     * Gets the current object that the player is looking at,
     * whether that be a block or an entity. Returns an air block when not looking
     * at anything.
     *
     * @return the {@link Block} or {@link Entity} being looked at
     */
    public Object lookingAt() {
        if (player == null
                || World.getWorld() == null
                || Client.getMinecraft().objectMouseOver == null)
            return new Block(0);

        MovingObjectPosition mop = Client.getMinecraft().objectMouseOver;

        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos pos = mop.getBlockPos();
            return new Block(World.getWorld().getBlockState(pos).getBlock()).setBlockPos(pos);
        } else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            return new Entity(mop.entityHit);
        } else {
            return new Block(0);
        }
    }

    /**
     * Gets the player's currently held item.
     *
     * @return the item
     */
    public Item getHeldItem() {
        return new Item(player.inventory.getCurrentItem());
    }

    /**
     * Gets the inventory of the player, i.e. the inventory accessed by 'e'.
     *
     * @return the player's inventory
     */
    public Inventory getInventory() {
        return new Inventory(player.inventory);
    }

    /**
     * Gets the display name for the player,
     * i.e. the name shown in tab list and in the player's nametag.
     * @return the display name
     */
    public TextComponent getDisplayName() {
        return new TextComponent(getPlayerName(getPlayerInfo()));
    }

    /**
     * Sets the name for this player shown in tab list
     *
     * @param textComponent the new name to display
     */
    public void setTabDisplayName(TextComponent textComponent) {
        getPlayerInfo().setDisplayName(textComponent.getChatComponentText());
    }

    private String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null
                ? networkPlayerInfoIn.getDisplayName().getFormattedText()
                : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    public NetworkPlayerInfo getPlayerInfo() {
        return Client.getMinecraft().getNetHandler().getPlayerInfo(player.getUniqueID());
    }

    /**
     * Gets the inventory the user currently has open, i.e. a chest.
     *
     * @return the currently opened inventory
     */
    public Inventory getOpenedInventory() {
        return new Inventory(player.openContainer);
    }

    private class Armor {
        /**
         * @return the item in the player's helmet slot
         */
        public Item getHelmet() {
            return getInventory().getStackInSlot(39);
        }

        /**
         * @return the item in the player's chestplate slot
         */
        public Item getChestplate() {
            return getInventory().getStackInSlot(38);
        }

        /**
         * @return the item in the player's leggings slot
         */
        public Item getLeggings() {
            return getInventory().getStackInSlot(37);
        }

        /**
         * @return the item in the player's boots slot
         */
        public Item getBoots() {
            return getInventory().getStackInSlot(36);
        }
    }
}
