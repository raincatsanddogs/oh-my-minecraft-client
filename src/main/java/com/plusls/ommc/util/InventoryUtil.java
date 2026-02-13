package com.plusls.ommc.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

//#if MC <= 11605
//$$ import net.minecraft.world.entity.Mob;
//#endif

public class InventoryUtil {
    public static @NotNull EquipmentSlot getEquipmentSlotForItem(ItemStack itemStack) {
        //#if MC > 12006
        return EquipmentSlot.MAINHAND;
        //#elseif MC > 11605
        return LivingEntity.getEquipmentSlotForItem(itemStack);
        //#else
        //$$ return Mob.getEquipmentSlotForItem(itemStack);
        //#endif
    }
}
