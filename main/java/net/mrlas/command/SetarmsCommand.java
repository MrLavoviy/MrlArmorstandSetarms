package net.mrlas.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SetarmsCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("setarms").then(CommandManager.argument("arms", BoolArgumentType.bool()).executes((context) -> {
            return execute((ServerCommandSource)context.getSource(), BoolArgumentType.getBool(context, "arms"));
        })));
    }
    public static int execute(ServerCommandSource source, boolean set) {
        PlayerEntity pl = source.getPlayer();
        List<Entity> es = source.getWorld().getOtherEntities(pl, new Box(pl.getX() - 5, pl.getY() - 5, pl.getZ() - 5,
                pl.getX() + 5, pl.getY() + 5, pl.getZ() + 5));
        int y = -1;
        double x = 10, z = 0;
        for (int i = 0; i < es.size(); i++) {
            if (es.get(i) instanceof ArmorStandEntity) {
                z = dist(pl.getPos(), es.get(i).getPos());
                if (z < x) {
                    x = z; y = i;
                }
            }
        } if (y != -1) {
            ((ArmorStandEntity)es.get(y)).setShowArms(set);
        }
        return 1;
    }

    public static double dist(Vec3d pos0, Vec3d pos1) {
        Vec3d pos2 = new Vec3d(pos1.x - pos0.x, pos1.y - pos0.y, pos1.z - pos0.z);
        if (pos2.x < 0) {
            pos2 = new Vec3d(-pos2.x, pos2.y, pos2.z);
        } if (pos2.y < 0) {
            pos2 = new Vec3d(pos2.x, -pos2.y, pos2.z);
        } if (pos2.z < 0) {
            pos2 = new Vec3d(pos2.x, pos2.y, -pos2.z);
        }
        return Math.sqrt(Math.pow(pos2.x, 2) + Math.pow(pos2.y, 2) + Math.pow(pos2.z, 2));
    }
}
