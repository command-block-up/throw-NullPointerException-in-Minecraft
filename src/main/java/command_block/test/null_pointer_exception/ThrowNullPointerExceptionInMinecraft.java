package command_block.test.null_pointer_exception;

import command_block.test.null_pointer_exception.entity.CrashProjectileEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThrowNullPointerExceptionInMinecraft implements ModInitializer {
	public static final String MOD_ID = "throw_null-pointer-exception_in_minecraft";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Item NullPointerException = new Item(new FabricItemSettings().maxCount(1));
	public static final EntityType<CrashProjectileEntity> CRASH_PROJECTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "crash_projectile"),
			FabricEntityTypeBuilder.<CrashProjectileEntity>create(SpawnGroup.MISC, CrashProjectileEntity::new)
					.dimensions(EntityDimensions.fixed(0.5F, 0.5F))
					.build()
	);
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		// 注册物品
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "crash_item"), NullPointerException);

		// 监听物品使用事件
		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemStack stack = player.getStackInHand(hand);
			if (stack.isOf(NullPointerException)) {
				// 发射自定义弹射物
				CrashProjectileEntity projectile = new CrashProjectileEntity(world, player);
				projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 1.0F);
				world.spawnEntity(projectile);
			}
			return TypedActionResult.pass(stack);
		});
	}
}