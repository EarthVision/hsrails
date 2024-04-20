package no.netb.mc.hsrails;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RedstoneRail;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;


public class MinecartListener implements Listener {

    /**
     * Default speed, in meters per tick. A tick is 0.05 seconds, thus 0.4 * 1/0.05 = 8 m/s
     */
    private static final double DEFAULT_SPEED_METERS_PER_TICK = 0.4d;

    private HashMap<Material, Double> materialAccelerationMap = new HashMap<>();
    private HashMap<Material, Double> materialDecelerationMap = new HashMap<>();

    public MinecartListener(HsRails plugin) {
        for (String key: plugin.getConfig().getConfigurationSection("boostBlock").getKeys(false)) {

        }

        for (String key: plugin.getConfig().getConfigurationSection("brakeBlock").getKeys(false)) {

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleMove(VehicleMoveEvent event) {

        if (event.getVehicle() instanceof Minecart) {
            Minecart cart = (Minecart) event.getVehicle();
            Location cartLocation = cart.getLocation();
            World cartsWorld = cart.getWorld();

            Block rail = cartsWorld.getBlockAt(cartLocation);
            Block blockBelow = cartsWorld.getBlockAt(cartLocation.add(0, -1, 0));

            if (rail.getType() == Material.POWERED_RAIL) {
                if (materialAccelerationMap.containsKey(blockBelow.getType())) {
                    cart.setMaxSpeed(DEFAULT_SPEED_METERS_PER_TICK * materialAccelerationMap.get(blockBelow.getType()));
                }
                else {
                    cart.setMaxSpeed(DEFAULT_SPEED_METERS_PER_TICK);
                }
                RedstoneRail railBlockData = (RedstoneRail) rail.getBlockData();
                if (!railBlockData.isPowered()
                        && materialDecelerationMap.containsKey(blockBelow.getType())) {
                    Vector cartVelocity = cart.getVelocity();
                    cartVelocity.multiply(materialDecelerationMap.get(blockBelow.getType()));
                    cart.setVelocity(cartVelocity);
                }
            }
        }
    }
}
