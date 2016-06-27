package net.tmxx.signops.util;

import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tmxx
 * @version 1.0
 */
public class PotionEffectUtil {
    private static Map<String, PotionEffectType> potionEffectTypeMap = new HashMap<>();

    /**
     * As the bukkit function {@link org.bukkit.potion.PotionEffectType#getByName(String)}
     * doesn't work, we have to create one ourselves.
     *
     * @param name  The name of the potion effect type to get.
     * @return      The potion effect type with the specified name.
     */
    public static PotionEffectType getTypeByName( String name ) {
        return potionEffectTypeMap.get( name.toLowerCase() );
    }

    /**
     * Checks whether the potion effect type with the specified name exists.
     *
     * @param name  The name of the potion effect type to check for.
     * @return      Whether the type with the specified name exists.
     */
    public static boolean isPotionEffectType( String name ) {
        return potionEffectTypeMap.containsKey( name.toLowerCase() );
    }

    /**
     * Fills up the potion effect type map.
     */
    static {
        potionEffectTypeMap.put( "speed", PotionEffectType.SPEED );
        potionEffectTypeMap.put( "slowness", PotionEffectType.SLOW );
        potionEffectTypeMap.put( "haste", PotionEffectType.FAST_DIGGING );
        potionEffectTypeMap.put( "miningfatique", PotionEffectType.SLOW_DIGGING );
        potionEffectTypeMap.put( "strength", PotionEffectType.INCREASE_DAMAGE );
        potionEffectTypeMap.put( "instanthealth", PotionEffectType.HEAL );
        potionEffectTypeMap.put( "instantdamage", PotionEffectType.HARM );
        potionEffectTypeMap.put( "jumpboost", PotionEffectType.JUMP );
        potionEffectTypeMap.put( "nausea", PotionEffectType.CONFUSION );
        potionEffectTypeMap.put( "regeneration", PotionEffectType.REGENERATION );
        potionEffectTypeMap.put( "resistance", PotionEffectType.DAMAGE_RESISTANCE );
        potionEffectTypeMap.put( "fireresistance", PotionEffectType.FIRE_RESISTANCE );
        potionEffectTypeMap.put( "waterbreathing", PotionEffectType.WATER_BREATHING );
        potionEffectTypeMap.put( "invisibility", PotionEffectType.INVISIBILITY );
        potionEffectTypeMap.put( "blindness", PotionEffectType.BLINDNESS );
        potionEffectTypeMap.put( "nightvision", PotionEffectType.NIGHT_VISION );
        potionEffectTypeMap.put( "hunger", PotionEffectType.HUNGER );
        potionEffectTypeMap.put( "weakness", PotionEffectType.WEAKNESS );
        potionEffectTypeMap.put( "poison", PotionEffectType.POISON );
        potionEffectTypeMap.put( "wither", PotionEffectType.WITHER );
        potionEffectTypeMap.put( "healthboost", PotionEffectType.HEALTH_BOOST );
        potionEffectTypeMap.put( "absorption", PotionEffectType.ABSORPTION );
        potionEffectTypeMap.put( "saturation", PotionEffectType.SATURATION );
        potionEffectTypeMap.put( "glowing", PotionEffectType.GLOWING );
        potionEffectTypeMap.put( "levitation", PotionEffectType.LEVITATION );
        potionEffectTypeMap.put( "luck", PotionEffectType.LUCK );
        potionEffectTypeMap.put( "unluck", PotionEffectType.UNLUCK );
    }
}
