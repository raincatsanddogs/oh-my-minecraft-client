package com.plusls.ommc.mixin.feature.betterSneaking;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.plusls.ommc.game.Configs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.EntityCompat;

//#if MC > 12006
import org.spongepowered.asm.mixin.Shadow;
//#endif

@Mixin(Player.class)
public abstract class MixinPlayerEntity {
    @Unique
    private static final float ommc$MAX_STEP_HEIGHT = 1.2F;

    @Unique
    private static final float ommc$MIN_STEP_HEIGHT = 0.001F;

    @Unique
    private float ommc$original_step_height = 0.0F;

    //#if MC > 12006
    @Shadow
    protected abstract boolean canFallAtLeast(double d, double e, double f);
    //#endif

    @WrapOperation(
            method = "maybeBackOffFromEdge",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;maxUpStep()F"
            )
    )
    private float fakeStepHeight(Player instance, Operation<Float> original) {
        EntityCompat entityCompat = EntityCompat.of(instance);
        this.ommc$original_step_height = original.call(instance);

        if (!entityCompat.getLevel().isClientSide()) {
            return this.ommc$original_step_height;
        }

        if (Configs.realSneaking.getBooleanValue() && instance.isShiftKeyDown()) {
            return ommc$MIN_STEP_HEIGHT;
        }

        if (Configs.betterSneaking.getBooleanValue()) {
            return ommc$MAX_STEP_HEIGHT;
        }

        return this.ommc$original_step_height;
    }

    @WrapOperation(
            method = "maybeBackOffFromEdge",
            at = @At(
                    value = "INVOKE",
                    //#if MC > 12006
                    target = "Lnet/minecraft/world/entity/player/Player;canFallAtLeast(DDD)Z"
                    //#else
                    //$$ target = "Lnet/minecraft/world/level/Level;noCollision(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Z"
                    //#endif
            )
    )
    private boolean checkFallAtLava(
            //#if MC > 12006
            Player entity,
            double d,
            double e,
            double f,
            //#else
            //$$ Level level,
            //$$ Entity entity,
            //$$ AABB aabb,
            //#endif
            Operation<Boolean> original
    ) {
        //#if MC > 12006
        Level level = entity.level();
        //#endif

        boolean result = original.call(
                //#if MC > 12006
                entity,
                d,
                e,
                f
                //#else
                //$$ level,
                //$$ entity,
                //$$ aabb
                //#endif
        );

        if (!Configs.betterSneaking.getBooleanValue() || !level.isClientSide()) {
            return result;
        }

        //#if MC > 12006
        boolean originalResult = this.canFallAtLeast(d, e, this.ommc$original_step_height);
        //#else
        //$$ EntityCompat entityCompat = EntityCompat.of(entity);
        //$$ boolean originalResult = level.noCollision(entity, aabb.move(0, ommc$MAX_STEP_HEIGHT - this.ommc$original_step_height, 0));
        //#endif

        //#if MC > 12006
        if ((originalResult && !result) && level.getFluidState(entity.blockPosition().below()).getType() instanceof LavaFluid) {
        //#else
        //$$ EntityCompat entityCompat = EntityCompat.of(entity);
        //$$ if ((originalResult && !result) && level.getFluidState(entityCompat.getBlockPosition().below()).getType() instanceof LavaFluid) {
        //#endif
            return true;
        }

        return result;
    }
}
