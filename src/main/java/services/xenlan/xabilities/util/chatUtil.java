package services.xenlan.xabilities.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class chatUtil {

    public static String chat(String message) {

        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static List<String> list(List<String> s){
        List<String> strings = new ArrayList<>();
        s.forEach(str -> strings.add(ChatColor.translateAlternateColorCodes('&', str)));
        return strings;
    }

}