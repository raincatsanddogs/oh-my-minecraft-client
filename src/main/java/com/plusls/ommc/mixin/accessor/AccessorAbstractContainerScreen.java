package com.plusls.ommc.mixin.accessor;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractContainerScreen.class)
public interface AccessorAbstractContainerScreen {
    //#if MC > 12006
    @Accessor("hoveredSlot")
    Slot getHoveredSlot();
    //#else

    @Invoker
    Slot invokeFindSlot(double d, double e);
    //#endif
}
