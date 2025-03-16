package command_block.test.null_pointer_exception.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CrashProjectileEntity extends PersistentProjectileEntity {

    public CrashProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, World world) {
        super(type, world);
    }

    public CrashProjectileEntity(World world, LivingEntity owner) {
        super(EntityType.ARROW, owner, world); // 使用箭的实体类型
        throw new NullPointerException(); // 触发崩溃
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY; // 弹射物消失时无掉落物
    }
}
